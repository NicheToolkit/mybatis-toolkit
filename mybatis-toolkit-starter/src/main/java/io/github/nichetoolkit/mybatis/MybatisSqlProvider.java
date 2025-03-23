package io.github.nichetoolkit.mybatis;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import io.github.nichetoolkit.mybatis.builder.SqlBuilder;
import io.github.nichetoolkit.mybatis.consts.EntityConstants;
import io.github.nichetoolkit.mybatis.consts.SQLConstants;
import io.github.nichetoolkit.mybatis.consts.ScriptConstants;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.enums.ExcludedType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisUnsupportedErrorException;
import io.github.nichetoolkit.mybatis.fickle.FickleField;
import io.github.nichetoolkit.rest.*;
import io.github.nichetoolkit.rest.actuator.ConsumerActuator;
import io.github.nichetoolkit.rest.holder.ApplicationContextHolder;
import io.github.nichetoolkit.rest.stream.RestCollectors;
import io.github.nichetoolkit.rest.stream.RestStream;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import io.github.nichetoolkit.rice.enums.OperateType;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface MybatisSqlProvider {

    List<DatabaseType> databaseTypes();

    MybatisSqlSupply.SimpleSqlSupply SELECT_SQL_SUPPLY = (tablename, table, sqlBuilder) ->
            SqlBuilder.sqlBuilder()
                    .select().append(table.sqlOfSelectColumns())
                    .from().append(table.tablename(tablename))
                    .where().append(sqlBuilder).toString();

    MybatisSqlSupply.SimpleSqlSupply WHERE_SQL_SUPPLY = (tablename, table, sqlBuilder) ->
            SqlBuilder.sqlBuilder()
                    .select().append(table.sqlOfSelectColumns())
                    .from().append(table.tablename(tablename))
                    .append(sqlBuilder).toString();

    MybatisSqlSupply.EntrySqlSupply SAVE_SQL_SUPPLY = (tablename, table, keySqlBuilder, valueSqlBuilder) -> {
        boolean mysqlIgnoreInsert = MybatisSqlProviderHolder.mysqlIgnoreInsert();
        SqlBuilder savesSqlBuilder = SqlBuilder.sqlBuilder();
        if (mysqlIgnoreInsert) {
            savesSqlBuilder.insertIgnore();
        } else {
            savesSqlBuilder.insert();
        }
        return savesSqlBuilder.append(table.tablename(tablename))
                .braceLt().append(keySqlBuilder).braceGt()
                .values().append(valueSqlBuilder).toString();
    };

    MybatisSqlSupply.SimpleSqlSupply REMOVE_SQL_SUPPLY = (tablename, table, sqlBuilder) ->
            SqlBuilder.sqlBuilder()
                    .update().append(table.tablename(tablename))
                    .set().append(table.getLogicColumn().columnEqualsProperty())
                    .comma().append(table.sqlOfForceUpdateColumns())
                    .where().append(sqlBuilder).toString();

    MybatisSqlSupply.SimpleSqlSupply OPERATE_SQL_SUPPLY = (tablename, table, sqlBuilder) ->
            SqlBuilder.sqlBuilder()
                    .update().append(table.tablename(tablename))
                    .set().append(table.getOperateColumn().columnEqualsProperty())
                    .comma().append(table.sqlOfForceUpdateColumns())
                    .where().append(sqlBuilder).toString();

    MybatisSqlSupply.SimpleSqlSupply DELETE_SQL_SUPPLY = (tablename, table, sqlBuilder) ->
            SqlBuilder.sqlBuilder()
                    .delete().from().append(table.tablename(tablename))
                    .where().append(sqlBuilder).toString();

    MybatisSqlSupply.AlertSqlSupply ALERT_SQL_SUPPLY = (tablename, table, sqlBuilder, status) ->
            SqlBuilder.sqlBuilder()
                    .update().append(table.tablename(tablename))
                    .set().append(sqlOfStatus(table, status))
                    .comma().append(table.sqlOfForceUpdateColumns())
                    .where().append(sqlBuilder).toString();

    @SuppressWarnings("unchecked")
    static <P> Object reviseParameter(P parameter) throws RestException {
        Class<?> parameterClass = parameter.getClass();
        if (Map.class.isAssignableFrom(parameterClass)) {
            Map<String, ?> param = (Map<String, ?>) parameter;
            Optional<?> firstParam = param.values().stream().findFirst();
            return firstParam.orElseThrow(MybatisParamErrorException::new);
        } else {
            return parameter;
        }
    }

    static <S> String sqlOfStatus(MybatisTable table, S status) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
        if (table.isSpecialAlertness()) {
            valueOfParameter(table.alertnessColumns(), status, table.getAlertnessType());
            String statusSql = sqlOfColumns(status, table.alertnessColumns(), false, true);
            sqlBuilder.append(statusSql);
        } else {
            Optional.ofNullable(table.getAlertColumn()).ifPresent(column -> {
                if (RestKey.class.isAssignableFrom(status.getClass())) {
                    sqlBuilder.append(column.columnName()).eq().value(status);
                } else {
                    sqlBuilder.append(column.columnEqualsProperty());
                }
            });
        }
        return sqlBuilder.toString();
    }

    static void databaseTypeOfColumnsSql(String tablename, SqlBuilder sqlBuilder) throws RestException {
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        switch (databaseType) {
            case SQLITE:
            case GAUSSDB:
            case POSTGRESQL:
            case MYSQL:
                /* SELECT column_name FROM information_schema.columns WHERE table_name = '表名'; */
                sqlBuilder.select().append("column_name")
                        .from().append("information_schema.columns")
                        .where().append("table_name").eq().value(tablename);
                break;
            default:
                String message = "it is unsupported currently of the " + databaseType.getKey() + "database type.";
                throw new MybatisUnsupportedErrorException(databaseType.getKey(), "tableColumns", message);
        }
    }

    static void databaseTypeOfIndexColumnSql(String tablename, SqlBuilder sqlBuilder, RestField<?> field) throws RestException {
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        switch (databaseType) {
            case GAUSSDB:
            case POSTGRESQL:
                /* CREATE INDEX "IDX_NTR_FICKLE_ENTRY_TIME" ON "public"."ntr_fickle_entry" USING btree (
                      "time" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
                   ); */
//                break;
            case SQLITE:
            case MYSQL:
                /* CREATE INDEX `IDX_NTR_FICKLE_ENTRY_TIME` ON `表名` (`time`); */
                sqlBuilder.select().append("column_name")
                        .from().append("information_schema.columns")
                        .where().append("table_name").eq().value(tablename);
                break;
            default:
                String message = "it is unsupported currently of the " + databaseType.getKey() + "database type.";
                throw new MybatisUnsupportedErrorException(databaseType.getKey(), "indexColumn", message);
        }
    }

    static void databaseTypeOfModifyColumnSql(String tablename, SqlBuilder sqlBuilder, RestField<?> field) throws RestException {
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        switch (databaseType) {
            case GAUSSDB:
            case POSTGRESQL:
                /* CREATE INDEX "IDX_NTR_FICKLE_ENTRY_TIME" ON "public"."ntr_fickle_entry" USING btree (
                      "time" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
                   ); */
//                break;
            case SQLITE:
            case MYSQL:
                /* CREATE INDEX `IDX_NTR_FICKLE_ENTRY_TIME` ON `表名` (`time`); */
//                sqlBuilder.select().append("column_name")
//                        .from().append("information_schema.columns")
//                        .where().append("table_name").eq().value(tablename);
//                break;
            default:
                String message = "it is unsupported currently of the " + databaseType.getKey() + "database type.";
                throw new MybatisUnsupportedErrorException(databaseType.getKey(), "indexColumn", message);
        }
    }


    static void databaseTypeOfAddColumnSql(String tablename, SqlBuilder sqlBuilder, RestField<?> field) throws RestException {
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        switch (databaseType) {
            case GAUSSDB:
            case POSTGRESQL:
                /* CREATE INDEX "IDX_NTR_FICKLE_ENTRY_TIME" ON "public"."ntr_fickle_entry" USING btree (
                      "time" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
                   ); */
//                break;
            case SQLITE:
            case MYSQL:
                /* CREATE INDEX `IDX_NTR_FICKLE_ENTRY_TIME` ON `表名` (`time`); */
//                sqlBuilder.select().append("column_name")
//                        .from().append("information_schema.columns")
//                        .where().append("table_name").eq().value(tablename);
//                break;
            default:
                String message = "it is unsupported currently of the " + databaseType.getKey() + "database type.";
                throw new MybatisUnsupportedErrorException(databaseType.getKey(), "indexColumn", message);
        }
    }

    static void databaseTypeOfRefreshColumnSql(String tablename, SqlBuilder sqlBuilder, RestField<?> field) throws RestException {
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        switch (databaseType) {
            case GAUSSDB:
            case POSTGRESQL:
                /* CREATE INDEX "IDX_NTR_FICKLE_ENTRY_TIME" ON "public"."ntr_fickle_entry" USING btree (
                      "time" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
                   ); */
//                break;
            case SQLITE:
            case MYSQL:
                /* CREATE INDEX `IDX_NTR_FICKLE_ENTRY_TIME` ON `表名` (`time`); */
//                sqlBuilder.select().append("column_name")
//                        .from().append("information_schema.columns")
//                        .where().append("table_name").eq().value(tablename);
//                break;
            default:
                String message = "it is unsupported currently of the " + databaseType.getKey() + "database type.";
                throw new MybatisUnsupportedErrorException(databaseType.getKey(), "indexColumn", message);
        }
    }

    static void databaseTypeOfDropColumnSql(String tablename, SqlBuilder sqlBuilder, RestField<?> field) throws RestException {
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        switch (databaseType) {
            case GAUSSDB:
            case POSTGRESQL:
                /* CREATE INDEX "IDX_NTR_FICKLE_ENTRY_TIME" ON "public"."ntr_fickle_entry" USING btree (
                      "time" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
                   ); */
//                break;
            case SQLITE:
            case MYSQL:
                /* CREATE INDEX `IDX_NTR_FICKLE_ENTRY_TIME` ON `表名` (`time`); */
//                sqlBuilder.select().append("column_name")
//                        .from().append("information_schema.columns")
//                        .where().append("table_name").eq().value(tablename);
//                break;
            default:
                String message = "it is unsupported currently of the " + databaseType.getKey() + "database type.";
                throw new MybatisUnsupportedErrorException(databaseType.getKey(), "indexColumn", message);
        }
    }

    static String providingOfTablename(ProviderContext providerContext, @NonNull String tablename) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
        databaseTypeOfColumnsSql(tablename, sqlBuilder);
        return sqlBuilder.toString();
    }

    static String providingOfIndexColumn(ProviderContext providerContext, @NonNull String tablename, RestField<?> field) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
        databaseTypeOfIndexColumnSql(tablename, sqlBuilder,field);
        return sqlBuilder.toString();
    }

    static String providingOfModifyColumn(ProviderContext providerContext, @NonNull String tablename, RestField<?> field) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
        databaseTypeOfModifyColumnSql(tablename, sqlBuilder, field);
        return sqlBuilder.toString();
    }

    static String providingOfAddColumn(ProviderContext providerContext, @NonNull String tablename, RestField<?> field) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
        databaseTypeOfAddColumnSql(tablename, sqlBuilder, field);
        return sqlBuilder.toString();
    }

    static String providingOfRefreshColumn(ProviderContext providerContext, @NonNull String tablename, RestField<?> field) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
        databaseTypeOfRefreshColumnSql(tablename, sqlBuilder, field);
        return sqlBuilder.toString();
    }

    static String providingOfDropColumn(ProviderContext providerContext, @NonNull String tablename, RestField<?> field) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
        databaseTypeOfDropColumnSql(tablename, sqlBuilder, field);
        return sqlBuilder.toString();
    }

    static <I, S> String providingOfId(ProviderContext providerContext, @Nullable String tablename, I idParameter, S statusParameter, MybatisSqlSupply.AlertSqlSupply sqlSupply) throws RestException {
        Object status = reviseParameter(statusParameter);
        return providingOfId(providerContext, tablename, idParameter, table -> {
        }, (tablenameValue, tableValue, sqlBuilder) -> sqlSupply.supply(tablenameValue, tableValue, sqlBuilder, status));
    }

    static <I, S> String providingOfAll(ProviderContext providerContext, @Nullable String tablename, Collection<I> idList, S statusParameter, MybatisSqlSupply.AlertSqlSupply sqlSupply) throws RestException {
        Object status = reviseParameter(statusParameter);
        return providingOfAll(providerContext, tablename, idList, table -> {
        }, (tablenameValue, tableValue, sqlBuilder) -> sqlSupply.supply(tablenameValue, tableValue, sqlBuilder, status));
    }

    static <S> String providingOfWhere(ProviderContext providerContext, @Nullable String tablename, String whereSqlParameter, S statusParameter, MybatisSqlSupply.AlertSqlSupply sqlSupply) throws RestException {
        Object status = reviseParameter(statusParameter);
        return providingOfWhere(providerContext, tablename, whereSqlParameter, table -> {
        }, (tablenameValue, tableValue, sqlBuilder) -> sqlSupply.supply(tablenameValue, tableValue, sqlBuilder, status));
    }

    static <L, S> String providingOfLinkId(ProviderContext providerContext, @Nullable String tablename, L linkIdParameter, S statusParameter, MybatisSqlSupply.AlertSqlSupply sqlSupply) throws RestException {
        Object status = reviseParameter(statusParameter);
        return providingOfLinkId(providerContext, tablename, linkIdParameter, table -> {
        }, (tablenameValue, tableValue, sqlBuilder) -> sqlSupply.supply(tablenameValue, tableValue, sqlBuilder, status));
    }

    static <L, S> String providingOfLinkIdAll(ProviderContext providerContext, @Nullable String tablename, Collection<L> linkIdList, S statusParameter, MybatisSqlSupply.AlertSqlSupply sqlSupply) throws RestException {
        Object status = reviseParameter(statusParameter);
        return providingOfLinkIdAll(providerContext, tablename, linkIdList, table -> {
        }, (tablenameValue, tableValue, sqlBuilder) -> sqlSupply.supply(tablenameValue, tableValue, sqlBuilder, status));
    }

    @SuppressWarnings("Duplicates")
    static <L> String providingOfLinkId(ProviderContext providerContext, @Nullable String tablename, L linkIdParameter, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
        Object linkId = reviseParameter(linkIdParameter);
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
            if (table.isSpecialLinkage()) {
                valueOfParameter(table.linkageColumns(), linkId, table.getLinkageType());
                String linkageSql = sqlOfColumns(linkId, table.getLinkageColumns(), true, true);
                sqlBuilder.append(linkageSql);
            } else {
                Optional.ofNullable(table.getLinkColumn()).ifPresent(column -> sqlBuilder.append(column.columnEqualsProperty()));
            }
            return sqlSupply.supply(tablename, table, sqlBuilder);
        });
    }

    @SuppressWarnings("Duplicates")
    static <L> String providingOfLinkIdAll(ProviderContext providerContext, @Nullable String tablename, Collection<L> linkIdList, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
            if (table.isSpecialLinkage()) {
                RestStream.stream(linkIdList).forEach(linkId -> valueOfParameter(table.linkageColumns(), linkId, table.getLinkageType()));
                Map<Integer, List<L>> sliceOfColumnsMap = sliceOfColumns(table.linkageColumns(), linkIdList);
                sqlBuilder.append(sqlOfColumns(sliceOfColumnsMap, table.linkageColumns(), sqlScript));
            } else {
                sqlBuilder.append(table.getIdentityColumn().columnName()).in().braceLt();
                linkIdList.forEach(linkId -> sqlBuilder.value(linkId).comma());
                sqlBuilder.delete(sqlBuilder.length() - 2).braceGt();
            }
            return sqlSupply.supply(tablename, table, sqlBuilder);
        });
    }

    @SuppressWarnings("Duplicates")
    static String providingOfName(ProviderContext providerContext, @Nullable String tablename, String name, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            String nameSql = Optional.ofNullable(table.fieldColumn(EntityConstants.NAME)).map(MybatisColumn::columnEqualsProperty).orElse(ScriptConstants.NAME_EQUALS_PROPERTY);
            SqlBuilder sqlBuilder = new SqlBuilder(nameSql);
            if (table.isUseLogic()) {
                Optional.ofNullable(table.getLogicColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnEqualsProperty()));
            }
            if (table.isUseOperate()) {
                Optional.ofNullable(table.getOperateColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnEqualsValue(OperateType.REMOVE)));
                Optional.ofNullable(table.getOperateColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnEqualsValue(OperateType.DELETE)));
            }
            return sqlSupply.supply(tablename, table, sqlBuilder);
        });
    }

    @SuppressWarnings("Duplicates")
    static <I> String providingOfName(ProviderContext providerContext, @Nullable String tablename, String name, I idParameter, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
        Object identity = reviseParameter(idParameter);
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            SqlBuilder sqlBuilder = new SqlBuilder();
            if (table.isSpecialIdentity()) {
                valueOfParameter(table.identityColumns(), identity, table.getIdentityType());
                String identitySql = sqlOfColumns(identity, table.identityColumns(), true, false);
                sqlBuilder.append(identitySql);
            } else {
                Optional.ofNullable(table.getIdentityColumn()).ifPresent(column -> sqlBuilder.append(column.columnNotEqualsProperty()));
            }
            String nameSql = Optional.ofNullable(table.fieldColumn(EntityConstants.NAME)).map(MybatisColumn::columnEqualsProperty).orElse(ScriptConstants.NAME_EQUALS_PROPERTY);
            sqlBuilder.and().append(nameSql);
            if (table.isUseLogic()) {
                Optional.ofNullable(table.getLogicColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnEqualsProperty()));
            }
            if (table.isUseOperate()) {
                Optional.ofNullable(table.getOperateColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnEqualsValue(OperateType.REMOVE)));
                Optional.ofNullable(table.getOperateColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnEqualsValue(OperateType.DELETE)));
            }
            return sqlSupply.supply(tablename, table, sqlBuilder);
        });
    }

    @SuppressWarnings("Duplicates")
    static <E> String providingOfEntity(ProviderContext providerContext, @Nullable String tablename, E entityParameter, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
        Object entity = reviseParameter(entityParameter);
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            SqlBuilder sqlBuilder = new SqlBuilder();
            List<MybatisColumn> uniqueColumns = table.uniqueColumns();
            String entitySql = table.uniqueColumns().stream()
                    .map(column -> column.columnEqualsProperty(EntityConstants.ENTITY_PREFIX))
                    .collect(Collectors.joining(SQLConstants.BLANK + SQLConstants.OR + SQLConstants.BLANK));
            if (uniqueColumns.size() == 1) {
                sqlBuilder.append(entitySql);
            } else {
                sqlBuilder.braceLt().append(entitySql).braceGt();
            }
            if (table.isUseLogic()) {
                Optional.ofNullable(table.getLogicColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnEqualsProperty()));
            }
            if (table.isUseOperate()) {
                Optional.ofNullable(table.getOperateColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnEqualsValue(OperateType.REMOVE)));
                Optional.ofNullable(table.getOperateColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnEqualsValue(OperateType.DELETE)));
            }
            return sqlSupply.supply(tablename, table, sqlBuilder);
        });
    }

    @SuppressWarnings("Duplicates")
    static <E, I> String providingOfEntity(ProviderContext providerContext, @Nullable String tablename, E entityParameter, I idParameter, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
        Object identity = reviseParameter(idParameter);
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            SqlBuilder sqlBuilder = new SqlBuilder();
            if (table.isSpecialIdentity()) {
                valueOfParameter(table.identityColumns(), identity, table.getIdentityType());
                String identitySql = sqlOfColumns(identity, table.identityColumns(), true, false);
                sqlBuilder.append(identitySql);
            } else {
                Optional.ofNullable(table.getIdentityColumn()).ifPresent(column -> sqlBuilder.append(column.columnEqualsProperty()));
            }
            List<MybatisColumn> uniqueColumns = table.uniqueColumns();
            String entitySql = table.uniqueColumns().stream()
                    .map(column -> column.columnEqualsProperty(EntityConstants.ENTITY_PREFIX))
                    .collect(Collectors.joining(SQLConstants.BLANK + SQLConstants.OR + SQLConstants.BLANK));
            if (uniqueColumns.size() == 1) {
                sqlBuilder.and().append(entitySql);
            } else {
                sqlBuilder.and().braceLt().append(entitySql).braceGt();
            }
            if (table.isUseLogic()) {
                Optional.ofNullable(table.getLogicColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnEqualsProperty()));
            }
            if (table.isUseOperate()) {
                Optional.ofNullable(table.getOperateColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnEqualsValue(OperateType.REMOVE)));
                Optional.ofNullable(table.getOperateColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnEqualsValue(OperateType.DELETE)));
            }
            return sqlSupply.supply(tablename, table, sqlBuilder);
        });
    }

    @SuppressWarnings("Duplicates")
    static <E> String providingOfSave(ProviderContext providerContext, @Nullable String tablename, E entityParameter, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            String tableName = table.tablename(tablename);
            List<MybatisColumn> insertColumns = table.insertColumns();
            List<MybatisColumn> fickleKeyColumns = fickleOfEntityColumns(tableName, table, entityParameter);
            insertColumns.addAll(fickleKeyColumns);
            String sqlOfKeyInsert = insertColumns.stream().map(MybatisColumn::columnName).distinct().collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
            SqlBuilder keySqlBuilder = SqlBuilder.sqlBuilder(sqlOfKeyInsert);
            String sqlOfInsert = sqlOfInsert(table, fickleKeyColumns);
            SqlBuilder valueSqlBuilder = SqlBuilder.sqlBuilder(sqlOfInsert);
            databaseTypeOfSql(tablename, table, valueSqlBuilder, fickleKeyColumns, databaseType);
            return sqlSupply.supply(tablename, table, keySqlBuilder, valueSqlBuilder);
        });
    }


    static <E> String providingOfAllSave(ProviderContext providerContext, @Nullable String tablename, Collection<E> entityList, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            String tableName = table.tablename(tablename);
            List<MybatisColumn> insertColumns = table.insertColumns();
            List<MybatisColumn> fickleKeyColumns = fickleOfEntitiesColumns(tableName, table, entityList);
            insertColumns.addAll(fickleKeyColumns);
            String sqlOfKeyInsert = insertColumns.stream().map(MybatisColumn::columnName).distinct().collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
            SqlBuilder keySqlBuilder = SqlBuilder.sqlBuilder(sqlOfKeyInsert);
            String valueSqlOfInsert = sqlScript.foreach(EntityConstants.ENTITY_LIST, EntityConstants.ENTITY, SQLConstants.COMMA + SQLConstants.BLANK, () -> sqlOfInsert(table, fickleKeyColumns));
            SqlBuilder valueSqlBuilder = SqlBuilder.sqlBuilder(valueSqlOfInsert);
            databaseTypeOfSql(tableName, table, valueSqlBuilder, fickleKeyColumns, databaseType);
            return sqlSupply.supply(tablename, table, keySqlBuilder, valueSqlBuilder);
        });
    }

    static void databaseTypeOfSql(String tablename, MybatisTable table, SqlBuilder sqlBuilder, List<MybatisColumn> fickleColumns, DatabaseType databaseType) throws RestException {
        switch (databaseType) {
            case SQLITE:
            case GAUSSDB:
            case POSTGRESQL:
                sqlBuilder.append(insertOfSaveSql(tablename, table, fickleColumns, true));
                break;
            case MYSQL:
                sqlBuilder.append(insertOfSaveSql(tablename, table, fickleColumns, false));
                break;
            default:
                String message = "it is unsupported currently of the " + databaseType.getKey() + "database type.";
                throw new MybatisUnsupportedErrorException(databaseType.getKey(), "save", message);
        }
    }

    @SuppressWarnings("Duplicates")
    static <I> String providingOfId(ProviderContext providerContext, @Nullable String tablename, I idParameter, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
        Object identity = reviseParameter(idParameter);
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
            if (table.isSpecialIdentity()) {
                valueOfParameter(table.identityColumns(), identity, table.getIdentityType());
                String identitySql = sqlOfColumns(identity, table.identityColumns(), true, true);
                sqlBuilder.append(identitySql);
            } else {
                Optional.ofNullable(table.getIdentityColumn()).ifPresent(column -> sqlBuilder.append(column.columnEqualsProperty()));
            }
            return sqlSupply.supply(tablename, table, sqlBuilder);
        });
    }

    static <I> String providingOfAll(ProviderContext providerContext, @Nullable String tablename, Collection<I> idList, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
            if (table.isSpecialIdentity()) {
                RestStream.stream(idList).forEach(id -> valueOfParameter(table.identityColumns(), id, table.getIdentityType()));
                Map<Integer, List<I>> sliceOfColumnsMap = sliceOfColumns(table.identityColumns(), idList);
                sqlBuilder.append(sqlOfColumns(sliceOfColumnsMap, table.identityColumns(), sqlScript));
            } else {
                sqlBuilder.append(table.getIdentityColumn().columnName()).in().braceLt();
                idList.forEach(id -> sqlBuilder.value(id).comma());
                sqlBuilder.delete(sqlBuilder.length() - 2).braceGt();
            }
            return sqlSupply.supply(tablename, table, sqlBuilder);
        });
    }

    static <I> String providingOfWhere(ProviderContext providerContext, @Nullable String tablename, String whereSqlParameter, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            String whereSql = whereSqlParameter.trim();
            if (whereSql.startsWith(SQLConstants.AND)) {
                whereSql = whereSql.substring(SQLConstants.AND.length());
            }
            if (!whereSql.startsWith(SQLConstants.ORDER_BY) && !whereSql.startsWith(SQLConstants.LIMIT)) {
                whereSql = SqlBuilder.sqlBuilder().where().append(whereSql).toString();
            }
            SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
            sqlBuilder.cdataLt().append(whereSql).cdataGt();
            return sqlSupply.supply(tablename, table, sqlBuilder);
        });
    }

    static void valueOfParameter(Collection<MybatisColumn> mybatisColumns, Object parameter, Class<?> parameterType) throws RestException {
        Boolean isPresent = parameter.getClass() == parameterType;
        OptionalUtils.ofFalse(isPresent, "The type of parameter is not " + parameterType.getName(), parameterType.getName(), MybatisParamErrorException::new);
        Boolean logicalOr = RestStream.stream(mybatisColumns).map(MybatisColumn::getField)
                .map(mybatisField -> GeneralUtils.isNotEmpty(mybatisField.get(parameter)))
                .collect(RestCollectors.logicalOr());
        OptionalUtils.ofFalse(logicalOr, "The field values of parameter can not all be empty, " + parameterType.getName(), MybatisParamErrorException::new);
    }

    static <I> Map<Integer, List<I>> sliceOfColumns(Collection<MybatisColumn> mybatisColumns, Collection<I> idList) throws RestException {
        List<MybatisField> mybatisFields = RestStream.stream(mybatisColumns).map(MybatisColumn::getField).collect(RestCollectors.toList());
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

    static <I> String sqlOfColumns(I parameter, Collection<MybatisColumn> mybatisColumns, boolean andOrComma, boolean isEquals) throws RestException {
        SqlBuilder sqlBuilder = new SqlBuilder();
        boolean isNotFirstValue = false;
        for (MybatisColumn mybatisColumn : mybatisColumns) {
            String columnName = mybatisColumn.columnName();
            MybatisField mybatisField = mybatisColumn.getField();
            Object fieldValue = mybatisField.get(parameter);
            if (GeneralUtils.isNotEmpty(fieldValue)) {
                if (RestKey.class.isAssignableFrom(fieldValue.getClass())) {
                    fieldValue = ((RestKey<?>) fieldValue).getKey();
                }
                if (isNotFirstValue) {
                    if (andOrComma) {
                        sqlBuilder.and();
                    } else {
                        sqlBuilder.comma();
                    }
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
    static <I> String sqlOfColumns(Map<Integer, List<I>> columnsSliceMap, List<MybatisColumn> columns, MybatisSqlScript sqlScript) throws RestException {
        if (GeneralUtils.isEmpty(columnsSliceMap)) {
            return SqlBuilder.EMPTY;
        }
        SqlBuilder sqlBuilder = new SqlBuilder(SQLConstants.BRACE_LT);
        for (Map.Entry<Integer, List<I>> entry : columnsSliceMap.entrySet()) {
            Integer key = entry.getKey();
            List<I> valueList = entry.getValue();
            if (GeneralUtils.isNotEmpty(key) && GeneralUtils.isNotEmpty(valueList)) {
                List<Number> indices = RestReckon.denexNumber(key);
                if (GeneralUtils.isNotEmpty(indices)) {
                    List<MybatisColumn> mybatisColumns = RestStream.stream(indices)
                            .map(index -> columns.get(index.intValue())).collect(RestCollectors.toList());
                    boolean isMultiColumns = mybatisColumns.size() > 1;
                    boolean isSingleValue = valueList.size() == 1;
                    if (isSingleValue) {
                        if (isMultiColumns) {
                            sqlBuilder.braceLt();
                        }
                        boolean isNotFirst = false;
                        I value = valueList.get(0);
                        for (MybatisColumn mybatisColumn : mybatisColumns) {
                            MybatisField mybatisField = mybatisColumn.getField();
                            Object fieldValue = mybatisField.get(value);
                            sqlBuilder.eq(mybatisColumn.columnName(), fieldValue, isNotFirst ? true : null);
                            isNotFirst = true;
                        }
                        if (isMultiColumns) {
                            sqlBuilder.braceGt();
                        }
                    } else {
                        String fieldSql = RestStream.stream(mybatisColumns).map(MybatisColumn::columnName).collect(RestCollectors.joining(SQLConstants.COMMA));
                        if (isMultiColumns) {
                            sqlBuilder.braceLt().append(fieldSql).braceGt().in().braceLt();
                        } else {
                            sqlBuilder.append(fieldSql).in().braceLt();
                        }
                        RestStream.stream(valueList).forEach(value -> {
                            if (isMultiColumns) {
                                sqlBuilder.braceLt();
                            }
                            RestStream.stream(mybatisColumns).map(MybatisColumn::getField).forEach(field -> {
                                Object indexValue = field.get(value);
                                sqlBuilder.value(indexValue).comma();
                            });
                            sqlBuilder.deleteLastChar();
                            if (isMultiColumns) {
                                sqlBuilder.braceGt().comma();
                            } else {
                                sqlBuilder.comma();
                            }
                        });
                        sqlBuilder.deleteLastChar().braceGt();
                    }
                }
                sqlBuilder.or();
            }
        }
        sqlBuilder.delete(sqlBuilder.length() - 4).braceGt();
        return sqlBuilder.toString();
    }

    static String insertOfSaveSql(@Nullable String tablename, MybatisTable table, List<MybatisColumn> fickleColumns, boolean conflictOrDuplicate) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
        List<MybatisColumn> updatedColumns = table.updateColumns();
        updatedColumns.addAll(fickleColumns);
        RestOptional<List<MybatisColumn>> optionalUpdate = RestOptional.ofEmptyable(updatedColumns);
        boolean mysqlIgnoreInsert = MybatisSqlProviderHolder.mysqlIgnoreInsert();
        if (conflictOrDuplicate) {
            sqlBuilder.onConflict().braceLt();
            if (table.isSpecialIdentity()) {
                sqlBuilder.append(table.sqlOfIdentityColumns());
            } else {
                sqlBuilder.append(table.getIdentityColumn().columnName());
            }
            sqlBuilder.braceGt();
        } else if (optionalUpdate.isPresent()) {
            sqlBuilder.onDuplicateKey();
        }
        if (optionalUpdate.isPresent()) {
            sqlBuilder.doUpdate(conflictOrDuplicate);
            if (conflictOrDuplicate) {
                sqlBuilder.set();
            }
        } else if (!mysqlIgnoreInsert) {
            sqlBuilder.doNothing();
        }
        ExcludedType excludedType = MybatisSqlProviderHolder.defaultExcludedType();
        optionalUpdate.ifEmptyPresent(updateColumns -> {
            String collect = RestStream.stream(updateColumns).map(column -> column.excluded(excludedType, table.tablename(tablename)))
                    .collect(RestCollectors.joining(SQLConstants.COMMA + SQLConstants.BLANK + SQLConstants.LINEFEED));
            sqlBuilder.append(collect);
        });
        return sqlBuilder.toString();
    }

    static <E> List<MybatisColumn> fickleOfEntityColumns(String tablename, MybatisTable table, E entityParameter) throws RestException {
        List<MybatisColumn> fickleKeyColumns = new ArrayList<>();
        if (GeneralUtils.isNotEmpty(table.fickleValueColumn())) {
            MybatisTableMapper tableMapper = ApplicationContextHolder.beanOfType(MybatisTableMapper.class);
            List<String> tableColumns = Collections.emptyList();
            if (GeneralUtils.isNotEmpty(tableMapper)) {
                tableColumns = tableMapper.tableColumns(tablename);
            }
            fickleOfEntityKeyColumns(table, tableColumns, entityParameter, fickleKeyColumns);
            fickleOfEntityValueColumns(table, fickleKeyColumns, entityParameter);
        }
        return fickleKeyColumns;
    }

    static <E> List<MybatisColumn> fickleOfEntitiesColumns(String tablename, MybatisTable table, Collection<E> entityList) throws RestException {
        List<MybatisColumn> fickleKeyColumns = new ArrayList<>();
        if (GeneralUtils.isNotEmpty(table.fickleValueColumn())) {
            MybatisTableMapper tableMapper = ApplicationContextHolder.beanOfType(MybatisTableMapper.class);
            List<String> tableColumns = new ArrayList<>();
            if (GeneralUtils.isNotEmpty(tableMapper)) {
                List<String> tableColumnsList = tableMapper.tableColumns(tablename);
                tableColumns.addAll(tableColumnsList);
            }
            if (GeneralUtils.isNotEmpty(entityList)) {
                RestStream.stream(entityList).forEach(entity -> fickleOfEntityKeyColumns(table, tableColumns, entity, fickleKeyColumns));
                RestStream.stream(entityList).forEach(entity -> fickleOfEntityValueColumns(table, fickleKeyColumns, entity));
            }
        }
        return fickleKeyColumns;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    static <E> void fickleOfEntityKeyColumns(MybatisTable table, List<String> tableColumns, E entityParameter, List<MybatisColumn> fickleKeyColumns) throws RestException {
        if (GeneralUtils.isNotEmpty(table.fickleValueColumn()) && GeneralUtils.isNotEmpty(tableColumns)) {
            Object entity = reviseParameter(entityParameter);
            MybatisColumn fickleKeyColumn = table.fickleKeyColumn();
            MybatisField fickleKeyField = fickleKeyColumn.getField();
            Object fickleKeyObject = fickleKeyField.get(entity);
            JavaType fickleType = fickleKeyColumn.getFickleType();
            Collection<MybatisColumn> fickleColumnsList = Collections.emptyList();
            if (fickleType instanceof CollectionType && fickleKeyObject instanceof Collection) {
                Collection<FickleField> fickleCollection = (Collection<FickleField>) fickleKeyObject;
                List<FickleField> fickleFieldsList = new ArrayList<>(fickleCollection);
                if (GeneralUtils.isNotEmpty(fickleFieldsList)) {
                    fickleFieldsList.stream()
                            .filter(fickleField -> tableColumns.contains(fickleField.getName()))
                            .forEach(fickleField -> {
                                MybatisColumn fickleColumn = MybatisColumn.of(table, fickleKeyColumn, fickleField);
                                if (!fickleKeyColumns.contains(fickleColumn)) {
                                    fickleKeyColumns.add(fickleColumn);
                                }
                            });

                }
            } else if (fickleType instanceof MapType && fickleKeyObject instanceof Map) {
                Map<String, FickleField> fickleMap = (Map<String, FickleField>) fickleKeyObject;
                Map<String, FickleField> fickleFieldsMap = new HashMap<>(fickleMap);
                if (GeneralUtils.isNotEmpty(fickleFieldsMap)) {
                    fickleFieldsMap.values().stream()
                            .filter(fickleField -> tableColumns.contains(fickleField.getName()))
                            .forEach(fickleField -> {
                                MybatisColumn fickleColumn = MybatisColumn.of(table, fickleKeyColumn, fickleField);
                                if (!fickleKeyColumns.contains(fickleColumn)) {
                                    fickleKeyColumns.add(fickleColumn);
                                }
                            });
                }
            }
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    static <E> List<MybatisColumn> fickleOfEntityValueColumns(MybatisTable table, List<MybatisColumn> fickleKeyColumns, E entityParameter) throws RestException {
        if (GeneralUtils.isNotEmpty(table.fickleValueColumn()) && GeneralUtils.isNotEmpty(fickleKeyColumns)) {
            Object entity = reviseParameter(entityParameter);
            MybatisColumn fickleValueColumn = table.fickleValueColumn();
            MybatisField fickleKeyField = fickleValueColumn.getField();
            Object fickleKeyObject = fickleKeyField.get(entity);
            JavaType fickleType = fickleValueColumn.getFickleType();
            Collection<MybatisColumn> fickleColumnsList = Collections.emptyList();
            if (fickleType instanceof CollectionType && fickleKeyObject instanceof Collection) {
                Collection<FickleField> fickleCollection = (Collection<FickleField>) fickleKeyObject;
                List<FickleField> fickleFieldsList = new ArrayList<>(fickleCollection);
                if (GeneralUtils.isNotEmpty(fickleFieldsList)) {
                    Map<String, FickleField> fickleValueFields = fickleFieldsList.stream().collect(Collectors.toMap(fickleField -> {
                        if (GeneralUtils.isNotEmpty(fickleField.getKey())) {
                            return fickleField.getKey();
                        } else {
                            return MybatisTableStyle.columnName(table, fickleField);
                        }
                    }, Function.identity(), (oldValue, newValue) -> newValue));
                    List<FickleField> fickleFields = new ArrayList<>(fickleKeyColumns.size());
                    fickleKeyColumns.forEach(fickleKeyColumn -> {
                        String column = fickleKeyColumn.getColumn();
                        FickleField fickleValueField = fickleValueFields.get(column);
                        if (GeneralUtils.isNotEmpty(fickleValueField)) {
                            fickleFields.add(fickleValueField);
                        } else {
                            fickleFields.add(new MybatisFickle(column));
                        }
                    });
                    fickleKeyField.set(entity, fickleFields);
                    return fickleFieldsList.stream()
                            .map(fickleField -> MybatisColumn.of(table, fickleValueColumn, fickleField))
                            .collect(Collectors.toList());
                }
            } else if (fickleType instanceof MapType && fickleKeyObject instanceof Map) {
                Map<String, FickleField> fickleMap = (Map<String, FickleField>) fickleKeyObject;
                Map<String, FickleField> fickleFieldsMap = new HashMap<>(fickleMap);
                if (GeneralUtils.isNotEmpty(fickleFieldsMap)) {
                    Map<String, FickleField> fickleFields = new HashMap<>(fickleKeyColumns.size());
                    fickleKeyColumns.forEach(fickleKeyColumn -> {
                        String column = fickleKeyColumn.getColumn();
                        FickleField fickleValueField = fickleFieldsMap.get(column);
                        if (GeneralUtils.isNotEmpty(fickleValueField)) {
                            fickleFields.put(column, fickleValueField);
                        } else {
                            fickleFields.put(column, new MybatisFickle(column));
                        }
                    });
                    fickleKeyField.set(entity, fickleFields);
                    return fickleFieldsMap.values().stream()
                            .map(fickleField -> MybatisColumn.of(table, fickleValueColumn, fickleField))
                            .collect(Collectors.toList());
                }
            }
        }
        return Collections.emptyList();
    }

    static String sqlOfInsert(MybatisTable table, List<MybatisColumn> fickleKeyColumns) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder().braceLt();
        String sqlOfInsert = table.insertColumns().stream().map(column -> column.variable(EntityConstants.ENTITY_PREFIX))
                .collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
        if (GeneralUtils.isNotEmpty(sqlOfInsert)) {
            sqlBuilder.append(sqlOfInsert);
        }
        if (GeneralUtils.isNotEmpty(fickleKeyColumns)) {
            String sqlOfFickle = IntStream.range(0, fickleKeyColumns.size())
                    .mapToObj(index -> {
                        MybatisColumn mybatisColumn = fickleKeyColumns.get(index);
                        return mybatisColumn.variable(EntityConstants.ENTITY_PREFIX, index);
                    }).collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
            if (GeneralUtils.isNotEmpty(sqlOfFickle)) {
                sqlBuilder.comma().blank().append(sqlOfFickle);
            }
        }
        return sqlBuilder.braceGt().toString();
    }

}
