package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.builder.SqlBuilder;
import io.github.nichetoolkit.mybatis.consts.EntityConstants;
import io.github.nichetoolkit.mybatis.consts.SQLConstants;
import io.github.nichetoolkit.mybatis.consts.ScriptConstants;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisIdentityLackError;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.rest.RestException;
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
import java.util.stream.Collectors;

public interface MybatisSqlProvider {

    DatabaseType databaseType();

    MybatisSqlSupply SELECT_SQL_SUPPLY = (tablename, table, whereSql, sqlScript) ->
            SqlBuilder.sqlBuilder()
                    .select().append(table.sqlOfSelectColumns())
                    .from().append(table.tablename(tablename))
                    .where().append(whereSql).toString();


    @SuppressWarnings("unchecked")
    static <I> Object reviseParameter(I parameter) throws RestException {
        Class<?> parameterClass = parameter.getClass();
        if (Map.class.isAssignableFrom(parameterClass)) {
            Map<String, ?> param = (Map<String, ?>) parameter;
            Optional<?> firstParam = param.values().stream().findFirst();
            return firstParam.orElseThrow(MybatisParamErrorException::new);
        } else {
            return parameter;
        }
    }

    static <I> String providing(ProviderContext providerContext, @Nullable String tablename, I idParameter, MybatisSqlSupply sqlSupply) throws RestException {
        return providing(providerContext, tablename, idParameter, table -> {
        }, sqlSupply);
    }

