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

import java.util.Collection;

/**
 * <code>PostgresOperateProvider</code>
 * <p>The postgres operate provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisSqlProvider
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.stereotype.Component
 * @since Jdk1.8
 */
@Slf4j
@Component
public class PostgresOperateProvider implements MybatisSqlProvider {

    @Override
    public DatabaseType databaseType() {
        return DatabaseType.POSTGRESQL;
    }

    /**
     * <code>operateById</code>
     * <p>The operate by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param id              I <p>The id parameter is <code>I</code> type.</p>
     * @param operate         {@link java.lang.Integer} <p>The operate parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>The operate by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.Integer
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String operateById(ProviderContext providerContext, I id, Integer operate) throws RestException {
        return operateDynamicById(providerContext, null, id, operate);
    }

    /**
     * <code>operateDynamicById</code>
     * <p>The operate dynamic by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param id              I <p>The id parameter is <code>I</code> type.</p>
     * @param operate         {@link java.lang.Integer} <p>The operate parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>The operate dynamic by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.lang.Integer
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String operateDynamicById(ProviderContext providerContext, String tablename, I id, Integer operate) throws RestException {
        OptionalUtils.ofEmpty(id, "the id param of 'operateById' method cannot be empty!", message -> new MybatisTableErrorException("operateById", "id", message));
        OptionalUtils.ofEmpty(operate, "the operate param of 'operateById' method cannot be empty!", message -> new MybatisParamErrorException("operateById", "operate", message));
        String operateColumn = "The operate column of table with 'operateById' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table ->
                OptionalUtils.ofEmpty(table.getOperateColumn(), operateColumn, log, message -> new MybatisTableErrorException("operateById", "operateColumn", message));
        return MybatisSqlProvider.providingOfId(providerContext, tablename, id, tableOptional, OPERATE_SQL_SUPPLY);
    }

    /**
     * <code>operateAll</code>
     * <p>The operate all method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @param operate         {@link java.lang.Integer} <p>The operate parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>The operate all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.util.Collection
     * @see java.lang.Integer
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String operateAll(ProviderContext providerContext, Collection<I> idList, Integer operate) throws RestException {
        return operateDynamicAll(providerContext, null, idList, operate);
    }

    /**
     * <code>operateDynamicAll</code>
     * <p>The operate dynamic all method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @param operate         {@link java.lang.Integer} <p>The operate parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>The operate dynamic all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.util.Collection
     * @see java.lang.Integer
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String operateDynamicAll(ProviderContext providerContext, String tablename, Collection<I> idList, Integer operate) throws RestException {
        OptionalUtils.ofEmpty(idList, "The id list param of 'operateAll' method cannot be empty!", message -> new MybatisParamErrorException("operateAll", "idList", message));
        OptionalUtils.ofEmpty(operate, "The operate param of 'operateAll' method cannot be empty!", message -> new MybatisParamErrorException("operateAll", "operate", message));
        String operateColumn = "The operate column of table with 'operateAll' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table ->
                OptionalUtils.ofEmpty(table.getOperateColumn(), operateColumn, log, message -> new MybatisTableErrorException("operateAll", "operateColumn", message));
        return MybatisSqlProvider.providingOfAll(providerContext, tablename, idList, tableOptional, OPERATE_SQL_SUPPLY);
    }

    /**
     * <code>operateAllByWhere</code>
     * <p>The operate all by where method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param whereSql        {@link java.lang.String} <p>The where sql parameter is <code>String</code> type.</p>
     * @param operate         {@link java.lang.Integer} <p>The operate parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>The operate all by where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.lang.Integer
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String operateAllByWhere(ProviderContext providerContext, String whereSql, Integer operate) throws RestException {
        return operateDynamicAllByWhere(providerContext, null, whereSql, operate);
    }

    /**
     * <code>operateDynamicAllByWhere</code>
     * <p>The operate dynamic all by where method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param whereSql        {@link java.lang.String} <p>The where sql parameter is <code>String</code> type.</p>
     * @param operate         {@link java.lang.Integer} <p>The operate parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>The operate dynamic all by where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.lang.Integer
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String operateDynamicAllByWhere(ProviderContext providerContext, String tablename, String whereSql, Integer operate) throws RestException {
        OptionalUtils.ofEmpty(whereSql, "The where sql param of 'operateAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("operateAllByWhere", "whereSql", message));
        OptionalUtils.ofEmpty(operate, "The operate param of 'operateAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("operateAllByWhere", "operate", message));
        String operateColumn = "The operate column of table with 'operateAllByWhere' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table ->
                OptionalUtils.ofEmpty(table.getOperateColumn(), operateColumn, log, message -> new MybatisTableErrorException("operateAllByWhere", "operateColumn", message));
        return MybatisSqlProvider.providingOfWhere(providerContext, tablename, whereSql, tableOptional, OPERATE_SQL_SUPPLY);
    }


}
