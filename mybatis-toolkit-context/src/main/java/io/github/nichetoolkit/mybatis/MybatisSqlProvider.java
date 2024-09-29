package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.builder.SqlBuilder;
import io.github.nichetoolkit.mybatis.consts.SQLConstants;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisIdentityLackError;
import io.github.nichetoolkit.mybatis.error.MybatisUnrealizedLackError;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.RestReckon;
import io.github.nichetoolkit.rest.actuator.ConsumerActuator;
import io.github.nichetoolkit.rest.stream.RestCollectors;
import io.github.nichetoolkit.rest.stream.RestStream;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public interface MybatisSqlProvider extends MybatisProviderResolver {

    Logger logger = LoggerFactory.getLogger(MybatisSqlProvider.class);


    <I> String provide(@Nullable String tablename, MybatisTable table, Map<Integer, List<I>> identitySliceMap, MybatisSqlScript sqlScript) throws RestException;

    static <I> String providing(ProviderContext providerContext, @Nullable String tablename, I id, MybatisSqlProvider sqlProvider) throws RestException {
        return providing(providerContext, tablename, id, table -> {}, sqlProvider);
    }

    static <I> String providing(ProviderContext providerContext, @Nullable String tablename, I id, ConsumerActuator<MybatisTable> actuator, MybatisSqlProvider sqlProvider) throws RestException {
        Object identity = MybatisProviderResolver.resolveParameter(id);
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            actuator.actuate(table);
            if (table.isCustomIdentity()) {
                identityValue(table, identity);
            }
            return sqlProvider.provide(tablename, table, Collections.emptyMap(), sqlScript);
        });
    }

    static <I> String providing(ProviderContext providerContext, @Nullable String tablename, Collection<I> idList, MybatisSqlProvider sqlProvider) throws RestException {
        return providing(providerContext, tablename, idList, table -> {}, sqlProvider);
    }

    static <I> String providing(ProviderContext providerContext, @Nullable String tablename, Collection<I> idList, ConsumerActuator<MybatisTable> actuator, MybatisSqlProvider sqlProvider) throws RestException {
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            actuator.actuate(table);
            Map<Integer, List<I>> identitySliceMap = Collections.emptyMap();
            if (table.isCustomIdentity()) {
                RestStream.stream(idList).forEach(id -> identityValue(table, id));
                identitySliceMap = identitySlice(table, idList);
            }
            return sqlProvider.provide(tablename, table, identitySliceMap, sqlScript);
        });
    }

    MybatisSqlProvider ALERT_FIELD_BY_ID = new MybatisSqlProvider() {
        @Override
        public <I> String provide(@Nullable String tablename, MybatisTable table, Map<Integer, List<I>> identitySliceMap, MybatisSqlScript sqlScript) throws RestException {
            return alertFieldUpdateSql(tablename, table, sqlScript) + identityWhereSql(table, sqlScript);
        }
    };

    MybatisSqlProvider ALERT_FIELD_ALL = new MybatisSqlProvider() {
        @Override
        public <I> String provide(@Nullable String tablename, MybatisTable table, Map<Integer, List<I>> identitySliceMap, MybatisSqlScript sqlScript) throws RestException {
            return alertFieldUpdateSql(tablename, table, sqlScript) + identitiesWhereSql(identitySliceMap, table, sqlScript);
        }
    };

    MybatisSqlProvider ALERT_BY_ID = new MybatisSqlProvider() {
        @Override
        public <I> String provide(@Nullable String tablename, MybatisTable table, Map<Integer, List<I>> identitySliceMap, MybatisSqlScript sqlScript) throws RestException {
            return alertUpdateSql(tablename, table, sqlScript) + identityWhereSql(table, sqlScript);
        }
    };

    MybatisSqlProvider ALERT_ALL = new MybatisSqlProvider() {
        @Override
        public <I> String provide(@Nullable String tablename, MybatisTable table, Map<Integer, List<I>> identitySliceMap, MybatisSqlScript sqlScript) throws RestException {
            return alertUpdateSql(tablename, table, sqlScript) + identitiesWhereSql(identitySliceMap, table, sqlScript);
        }
    };

    MybatisSqlProvider DELETE_BY_LINK_ID = new MybatisSqlProvider() {
        @Override
        public <I> String provide(@Nullable String tablename, MybatisTable table, Map<Integer, List<I>> identitySliceMap, MybatisSqlScript sqlScript) throws RestException {
            return alertFieldUpdateSql(tablename, table, sqlScript) + identityWhereSql(table, sqlScript);
        }
    };

    MybatisSqlProvider DELETE_BY_LINK_IDS = new MybatisSqlProvider() {
        @Override
        public <I> String provide(@Nullable String tablename, MybatisTable table, Map<Integer, List<I>> identitySliceMap, MybatisSqlScript sqlScript) throws RestException {
            return alertFieldUpdateSql(tablename, table, sqlScript) + identitiesWhereSql(identitySliceMap, table, sqlScript);
        }
    };




    static void identityValue(MybatisTable table, Object id) throws RestException {
        Boolean logicalAnd = RestStream.stream(table.identityColumns()).map(MybatisColumn::getField)
                .map(mybatisField -> GeneralUtils.isNotEmpty(mybatisField.get(id)))
                .collect(RestCollectors.logicalOr());
        String message = "the field values of identity can not all be empty, " + table.getIdentity().getName();
        OptionalUtils.ofFalseError(logicalAnd, message, MybatisIdentityLackError::new);
    }

    static <I> Map<Integer, List<I>> identitySlice(MybatisTable table, Collection<I> idList) throws RestException {
        List<MybatisColumn> mybatisColumns = table.identityColumns();
        List<MybatisField> mybatisFields = RestStream.stream(table.identityColumns()).map(MybatisColumn::getField).collect(RestCollectors.toList());
        /*
         * mybatisFields: {a,b,c}, index: 0,1,2 indexValue: 1,2,4
         * 0: {}, 1: {a}, 2: {b}, 3: {a,b}, 4: {c} 5: {a,c}, 6: {b,c}, 7: {a,b,c}
         */
        return RestStream.stream(idList).collect(RestCollectors.groupingBy(id -> {
            int indexValue = 0;
            for (int index = 0; index < mybatisFields.size(); index++) {
                MybatisField mybatisField = mybatisFields.get(index);
                if (GeneralUtils.isValid(mybatisField.get(id))) {
                    indexValue = indexValue | -(-1 << index);
                }
            }
            return indexValue;
        }));
    }


    default String alertUpdateSql(String tablename, MybatisTable table, MybatisSqlScript sqlScript) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder().update().append(table.tablename(tablename))
                .set().eq("update_time","now()",null);
        return "UPDATE " + table.tablename(tablename)
                + " SET update_time = now(),"
                + table.getAlertColumn().columnEqualsKey();
    }


    default String alertFieldUpdateSql(String tablename, MybatisTable table, MybatisSqlScript sqlScript) throws RestException {
        StringBuilder sqlBuilder = new StringBuilder(" UPDATE ");
        sqlBuilder.append(table.tablename(tablename));
        sqlBuilder.append(" SET update_time = now()");
        String fieldIfTest = sqlScript.ifTest("field != null and field != ''", () -> ",${field} = ${key}");
        sqlBuilder.append(fieldIfTest);
        return sqlBuilder.toString();
    }

    static String saveUpsetSql(@Nullable String tablename, MybatisTable table) {
        DatabaseType databaseType = MybatisProviderSupport.DATABASE_TYPE;
        List<MybatisColumn> updateColumns = table.updateColumns();
        boolean doNothing = false;
        String doUpdateSql = "";
        if (GeneralUtils.isEmpty(updateColumns)) {
            doNothing = true;
        } else {
            String dynamicTablename = table.tablename(tablename);
            doUpdateSql = table.updateColumns().stream().map(column -> column.excluded(dynamicTablename)).collect(Collectors.joining(", "));
        }
        String upsetSql;
        switch (databaseType) {
            case MARIADB:
            case SQLSEVER:
            case ORACLE:
            case SQLITE:
            case H2:
            case HSQLDB:
            case REDSHIFT:
            case CASSANDRA:
                throw new MybatisUnrealizedLackError("the function is unrealized of this database type: " + databaseType.getKey());
            case GAUSSDB:
            case MYSQL:
                if (doNothing) {
                    upsetSql = " ON DUPLICATE KEY DO NOTHING ";
                } else {
                    upsetSql = " ON DUPLICATE KEY UPDATE ";
                }
                break;
            case POSTGRESQL:
            default:
                if (doNothing) {
                    upsetSql = " ON CONFLICT (" + table.identityColumnList() + ") DO NOTHING ";
                } else {
                    upsetSql = " ON CONFLICT (" + table.identityColumnList() + ") DO UPDATE SET ";
                }
                break;
        }
        return upsetSql + doUpdateSql;
    }

    default String identityWhereSql(MybatisTable table, MybatisSqlScript sqlScript) throws RestException {
        if (table.isCustomIdentity()) {
            return customIdentityWhereSql(table, sqlScript);
        } else {
            return defaultIdentityWhereSql(table, sqlScript);
        }
    }

    default <I> String identitiesWhereSql(Map<Integer, List<I>> identitySliceMap, MybatisTable table, MybatisSqlScript sqlScript) throws RestException {
        if (table.isCustomIdentity()) {
            return customIdentitiesWhereSql(identitySliceMap, table, sqlScript);
        } else {
            return defaultIdentitiesWhereSql(table, sqlScript);
        }
    }

    default String defaultIdentityWhereSql(MybatisTable table, MybatisSqlScript sqlScript) throws RestException {
        StringBuilder sqlBuilder = new StringBuilder(" WHERE ");
        RestOptional<MybatisColumn> columnOptional = RestStream.stream(table.identityColumns()).findFirst();
        columnOptional.ifNullPresent(column -> sqlBuilder.append(table.identityColumnEqualsProperty()));
        return sqlBuilder.toString();
    }

    default String defaultIdentitiesWhereSql(MybatisTable table, MybatisSqlScript sqlScript) throws RestException {
        StringBuilder sqlBuilder = new StringBuilder(" WHERE ");
        RestOptional<MybatisColumn> columnOptional = RestStream.stream(table.identityColumns()).findFirst();
        columnOptional.ifNullPresent(column -> {
            sqlBuilder.append("id IN ");
            String foreach = sqlScript.foreach("idList", "id", ", ", "(", ")", column::variable);
            sqlBuilder.append(table.identityColumnEqualsProperty());
        });
        return sqlBuilder.toString();
    }

    default String customIdentityWhereSql(MybatisTable table, MybatisSqlScript sqlScript) throws RestException {
        StringBuilder sqlBuilder = new StringBuilder(" WHERE 1=1 ");
        sqlBuilder.append(SQLConstants.LINEFEED);
        String identityTestSql = RestStream.stream(table.identityColumns())
                .map(column -> sqlScript.ifTest(column.notEmptyTest("id."),
                        () -> " AND " + column.columnEqualsProperty("id.")))
                .collect(RestCollectors.joining(SQLConstants.LINEFEED));
        sqlBuilder.append(identityTestSql);
        return sqlBuilder.toString();
    }

    /*
     * mybatisFields: {a,b,c}, index: 0,1,2 indexValue: 1,2,4
     * 0: {}, 1: {a}, 2: {b}, 3: {a,b}, 4: {c} 5: {a,c}, 6: {b,c}, 7: {a,b,c}
     * SELECT template_pk1, template_pk2, name, description, time, update_time, create_time, logic_sign
     * FROM ntr_template WHERE 1=1 AND ((template_pk1) IN (('1' )) OR (template_pk2) IN (('3' )))
     */
    default <I> String customIdentitiesWhereSql(Map<Integer, List<I>> identitySliceMap, MybatisTable table, MybatisSqlScript sqlScript) throws RestException {
        SqlBuilder sqlBuilder = new SqlBuilder(" WHERE 1=1 ");
        sqlBuilder.append(SQLConstants.LINEFEED);
        sqlBuilder.append("AND (");
        for (Map.Entry<Integer, List<I>> entry : identitySliceMap.entrySet()) {
            Integer key = entry.getKey();
            List<I> valueList = entry.getValue();
            if (GeneralUtils.isNotEmpty(key) && GeneralUtils.isNotEmpty(valueList)) {
                List<Number> indices = RestReckon.denexNumber(key);
                if (GeneralUtils.isNotEmpty(indices)) {
                    List<MybatisColumn> mybatisColumns = RestStream.stream(indices)
                            .map(index -> table.identityColumns().get(index.intValue()))
                            .collect(RestCollectors.toList());
                    String fieldSql = RestStream.stream(mybatisColumns).map(MybatisColumn::columnName).collect(RestCollectors.joining(","));
                    boolean isMultiColumns = mybatisColumns.size() > 1;
                    if (isMultiColumns) {
                        sqlBuilder.append("(");
                        sqlBuilder.append(fieldSql).append(") IN (");
                    } else {
                        sqlBuilder.append(fieldSql).append(" IN (");
                    }
                    RestStream.stream(valueList).forEach(value -> {
                        if (isMultiColumns) {
                            sqlBuilder.append("(");
                        }
                        RestStream.stream(mybatisColumns).map(MybatisColumn::getField).forEach(field -> {
                            Object indexValue = field.get(value);
                            sqlBuilder.value(indexValue).append(",");
                        });
                        sqlBuilder.deleteLastChar();
                        if (isMultiColumns) {
                            sqlBuilder.append("),");
                        } else {
                            sqlBuilder.append(",");
                        }
                    });
                    sqlBuilder.deleteLastChar().append(")");
                }
                sqlBuilder.or();
            }
        }
        sqlBuilder.delete(sqlBuilder.length() - 4, sqlBuilder.length()).append(")");
        return sqlBuilder.toString();
    }

    interface SimpleSqlProvider extends MybatisSqlProvider {

        @Override
        default <I> String provide(@Nullable String tablename, MybatisTable mybatisTable, Map<Integer, List<I>> identitySliceMap, MybatisSqlScript sqlScript) throws RestException {
            return provide(tablename, mybatisTable, identitySliceMap, sqlScript, this);
        }

        <I> String provide(@Nullable String tablename, MybatisTable mybatisTable, Map<Integer, List<I>> identitySliceMap, MybatisSqlScript sqlScript, MybatisSqlProvider sqlProvider) throws RestException;

    }


}