    static <I> String providing(ProviderContext providerContext, @Nullable String tablename, I idParameter, ConsumerActuator<MybatisTable> actuator, MybatisSqlSupply sqlSupply) throws RestException {
        Object identity = reviseParameter(idParameter);
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            actuator.actuate(table);
            String whereSql;
            if (table.isSpecialIdentity()) {
                valueOfIdentity(table, identity);
                whereSql = specialWhereSqlOfId(identity, table, sqlScript);
            } else {
                whereSql = defaultWhereSqlOfId(table, sqlScript);
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
            if (table.isSpecialIdentity()) {
                RestStream.stream(idList).forEach(id -> valueOfIdentity(table, id));
                Map<Integer, List<I>> identities = sliceOfIdentity(table, idList);
                whereSql = specialWhereSqlOfIds(identities, table, sqlScript);
            } else {
                whereSql = defaultWhereSqlOfIds(table, sqlScript);
            }
            return sqlSupply.supply(tablename, table, whereSql, sqlScript);
        });
    }

    static <I> String providing(ProviderContext providerContext, @Nullable String tablename, String whereSqlParameter, MybatisSqlSupply sqlProvider) throws RestException {
        return providing(providerContext, tablename, whereSqlParameter, table -> {
        }, sqlProvider);
    }

    static <I> String providing(ProviderContext providerContext, @Nullable String tablename, String whereSqlParameter, ConsumerActuator<MybatisTable> actuator, MybatisSqlSupply sqlSupply) throws RestException {
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            actuator.actuate(table);
            String whereSql = whereSqlParameter;
            if (whereSql.startsWith(SQLConstants.AND_MATCH)) {
                whereSql = whereSql.substring(SQLConstants.AND_MATCH.length());
            }
            whereSql = ScriptConstants.CDATA_LT + whereSql + ScriptConstants.CDATA_GT;
            return sqlSupply.supply(tablename, table, whereSql, sqlScript);
        });
    }

    @SuppressWarnings("Duplicates")
    static <I> String providing(ProviderContext providerContext, @Nullable String tablename, String name, String logic, ConsumerActuator<MybatisTable> actuator, MybatisSqlSupply sqlSupply) throws RestException {
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            actuator.actuate(table);
            String nameSql = Optional.ofNullable(table.fieldColumn(EntityConstants.NAME)).map(MybatisColumn::columnEqualsProperty).orElse(ScriptConstants.NAME_EQUALS_PROPERTY);
            SqlBuilder sqlBuilder = new SqlBuilder(nameSql);
            Optional.ofNullable(table.getLogicColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnEqualsLogic()));
            return sqlSupply.supply(tablename, table, sqlBuilder.toString(), sqlScript);
        });
    }

    @SuppressWarnings("Duplicates")
    static <I> String providing(ProviderContext providerContext, @Nullable String tablename, String name, I idParameter, String logic, ConsumerActuator<MybatisTable> actuator, MybatisSqlSupply sqlSupply) throws RestException {
        Object identity = reviseParameter(idParameter);
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            actuator.actuate(table);
            String nameSql = Optional.ofNullable(table.fieldColumn(EntityConstants.NAME)).map(MybatisColumn::columnEqualsProperty).orElse(ScriptConstants.NAME_EQUALS_PROPERTY);
            SqlBuilder sqlBuilder = new SqlBuilder(nameSql);
            Optional.ofNullable(table.getLogicColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnEqualsLogic()));
            if (table.isSpecialIdentity()) {
                valueOfIdentity(table, identity);
                String identitySql = sqlOfIdentity(identity, table.identityColumns(), false);
                sqlBuilder.append(identitySql);
            } else {
                Optional.ofNullable(table.getIdentityColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnNotEqualsProperty()));
            }
            return sqlSupply.supply(tablename, table, sqlBuilder.toString(), sqlScript);
        });
    }

    @SuppressWarnings("Duplicates")
    static <E> String providing(ProviderContext providerContext, @Nullable String tablename, E entityParameter, String logic, ConsumerActuator<MybatisTable> actuator, MybatisSqlSupply sqlSupply) throws RestException {
        Object entity = reviseParameter(entityParameter);
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            actuator.actuate(table);
            SqlBuilder sqlBuilder = new SqlBuilder();
            List<MybatisColumn> uniqueColumns = table.uniqueColumns();
            String entitySql = table.uniqueColumns().stream()
                    .map(column -> column.columnEqualsProperty(EntityConstants.ENTITY))
                    .collect(Collectors.joining(SQLConstants.BLANK + SQLConstants.OR + SQLConstants.BLANK));
            if (uniqueColumns.size() == 1) {
                sqlBuilder.append(entitySql);
            } else {
                sqlBuilder.braceLt().append(entitySql).braceGt();
            }
            Optional.ofNullable(table.getLogicColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnEqualsLogic()));
            return sqlSupply.supply(tablename, table, sqlBuilder.toString(), sqlScript);
        });
    }

    @SuppressWarnings("Duplicates")
    static <E, I> String providing(ProviderContext providerContext, @Nullable String tablename, E entityParameter, I idParameter, String logic, ConsumerActuator<MybatisTable> actuator, MybatisSqlSupply sqlSupply) throws RestException {
        Object entity = reviseParameter(entityParameter);
        Object identity = reviseParameter(idParameter);
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            actuator.actuate(table);
            SqlBuilder sqlBuilder = new SqlBuilder();
            List<MybatisColumn> uniqueColumns = table.uniqueColumns();
            String entitySql = table.uniqueColumns().stream()
                    .map(column -> column.columnEqualsProperty(EntityConstants.ENTITY))
                    .collect(Collectors.joining(SQLConstants.BLANK + SQLConstants.OR + SQLConstants.BLANK));
            if (uniqueColumns.size() == 1) {
                sqlBuilder.append(entitySql);
            } else {
                sqlBuilder.braceLt().append(entitySql).braceGt();
            }
            Optional.ofNullable(table.getLogicColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnEqualsLogic()));
            if (table.isSpecialIdentity()) {
                valueOfIdentity(table, identity);
                String identitySql = sqlOfIdentity(identity, table.identityColumns(), false);
                sqlBuilder.append(identitySql);
            } else {
                Optional.ofNullable(table.getIdentityColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnNotEqualsProperty()));
            }
            return sqlSupply.supply(tablename, table, sqlBuilder.toString(), sqlScript);
        });
    }

    static void valueOfIdentity(MybatisTable table, Object id) throws RestException {
        Boolean logicalOr = RestStream.stream(table.identityColumns()).map(MybatisColumn::getField)
                .map(mybatisField -> GeneralUtils.isNotEmpty(mybatisField.get(id)))
                .collect(RestCollectors.logicalOr());
        String message = "The field values of identity can not all be empty, " + table.getIdentityType().getName();
        OptionalUtils.ofFalseError(logicalOr, message, MybatisIdentityLackError::new);
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

    static String defaultWhereSqlOfId(MybatisTable table, MybatisSqlScript sqlScript) throws RestException {
        return SqlBuilder.sqlBuilder().append(table.sqlOfIdentityColumn()).toString();
    }

    static String defaultWhereSqlOfIds(MybatisTable table, MybatisSqlScript sqlScript) throws RestException {
        return SqlBuilder.sqlBuilder().append(EntityConstants.IDENTITY).in().append(sqlScript.foreachOfIdList(() ->
                MybatisColumn.property(table.getIdentityType(), EntityConstants.IDENTITY))).toString();
    }

    static <I> String specialWhereSqlOfId(I identity, MybatisTable table, MybatisSqlScript sqlScript) throws RestException {
        return sqlOfIdentity(identity, table.identityColumns(), true);
    }

    static <I> String sqlOfIdentity(I identity, Collection<MybatisColumn> mybatisColumns, boolean isEquals) throws RestException {
        SqlBuilder sqlBuilder = new SqlBuilder();
        boolean isNotFirstValue = false;
        for (MybatisColumn mybatisColumn : mybatisColumns) {
            String columnName = mybatisColumn.columnName();
            MybatisField mybatisField = mybatisColumn.getField();
            Object fieldValue = mybatisField.get(identity);
            if (GeneralUtils.isNotEmpty(fieldValue)) {
                if (isNotFirstValue) {
                    sqlBuilder.and();
                } else {
                    isNotFirstValue = true;
                }
                sqlBuilder.append(columnName);
                if (isEquals) {
                    sqlBuilder.eq();
                } else {
                    sqlBuilder.neq();
                }
                sqlBuilder.value(fieldValue);
            }
        }
        sqlBuilder.deleteLastChar();
        return sqlBuilder.toString();
    }

    /*
     * mybatisFields: {a,b,c}, index: 0,1,2 indexValue: 1,2,4
     * 0: {}, 1: {a}, 2: {b}, 3: {a,b}, 4: {c} 5: {a,c}, 6: {b,c}, 7: {a,b,c}
     * SELECT template_pk1, template_pk2, name, description, time, update_time, create_time, logic_sign
     * FROM ntr_template WHERE 1=1 AND ((template_pk1) IN (('1' )) OR (template_pk2) IN (('3' )))
     */
    @SuppressWarnings("Duplicates")
    static <I> String specialWhereSqlOfIds(Map<Integer, List<I>> identitySliceMap, MybatisTable table, MybatisSqlScript sqlScript) throws RestException {
        if (GeneralUtils.isEmpty(identitySliceMap)) {
            return SqlBuilder.EMPTY;
        }
        SqlBuilder sqlBuilder = new SqlBuilder(SQLConstants.BRACE_LT);
        for (Map.Entry<Integer, List<I>> entry : identitySliceMap.entrySet()) {
            Integer key = entry.getKey();
            List<I> valueList = entry.getValue();
            if (GeneralUtils.isNotEmpty(key) && GeneralUtils.isNotEmpty(valueList)) {
                List<Number> indices = RestReckon.denexNumber(key);
                if (GeneralUtils.isNotEmpty(indices)) {
                    List<MybatisColumn> mybatisColumns = RestStream.stream(indices)
                            .map(index -> table.identityColumns().get(index.intValue()))
                            .collect(RestCollectors.toList());
                    String fieldSql = RestStream.stream(mybatisColumns).map(MybatisColumn::columnName).collect(RestCollectors.joining(SQLConstants.COMMA));
                    boolean isMultiColumns = mybatisColumns.size() > 1;
                    if (isMultiColumns) {
                        sqlBuilder.append(SQLConstants.BRACE_LT).append(fieldSql).append(SQLConstants.BRACE_GT).in().append(SQLConstants.BRACE_LT);
                    } else {
                        sqlBuilder.append(fieldSql).in().append(SQLConstants.BRACE_LT);
                    }
                    RestStream.stream(valueList).forEach(value -> {
                        if (isMultiColumns) {
                            sqlBuilder.append(SQLConstants.BRACE_LT);
                        }
                        RestStream.stream(mybatisColumns).map(MybatisColumn::getField).forEach(field -> {
                            Object indexValue = field.get(value);
                            sqlBuilder.value(indexValue).append(SQLConstants.COMMA);
                        });
                        sqlBuilder.deleteLastChar();
                        if (isMultiColumns) {
                            sqlBuilder.append(SQLConstants.BRACE_GT).append(SQLConstants.COMMA);
                        } else {
                            sqlBuilder.append(SQLConstants.COMMA);
                        }
                    });
                    sqlBuilder.deleteLastChar().append(SQLConstants.BRACE_GT);
                }
                sqlBuilder.or();
            }
        }
        sqlBuilder.delete(sqlBuilder.length() - 4, sqlBuilder.length()).append(SQLConstants.BRACE_GT);
        return sqlBuilder.toString();
    }

    default String updateOfAlertSql(String tablename, MybatisTable table, MybatisSqlScript sqlScript) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder().update().append(table.tablename(tablename))
                .set().eq("update_time", "now()", null);
        return "UPDATE " + table.tablename(tablename)
                + " SET update_time = now(),"
                + table.getAlertColumn().columnEqualsKey();
    }

