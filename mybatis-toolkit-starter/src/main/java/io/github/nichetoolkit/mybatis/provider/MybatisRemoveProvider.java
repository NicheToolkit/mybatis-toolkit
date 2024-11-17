package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.actuator.ConsumerActuator;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * <code>PostgresRemoveProvider</code>
 * <p>The postgres remove provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisSqlProvider
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.stereotype.Component
 * @since Jdk1.8
 */
@Slf4j
@Component
public class MybatisRemoveProvider implements MybatisSqlProvider {

    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB,DatabaseType.SQLITE);
    }
    /**
     * <code>removeById</code>
     * <p>The remove by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param id              I <p>The id parameter is <code>I</code> type.</p>
     * @param logic           {@link java.lang.Object} <p>The logic parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.String} <p>The remove by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.Object
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String removeById(ProviderContext providerContext, I id, Object logic) throws RestException {
        return removeDynamicById(providerContext, null, id, logic);
    }

    /**
     * <code>removeDynamicById</code>
     * <p>The remove dynamic by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param id              I <p>The id parameter is <code>I</code> type.</p>
     * @param logic           {@link java.lang.Object} <p>The logic parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.String} <p>The remove dynamic by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.lang.Object
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String removeDynamicById(ProviderContext providerContext, String tablename, I id, Object logic) throws RestException {
        OptionalUtils.ofEmpty(id, "The id param of 'removeById' method cannot be empty!", message -> new MybatisTableErrorException("removeById", "id", message));
        OptionalUtils.ofEmpty(logic, "The logic param of 'removeById' method cannot be empty!", message -> new MybatisParamErrorException("removeById", "logic", message));
        String logicColumn = "The logic column of table with 'removeById' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table ->
                OptionalUtils.ofEmpty(table.getLogicColumn(), logicColumn, log, message -> new MybatisTableErrorException("removeById", "logicColumn", message));
        return MybatisSqlProvider.providingOfId(providerContext, tablename, id, tableOptional, REMOVE_SQL_SUPPLY);
    }

    /**
     * <code>removeAll</code>
     * <p>The remove all method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @param logic           {@link java.lang.Object} <p>The logic parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.String} <p>The remove all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.util.Collection
     * @see java.lang.Object
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String removeAll(ProviderContext providerContext, Collection<I> idList,  Object logic) throws RestException {
        return removeDynamicAll(providerContext, null, idList, logic);
    }

    /**
     * <code>removeDynamicAll</code>
     * <p>The remove dynamic all method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @param logic           {@link java.lang.Object} <p>The logic parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.String} <p>The remove dynamic all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.util.Collection
     * @see java.lang.Object
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String removeDynamicAll(ProviderContext providerContext,  String tablename, Collection<I> idList, Object logic) throws RestException {
        OptionalUtils.ofEmpty(idList, "The id list param of 'removeAll' method cannot be empty!", message -> new MybatisParamErrorException("removeAll", "idList", message));
        OptionalUtils.ofEmpty(logic, "The logic param of 'removeAll' method cannot be empty!", message -> new MybatisParamErrorException("removeAll", "logic", message));
        String logicColumn = "The logic column of table with 'removeAll' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table ->
                OptionalUtils.ofEmpty(table.getLogicColumn(), logicColumn, log, message -> new MybatisTableErrorException("removeAll", "logicColumn", message));
        return MybatisSqlProvider.providingOfAll(providerContext, tablename, idList, tableOptional, REMOVE_SQL_SUPPLY);
    }

    /**
     * <code>removeAllByWhere</code>
     * <p>The remove all by where method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param whereSql        {@link java.lang.String} <p>The where sql parameter is <code>String</code> type.</p>
     * @param logic           {@link java.lang.Object} <p>The logic parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.String} <p>The remove all by where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.lang.Object
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String removeAllByWhere(ProviderContext providerContext, String whereSql,Object logic) throws RestException {
        return removeDynamicAllByWhere(providerContext, null, whereSql, logic);
    }

    /**
     * <code>removeDynamicAllByWhere</code>
     * <p>The remove dynamic all by where method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param whereSql        {@link java.lang.String} <p>The where sql parameter is <code>String</code> type.</p>
     * @param logic           {@link java.lang.Object} <p>The logic parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.String} <p>The remove dynamic all by where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.lang.Object
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String removeDynamicAllByWhere(ProviderContext providerContext,String tablename, String whereSql, Object logic) throws RestException {
        OptionalUtils.ofEmpty(whereSql, "The where sql param of 'removeAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("removeAllByWhere", "whereSql", message));
        OptionalUtils.ofEmpty(logic, "The logic param of 'removeAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("removeAllByWhere", "logic", message));
        String logicColumn = "The logic column of table with 'removeAllByWhere' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table ->
                OptionalUtils.ofEmpty(table.getLogicColumn(), logicColumn, log, message -> new MybatisTableErrorException("removeAllByWhere", "logicColumn", message));
        return MybatisSqlProvider.providingOfWhere(providerContext, tablename, whereSql, tableOptional, REMOVE_SQL_SUPPLY);
    }

}
