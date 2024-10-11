package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.builder.SqlBuilder;
import io.github.nichetoolkit.mybatis.consts.SQLConstants;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisIdentityLackError;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
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
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MybatisSqlProvider {

    DatabaseType databaseType();

    @SuppressWarnings("unchecked")
    static <I> Object provideParameter(I parameter) throws RestException {
        Class<?> parameterClass = parameter.getClass();
        if (Map.class.isAssignableFrom(parameterClass)) {
            Map<String, ?> param = (Map<String, ?>) parameter;
            Optional<?> firstParam = param.values().stream().findFirst();
            return firstParam.orElseThrow(MybatisParamErrorException::new);
        } else {
            return parameter;
        }
    }

    static <I> String providing(ProviderContext providerContext, @Nullable String tablename, I id, MybatisSqlSupply sqlSupply) throws RestException {
        return providing(providerContext, tablename, id, table -> {
        }, sqlSupply);
    }

    static <I> String providing(ProviderContext providerContext, @Nullable String tablename, I id, ConsumerActuator<MybatisTable> actuator, MybatisSqlSupply sqlSupply) throws RestException {
        Object identity = provideParameter(id);
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            actuator.actuate(table);
            String whereSql;
            if (table.isIdentity()) {
                valueOfIdentity(table, identity);
                whereSql = customIdentityWhereSql(table, sqlScript);
            } else {
                whereSql = defaultIdentityWhereSql(table, sqlScript);
            }
            return sqlSupply.supply(tablename, table, whereSql, sqlScript);
        });
    }

    static <I> String providing(ProviderContext providerContext, @Nullable String tablename, Collection<I> idList, MybatisSqlSupply sqlSupply) throws RestException {
        return providing(providerContext, tablename, idList, table -> {
        }, sqlSupply);
    }

    static <I> String providing(ProviderContext providerContext, @Nullable String tablename, Collection<I> idList, ConsumerActuator<MybatisTable> actuator, MybatisSqlSupply sqlSupply) throws RestException {
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            actuator.actuate(table);
            String whereSql;
            if (table.isIdentity()) {
                RestStream.stream(idList).forEach(id -> valueOfIdentity(table, id));
                Map<Integer, List<I>> identities = sliceOfIdentity(table, idList);
                whereSql = customIdentitiesWhereSql(identities, table, sqlScript);
            } else {
                whereSql = defaultIdentitiesWhereSql(table, sqlScript);
            }
            return sqlSupply.supply(tablename, table, whereSql, sqlScript);
        });
    }

    static <I> String providing(ProviderContext providerContext, @Nullable String tablename, String whereSql, MybatisSqlSupply sqlProvider) throws RestException {
        return providing(providerContext, tablename, whereSql, table -> {
        }, sqlProvider);
    }

    static <I> String providing(ProviderContext providerContext, @Nullable String tablename, String whereSql, ConsumerActuator<MybatisTable> actuator, MybatisSqlSupply sqlSupply) throws RestException {
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            actuator.actuate(table);
            return sqlSupply.supply(tablename, table, whereSql, sqlScript);
        });
    }

    static void valueOfIdentity(MybatisTable table, Object id) throws RestException {
        Boolean logicalAnd = RestStream.stream(table.identityColumns()).map(MybatisColumn::getField)
                .map(mybatisField -> GeneralUtils.isNotEmpty(mybatisField.get(id)))
                .collect(RestCollectors.logicalOr());
        String message = "the field values of identity can not all be empty, " + table.getIdentity().getName();
        OptionalUtils.ofFalseError(logicalAnd, message, MybatisIdentityLackError::new);
    }

    static <I> Map<Integer, List<I>> sliceOfIdentity(MybatisTable table, Collection<I> idList) throws RestException {
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

    default String updateOfAlertSql(String tablename, MybatisTable table, MybatisSqlScript sqlScript) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder().update().append(table.tablename(tablename))
                .set().eq("update_time", "now()", null);
        return "UPDATE " + table.tablename(tablename)
                + " SET update_time = now(),"
                + table.getAlertColumn().columnEqualsKey();
    }

    default String updateOfAlertByFieldSql(String tablename, MybatisTable table, MybatisSqlScript sqlScript) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder()
                .update().append(table.tablename(tablename))
                .set();

        sqlBuilder.append(table.tablename(tablename));
        sqlBuilder.append(" SET update_time = now()");
        String fieldIfTest = sqlScript.ifTest("field != null and field != ''", () -> ",${field} = ${key}");
        sqlBuilder.append(fieldIfTest);
        return sqlBuilder.toString();
    }

    default String upsetOfSaveSql(@Nullable String tablename, MybatisTable table) throws RestException {
        DatabaseType databaseType = databaseType();
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
        RestOptional<List<MybatisColumn>> optionalColumns = RestOptional.ofEmptyable(table.updateColumns());
        String dynamicTablename = table.tablename(tablename);
        boolean isDoNothing = !optionalColumns.isPresent();
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
                if (isDoNothing) {
                    sqlBuilder.append(" ON DUPLICATE KEY DO NOTHING ");
                } else {
                    sqlBuilder.append(" ON DUPLICATE KEY UPDATE ");
                }
                break;
            case POSTGRESQL:
            default:
                if (isDoNothing) {
                    upsetSql = " ON CONFLICT (" + table.sqlOfIdentityColumns() + ") DO NOTHING ";
                } else {
                    upsetSql = " ON CONFLICT (" + table.sqlOfIdentityColumns() + ") DO UPDATE SET ";
                }
                break;
        }
        optionalColumns.ifEmptyPresent(updateColumns -> {
            String collect = RestStream.stream(updateColumns).map(column -> column.excluded(table.tablename(tablename)))
                    .collect(RestCollectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
            sqlBuilder.append(collect);
        });
        return sqlBuilder.toString();
    }

    static String defaultIdentityWhereSql(MybatisTable table, MybatisSqlScript sqlScript) throws RestException {
        StringBuilder sqlBuilder = new StringBuilder(" WHERE ");
        RestOptional<MybatisColumn> columnOptional = RestStream.stream(table.identityColumns()).findFirst();
        columnOptional.ifNullPresent(column -> sqlBuilder.append(table.sqlOfIdentityColumn()));
        return sqlBuilder.toString();
    }

    static String defaultIdentitiesWhereSql(MybatisTable table, MybatisSqlScript sqlScript) throws RestException {
        StringBuilder sqlBuilder = new StringBuilder(" WHERE ");
        RestOptional<MybatisColumn> columnOptional = RestStream.stream(table.identityColumns()).findFirst();
        columnOptional.ifNullPresent(column -> {
            sqlBuilder.append("id IN ");
            String foreach = sqlScript.foreach("idList", "id", ", ", "(", ")", column::variable);
            sqlBuilder.append(table.sqlOfIdentityColumn());
        });
        return sqlBuilder.toString();
    }

    static String customIdentityWhereSql(MybatisTable table, MybatisSqlScript sqlScript) throws RestException {
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
    static <I> String customIdentitiesWhereSql(Map<Integer, List<I>> identitySliceMap, MybatisTable table, MybatisSqlScript sqlScript) throws RestException {
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

}