//    default String updateOfAlertByFieldSql(String tablename, MybatisTable table, MybatisSqlScript sqlScript) throws RestException {
//        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder()
//                .update().append(table.tablename(tablename))
//                .set();
//
//        sqlBuilder.append(table.tablename(tablename));
//        sqlBuilder.append(" SET update_time = now()");
//        String fieldIfTest = sqlScript.ifTest("field != null and field != ''", () -> ",${field} = ${key}");
//        sqlBuilder.append(fieldIfTest);
//        return sqlBuilder.toString();
//    }
//
//    default String upsetOfSaveSql(@Nullable String tablename, MybatisTable table) throws RestException {
//        DatabaseType databaseType = databaseType();
//        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
//        RestOptional<List<MybatisColumn>> optionalColumns = RestOptional.ofEmptyable(table.updateColumns());
//        String dynamicTablename = table.tablename(tablename);
//        boolean isDoNothing = !optionalColumns.isPresent();
//        String upsetSql;
//        switch (databaseType) {
//            case MARIADB:
//            case SQLSEVER:
//            case ORACLE:
//            case SQLITE:
//            case H2:
//            case HSQLDB:
//            case REDSHIFT:
//            case CASSANDRA:
//                throw new MybatisUnrealizedLackError("the function is unrealized of this database type: " + databaseType.getKey());
//            case GAUSSDB:
//            case MYSQL:
//                if (isDoNothing) {
//                    sqlBuilder.append(" ON DUPLICATE KEY DO NOTHING ");
//                } else {
//                    sqlBuilder.append(" ON DUPLICATE KEY UPDATE ");
//                }
//                break;
//            case POSTGRESQL:
//            default:
//                if (isDoNothing) {
//                    upsetSql = " ON CONFLICT (" + table.sqlOfIdentityColumns() + ") DO NOTHING ";
//                } else {
//                    upsetSql = " ON CONFLICT (" + table.sqlOfIdentityColumns() + ") DO UPDATE SET ";
//                }
//                break;
//        }
//        optionalColumns.ifEmptyPresent(updateColumns -> {
//            String collect = RestStream.stream(updateColumns).map(column -> column.excluded(table.tablename(tablename)))
//                    .collect(RestCollectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
//            sqlBuilder.append(collect);
//        });
//        return sqlBuilder.toString();
//    }


}
