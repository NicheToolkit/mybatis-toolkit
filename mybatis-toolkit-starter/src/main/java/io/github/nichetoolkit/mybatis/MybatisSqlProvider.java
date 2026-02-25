package io.github.nichetoolkit.mybatis;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.ArrayType;
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
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.mybatis.load.RestLoad;
import io.github.nichetoolkit.mybatis.load.RestParam;
import io.github.nichetoolkit.rest.*;
import io.github.nichetoolkit.rest.actuator.ConsumerActuator;
import io.github.nichetoolkit.rest.holder.ApplicationContextHolder;
import io.github.nichetoolkit.rest.stream.RestCollectors;
import io.github.nichetoolkit.rest.stream.RestStream;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.JacksonUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import io.github.nichetoolkit.rice.enums.OperateType;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.lang.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <code>MybatisSqlProvider</code>
 * <p>The mybatis sql provider interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface MybatisSqlProvider {

    /**
     * <code>databaseTypes</code>
     * <p>The database types method.</p>
     * @return {@link java.util.List} <p>The database types return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    List<DatabaseType> databaseTypes();

    /**
     * <code>SELECT_SQL_SUPPLY</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The constant <code>SELECT_SQL_SUPPLY</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     */
    MybatisSqlSupply.SimpleSqlSupply SELECT_SQL_SUPPLY = (tableName, table, sqlBuilder) ->
            SqlBuilder.sqlBuilder()
                    .select().append(table.sqlOfSelectColumns())
                    .from().append(table.tableName(tableName))
                    .where(sqlBuilder).toString();

    /**
     * <code>ENTRY_SQL_SUPPLY</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The constant <code>ENTRY_SQL_SUPPLY</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     */
    MybatisSqlSupply.EntrySqlSupply ENTRY_SQL_SUPPLY = (tableName, table, keySqlBuilder, valueSqlBuilder) ->
            SqlBuilder.sqlBuilder()
                    .select().append(keySqlBuilder)
                    .from().append(table.tableName(tableName))
                    .where(valueSqlBuilder).toString();

    /**
     * <code>WHERE_SQL_SUPPLY</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The constant <code>WHERE_SQL_SUPPLY</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     */
    MybatisSqlSupply.SimpleSqlSupply WHERE_SQL_SUPPLY = (tableName, table, sqlBuilder) ->
            SqlBuilder.sqlBuilder()
                    .select().append(table.sqlOfSelectColumns())
                    .from().append(table.tableName(tableName))
                    .where(sqlBuilder).toString();

    /**
     * <code>SAVE_SQL_SUPPLY</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The constant <code>SAVE_SQL_SUPPLY</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     */
    MybatisSqlSupply.EntrySqlSupply SAVE_SQL_SUPPLY = (tableName, table, keySqlBuilder, valueSqlBuilder) -> {
        boolean mysqlIgnoreInsert = MybatisSqlProviderHolder.mysqlIgnoreInsert();
        SqlBuilder savesSqlBuilder = SqlBuilder.sqlBuilder();
        if (mysqlIgnoreInsert) {
            savesSqlBuilder.insertIgnore();
        } else {
            savesSqlBuilder.insert();
        }
        return savesSqlBuilder.append(table.tableName(tableName))
                .braceLt().append(keySqlBuilder).braceGt()
                .values().append(valueSqlBuilder).toString();
    };

    /**
     * <code>REMOVE_SQL_SUPPLY</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The constant <code>REMOVE_SQL_SUPPLY</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     */
    MybatisSqlSupply.SimpleSqlSupply REMOVE_SQL_SUPPLY = (tableName, table, sqlBuilder) ->
            SqlBuilder.sqlBuilder()
                    .update().append(table.tableName(tableName))
                    .set().append(table.getLogicColumn().columnEqualsProperty())
                    .comma().append(table.sqlOfForceUpdateColumns())
                    .where(sqlBuilder).toString();

    /**
     * <code>OPERATE_SQL_SUPPLY</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The constant <code>OPERATE_SQL_SUPPLY</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     */
    MybatisSqlSupply.SimpleSqlSupply OPERATE_SQL_SUPPLY = (tableName, table, sqlBuilder) ->
            SqlBuilder.sqlBuilder()
                    .update().append(table.tableName(tableName))
                    .set().append(table.getOperateColumn().columnEqualsProperty())
                    .comma().append(table.sqlOfForceUpdateColumns())
                    .where(sqlBuilder).toString();

    /**
     * <code>DELETE_SQL_SUPPLY</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The constant <code>DELETE_SQL_SUPPLY</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     */
    MybatisSqlSupply.SimpleSqlSupply DELETE_SQL_SUPPLY = (tableName, table, sqlBuilder) ->
            SqlBuilder.sqlBuilder()
                    .delete().from().append(table.tableName(tableName))
                    .where(sqlBuilder).toString();

    /**
     * <code>ALERT_SQL_SUPPLY</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The constant <code>ALERT_SQL_SUPPLY</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     */
    MybatisSqlSupply.EntrySqlSupply ALERT_SQL_SUPPLY = (tableName, table, keySqlBuilder, valueSqlBuilder) ->
            SqlBuilder.sqlBuilder()
                    .update().append(table.tableName(tableName))
                    .set().append(keySqlBuilder)
                    .comma().append(table.sqlOfForceUpdateColumns())
                    .where(valueSqlBuilder).toString();

    /**
     * <code>reviseParameter</code>
     * <p>The revise parameter method.</p>
     * @param <P>       {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param parameter P <p>The parameter parameter is <code>P</code> type.</p>
     * @return {@link java.lang.Object} <p>The revise parameter return object is <code>Object</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.Object
     * @see java.lang.SuppressWarnings
     * @see io.github.nichetoolkit.rest.RestException
     */
    @SuppressWarnings("unchecked")
    static <P> Object reviseParameter(P parameter) throws RestException {
        if (GeneralUtils.isEmpty(parameter)) {
            return parameter;
        }
        Class<?> parameterClass = parameter.getClass();
        if (Map.class.isAssignableFrom(parameterClass)) {
            Map<String, ?> param = (Map<String, ?>) parameter;
            Optional<?> firstParam = param.values().stream().findFirst();
            return firstParam.orElseThrow(MybatisParamErrorException::new);
        } else {
            return parameter;
        }
    }

    /**
     * <code>valueOfState</code>
     * <p>The value of state method.</p>
     * @param <S>          {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param table        {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param state        S <p>The state parameter is <code>S</code> type.</p>
     * @param stateName    {@link java.lang.String} <p>The state name parameter is <code>String</code> type.</p>
     * @param valueBuilder {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The value builder parameter is <code>SqlBuilder</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <S> void valueOfState(MybatisTable table, S state, String stateName, SqlBuilder valueBuilder) throws RestException {
        if (GeneralUtils.isNotEmpty(stateName)) {
            Optional.ofNullable(table.alertColumn(stateName)).ifPresent(column -> valueBuilder.append(column.columnName()).eq().value(state));
        } else {
            if (table.isSpecialAlertness()) {
                valueOfParameter(table.alertnessColumns(), state, table.getAlertnessType());
                String stateSql = sqlOfColumns(state, table.alertnessColumns(), false, true);
                valueBuilder.append(stateSql);
            } else if (RestState.class.isAssignableFrom(state.getClass())) {
                String stateKey = ((RestState<?>) state).getName();
                Object stateValue = ((RestState<?>) state).getState();
                if (GeneralUtils.isNotEmpty(stateKey) && GeneralUtils.isNotEmpty(stateValue)) {
                    Optional.ofNullable(table.alertColumn(stateKey)).ifPresent(column -> valueBuilder.append(column.columnName()).eq().value(stateValue));
                } else if (GeneralUtils.isNotEmpty(stateValue)) {
                    Optional.ofNullable(table.getAlertColumn()).ifPresent(column -> {
                        valueBuilder.append(column.columnName()).eq().value(stateValue);
                    });
                } else {
                    Optional.ofNullable(table.getAlertColumn()).ifPresent(column -> {
                        valueBuilder.append(column.columnEqualsProperty());
                    });
                }
            } else {
                Optional.ofNullable(table.getAlertColumn()).ifPresent(column -> {
                    if (RestKey.class.isAssignableFrom(state.getClass())) {
                        valueBuilder.append(column.columnName()).eq().value(state);
                    } else {
                        valueBuilder.append(column.columnEqualsProperty());
                    }
                });
            }
        }
    }

    /**
     * <code>databaseTypeOfColumnsSql</code>
     * <p>The database type of columns sql method.</p>
     * @param tableName  {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param sqlBuilder {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The sql builder parameter is <code>SqlBuilder</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
     * @see io.github.nichetoolkit.rest.RestException
     */
    static void databaseTypeOfColumnsSql(String tableName, SqlBuilder sqlBuilder) throws RestException {
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        switch (databaseType) {
            case SQLITE:
                /* SELECT name FROM pragma_table_info('表名') */
                sqlBuilder.select().append("name").from().append("pragma_table_info").braceLt().value(tableName).braceGt();
                break;
            case GAUSSDB:
            case POSTGRESQL:
            case MYSQL:
                /* SELECT column_name FROM information_schema.columns WHERE table_name = '表名'; */
                sqlBuilder.select().append("column_name")
                        .from().append("information_schema.columns")
                        .where().append("table_name").eq().value(tableName);
                break;
            default:
                String message = "it is unsupported currently of the " + databaseType.getKey() + " database type.";
                throw new MybatisUnsupportedErrorException(databaseType.getKey(), "tableColumns", message);
        }
    }

    /**
     * <code>databaseTypeOfCreateIndexSql</code>
     * <p>The database type of create index sql method.</p>
     * @param tableName  {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param sqlBuilder {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The sql builder parameter is <code>SqlBuilder</code> type.</p>
     * @param field      {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
     * @see io.github.nichetoolkit.rest.RestField
     * @see io.github.nichetoolkit.rest.RestException
     */
    static void databaseTypeOfCreateIndexSql(String tableName, SqlBuilder sqlBuilder, RestField<?> field) throws RestException {
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        switch (databaseType) {
            case GAUSSDB:
            case POSTGRESQL:
                /* CREATE INDEX IF NOT EXISTS IDX_NTR_FICKLE_ENTRY_TIME ON 表名 (字段); */
                sqlBuilder.createIndex().ifNotExists().append("IDX_").append(tableName.toUpperCase()).append("_").append(field.getKey().toUpperCase())
                        .on().append(tableName).braceLt().append(field.getKey()).braceGt();
                break;
            case SQLITE:
            case MYSQL:
                /* CREATE INDEX IDX_NTR_FICKLE_ENTRY_TIME ON 表名 (字段); */
                sqlBuilder.createIndex().append("IDX_").append(tableName.toUpperCase()).append("_").append(field.getKey().toUpperCase())
                        .on().append(tableName).braceLt().append(field.getKey()).braceGt();
                break;
            default:
                String message = "it is unsupported currently of the " + databaseType.getKey() + " database type.";
                throw new MybatisUnsupportedErrorException(databaseType.getKey(), "indexColumn", message);
        }
    }

    /**
     * <code>databaseTypeOfDropIndexSql</code>
     * <p>The database type of drop index sql method.</p>
     * @param tableName  {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param sqlBuilder {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The sql builder parameter is <code>SqlBuilder</code> type.</p>
     * @param field      {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
     * @see io.github.nichetoolkit.rest.RestField
     * @see io.github.nichetoolkit.rest.RestException
     */
    static void databaseTypeOfDropIndexSql(String tableName, SqlBuilder sqlBuilder, RestField<?> field) throws RestException {
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        switch (databaseType) {
            case SQLITE:
            case GAUSSDB:
            case POSTGRESQL:
                /* DROP INDEX IF EXISTS IDX_NTR_FICKLE_ENTRY_TIME; */
                sqlBuilder.dropIndex().ifExists().append("IDX_").append(tableName.toUpperCase()).append("_").append(field.getKey().toUpperCase());
                break;
            case MYSQL:
                /* DROP INDEX IDX_NTR_FICKLE_ENTRY_TIME ON 表名; */
                sqlBuilder.dropIndex().append("IDX_").append(tableName.toUpperCase()).append("_").append(field.getKey().toUpperCase())
                        .on().append(tableName);
                break;
            default:
                String message = "it is unsupported currently of the " + databaseType.getKey() + " database type.";
                throw new MybatisUnsupportedErrorException(databaseType.getKey(), "indexColumn", message);
        }
    }

    /**
     * <code>databaseTypeOfModifyColumnSql</code>
     * <p>The database type of modify column sql method.</p>
     * @param tableName  {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param sqlBuilder {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The sql builder parameter is <code>SqlBuilder</code> type.</p>
     * @param field      {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
     * @see io.github.nichetoolkit.rest.RestField
     * @see io.github.nichetoolkit.rest.RestException
     */
    static void databaseTypeOfModifyColumnSql(String tableName, SqlBuilder sqlBuilder, RestField<?> field) throws RestException {
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        switch (databaseType) {
            case GAUSSDB:
            case POSTGRESQL:
                /* ALTER TABLE IF EXISTS 表名 ALTER COLUMN time TYPE TIMESTAMP;
                    ALTER TABLE IF EXISTS 表名 ALTER COLUMN time SET NOT NULL;
                    ALTER TABLE IF EXISTS 表名 ALTER COLUMN time SET DEFAULT now();
                    COMMENT ON COLUMN  表名.time IS '时间';
                    ALTER TABLE IF EXISTS 表名 ALTER COLUMN time DROP NOT NULL;
                    ALTER TABLE IF EXISTS 表名 ALTER COLUMN time DROP DEFAULT;
                    COMMENT ON COLUMN  表名.time IS NULL; */
                sqlBuilder.alterTable().ifExists().append(tableName).alterColumn().append(field.getKey())
                        .type().append(field.getType().getValue().toUpperCase()).semicolon();
                if (field.notNull()) {
                    sqlBuilder.alterTable().ifExists().append(tableName).alterColumn().append(field.getKey())
                            .set().notNull().semicolon();
                } else {
                    sqlBuilder.alterTable().ifExists().append(tableName).alterColumn().append(field.getKey())
                            .drop().notNull().semicolon();
                }
                if (GeneralUtils.isNotEmpty(field.defaultValue())) {
                    sqlBuilder.alterTable().ifExists().append(tableName).alterColumn().append(field.getKey())
                            .set().deft().append(field.defaultValue().toString()).semicolon();
                } else {
                    sqlBuilder.alterTable().ifExists().append(tableName).alterColumn().append(field.getKey())
                            .drop().deft().semicolon();
                }
                if (GeneralUtils.isNotEmpty(field.getComment())) {
                    sqlBuilder.linefeed().comment().on().column().append(tableName).period().append(field.getKey())
                            .is().append(field.getComment());
                } else {
                    sqlBuilder.linefeed().comment().on().column().append(tableName).period().append(field.getKey())
                            .isNull();
                }
                break;
            case MYSQL:
                /* ALTER TABLE 表名 MODIFY COLUMN time TIMESTAMP NOT NULL DEFAULT now() COMMENT '时间'; */
                sqlBuilder.alterTable().append(tableName).modifyColumn().append(field.getKey())
                        .blank().append(field.getType().getValue().toUpperCase());
                if (field.notNull()) {
                    sqlBuilder.notNull();
                }
                if (GeneralUtils.isNotEmpty(field.defaultValue())) {
                    sqlBuilder.deft().append(field.defaultValue().toString());
                }
                if (GeneralUtils.isNotEmpty(field.getComment())) {
                    sqlBuilder.comment().append(field.getComment());
                }
                break;
            case SQLITE:
            default:
                String message = "it is unsupported currently of the " + databaseType.getKey() + " database type.";
                throw new MybatisUnsupportedErrorException(databaseType.getKey(), "indexColumn", message);
        }
    }


    /**
     * <code>databaseTypeOfAddColumnSql</code>
     * <p>The database type of add column sql method.</p>
     * @param tableName  {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param sqlBuilder {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The sql builder parameter is <code>SqlBuilder</code> type.</p>
     * @param field      {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
     * @see io.github.nichetoolkit.rest.RestField
     * @see io.github.nichetoolkit.rest.RestException
     */
    static void databaseTypeOfAddColumnSql(String tableName, SqlBuilder sqlBuilder, RestField<?> field) throws RestException {
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        switch (databaseType) {
            case GAUSSDB:
            case POSTGRESQL:
                /* ALTER TABLE 表名 ADD COLUMN time1 TIMESTAMP NOT NULL DEFAULT now();
                   COMMENT ON COLUMN  表名.time IS '时间';
                   COMMENT ON COLUMN  表名.time IS NULL; */
                sqlBuilder.alterTable().append(tableName).addColumn().append(field.getKey())
                        .blank().append(field.getType().getValue().toUpperCase());
                if (field.notNull()) {
                    sqlBuilder.notNull();
                }
                if (GeneralUtils.isNotEmpty(field.defaultValue())) {
                    sqlBuilder.deft().append(field.defaultValue().toString());
                }
                if (GeneralUtils.isNotEmpty(field.getComment())) {
                    sqlBuilder.semicolon().linefeed().comment().on().column().append(tableName).period().append(field.getKey())
                            .is().append(field.getComment());
                }
                break;
            case MYSQL:
                /* ALTER TABLE 表名 ADD COLUMN time1 TIMESTAMP NOT NULL DEFAULT now() COMMENT '时间'; */
                sqlBuilder.alterTable().append(tableName).addColumn().append(field.getKey())
                        .blank().append(field.getType().getValue().toUpperCase());
                if (field.notNull()) {
                    sqlBuilder.notNull();
                }
                if (GeneralUtils.isNotEmpty(field.defaultValue())) {
                    sqlBuilder.deft().append(field.defaultValue().toString());
                }
                if (GeneralUtils.isNotEmpty(field.getComment())) {
                    sqlBuilder.comment().append(field.getComment());
                }
                break;
            case SQLITE:
            default:
                String message = "it is unsupported currently of the " + databaseType.getKey() + " database type.";
                throw new MybatisUnsupportedErrorException(databaseType.getKey(), "indexColumn", message);
        }
    }

    /**
     * <code>databaseTypeOfDropColumnSql</code>
     * <p>The database type of drop column sql method.</p>
     * @param tableName  {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param sqlBuilder {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The sql builder parameter is <code>SqlBuilder</code> type.</p>
     * @param field      {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
     * @see io.github.nichetoolkit.rest.RestField
     * @see io.github.nichetoolkit.rest.RestException
     */
    static void databaseTypeOfDropColumnSql(String tableName, SqlBuilder sqlBuilder, RestField<?> field) throws RestException {
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        switch (databaseType) {
            case GAUSSDB:
            case POSTGRESQL:
            case SQLITE:
            case MYSQL:
                /* ALTER TABLE 表名 DROP COLUMN time1; */
                sqlBuilder.alterTable().append(tableName).dropColumn().append(field.getKey());
                break;
            default:
                String message = "it is unsupported currently of the " + databaseType.getKey() + "database type.";
                throw new MybatisUnsupportedErrorException(databaseType.getKey(), "indexColumn", message);
        }
    }

    /**
     * <code>providingOfTableName</code>
     * <p>The providing of table name method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of table name return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.RestException
     */
    static String providingOfTableName(ProviderContext providerContext, @Nullable String tableName) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
        if (GeneralUtils.isNotEmpty(tableName)) {
            databaseTypeOfColumnsSql(tableName, sqlBuilder);
            return sqlBuilder.toString();
        } else {
            return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
                databaseTypeOfColumnsSql(table.tableName(), sqlBuilder);
                return sqlBuilder.toString();
            });
        }
    }

    /**
     * <code>providingOfCreateIndex</code>
     * <p>The providing of create index method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param field           {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of create index return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.RestField
     * @see io.github.nichetoolkit.rest.RestException
     */
    static String providingOfCreateIndex(ProviderContext providerContext, @Nullable String tableName, RestField<?> field) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
        if (GeneralUtils.isNotEmpty(tableName)) {
            databaseTypeOfCreateIndexSql(tableName, sqlBuilder, field);
            return sqlBuilder.toString();
        } else {
            return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
                databaseTypeOfCreateIndexSql(table.tableName(), sqlBuilder, field);
                return sqlBuilder.toString();
            });
        }
    }

    /**
     * <code>providingOfDropIndex</code>
     * <p>The providing of drop index method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param field           {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of drop index return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.RestField
     * @see io.github.nichetoolkit.rest.RestException
     */
    static String providingOfDropIndex(ProviderContext providerContext, @Nullable String tableName, RestField<?> field) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
        if (GeneralUtils.isNotEmpty(tableName)) {
            databaseTypeOfDropIndexSql(tableName, sqlBuilder, field);
            return sqlBuilder.toString();
        } else {
            return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
                databaseTypeOfDropIndexSql(table.tableName(), sqlBuilder, field);
                return sqlBuilder.toString();
            });
        }
    }

    /**
     * <code>providingOfModifyColumn</code>
     * <p>The providing of modify column method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param field           {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of modify column return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.RestField
     * @see io.github.nichetoolkit.rest.RestException
     */
    static String providingOfModifyColumn(ProviderContext providerContext, @Nullable String tableName, RestField<?> field) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
        if (GeneralUtils.isNotEmpty(tableName)) {
            databaseTypeOfModifyColumnSql(tableName, sqlBuilder, field);
            return sqlBuilder.toString();
        } else {
            return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
                databaseTypeOfModifyColumnSql(table.tableName(), sqlBuilder, field);
                return sqlBuilder.toString();
            });
        }
    }

    /**
     * <code>providingOfAddColumn</code>
     * <p>The providing of add column method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param field           {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of add column return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.RestField
     * @see io.github.nichetoolkit.rest.RestException
     */
    static String providingOfAddColumn(ProviderContext providerContext, @Nullable String tableName, RestField<?> field) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
        if (GeneralUtils.isNotEmpty(tableName)) {
            databaseTypeOfAddColumnSql(tableName, sqlBuilder, field);
            return sqlBuilder.toString();
        } else {
            return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
                databaseTypeOfAddColumnSql(table.tableName(), sqlBuilder, field);
                return sqlBuilder.toString();
            });
        }
    }

    /**
     * <code>providingOfDropColumn</code>
     * <p>The providing of drop column method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param field           {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of drop column return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.RestField
     * @see io.github.nichetoolkit.rest.RestException
     */
    static String providingOfDropColumn(ProviderContext providerContext, @Nullable String tableName, RestField<?> field) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
        if (GeneralUtils.isNotEmpty(tableName)) {
            databaseTypeOfDropColumnSql(tableName, sqlBuilder, field);
            return sqlBuilder.toString();
        } else {
            return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
                databaseTypeOfDropColumnSql(table.tableName(), sqlBuilder, field);
                return sqlBuilder.toString();
            });
        }
    }

    /**
     * <code>providingOfIdOrParams</code>
     * <p>The providing of id or params method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param idParameter     I <p>The id parameter parameter is <code>I</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param params          {@link io.github.nichetoolkit.mybatis.load.RestParam} <p>The params parameter is <code>RestParam</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of id or params return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.load.RestParam
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <I, S> String providingOfIdOrParams(ProviderContext providerContext, @Nullable String tableName, I idParameter, ConsumerActuator<MybatisTable> tableOptional, RestParam[] params, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
        Object identity = reviseParameter(idParameter);
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            SqlBuilder valueBuilder = SqlBuilder.sqlBuilder();
            if (GeneralUtils.isNotEmpty(identity)) {
                valueOfIdentity(table, identity, valueBuilder);
            } else if (GeneralUtils.isNotEmpty(params)) {
                valuesOfParams(table, params, valueBuilder);
            }
            return sqlSupply.supply(tableName, table, valueBuilder);
        });
    }

    /**
     * <code>providingOfIdOrParams</code>
     * <p>The providing of id or params method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param idParameter     I <p>The id parameter parameter is <code>I</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param params          {@link io.github.nichetoolkit.mybatis.load.RestParam} <p>The params parameter is <code>RestParam</code> type.</p>
     * @param loadParams      {@link io.github.nichetoolkit.mybatis.load.RestLoad} <p>The load params parameter is <code>RestLoad</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of id or params return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.load.RestParam
     * @see io.github.nichetoolkit.mybatis.load.RestLoad
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <I, S> String providingOfIdOrParams(ProviderContext providerContext, @Nullable String tableName, I idParameter, ConsumerActuator<MybatisTable> tableOptional, RestParam[] params, RestLoad[] loadParams, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfIdOrParams(providerContext, tableName, idParameter, tableOptional, params,
                (tableNameValue, tableValue, valueBuilder) -> {
                    String ofSelectColumns = tableValue.sqlOfSelectColumns();
                    SqlBuilder keyBuilder = SqlBuilder.sqlBuilder(ofSelectColumns);
                    if (GeneralUtils.isNotEmpty(loadParams)) {
                        keyOfLoad(tableValue, loadParams, keyBuilder);
                    }
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfId</code>
     * <p>The providing of id method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param idParameter     I <p>The id parameter parameter is <code>I</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param loadParams      {@link io.github.nichetoolkit.mybatis.load.RestLoad} <p>The load params parameter is <code>RestLoad</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.load.RestLoad
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <I, S> String providingOfId(ProviderContext providerContext, @Nullable String tableName, I idParameter, ConsumerActuator<MybatisTable> tableOptional, RestLoad[] loadParams, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfId(providerContext, tableName, idParameter, tableOptional,
                (MybatisSqlSupply.EntrySqlSupply) (tableNameValue, tableValue, keyBuilder, valueBuilder) -> {
                    keyOfLoad(tableValue, loadParams, keyBuilder);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfId</code>
     * <p>The providing of id method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param idParameter     I <p>The id parameter parameter is <code>I</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param fickleParams    {@link io.github.nichetoolkit.mybatis.fickle.RestFickle} <p>The fickle params parameter is <code>RestFickle</code> type.</p>
     * @param loadParams      {@link io.github.nichetoolkit.mybatis.load.RestLoad} <p>The load params parameter is <code>RestLoad</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.fickle.RestFickle
     * @see io.github.nichetoolkit.mybatis.load.RestLoad
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <I, S> String providingOfId(ProviderContext providerContext, @Nullable String tableName, I idParameter, ConsumerActuator<MybatisTable> tableOptional, RestFickle<?>[] fickleParams, RestLoad[] loadParams, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfId(providerContext, tableName, idParameter, tableOptional,
                (MybatisSqlSupply.EntrySqlSupply) (tableNameValue, tableValue, keyBuilder, valueBuilder) -> {
                    keyOfFickle(tableValue, fickleParams, keyBuilder);
                    keyOfLoad(tableValue, loadParams, keyBuilder);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfId</code>
     * <p>The providing of id method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param idParameter     I <p>The id parameter parameter is <code>I</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param fickleParams    {@link io.github.nichetoolkit.mybatis.fickle.RestFickle} <p>The fickle params parameter is <code>RestFickle</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.fickle.RestFickle
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <I, S> String providingOfId(ProviderContext providerContext, @Nullable String tableName, I idParameter, ConsumerActuator<MybatisTable> tableOptional, RestFickle<?>[] fickleParams, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfId(providerContext, tableName, idParameter, tableOptional,
                (tableNameValue, tableValue, keyBuilder, valueBuilder) -> {
                    keyOfFickle(tableValue, fickleParams, keyBuilder);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfId</code>
     * <p>The providing of id method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param idParameter     I <p>The id parameter parameter is <code>I</code> type.</p>
     * @param stateParameter  S <p>The state parameter parameter is <code>S</code> type.</p>
     * @param stateName       {@link java.lang.String} <p>The state name parameter is <code>String</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <I, S> String providingOfId(ProviderContext providerContext, @Nullable String tableName, I idParameter, S stateParameter, String stateName, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        Object state = reviseParameter(stateParameter);
        return providingOfId(providerContext, tableName, idParameter, tableIgnored -> {
        }, (tableNameValue, tableValue, sqlBuilder) -> {
            SqlBuilder keyBuilder = SqlBuilder.sqlBuilder();
            valueOfState(tableValue, state, stateName, keyBuilder);
            return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, sqlBuilder);
        });
    }

    /**
     * <code>providingOfId</code>
     * <p>The providing of id method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param idParameter     I <p>The id parameter parameter is <code>I</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <I> String providingOfId(ProviderContext providerContext, @Nullable String tableName, I idParameter, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfId(providerContext, tableName, idParameter, tableOptional,
                (tableNameValue, tableValue, valueBuilder) -> {
                    String ofSelectColumns = tableValue.sqlOfSelectColumns();
                    SqlBuilder keyBuilder = SqlBuilder.sqlBuilder(ofSelectColumns);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfId</code>
     * <p>The providing of id method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param idParameter     I <p>The id parameter parameter is <code>I</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <I> String providingOfId(ProviderContext providerContext, @Nullable String tableName, I idParameter, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
        Object identity = reviseParameter(idParameter);
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            SqlBuilder valueBuilder = SqlBuilder.sqlBuilder();
            valueOfIdentity(table, identity, valueBuilder);
            return sqlSupply.supply(tableName, table, valueBuilder);
        });
    }

    /**
     * <code>providingOfAll</code>
     * <p>The providing of all method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param fickleParams    {@link io.github.nichetoolkit.mybatis.fickle.RestFickle} <p>The fickle params parameter is <code>RestFickle</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.fickle.RestFickle
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <I, S> String providingOfAll(ProviderContext providerContext, @Nullable String tableName, Collection<I> idList, ConsumerActuator<MybatisTable> tableOptional, RestFickle<?>[] fickleParams, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfAll(providerContext, tableName, idList, tableOptional,
                (tableNameValue, tableValue, keyBuilder, valueBuilder) -> {
                    keyOfFickle(tableValue, fickleParams, keyBuilder);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfAll</code>
     * <p>The providing of all method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param fickleParams    {@link io.github.nichetoolkit.mybatis.fickle.RestFickle} <p>The fickle params parameter is <code>RestFickle</code> type.</p>
     * @param loadParams      {@link io.github.nichetoolkit.mybatis.load.RestLoad} <p>The load params parameter is <code>RestLoad</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.fickle.RestFickle
     * @see io.github.nichetoolkit.mybatis.load.RestLoad
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <I, S> String providingOfAll(ProviderContext providerContext, @Nullable String tableName, Collection<I> idList, ConsumerActuator<MybatisTable> tableOptional, RestFickle<?>[] fickleParams, RestLoad[] loadParams, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfAll(providerContext, tableName, idList, tableOptional,
                (tableNameValue, tableValue, keyBuilder, valueBuilder) -> {
                    keyOfFickle(tableValue, fickleParams, keyBuilder);
                    keyOfLoad(tableValue, loadParams, keyBuilder);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfAll</code>
     * <p>The providing of all method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param loadParams      {@link io.github.nichetoolkit.mybatis.load.RestLoad} <p>The load params parameter is <code>RestLoad</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.load.RestLoad
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <I, S> String providingOfAll(ProviderContext providerContext, @Nullable String tableName, Collection<I> idList, ConsumerActuator<MybatisTable> tableOptional, RestLoad[] loadParams, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfAll(providerContext, tableName, idList, tableOptional,
                (tableNameValue, tableValue, keyBuilder, valueBuilder) -> {
                    keyOfLoad(tableValue, loadParams, keyBuilder);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfAll</code>
     * <p>The providing of all method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @param stateParameter  S <p>The state parameter parameter is <code>S</code> type.</p>
     * @param stateName       {@link java.lang.String} <p>The state name parameter is <code>String</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see java.util.Collection
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <I, S> String providingOfAll(ProviderContext providerContext, @Nullable String tableName, Collection<I> idList, S stateParameter, String stateName, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        Object state = reviseParameter(stateParameter);
        return providingOfAll(providerContext, tableName, idList, tableIgnored -> {
        }, (tableNameValue, tableValue, sqlBuilder) -> {
            SqlBuilder keyBuilder = SqlBuilder.sqlBuilder();
            valueOfState(tableValue, state, stateName, keyBuilder);
            return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, sqlBuilder);
        });
    }


    /**
     * <code>providingOfAll</code>
     * <p>The providing of all method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <I> String providingOfAll(ProviderContext providerContext, @Nullable String tableName, Collection<I> idList, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfAll(providerContext, tableName, idList, tableOptional,
                (tableNameValue, tableValue, valueBuilder) -> {
                    String ofSelectColumns = tableValue.sqlOfSelectColumns();
                    SqlBuilder keyBuilder = SqlBuilder.sqlBuilder(ofSelectColumns);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfAll</code>
     * <p>The providing of all method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <I> String providingOfAll(ProviderContext providerContext, @Nullable String tableName, Collection<I> idList, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            SqlBuilder valueBuilder = SqlBuilder.sqlBuilder();
            valuesOfIdentity(table, idList, valueBuilder, sqlScript);
            return sqlSupply.supply(tableName, table, valueBuilder);
        });
    }

    /**
     * <code>providingOfWhere</code>
     * <p>The providing of where method.</p>
     * @param <S>               {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext   {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName         {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param whereSqlParameter {@link java.lang.String} <p>The where sql parameter parameter is <code>String</code> type.</p>
     * @param tableOptional     {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param fickleParams      {@link io.github.nichetoolkit.mybatis.fickle.RestFickle} <p>The fickle params parameter is <code>RestFickle</code> type.</p>
     * @param loadParams        {@link io.github.nichetoolkit.mybatis.load.RestLoad} <p>The load params parameter is <code>RestLoad</code> type.</p>
     * @param sqlSupply         {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.fickle.RestFickle
     * @see io.github.nichetoolkit.mybatis.load.RestLoad
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <S> String providingOfWhere(ProviderContext providerContext, @Nullable String tableName, String whereSqlParameter, ConsumerActuator<MybatisTable> tableOptional, RestFickle<?>[] fickleParams, RestLoad[] loadParams, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfWhere(providerContext, tableName, whereSqlParameter, tableOptional,
                (tableNameValue, tableValue, keyBuilder, valueBuilder) -> {
                    keyOfFickle(tableValue, fickleParams, keyBuilder);
                    keyOfLoad(tableValue, loadParams, keyBuilder);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfWhere</code>
     * <p>The providing of where method.</p>
     * @param <S>               {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext   {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName         {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param whereSqlParameter {@link java.lang.String} <p>The where sql parameter parameter is <code>String</code> type.</p>
     * @param tableOptional     {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param fickleParams      {@link io.github.nichetoolkit.mybatis.fickle.RestFickle} <p>The fickle params parameter is <code>RestFickle</code> type.</p>
     * @param sqlSupply         {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.fickle.RestFickle
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <S> String providingOfWhere(ProviderContext providerContext, @Nullable String tableName, String whereSqlParameter, ConsumerActuator<MybatisTable> tableOptional, RestFickle<?>[] fickleParams, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfWhere(providerContext, tableName, whereSqlParameter, tableOptional,
                (tableNameValue, tableValue, keyBuilder, valueBuilder) -> {
                    keyOfFickle(tableValue, fickleParams, keyBuilder);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfWhere</code>
     * <p>The providing of where method.</p>
     * @param <S>               {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext   {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName         {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param whereSqlParameter {@link java.lang.String} <p>The where sql parameter parameter is <code>String</code> type.</p>
     * @param tableOptional     {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param loadParams        {@link io.github.nichetoolkit.mybatis.load.RestLoad} <p>The load params parameter is <code>RestLoad</code> type.</p>
     * @param sqlSupply         {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.load.RestLoad
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <S> String providingOfWhere(ProviderContext providerContext, @Nullable String tableName, String whereSqlParameter, ConsumerActuator<MybatisTable> tableOptional, RestLoad[] loadParams, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfWhere(providerContext, tableName, whereSqlParameter, tableOptional,
                (tableNameValue, tableValue, keyBuilder, valueBuilder) -> {
                    keyOfLoad(tableValue, loadParams, keyBuilder);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfWhere</code>
     * <p>The providing of where method.</p>
     * @param <S>               {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext   {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName         {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param whereSqlParameter {@link java.lang.String} <p>The where sql parameter parameter is <code>String</code> type.</p>
     * @param stateParameter    S <p>The state parameter parameter is <code>S</code> type.</p>
     * @param stateName         {@link java.lang.String} <p>The state name parameter is <code>String</code> type.</p>
     * @param sqlSupply         {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <S> String providingOfWhere(ProviderContext providerContext, @Nullable String tableName, String whereSqlParameter, S stateParameter, String stateName, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        Object state = reviseParameter(stateParameter);
        return providingOfWhere(providerContext, tableName, whereSqlParameter, tableIgnored -> {
        }, (tableNameValue, tableValue, sqlBuilder) -> {
            SqlBuilder keyBuilder = SqlBuilder.sqlBuilder();
            valueOfState(tableValue, state, stateName, keyBuilder);
            return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, sqlBuilder);
        });
    }

    /**
     * <code>providingOfWhere</code>
     * <p>The providing of where method.</p>
     * @param <I>               {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext   {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName         {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param whereSqlParameter {@link java.lang.String} <p>The where sql parameter parameter is <code>String</code> type.</p>
     * @param tableOptional     {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply         {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <I> String providingOfWhere(ProviderContext providerContext, @Nullable String tableName, String whereSqlParameter, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfWhere(providerContext, tableName, whereSqlParameter, tableOptional,
                (tableNameValue, tableValue, valueBuilder) -> {
                    String ofSelectColumns = tableValue.sqlOfSelectColumns();
                    SqlBuilder keyBuilder = SqlBuilder.sqlBuilder(ofSelectColumns);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfWhere</code>
     * <p>The providing of where method.</p>
     * @param <I>               {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext   {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName         {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param whereSqlParameter {@link java.lang.String} <p>The where sql parameter parameter is <code>String</code> type.</p>
     * @param tableOptional     {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply         {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <I> String providingOfWhere(ProviderContext providerContext, @Nullable String tableName, String whereSqlParameter, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            return sqlSupply.supply(tableName, table, SqlBuilder.sqlBuilder(whereSqlParameter));
        });
    }

    /**
     * <code>providingOfLinkId</code>
     * <p>The providing of link id method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param linkIdParameter L <p>The link id parameter parameter is <code>L</code> type.</p>
     * @param linkName        {@link java.lang.String} <p>The link name parameter is <code>String</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param fickleParams    {@link io.github.nichetoolkit.mybatis.fickle.RestFickle} <p>The fickle params parameter is <code>RestFickle</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.fickle.RestFickle
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <L> String providingOfLinkId(ProviderContext providerContext, @Nullable String tableName, L linkIdParameter, String linkName, ConsumerActuator<MybatisTable> tableOptional, RestFickle<?>[] fickleParams, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfLinkId(providerContext, tableName, linkIdParameter, linkName, tableOptional,
                (tableNameValue, tableValue, keyBuilder, valueBuilder) -> {
                    keyOfFickle(tableValue, fickleParams, keyBuilder);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfLinkId</code>
     * <p>The providing of link id method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param linkIdParameter L <p>The link id parameter parameter is <code>L</code> type.</p>
     * @param linkName        {@link java.lang.String} <p>The link name parameter is <code>String</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param loadParams      {@link io.github.nichetoolkit.mybatis.load.RestLoad} <p>The load params parameter is <code>RestLoad</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.load.RestLoad
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <L> String providingOfLinkId(ProviderContext providerContext, @Nullable String tableName, L linkIdParameter, String linkName, ConsumerActuator<MybatisTable> tableOptional, RestLoad[] loadParams, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfLinkId(providerContext, tableName, linkIdParameter, linkName, tableOptional,
                (tableNameValue, tableValue, keyBuilder, valueBuilder) -> {
                    keyOfLoad(tableValue, loadParams, keyBuilder);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfLinkId</code>
     * <p>The providing of link id method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param linkIdParameter L <p>The link id parameter parameter is <code>L</code> type.</p>
     * @param linkName        {@link java.lang.String} <p>The link name parameter is <code>String</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param fickleParams    {@link io.github.nichetoolkit.mybatis.fickle.RestFickle} <p>The fickle params parameter is <code>RestFickle</code> type.</p>
     * @param loadParams      {@link io.github.nichetoolkit.mybatis.load.RestLoad} <p>The load params parameter is <code>RestLoad</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.fickle.RestFickle
     * @see io.github.nichetoolkit.mybatis.load.RestLoad
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <L> String providingOfLinkId(ProviderContext providerContext, @Nullable String tableName, L linkIdParameter, String linkName, ConsumerActuator<MybatisTable> tableOptional, RestFickle<?>[] fickleParams, RestLoad[] loadParams, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfLinkId(providerContext, tableName, linkIdParameter, linkName, tableOptional,
                (tableNameValue, tableValue, keyBuilder, valueBuilder) -> {
                    keyOfFickle(tableValue, fickleParams, keyBuilder);
                    keyOfLoad(tableValue, loadParams, keyBuilder);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfLinkId</code>
     * <p>The providing of link id method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param linkIdParameter L <p>The link id parameter parameter is <code>L</code> type.</p>
     * @param linkName        {@link java.lang.String} <p>The link name parameter is <code>String</code> type.</p>
     * @param stateParameter  S <p>The state parameter parameter is <code>S</code> type.</p>
     * @param stateName       {@link java.lang.String} <p>The state name parameter is <code>String</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <L, S> String providingOfLinkId(ProviderContext providerContext, @Nullable String tableName, L linkIdParameter, String linkName, S stateParameter, String stateName, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        Object state = reviseParameter(stateParameter);
        return providingOfLinkId(providerContext, tableName, linkIdParameter, linkName, tableIgnored -> {
        }, (tableNameValue, tableValue, sqlBuilder) -> {
            SqlBuilder keyBuilder = SqlBuilder.sqlBuilder();
            valueOfState(tableValue, state, stateName, keyBuilder);
            return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, sqlBuilder);
        });
    }

    /**
     * <code>providingOfLinkId</code>
     * <p>The providing of link id method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param linkIdParameter L <p>The link id parameter parameter is <code>L</code> type.</p>
     * @param linkName        {@link java.lang.String} <p>The link name parameter is <code>String</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <L> String providingOfLinkId(ProviderContext providerContext, @Nullable String tableName, L linkIdParameter, String linkName, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfLinkId(providerContext, tableName, linkIdParameter, linkName, tableOptional,
                (tableNameValue, tableValue, valueBuilder) -> {
                    String ofSelectColumns = tableValue.sqlOfSelectColumns();
                    SqlBuilder keyBuilder = SqlBuilder.sqlBuilder(ofSelectColumns);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfLinkId</code>
     * <p>The providing of link id method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param linkIdParameter L <p>The link id parameter parameter is <code>L</code> type.</p>
     * @param linkName        {@link java.lang.String} <p>The link name parameter is <code>String</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <L> String providingOfLinkId(ProviderContext providerContext, @Nullable String tableName, L linkIdParameter, String linkName, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
        Object linkId = reviseParameter(linkIdParameter);
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            SqlBuilder valueBuilder = SqlBuilder.sqlBuilder();
            valueOfLinkId(table, linkId, linkName, valueBuilder);
            return sqlSupply.supply(tableName, table, valueBuilder);
        });
    }

    /**
     * <code>providingOfLinkIdAll</code>
     * <p>The providing of link id all method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param linkIdList      {@link java.util.Collection} <p>The link id list parameter is <code>Collection</code> type.</p>
     * @param linkName        {@link java.lang.String} <p>The link name parameter is <code>String</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param fickleParams    {@link io.github.nichetoolkit.mybatis.fickle.RestFickle} <p>The fickle params parameter is <code>RestFickle</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of link id all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.fickle.RestFickle
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <L> String providingOfLinkIdAll(ProviderContext providerContext, @Nullable String tableName, Collection<L> linkIdList, String linkName, ConsumerActuator<MybatisTable> tableOptional, RestFickle<?>[] fickleParams, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfLinkIdAll(providerContext, tableName, linkIdList, linkName, tableOptional,
                (tableNameValue, tableValue, keyBuilder, valueBuilder) -> {
                    keyOfFickle(tableValue, fickleParams, keyBuilder);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfLinkIdAll</code>
     * <p>The providing of link id all method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param linkIdList      {@link java.util.Collection} <p>The link id list parameter is <code>Collection</code> type.</p>
     * @param linkName        {@link java.lang.String} <p>The link name parameter is <code>String</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param fickleParams    {@link io.github.nichetoolkit.mybatis.fickle.RestFickle} <p>The fickle params parameter is <code>RestFickle</code> type.</p>
     * @param loadParams      {@link io.github.nichetoolkit.mybatis.load.RestLoad} <p>The load params parameter is <code>RestLoad</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of link id all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.fickle.RestFickle
     * @see io.github.nichetoolkit.mybatis.load.RestLoad
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <L> String providingOfLinkIdAll(ProviderContext providerContext, @Nullable String tableName, Collection<L> linkIdList, String linkName, ConsumerActuator<MybatisTable> tableOptional, RestFickle<?>[] fickleParams, RestLoad[] loadParams, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfLinkIdAll(providerContext, tableName, linkIdList, linkName, tableOptional,
                (tableNameValue, tableValue, keyBuilder, valueBuilder) -> {
                    keyOfFickle(tableValue, fickleParams, keyBuilder);
                    keyOfLoad(tableValue, loadParams, keyBuilder);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfLinkIdAll</code>
     * <p>The providing of link id all method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param linkIdList      {@link java.util.Collection} <p>The link id list parameter is <code>Collection</code> type.</p>
     * @param linkName        {@link java.lang.String} <p>The link name parameter is <code>String</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param loadParams      {@link io.github.nichetoolkit.mybatis.load.RestLoad} <p>The load params parameter is <code>RestLoad</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of link id all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.load.RestLoad
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <L> String providingOfLinkIdAll(ProviderContext providerContext, @Nullable String tableName, Collection<L> linkIdList, String linkName, ConsumerActuator<MybatisTable> tableOptional, RestLoad[] loadParams, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfLinkIdAll(providerContext, tableName, linkIdList, linkName, tableOptional,
                (tableNameValue, tableValue, keyBuilder, valueBuilder) -> {
                    keyOfLoad(tableValue, loadParams, keyBuilder);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfLinkIdAll</code>
     * <p>The providing of link id all method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param linkIdList      {@link java.util.Collection} <p>The link id list parameter is <code>Collection</code> type.</p>
     * @param linkName        {@link java.lang.String} <p>The link name parameter is <code>String</code> type.</p>
     * @param stateParameter  S <p>The state parameter parameter is <code>S</code> type.</p>
     * @param stateName       {@link java.lang.String} <p>The state name parameter is <code>String</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of link id all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see java.util.Collection
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <L, S> String providingOfLinkIdAll(ProviderContext providerContext, @Nullable String tableName, Collection<L> linkIdList, String linkName, S stateParameter, String stateName, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        Object state = reviseParameter(stateParameter);
        return providingOfLinkIdAll(providerContext, tableName, linkIdList, linkName, tableIgnored -> {
        }, (tableNameValue, tableValue, sqlBuilder) -> {
            SqlBuilder keyBuilder = SqlBuilder.sqlBuilder();
            valueOfState(tableValue, state, stateName, keyBuilder);
            return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, sqlBuilder);
        });
    }

    /**
     * <code>providingOfLinkIdAll</code>
     * <p>The providing of link id all method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param linkIdList      {@link java.util.Collection} <p>The link id list parameter is <code>Collection</code> type.</p>
     * @param linkName        {@link java.lang.String} <p>The link name parameter is <code>String</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of link id all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <L> String providingOfLinkIdAll(ProviderContext providerContext, @Nullable String tableName, Collection<L> linkIdList, String linkName, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfLinkIdAll(providerContext, tableName, linkIdList, linkName, tableOptional,
                (tableNameValue, tableValue, valueBuilder) -> {
                    String ofSelectColumns = tableValue.sqlOfSelectColumns();
                    SqlBuilder keyBuilder = SqlBuilder.sqlBuilder(ofSelectColumns);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfLinkIdAll</code>
     * <p>The providing of link id all method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param linkIdList      {@link java.util.Collection} <p>The link id list parameter is <code>Collection</code> type.</p>
     * @param linkName        {@link java.lang.String} <p>The link name parameter is <code>String</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of link id all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <L> String providingOfLinkIdAll(ProviderContext providerContext, @Nullable String tableName, Collection<L> linkIdList, String linkName, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            SqlBuilder valueBuilder = SqlBuilder.sqlBuilder();
            valuesOfLinkId(table, linkIdList, linkName, valueBuilder, sqlScript);
            return sqlSupply.supply(tableName, table, valueBuilder);
        });
    }

    /**
     * <code>providingOfName</code>
     * <p>The providing of name method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param name            {@link java.lang.String} <p>The name parameter is <code>String</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param loadParams      {@link io.github.nichetoolkit.mybatis.load.RestLoad} <p>The load params parameter is <code>RestLoad</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of name return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.load.RestLoad
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static String providingOfName(ProviderContext providerContext, @Nullable String tableName, String name, ConsumerActuator<MybatisTable> tableOptional, RestLoad[] loadParams, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfName(providerContext, tableName, name, tableOptional,
                (tableNameValue, tableValue, keyBuilder, valueBuilder) -> {
                    keyOfLoad(tableValue, loadParams, keyBuilder);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfName</code>
     * <p>The providing of name method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param name            {@link java.lang.String} <p>The name parameter is <code>String</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of name return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static String providingOfName(ProviderContext providerContext, @Nullable String tableName, String name, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        return providingOfName(providerContext, tableName, name, tableOptional,
                (tableNameValue, tableValue, valueBuilder) -> {
                    String ofSelectColumns = tableValue.sqlOfSelectColumns();
                    SqlBuilder keyBuilder = SqlBuilder.sqlBuilder(ofSelectColumns);
                    return sqlSupply.supply(tableNameValue, tableValue, keyBuilder, valueBuilder);
                });
    }

    /**
     * <code>providingOfName</code>
     * <p>The providing of name method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param name            {@link java.lang.String} <p>The name parameter is <code>String</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of name return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see java.lang.SuppressWarnings
     * @see io.github.nichetoolkit.rest.RestException
     */
    @SuppressWarnings("Duplicates")
    static String providingOfName(ProviderContext providerContext, @Nullable String tableName, String name, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
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
            return sqlSupply.supply(tableName, table, sqlBuilder);
        });
    }

    /**
     * <code>providingOfName</code>
     * <p>The providing of name method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param name            {@link java.lang.String} <p>The name parameter is <code>String</code> type.</p>
     * @param idParameter     I <p>The id parameter parameter is <code>I</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of name return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see java.lang.SuppressWarnings
     * @see io.github.nichetoolkit.rest.RestException
     */
    @SuppressWarnings("Duplicates")
    static <I> String providingOfName(ProviderContext providerContext, @Nullable String tableName, String name, I idParameter, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
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
            return sqlSupply.supply(tableName, table, sqlBuilder);
        });
    }

    /**
     * <code>providingOfEntity</code>
     * <p>The providing of entity method.</p>
     * @param <E>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param entityParameter E <p>The entity parameter parameter is <code>E</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of entity return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see java.lang.SuppressWarnings
     * @see io.github.nichetoolkit.rest.RestException
     */
    @SuppressWarnings("Duplicates")
    static <E> String providingOfEntity(ProviderContext providerContext, @Nullable String tableName, E entityParameter, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
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
            return sqlSupply.supply(tableName, table, sqlBuilder);
        });
    }

    /**
     * <code>providingOfEntity</code>
     * <p>The providing of entity method.</p>
     * @param <E>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param entityParameter E <p>The entity parameter parameter is <code>E</code> type.</p>
     * @param idParameter     I <p>The id parameter parameter is <code>I</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of entity return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see java.lang.SuppressWarnings
     * @see io.github.nichetoolkit.rest.RestException
     */
    @SuppressWarnings("Duplicates")
    static <E, I> String providingOfEntity(ProviderContext providerContext, @Nullable String tableName, E entityParameter, I idParameter, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
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
            return sqlSupply.supply(tableName, table, sqlBuilder);
        });
    }

    /**
     * <code>providingOfSave</code>
     * <p>The providing of save method.</p>
     * @param <E>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableNameValue  {@link java.lang.String} <p>The table name value parameter is <code>String</code> type.</p>
     * @param entityParameter E <p>The entity parameter parameter is <code>E</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of save return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see java.lang.SuppressWarnings
     * @see io.github.nichetoolkit.rest.RestException
     */
    @SuppressWarnings("Duplicates")
    static <E> String providingOfSave(ProviderContext providerContext, @Nullable String tableNameValue, E entityParameter, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            String tableName = table.tableName(tableNameValue);
            List<MybatisColumn> insertColumns = table.insertColumns();
            List<MybatisColumn> fickleKeyColumns = fickleOfEntityColumns(tableName, table, entityParameter);
            insertColumns.addAll(fickleKeyColumns);
            String sqlOfKeyInsert = insertColumns.stream().map(MybatisColumn::columnName).distinct().collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
            SqlBuilder keySqlBuilder = SqlBuilder.sqlBuilder(sqlOfKeyInsert);
            String sqlOfInsert = sqlOfInsert(table, fickleKeyColumns);
            SqlBuilder valueSqlBuilder = SqlBuilder.sqlBuilder(sqlOfInsert);
            databaseTypeOfSql(tableName, table, valueSqlBuilder, fickleKeyColumns, databaseType);
            return sqlSupply.supply(tableName, table, keySqlBuilder, valueSqlBuilder);
        });
    }


    /**
     * <code>providingOfAllSave</code>
     * <p>The providing of all save method.</p>
     * @param <E>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableNameValue  {@link java.lang.String} <p>The table name value parameter is <code>String</code> type.</p>
     * @param entityList      {@link java.util.Collection} <p>The entity list parameter is <code>Collection</code> type.</p>
     * @param tableOptional   {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply       {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply} <p>The sql supply parameter is <code>EntrySqlSupply</code> type.</p>
     * @return {@link java.lang.String} <p>The providing of all save return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.mybatis.MybatisSqlSupply.EntrySqlSupply
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <E> String providingOfAllSave(ProviderContext providerContext, @Nullable String tableNameValue, Collection<E> entityList, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.EntrySqlSupply sqlSupply) throws RestException {
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            String tableName = table.tableName(tableNameValue);
            List<MybatisColumn> insertColumns = table.insertColumns();
            List<MybatisColumn> fickleKeyColumns = fickleOfEntitiesColumns(tableName, table, entityList);
            insertColumns.addAll(fickleKeyColumns);
            String sqlOfKeyInsert = insertColumns.stream().map(MybatisColumn::columnName).distinct().collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
            SqlBuilder keySqlBuilder = SqlBuilder.sqlBuilder(sqlOfKeyInsert);
            String valueSqlOfInsert = sqlScript.foreach(EntityConstants.ENTITY_LIST, EntityConstants.ENTITY, SQLConstants.COMMA + SQLConstants.BLANK, () -> sqlOfInsert(table, fickleKeyColumns));
            SqlBuilder valueSqlBuilder = SqlBuilder.sqlBuilder(valueSqlOfInsert);
            databaseTypeOfSql(tableName, table, valueSqlBuilder, fickleKeyColumns, databaseType);
            return sqlSupply.supply(tableName, table, keySqlBuilder, valueSqlBuilder);
        });
    }

    /**
     * <code>databaseTypeOfSql</code>
     * <p>The database type of sql method.</p>
     * @param tableName     {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param table         {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param sqlBuilder    {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The sql builder parameter is <code>SqlBuilder</code> type.</p>
     * @param fickleColumns {@link java.util.List} <p>The fickle columns parameter is <code>List</code> type.</p>
     * @param databaseType  {@link io.github.nichetoolkit.mybatis.enums.DatabaseType} <p>The database type parameter is <code>DatabaseType</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
     * @see java.util.List
     * @see io.github.nichetoolkit.mybatis.enums.DatabaseType
     * @see io.github.nichetoolkit.rest.RestException
     */
    static void databaseTypeOfSql(String tableName, MybatisTable table, SqlBuilder sqlBuilder, List<MybatisColumn> fickleColumns, DatabaseType databaseType) throws RestException {
        switch (databaseType) {
            case SQLITE:
            case GAUSSDB:
            case POSTGRESQL:
                sqlBuilder.append(insertOfSaveSql(tableName, table, fickleColumns, true));
                break;
            case MYSQL:
                sqlBuilder.append(insertOfSaveSql(tableName, table, fickleColumns, false));
                break;
            default:
                String message = "it is unsupported currently of the " + databaseType.getKey() + "database type.";
                throw new MybatisUnsupportedErrorException(databaseType.getKey(), "save", message);
        }
    }


    /**
     * <code>valueOfParameter</code>
     * <p>The value of parameter method.</p>
     * @param mybatisColumns {@link java.util.Collection} <p>The mybatis columns parameter is <code>Collection</code> type.</p>
     * @param parameter      {@link java.lang.Object} <p>The parameter parameter is <code>Object</code> type.</p>
     * @param parameterType  {@link java.lang.Class} <p>The parameter type parameter is <code>Class</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.util.Collection
     * @see java.lang.Object
     * @see java.lang.Class
     * @see io.github.nichetoolkit.rest.RestException
     */
    static void valueOfParameter(Collection<MybatisColumn> mybatisColumns, Object parameter, Class<?> parameterType) throws RestException {
        Boolean isPresent = parameter.getClass() == parameterType;
        OptionalUtils.ofFalse(isPresent, "The type of parameter is not " + parameterType.getName(), parameterType.getName(), MybatisParamErrorException::new);
        Boolean logicalOr = RestStream.stream(mybatisColumns).map(MybatisColumn::getField)
                .map(mybatisField -> GeneralUtils.isNotEmpty(mybatisField.get(parameter)))
                .collect(RestCollectors.logicalOr());
        OptionalUtils.ofFalse(logicalOr, "The field values of parameter can not all be empty, " + parameterType.getName(), MybatisParamErrorException::new);
    }

    /**
     * <code>sliceOfColumns</code>
     * <p>The slice of columns method.</p>
     * @param <I>            {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param mybatisColumns {@link java.util.Collection} <p>The mybatis columns parameter is <code>Collection</code> type.</p>
     * @param idList         {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @return {@link java.util.Map} <p>The slice of columns return object is <code>Map</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.util.Collection
     * @see java.util.Map
     * @see io.github.nichetoolkit.rest.RestException
     */
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

    /**
     * <code>sqlOfLoad</code>
     * <p>The sql of load method.</p>
     * @param load {@link io.github.nichetoolkit.mybatis.load.RestLoad} <p>The load parameter is <code>RestLoad</code> type.</p>
     * @return {@link java.lang.String} <p>The sql of load return object is <code>String</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.load.RestLoad
     * @see java.lang.String
     */
    static String sqlOfLoad(RestLoad load) {
        /* "'{\"key\":\"" + loadKey + "\",\"value\":', " + true + ", '}', "; */
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
        sqlBuilder.sQuote().curlyLt();
        if (GeneralUtils.isNotEmpty(load.getKey())) {
            sqlBuilder.dQuote(RestLoad._KEY).colon().dQuote(load.getKey()).comma();
        }
        sqlBuilder.dQuote(RestLoad._VALUE).colon().sQuote().comma()
                .dQuote(true).comma().sQuote(SQLConstants.CURLY_GT).comma();
        return sqlBuilder.toString();
    }

    /**
     * <code>sqlOfFickle</code>
     * <p>The sql of fickle method.</p>
     * @param columnName {@link java.lang.String} <p>The column name parameter is <code>String</code> type.</p>
     * @param fickleName {@link java.lang.String} <p>The fickle name parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The sql of fickle return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    static String sqlOfFickle(String columnName, String fickleName) {
        /* "'{\"key\":\"" + columnName + "\",\"name\":\"" + fickleName + "\",\"value\":', " + columnName + ", '}', "; */
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
        sqlBuilder.sQuote().curlyLt().dQuote(RestFickle._KEY).colon().dQuote(columnName).comma()
                .dQuote(RestFickle._NAME).colon().dQuote(fickleName).comma()
                .dQuote(RestFickle._VALUE).colon().sQuote().comma()
                .append(columnName).comma().sQuote(SQLConstants.CURLY_GT).comma();
        return sqlBuilder.toString();
    }

    /**
     * <code>keyOfLoad</code>
     * <p>The key of load method.</p>
     * @param table      {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param loadParams {@link io.github.nichetoolkit.mybatis.load.RestLoad} <p>The load params parameter is <code>RestLoad</code> type.</p>
     * @param keyBuilder {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The key builder parameter is <code>SqlBuilder</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see io.github.nichetoolkit.mybatis.load.RestLoad
     * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
     * @see io.github.nichetoolkit.rest.RestException
     */
    static void keyOfLoad(MybatisTable table, RestLoad[] loadParams, SqlBuilder keyBuilder) throws RestException {
        MybatisTableStyle mybatisTableStyle = MybatisContextHolder.defaultTableStyle();
        RestOptional.ofEmptyable(loadParams).isNotEmpty(params -> {
            List<String> keys = RestStream.stream(params).filter(RestLoad::getValue).map(RestLoad::getKey).distinct().collect(RestCollectors.toList());
            if (GeneralUtils.isNotEmpty(keys)) {
                String keysJson = JacksonUtils.parseJson(keys);
                keyBuilder.comma().value(keysJson).as(EntityConstants.LOADS);
            }
        });
    }

    /**
     * <code>keyOfFickle</code>
     * <p>The key of fickle method.</p>
     * @param table        {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param fickleParams {@link io.github.nichetoolkit.mybatis.fickle.RestFickle} <p>The fickle params parameter is <code>RestFickle</code> type.</p>
     * @param keyBuilder   {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The key builder parameter is <code>SqlBuilder</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see io.github.nichetoolkit.mybatis.fickle.RestFickle
     * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
     * @see io.github.nichetoolkit.rest.RestException
     */
    static void keyOfFickle(MybatisTable table, RestFickle<?>[] fickleParams, SqlBuilder keyBuilder) throws RestException {
        MybatisTableStyle mybatisTableStyle = MybatisContextHolder.defaultTableStyle();
        RestOptional.ofEmptyable(fickleParams).isNotEmpty(params -> {
            keyBuilder.comma().concat().braceLt().sQuote(SQLConstants.SQUARE_LT).comma();
            String ofFickleParams = RestStream.stream(params).map(fickle -> {
                String fickleName = fickle.getName();
                String columnName = RestOptional.ofEmptyable(fickle.getKey()).orElse(mybatisTableStyle.columnName(fickle));
                return sqlOfFickle(columnName, fickleName);
            }).collect(RestCollectors.joining(SqlBuilder.sqlBuilder().sQuote(SQLConstants.COMMA).comma()));
            keyBuilder.append(ofFickleParams).sQuote(SQLConstants.SQUARE_GT).braceGt().as(table.fickleValueColumn().columnName());
        });
    }

    /**
     * <code>valueOfLinkId</code>
     * <p>The value of link id method.</p>
     * @param table        {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param linkId       {@link java.lang.Object} <p>The link id parameter is <code>Object</code> type.</p>
     * @param linkName     {@link java.lang.String} <p>The link name parameter is <code>String</code> type.</p>
     * @param valueBuilder {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The value builder parameter is <code>SqlBuilder</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.lang.Object
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
     * @see io.github.nichetoolkit.rest.RestException
     */
    static void valueOfLinkId(MybatisTable table, Object linkId, String linkName, SqlBuilder valueBuilder) throws RestException {
        if (GeneralUtils.isNotEmpty(linkName)) {
            Optional.ofNullable(table.linkColumn(linkName)).ifPresent(column -> valueBuilder.append(column.columnName()).eq().value(linkId));
        } else {
            if (table.isSpecialLinkage()) {
                valueOfParameter(table.linkageColumns(), linkId, table.getLinkageType());
                String linkageSql = sqlOfColumns(linkId, table.getLinkageColumns(), true, true);
                valueBuilder.append(linkageSql);
            } else {
                Optional.ofNullable(table.getLinkColumn()).ifPresent(column -> valueBuilder.append(column.columnEqualsProperty()));
            }
        }
    }


    /**
     * <code>valuesOfLinkId</code>
     * <p>The values of link id method.</p>
     * @param <L>          {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param table        {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param linkIdList   {@link java.util.Collection} <p>The link id list parameter is <code>Collection</code> type.</p>
     * @param linkName     {@link java.lang.String} <p>The link name parameter is <code>String</code> type.</p>
     * @param valueBuilder {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The value builder parameter is <code>SqlBuilder</code> type.</p>
     * @param sqlScript    {@link io.github.nichetoolkit.mybatis.MybatisSqlScript} <p>The sql script parameter is <code>MybatisSqlScript</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.util.Collection
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <L> void valuesOfLinkId(MybatisTable table, Collection<L> linkIdList, String linkName, SqlBuilder valueBuilder, MybatisSqlScript sqlScript) throws RestException {
        if (GeneralUtils.isNotEmpty(linkName)) {
            Optional.ofNullable(table.linkColumn(linkName)).ifPresent(column -> {
                valueBuilder.append(column.columnName()).in().braceLt();
                linkIdList.forEach(linkId -> valueBuilder.value(linkId).comma());
                valueBuilder.delete(valueBuilder.length() - 2).braceGt();
            });
        } else {
            if (table.isSpecialLinkage()) {
                RestStream.stream(linkIdList).forEach(linkId -> valueOfParameter(table.linkageColumns(), linkId, table.getLinkageType()));
                Map<Integer, List<L>> sliceOfColumnsMap = sliceOfColumns(table.linkageColumns(), linkIdList);
                valueBuilder.append(sqlOfColumns(sliceOfColumnsMap, table.linkageColumns(), sqlScript));
            } else {
                Optional.ofNullable(table.getLinkColumn()).ifPresent(column -> {
                    valueBuilder.append(column.columnName()).in().braceLt();
                    linkIdList.forEach(linkId -> valueBuilder.value(linkId).comma());
                    valueBuilder.delete(valueBuilder.length() - 2).braceGt();
                });
            }
        }
    }

    /**
     * <code>valuesOfParams</code>
     * <p>The values of params method.</p>
     * @param <I>          {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param table        {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param params       {@link io.github.nichetoolkit.mybatis.load.RestParam} <p>The params parameter is <code>RestParam</code> type.</p>
     * @param valueBuilder {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The value builder parameter is <code>SqlBuilder</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see io.github.nichetoolkit.mybatis.load.RestParam
     * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <I> void valuesOfParams(MybatisTable table, RestParam[] params, SqlBuilder valueBuilder) throws RestException {
        String collect = RestStream.stream(params).map(param -> SqlBuilder.sqlBuilder().append(param.getKey()).eq().value(param.getValue()).toString())
                .collect(RestCollectors.joining(SQLConstants.BLANK + SQLConstants.AND + SQLConstants.BLANK));
        valueBuilder.append(collect);
    }

    /**
     * <code>valueOfIdentity</code>
     * <p>The value of identity method.</p>
     * @param table        {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param identity     {@link java.lang.Object} <p>The identity parameter is <code>Object</code> type.</p>
     * @param valueBuilder {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The value builder parameter is <code>SqlBuilder</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.lang.Object
     * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
     * @see io.github.nichetoolkit.rest.RestException
     */
    static void valueOfIdentity(MybatisTable table, Object identity, SqlBuilder valueBuilder) throws RestException {
        if (table.isSpecialIdentity()) {
            valueOfParameter(table.identityColumns(), identity, table.getIdentityType());
            String identitySql = sqlOfColumns(identity, table.identityColumns(), true, true);
            valueBuilder.append(identitySql);
        } else {
            Optional.ofNullable(table.getIdentityColumn()).ifPresent(column -> valueBuilder.append(column.columnEqualsProperty()));
        }
    }

    /**
     * <code>valuesOfIdentity</code>
     * <p>The values of identity method.</p>
     * @param <I>          {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param table        {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param idList       {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @param valueBuilder {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The value builder parameter is <code>SqlBuilder</code> type.</p>
     * @param sqlScript    {@link io.github.nichetoolkit.mybatis.MybatisSqlScript} <p>The sql script parameter is <code>MybatisSqlScript</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.util.Collection
     * @see io.github.nichetoolkit.mybatis.builder.SqlBuilder
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <I> void valuesOfIdentity(MybatisTable table, Collection<I> idList, SqlBuilder valueBuilder, MybatisSqlScript sqlScript) throws RestException {
        if (table.isSpecialIdentity()) {
            RestStream.stream(idList).forEach(id -> valueOfParameter(table.identityColumns(), id, table.getIdentityType()));
            Map<Integer, List<I>> sliceOfColumnsMap = sliceOfColumns(table.identityColumns(), idList);
            valueBuilder.append(sqlOfColumns(sliceOfColumnsMap, table.identityColumns(), sqlScript));
        } else {
            valueBuilder.append(table.getIdentityColumn().columnName()).in().braceLt();
            idList.forEach(id -> valueBuilder.value(id).comma());
            valueBuilder.delete(valueBuilder.length() - 2).braceGt();
        }
    }

    /**
     * <code>sqlOfColumns</code>
     * <p>The sql of columns method.</p>
     * @param <I>            {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param parameter      I <p>The parameter parameter is <code>I</code> type.</p>
     * @param mybatisColumns {@link java.util.Collection} <p>The mybatis columns parameter is <code>Collection</code> type.</p>
     * @param andOrComma     boolean <p>The and or comma parameter is <code>boolean</code> type.</p>
     * @param isEquals       boolean <p>The is equals parameter is <code>boolean</code> type.</p>
     * @return {@link java.lang.String} <p>The sql of columns return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.util.Collection
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
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

    /**
     * <code>sqlOfColumns</code>
     * <p>The sql of columns method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param columnsSliceMap {@link java.util.Map} <p>The columns slice map parameter is <code>Map</code> type.</p>
     * @param columns         {@link java.util.List} <p>The columns parameter is <code>List</code> type.</p>
     * @param sqlScript       {@link io.github.nichetoolkit.mybatis.MybatisSqlScript} <p>The sql script parameter is <code>MybatisSqlScript</code> type.</p>
     * @return {@link java.lang.String} <p>The sql of columns return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.util.Map
     * @see java.util.List
     * @see io.github.nichetoolkit.mybatis.MybatisSqlScript
     * @see java.lang.String
     * @see java.lang.SuppressWarnings
     * @see io.github.nichetoolkit.rest.RestException
     */
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
                            sqlBuilder.deleteLastChar(2);
                            if (isMultiColumns) {
                                sqlBuilder.braceGt().comma();
                            } else {
                                sqlBuilder.comma();
                            }
                        });
                        sqlBuilder.deleteLastChar(2).braceGt();
                    }
                }
                sqlBuilder.or();
            }
        }
        sqlBuilder.delete(sqlBuilder.length() - 4).braceGt();
        return sqlBuilder.toString();
    }

    /**
     * <code>insertOfSaveSql</code>
     * <p>The insert of save sql method.</p>
     * @param tableName           {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param table               {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param fickleColumns       {@link java.util.List} <p>The fickle columns parameter is <code>List</code> type.</p>
     * @param conflictOrDuplicate boolean <p>The conflict or duplicate parameter is <code>boolean</code> type.</p>
     * @return {@link java.lang.String} <p>The insert of save sql return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.lang.Nullable
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.util.List
     * @see io.github.nichetoolkit.rest.RestException
     */
    static String insertOfSaveSql(@Nullable String tableName, MybatisTable table, List<MybatisColumn> fickleColumns, boolean conflictOrDuplicate) throws RestException {
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
        optionalUpdate.isNotEmpty(updateColumns -> {
            String collect = RestStream.stream(updateColumns).map(column -> column.excluded(excludedType, table.tableName(tableName)))
                    .collect(RestCollectors.joining(SQLConstants.COMMA + SQLConstants.BLANK + SQLConstants.LINEFEED));
            sqlBuilder.append(collect);
        });
        return sqlBuilder.toString();
    }

    /**
     * <code>fickleOfEntityColumns</code>
     * <p>The fickle of entity columns method.</p>
     * @param <E>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param table           {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param entityParameter E <p>The entity parameter parameter is <code>E</code> type.</p>
     * @return {@link java.util.List} <p>The fickle of entity columns return object is <code>List</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.util.List
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <E> List<MybatisColumn> fickleOfEntityColumns(String tableName, MybatisTable table, E entityParameter) throws RestException {
        List<MybatisColumn> fickleKeyColumns = new ArrayList<>();
        if (GeneralUtils.isNotEmpty(table.fickleValueColumn())) {
            MybatisTableMapper tableMapper = ApplicationContextHolder.beanOfType(MybatisTableMapper.class);
            List<String> tableColumns = Collections.emptyList();
            if (GeneralUtils.isNotEmpty(tableMapper)) {
                tableColumns = tableMapper.findTableColumns(tableName);
            }
            fickleOfEntityKeyColumns(table, tableColumns, entityParameter, fickleKeyColumns);
            fickleOfEntityValueColumns(table, fickleKeyColumns, entityParameter);
        }
        return fickleKeyColumns;
    }

    /**
     * <code>fickleOfEntitiesColumns</code>
     * <p>The fickle of entities columns method.</p>
     * @param <E>        {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param tableName  {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param table      {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param entityList {@link java.util.Collection} <p>The entity list parameter is <code>Collection</code> type.</p>
     * @return {@link java.util.List} <p>The fickle of entities columns return object is <code>List</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.util.Collection
     * @see java.util.List
     * @see io.github.nichetoolkit.rest.RestException
     */
    static <E> List<MybatisColumn> fickleOfEntitiesColumns(String tableName, MybatisTable table, Collection<E> entityList) throws RestException {
        List<MybatisColumn> fickleKeyColumns = new ArrayList<>();
        if (GeneralUtils.isNotEmpty(table.fickleValueColumn())) {
            MybatisTableMapper tableMapper = ApplicationContextHolder.beanOfType(MybatisTableMapper.class);
            List<String> tableColumns = new ArrayList<>();
            if (GeneralUtils.isNotEmpty(tableMapper)) {
                List<String> tableColumnsList = tableMapper.findTableColumns(tableName);
                tableColumns.addAll(tableColumnsList);
            }
            if (GeneralUtils.isNotEmpty(entityList)) {
                RestStream.stream(entityList).forEach(entity -> fickleOfEntityKeyColumns(table, tableColumns, entity, fickleKeyColumns));
                RestStream.stream(entityList).forEach(entity -> fickleOfEntityValueColumns(table, fickleKeyColumns, entity));
            }
        }
        return fickleKeyColumns;
    }

    /**
     * <code>fickleOfEntityKeyColumns</code>
     * <p>The fickle of entity key columns method.</p>
     * @param <E>              {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param table            {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param tableColumns     {@link java.util.List} <p>The table columns parameter is <code>List</code> type.</p>
     * @param entityParameter  E <p>The entity parameter parameter is <code>E</code> type.</p>
     * @param fickleKeyColumns {@link java.util.List} <p>The fickle key columns parameter is <code>List</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.util.List
     * @see java.lang.SuppressWarnings
     * @see io.github.nichetoolkit.rest.RestException
     */
    @SuppressWarnings("unchecked")
    static <E> void fickleOfEntityKeyColumns(MybatisTable table, List<String> tableColumns, E entityParameter, List<MybatisColumn> fickleKeyColumns) throws RestException {
        if (GeneralUtils.isNotEmpty(table.fickleValueColumn()) && GeneralUtils.isNotEmpty(tableColumns)) {
            Object entity = reviseParameter(entityParameter);
            MybatisColumn fickleKeyColumn = table.fickleKeyColumn();
            MybatisField fickleKeyField = fickleKeyColumn.getField();
            Object fickleKeyObject = fickleKeyField.get(entity);
            JavaType fickleType = fickleKeyColumn.getFickleType();
            Collection<MybatisColumn> fickleColumnsList = Collections.emptyList();
            MybatisTableStyle tableStyle = MybatisTableStyle.style(table.getStyleName());
            if (fickleType instanceof CollectionType && fickleKeyObject instanceof Collection) {
                Collection<RestFickle<?>> fickleCollection = (Collection<RestFickle<?>>) fickleKeyObject;
                List<RestFickle<?>> fickleFieldsList = new ArrayList<>(fickleCollection);
                if (GeneralUtils.isNotEmpty(fickleFieldsList)) {
                    fickleFieldsList.stream()
                            .filter(fickleField -> {
                                if (GeneralUtils.isNotEmpty(fickleField.getKey())) {
                                    return tableColumns.contains(fickleField.getKey());
                                } else {
                                    String fieldName = fickleField.getName();
                                    String columnName = tableStyle.columnName(fieldName);
                                    return tableColumns.contains(columnName);
                                }
                            })
                            .forEach(fickleField -> {
                                MybatisColumn fickleColumn = MybatisColumn.of(table, fickleKeyColumn, fickleField);
                                if (!fickleKeyColumns.contains(fickleColumn)) {
                                    fickleKeyColumns.add(fickleColumn);
                                }
                            });

                }
            } else if (fickleType instanceof MapType && fickleKeyObject instanceof Map) {
                Map<String, RestFickle<?>> fickleMap = (Map<String, RestFickle<?>>) fickleKeyObject;
                Map<String, RestFickle<?>> fickleFieldsMap = new HashMap<>(fickleMap);
                if (GeneralUtils.isNotEmpty(fickleFieldsMap)) {
                    fickleFieldsMap.values().stream()
                            .filter(fickleField -> {
                                if (GeneralUtils.isNotEmpty(fickleField.getKey())) {
                                    return tableColumns.contains(fickleField.getKey());
                                } else {
                                    String fieldName = fickleField.getName();
                                    String columnName = tableStyle.columnName(fieldName);
                                    return tableColumns.contains(columnName);
                                }
                            })
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

    /**
     * <code>fickleOfEntityValueColumns</code>
     * <p>The fickle of entity value columns method.</p>
     * @param <E>              {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param table            {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param fickleKeyColumns {@link java.util.List} <p>The fickle key columns parameter is <code>List</code> type.</p>
     * @param entityParameter  E <p>The entity parameter parameter is <code>E</code> type.</p>
     * @return {@link java.util.List} <p>The fickle of entity value columns return object is <code>List</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.util.List
     * @see java.lang.SuppressWarnings
     * @see io.github.nichetoolkit.rest.RestException
     */
    @SuppressWarnings("unchecked")
    static <E> List<MybatisColumn> fickleOfEntityValueColumns(MybatisTable table, List<MybatisColumn> fickleKeyColumns, E entityParameter) throws RestException {
        if (GeneralUtils.isNotEmpty(table.fickleValueColumn()) && GeneralUtils.isNotEmpty(fickleKeyColumns)) {
            Object entity = reviseParameter(entityParameter);
            MybatisColumn fickleValueColumn = table.fickleValueColumn();
            MybatisField fickleKeyField = fickleValueColumn.getField();
            Object fickleKeyObject = fickleKeyField.get(entity);
            JavaType fickleType = fickleValueColumn.getFickleType();
            Collection<MybatisColumn> fickleColumnsList = Collections.emptyList();
            if (fickleType instanceof CollectionType && fickleKeyObject instanceof Collection) {
                Collection<RestFickle<?>> fickleCollection = (Collection<RestFickle<?>>) fickleKeyObject;
                List<RestFickle<?>> fickleFieldsList = new ArrayList<>(fickleCollection);
                if (GeneralUtils.isNotEmpty(fickleFieldsList)) {
                    Map<String, RestFickle<?>> fickleValueFields = fickleFieldsList.stream().collect(Collectors.toMap(fickleField -> {
                        if (GeneralUtils.isNotEmpty(fickleField.getKey())) {
                            return fickleField.getKey();
                        } else {
                            return MybatisTableStyle.columnName(table, fickleField);
                        }
                    }, Function.identity(), (oldValue, newValue) -> newValue));
                    List<RestFickle<?>> fickleFields = new ArrayList<>(fickleKeyColumns.size());
                    fickleKeyColumns.forEach(fickleKeyColumn -> {
                        String column = fickleKeyColumn.getColumn();
                        RestFickle<?> fickleValueField = fickleValueFields.get(column);
                        if (GeneralUtils.isNotEmpty(fickleValueField)) {
                            fickleFields.add(fickleValueField);
                        } else {
                            fickleFields.add(RestFickle.of(column));
                        }
                    });
                    fickleKeyField.set(entity, fickleFields);
                    return fickleFieldsList.stream()
                            .map(fickleField -> MybatisColumn.of(table, fickleValueColumn, fickleField))
                            .collect(Collectors.toList());
                }
            } else if (fickleType instanceof MapType && fickleKeyObject instanceof Map) {
                Map<String, RestFickle<?>> fickleMap = (Map<String, RestFickle<?>>) fickleKeyObject;
                Map<String, RestFickle<?>> fickleFieldsMap = new HashMap<>(fickleMap);
                if (GeneralUtils.isNotEmpty(fickleFieldsMap)) {
                    Map<String, RestFickle<?>> fickleFields = new HashMap<>(fickleKeyColumns.size());
                    fickleKeyColumns.forEach(fickleKeyColumn -> {
                        String column = fickleKeyColumn.getColumn();
                        RestFickle<?> fickleValueField = fickleFieldsMap.get(column);
                        if (GeneralUtils.isNotEmpty(fickleValueField)) {
                            fickleFields.put(column, fickleValueField);
                        } else {
                            fickleFields.put(column, RestFickle.of(column));
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

    /**
     * <code>sqlOfInsert</code>
     * <p>The sql of insert method.</p>
     * @param table            {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param fickleKeyColumns {@link java.util.List} <p>The fickle key columns parameter is <code>List</code> type.</p>
     * @return {@link java.lang.String} <p>The sql of insert return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see java.util.List
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    static String sqlOfInsert(MybatisTable table, List<MybatisColumn> fickleKeyColumns) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder().braceLt();
        String sqlOfInsert = table.insertColumns().stream().map(column -> column.prefixVariable(EntityConstants.ENTITY_PREFIX))
                .collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
        if (GeneralUtils.isNotEmpty(sqlOfInsert)) {
            sqlBuilder.append(sqlOfInsert);
        }
        if (GeneralUtils.isNotEmpty(fickleKeyColumns) && GeneralUtils.isNotEmpty(table.fickleValueColumn())) {
            JavaType fickleType = table.fickleValueColumn().getFickleType();
            String sqlOfFickle = null;
            if (fickleType instanceof CollectionType || fickleType instanceof ArrayType) {
                sqlOfFickle = IntStream.range(0, fickleKeyColumns.size())
                        .mapToObj(index -> {
                            MybatisColumn mybatisColumn = fickleKeyColumns.get(index);
                            return mybatisColumn.prefixVariable(EntityConstants.ENTITY_PREFIX, index);
                        }).collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
            } else if (fickleType instanceof MapType) {
                sqlOfFickle = fickleKeyColumns.stream().map(fickleColumn ->
                                fickleColumn.prefixVariable(EntityConstants.ENTITY_PREFIX) + SQLConstants.PERIOD + EntityConstants.VALUE)
                        .collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
            }
            if (GeneralUtils.isNotEmpty(sqlOfFickle)) {
                sqlBuilder.comma().blank().append(sqlOfFickle);
            }
        }
        return sqlBuilder.braceGt().toString();
    }

}
