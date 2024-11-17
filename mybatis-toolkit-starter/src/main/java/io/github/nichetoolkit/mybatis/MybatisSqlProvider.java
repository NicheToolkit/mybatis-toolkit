package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.builder.SqlBuilder;
import io.github.nichetoolkit.mybatis.consts.EntityConstants;
import io.github.nichetoolkit.mybatis.consts.SQLConstants;
import io.github.nichetoolkit.mybatis.consts.ScriptConstants;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.enums.ExcludedType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisUnsupportedErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestKey;
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
import java.util.stream.Collectors;

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
     * @return  {@link java.util.List} <p>The database types return object is <code>List</code> type.</p>
     * @see  java.util.List
     */
    List<DatabaseType> databaseTypes();

    /**
     * <code>SELECT_SQL_SUPPLY</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The constant <code>SELECT_SQL_SUPPLY</code> field.</p>
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     */
    MybatisSqlSupply.SimpleSqlSupply SELECT_SQL_SUPPLY = (tablename, table, sqlBuilder) ->
            SqlBuilder.sqlBuilder()
                    .select().append(table.sqlOfSelectColumns())
                    .from().append(table.tablename(tablename))
                    .where().append(sqlBuilder).toString();

    /**
     * <code>SAVE_SQL_SUPPLY</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The constant <code>SAVE_SQL_SUPPLY</code> field.</p>
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     */
    MybatisSqlSupply.SimpleSqlSupply SAVE_SQL_SUPPLY = (tablename, table, sqlBuilder) ->
            SqlBuilder.sqlBuilder()
                    .insert().append(table.tablename(tablename))
                    .braceLt().append(table.sqlOfInsertColumns()).braceGt()
                    .values().append(sqlBuilder).toString();

    /**
     * <code>REMOVE_SQL_SUPPLY</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The constant <code>REMOVE_SQL_SUPPLY</code> field.</p>
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     */
    MybatisSqlSupply.SimpleSqlSupply REMOVE_SQL_SUPPLY = (tablename, table, sqlBuilder) ->
            SqlBuilder.sqlBuilder()
                    .update().append(table.tablename(tablename))
                    .set().append(table.getLogicColumn().columnEqualsProperty())
                    .comma().append(table.sqlOfForceUpdateColumns())
                    .where().append(sqlBuilder).toString();

    /**
     * <code>OPERATE_SQL_SUPPLY</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The constant <code>OPERATE_SQL_SUPPLY</code> field.</p>
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     */
    MybatisSqlSupply.SimpleSqlSupply OPERATE_SQL_SUPPLY = (tablename, table, sqlBuilder) ->
            SqlBuilder.sqlBuilder()
                    .update().append(table.tablename(tablename))
                    .set().append(table.getOperateColumn().columnEqualsProperty())
                    .comma().append(table.sqlOfForceUpdateColumns())
                    .where().append(sqlBuilder).toString();

    /**
     * <code>DELETE_SQL_SUPPLY</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The constant <code>DELETE_SQL_SUPPLY</code> field.</p>
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     */
    MybatisSqlSupply.SimpleSqlSupply DELETE_SQL_SUPPLY = (tablename, table, sqlBuilder) ->
            SqlBuilder.sqlBuilder()
                    .delete().from().append(table.tablename(tablename))
                    .where().append(sqlBuilder).toString();

    /**
     * <code>ALERT_SQL_SUPPLY</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.AlertSqlSupply} <p>The constant <code>ALERT_SQL_SUPPLY</code> field.</p>
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.AlertSqlSupply
     */
    MybatisSqlSupply.AlertSqlSupply ALERT_SQL_SUPPLY = (tablename, table, sqlBuilder, status) ->
            SqlBuilder.sqlBuilder()
                    .update().append(table.tablename(tablename))
                    .set().append(sqlOfStatus(table, status))
                    .comma().append(table.sqlOfForceUpdateColumns())
                    .where().append(sqlBuilder).toString();

    /**
     * <code>reviseParameter</code>
     * <p>The revise parameter method.</p>
     * @param <P>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param parameter P <p>The parameter parameter is <code>P</code> type.</p>
     * @return  {@link java.lang.Object} <p>The revise parameter return object is <code>Object</code> type.</p>
     * @see  java.lang.Object
     * @see  java.lang.SuppressWarnings
     * @see  io.github.nichetoolkit.rest.RestException
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
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

    /**
     * <code>sqlOfStatus</code>
     * <p>The sql of status method.</p>
     * @param <S>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param table {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param status S <p>The status parameter is <code>S</code> type.</p>
     * @see  io.github.nichetoolkit.mybatis.MybatisTable
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The sql of status return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
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

    /**
     * <code>providingOfId</code>
     * <p>The providing of id method.</p>
     * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param idParameter I <p>The id parameter parameter is <code>I</code> type.</p>
     * @param statusParameter S <p>The status parameter parameter is <code>S</code> type.</p>
     * @param sqlSupply {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.AlertSqlSupply} <p>The sql supply parameter is <code>AlertSqlSupply</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  org.springframework.lang.Nullable
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.AlertSqlSupply
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The providing of id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    static <I, S> String providingOfId(ProviderContext providerContext, @Nullable String tablename, I idParameter, S statusParameter, MybatisSqlSupply.AlertSqlSupply sqlSupply) throws RestException {
        Object status = reviseParameter(statusParameter);
        return providingOfId(providerContext, tablename, idParameter, table -> {
        }, (tablenameValue, tableValue, sqlBuilder) -> sqlSupply.supply(tablenameValue, tableValue, sqlBuilder, status));
    }

    /**
     * <code>providingOfAll</code>
     * <p>The providing of all method.</p>
     * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param idList {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @param statusParameter S <p>The status parameter parameter is <code>S</code> type.</p>
     * @param sqlSupply {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.AlertSqlSupply} <p>The sql supply parameter is <code>AlertSqlSupply</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  org.springframework.lang.Nullable
     * @see  java.util.Collection
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.AlertSqlSupply
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The providing of all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    static <I, S> String providingOfAll(ProviderContext providerContext, @Nullable String tablename, Collection<I> idList, S statusParameter, MybatisSqlSupply.AlertSqlSupply sqlSupply) throws RestException {
        Object status = reviseParameter(statusParameter);
        return providingOfAll(providerContext, tablename, idList, table -> {
        }, (tablenameValue, tableValue, sqlBuilder) -> sqlSupply.supply(tablenameValue, tableValue, sqlBuilder, status));
    }

    /**
     * <code>providingOfWhere</code>
     * <p>The providing of where method.</p>
     * @param <S>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param whereSqlParameter {@link java.lang.String} <p>The where sql parameter parameter is <code>String</code> type.</p>
     * @param statusParameter S <p>The status parameter parameter is <code>S</code> type.</p>
     * @param sqlSupply {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.AlertSqlSupply} <p>The sql supply parameter is <code>AlertSqlSupply</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  org.springframework.lang.Nullable
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.AlertSqlSupply
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The providing of where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    static <S> String providingOfWhere(ProviderContext providerContext, @Nullable String tablename, String whereSqlParameter, S statusParameter, MybatisSqlSupply.AlertSqlSupply sqlSupply) throws RestException {
        Object status = reviseParameter(statusParameter);
        return providingOfWhere(providerContext, tablename, whereSqlParameter, table -> {
        }, (tablenameValue, tableValue, sqlBuilder) -> sqlSupply.supply(tablenameValue, tableValue, sqlBuilder, status));
    }

    /**
     * <code>providingOfLinkId</code>
     * <p>The providing of link id method.</p>
     * @param <L>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param linkIdParameter L <p>The link id parameter parameter is <code>L</code> type.</p>
     * @param statusParameter S <p>The status parameter parameter is <code>S</code> type.</p>
     * @param sqlSupply {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.AlertSqlSupply} <p>The sql supply parameter is <code>AlertSqlSupply</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  org.springframework.lang.Nullable
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.AlertSqlSupply
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The providing of link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    static <L, S> String providingOfLinkId(ProviderContext providerContext, @Nullable String tablename, L linkIdParameter, S statusParameter, MybatisSqlSupply.AlertSqlSupply sqlSupply) throws RestException {
        Object status = reviseParameter(statusParameter);
        return providingOfLinkId(providerContext, tablename, linkIdParameter, table -> {
        }, (tablenameValue, tableValue, sqlBuilder) -> sqlSupply.supply(tablenameValue, tableValue, sqlBuilder, status));
    }

    /**
     * <code>providingOfLinkIdAll</code>
     * <p>The providing of link id all method.</p>
     * @param <L>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param linkIdList {@link java.util.Collection} <p>The link id list parameter is <code>Collection</code> type.</p>
     * @param statusParameter S <p>The status parameter parameter is <code>S</code> type.</p>
     * @param sqlSupply {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.AlertSqlSupply} <p>The sql supply parameter is <code>AlertSqlSupply</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  org.springframework.lang.Nullable
     * @see  java.util.Collection
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.AlertSqlSupply
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The providing of link id all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    static <L, S> String providingOfLinkIdAll(ProviderContext providerContext, @Nullable String tablename, Collection<L> linkIdList, S statusParameter, MybatisSqlSupply.AlertSqlSupply sqlSupply) throws RestException {
        Object status = reviseParameter(statusParameter);
        return providingOfLinkIdAll(providerContext, tablename, linkIdList, table -> {
        }, (tablenameValue, tableValue, sqlBuilder) -> sqlSupply.supply(tablenameValue, tableValue, sqlBuilder, status));
    }

    /**
     * <code>providingOfLinkId</code>
     * <p>The providing of link id method.</p>
     * @param <L>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param linkIdParameter L <p>The link id parameter parameter is <code>L</code> type.</p>
     * @param tableOptional {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  org.springframework.lang.Nullable
     * @see  io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see  java.lang.SuppressWarnings
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The providing of link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
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

    /**
     * <code>providingOfLinkIdAll</code>
     * <p>The providing of link id all method.</p>
     * @param <L>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param linkIdList {@link java.util.Collection} <p>The link id list parameter is <code>Collection</code> type.</p>
     * @param tableOptional {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  org.springframework.lang.Nullable
     * @see  java.util.Collection
     * @see  io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see  java.lang.SuppressWarnings
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The providing of link id all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
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

    /**
     * <code>providingOfName</code>
     * <p>The providing of name method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param name {@link java.lang.String} <p>The name parameter is <code>String</code> type.</p>
     * @param tableOptional {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  org.springframework.lang.Nullable
     * @see  io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see  java.lang.SuppressWarnings
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The providing of name return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    @SuppressWarnings("Duplicates")
    static String providingOfName(ProviderContext providerContext, @Nullable String tablename, String name, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            String nameSql = Optional.ofNullable(table.fieldColumn(EntityConstants.NAME)).map(MybatisColumn::columnEqualsProperty).orElse(ScriptConstants.NAME_EQUALS_PROPERTY);
            SqlBuilder sqlBuilder = new SqlBuilder(nameSql);
            Optional.ofNullable(table.getLogicColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnEqualsProperty()));
            return sqlSupply.supply(tablename, table, sqlBuilder);
        });
    }

    /**
     * <code>providingOfName</code>
     * <p>The providing of name method.</p>
     * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param name {@link java.lang.String} <p>The name parameter is <code>String</code> type.</p>
     * @param idParameter I <p>The id parameter parameter is <code>I</code> type.</p>
     * @param tableOptional {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  org.springframework.lang.Nullable
     * @see  io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see  java.lang.SuppressWarnings
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The providing of name return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
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
            Optional.ofNullable(table.getLogicColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnEqualsProperty()));
            return sqlSupply.supply(tablename, table, sqlBuilder);
        });
    }

    /**
     * <code>providingOfEntity</code>
     * <p>The providing of entity method.</p>
     * @param <E>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param entityParameter E <p>The entity parameter parameter is <code>E</code> type.</p>
     * @param tableOptional {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  org.springframework.lang.Nullable
     * @see  io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see  java.lang.SuppressWarnings
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The providing of entity return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
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
            Optional.ofNullable(table.getLogicColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnEqualsProperty()));
            return sqlSupply.supply(tablename, table, sqlBuilder);
        });
    }

    /**
     * <code>providingOfEntity</code>
     * <p>The providing of entity method.</p>
     * @param <E>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param entityParameter E <p>The entity parameter parameter is <code>E</code> type.</p>
     * @param idParameter I <p>The id parameter parameter is <code>I</code> type.</p>
     * @param tableOptional {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  org.springframework.lang.Nullable
     * @see  io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see  java.lang.SuppressWarnings
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The providing of entity return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
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
            Optional.ofNullable(table.getLogicColumn()).ifPresent(column -> sqlBuilder.and().append(column.columnEqualsProperty()));
            return sqlSupply.supply(tablename, table, sqlBuilder);
        });
    }

    /**
     * <code>providingOfSave</code>
     * <p>The providing of save method.</p>
     * @param <E>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param entityParameter E <p>The entity parameter parameter is <code>E</code> type.</p>
     * @param tableOptional {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  org.springframework.lang.Nullable
     * @see  io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see  java.lang.SuppressWarnings
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The providing of save return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    @SuppressWarnings("Duplicates")
    static <E> String providingOfSave(ProviderContext providerContext, @Nullable String tablename, E entityParameter, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            String sqlOfInsert = table.insertColumns().stream()
                    .map(column -> column.variable(EntityConstants.ENTITY_PREFIX))
                    .collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK));
            SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder().braceLt().append(sqlOfInsert).braceGt();
            databaseTypeOfSql(tablename, table, sqlBuilder, databaseType);
            return sqlSupply.supply(tablename, table, sqlBuilder);
        });
    }

    /**
     * <code>providingOfAllSave</code>
     * <p>The providing of all save method.</p>
     * @param <E>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param entityList {@link java.util.Collection} <p>The entity list parameter is <code>Collection</code> type.</p>
     * @param tableOptional {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  org.springframework.lang.Nullable
     * @see  java.util.Collection
     * @see  io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The providing of all save return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    static <E> String providingOfAllSave(ProviderContext providerContext, @Nullable String tablename, Collection<E> entityList, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
        DatabaseType databaseType = MybatisSqlProviderHolder.defaultDatabaseType();
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            String sqlOfInsert = sqlScript.foreach(EntityConstants.ENTITY_LIST, EntityConstants.ENTITY, SQLConstants.COMMA + SQLConstants.BLANK, () ->
                    SQLConstants.BRACE_LT + table.insertColumns().stream().map(column -> column.variable(EntityConstants.ENTITY_PREFIX))
                            .collect(Collectors.joining(SQLConstants.COMMA + SQLConstants.BLANK)) + SQLConstants.BRACE_GT);
            SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder(sqlOfInsert);
            databaseTypeOfSql(tablename, table, sqlBuilder, databaseType);
            return sqlSupply.supply(tablename, table, sqlBuilder);
        });
    }

    /**
     * <code>databaseTypeOfSql</code>
     * <p>The database type of sql method.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param table {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param sqlBuilder {@link io.github.nichetoolkit.mybatis.builder.SqlBuilder} <p>The sql builder parameter is <code>SqlBuilder</code> type.</p>
     * @param databaseType {@link io.github.nichetoolkit.mybatis.enums.DatabaseType} <p>The database type parameter is <code>DatabaseType</code> type.</p>
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.mybatis.MybatisTable
     * @see  io.github.nichetoolkit.mybatis.builder.SqlBuilder
     * @see  io.github.nichetoolkit.mybatis.enums.DatabaseType
     * @see  io.github.nichetoolkit.rest.RestException
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    static void databaseTypeOfSql(String tablename, MybatisTable table, SqlBuilder sqlBuilder, DatabaseType databaseType) throws RestException {
        switch (databaseType) {
            case SQLITE:
            case GAUSSDB:
            case POSTGRESQL:
                sqlBuilder.append(insertOfSaveSql(tablename, table, true));
                break;
            case MYSQL:
                sqlBuilder.append(insertOfSaveSql(tablename, table, false));
                break;
            default:
                String message = "it is unsupported currently of the " + databaseType.getKey() + "database type.";
                throw new MybatisUnsupportedErrorException(databaseType.getKey(), "save", message);
        }
    }

    /**
     * <code>providingOfId</code>
     * <p>The providing of id method.</p>
     * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param idParameter I <p>The id parameter parameter is <code>I</code> type.</p>
     * @param tableOptional {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  org.springframework.lang.Nullable
     * @see  io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see  java.lang.SuppressWarnings
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The providing of id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
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

    /**
     * <code>providingOfAll</code>
     * <p>The providing of all method.</p>
     * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param idList {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @param tableOptional {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  org.springframework.lang.Nullable
     * @see  java.util.Collection
     * @see  io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The providing of all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
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

    /**
     * <code>providingOfWhere</code>
     * <p>The providing of where method.</p>
     * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param whereSqlParameter {@link java.lang.String} <p>The where sql parameter parameter is <code>String</code> type.</p>
     * @param tableOptional {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional parameter is <code>ConsumerActuator</code> type.</p>
     * @param sqlSupply {@link io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply} <p>The sql supply parameter is <code>SimpleSqlSupply</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  org.springframework.lang.Nullable
     * @see  io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlSupply.SimpleSqlSupply
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The providing of where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    static <I> String providingOfWhere(ProviderContext providerContext, @Nullable String tablename, String whereSqlParameter, ConsumerActuator<MybatisTable> tableOptional, MybatisSqlSupply.SimpleSqlSupply sqlSupply) throws RestException {
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            tableOptional.actuate(table);
            String whereSql = whereSqlParameter;
            if (whereSql.startsWith(SQLConstants.AND_MATCH)) {
                whereSql = whereSql.substring(SQLConstants.AND_MATCH.length());
            }
            SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
            sqlBuilder.cdataLt().append(whereSql).cdataGt();
            return sqlSupply.supply(tablename, table, sqlBuilder);
        });
    }

    /**
     * <code>valueOfParameter</code>
     * <p>The value of parameter method.</p>
     * @param mybatisColumns {@link java.util.Collection} <p>The mybatis columns parameter is <code>Collection</code> type.</p>
     * @param parameter {@link java.lang.Object} <p>The parameter parameter is <code>Object</code> type.</p>
     * @param parameterType {@link java.lang.Class} <p>The parameter type parameter is <code>Class</code> type.</p>
     * @see  java.util.Collection
     * @see  java.lang.Object
     * @see  java.lang.Class
     * @see  io.github.nichetoolkit.rest.RestException
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
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
     * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param mybatisColumns {@link java.util.Collection} <p>The mybatis columns parameter is <code>Collection</code> type.</p>
     * @param idList {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @see  java.util.Collection
     * @see  java.util.Map
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.util.Map} <p>The slice of columns return object is <code>Map</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
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
     * <code>sqlOfColumns</code>
     * <p>The sql of columns method.</p>
     * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param parameter I <p>The parameter parameter is <code>I</code> type.</p>
     * @param mybatisColumns {@link java.util.Collection} <p>The mybatis columns parameter is <code>Collection</code> type.</p>
     * @param andOrComma boolean <p>The and or comma parameter is <code>boolean</code> type.</p>
     * @param isEquals boolean <p>The is equals parameter is <code>boolean</code> type.</p>
     * @see  java.util.Collection
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The sql of columns return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
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
     * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param columnsSliceMap {@link java.util.Map} <p>The columns slice map parameter is <code>Map</code> type.</p>
     * @param columns {@link java.util.List} <p>The columns parameter is <code>List</code> type.</p>
     * @param sqlScript {@link io.github.nichetoolkit.mybatis.MybatisSqlScript} <p>The sql script parameter is <code>MybatisSqlScript</code> type.</p>
     * @see  java.util.Map
     * @see  java.util.List
     * @see  io.github.nichetoolkit.mybatis.MybatisSqlScript
     * @see  java.lang.String
     * @see  java.lang.SuppressWarnings
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The sql of columns return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
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

    /**
     * <code>insertOfSaveSql</code>
     * <p>The insert of save sql method.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param table {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param conflictOrDuplicate boolean <p>The conflict or duplicate parameter is <code>boolean</code> type.</p>
     * @see  java.lang.String
     * @see  org.springframework.lang.Nullable
     * @see  io.github.nichetoolkit.mybatis.MybatisTable
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The insert of save sql return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    static String insertOfSaveSql(@Nullable String tablename, MybatisTable table, boolean conflictOrDuplicate) throws RestException {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
        RestOptional<List<MybatisColumn>> optionalColumns = RestOptional.ofEmptyable(table.updateColumns());
        if (conflictOrDuplicate) {
            sqlBuilder.onConflict().braceLt();
            if (table.isSpecialIdentity()) {
                sqlBuilder.append(table.sqlOfIdentityColumns());
            } else {
                sqlBuilder.append(table.getIdentityColumn().columnName());
            }
            sqlBuilder.braceGt();
        } else {
            sqlBuilder.onDuplicateKey();
        }
        if (optionalColumns.isPresent()) {
            sqlBuilder.doUpdate(conflictOrDuplicate);
            if (conflictOrDuplicate) {
                sqlBuilder.set();
            }
        } else {
            sqlBuilder.doNothing();
        }
        ExcludedType excludedType = MybatisSqlProviderHolder.defaultExcludedType();
        optionalColumns.ifEmptyPresent(updateColumns -> {
            String collect = RestStream.stream(updateColumns).map(column -> column.excluded(excludedType, table.tablename(tablename)))
                    .collect(RestCollectors.joining(SQLConstants.COMMA + SQLConstants.BLANK + SQLConstants.LINEFEED));
            sqlBuilder.append(collect);
        });
        return sqlBuilder.toString();
    }

}
