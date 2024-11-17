package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.rice.enums.DatabaseType;
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
 * <code>MybatisOperateLinkProvider</code>
 * <p>The mybatis operate link provider class.</p>
 * @see  io.github.nichetoolkit.mybatis.MybatisSqlProvider
 * @see  lombok.extern.slf4j.Slf4j
 * @see  org.springframework.stereotype.Component
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Slf4j
@Component
public class MybatisOperateLinkProvider implements MybatisSqlProvider {

    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB,DatabaseType.SQLITE);
    }

    /**
     * <code>operateByLinkId</code>
     * <p>The operate by link id method.</p>
     * @param <L>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param linkId L <p>The link id parameter is <code>L</code> type.</p>
     * @param operate {@link java.lang.Integer} <p>The operate parameter is <code>Integer</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.Integer
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The operate by link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static <L> String operateByLinkId(ProviderContext providerContext, L linkId, Integer operate) throws RestException {
        return operateDynamicByLinkId(providerContext, null, linkId, operate);
    }

    /**
     * <code>operateDynamicByLinkId</code>
     * <p>The operate dynamic by link id method.</p>
     * @param <L>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param linkId L <p>The link id parameter is <code>L</code> type.</p>
     * @param operate {@link java.lang.Integer} <p>The operate parameter is <code>Integer</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  java.lang.Integer
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The operate dynamic by link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static <L> String operateDynamicByLinkId(ProviderContext providerContext,  String tablename, L linkId, Integer operate) throws RestException {
        OptionalUtils.ofEmpty(linkId, "The link id param of 'operateByLinkId' method cannot be empty!", message -> new MybatisTableErrorException("operateByLinkId", "linkId", message));
        OptionalUtils.ofEmpty(operate, "The operate param of 'operateByLinkId' method cannot be empty!", message -> new MybatisParamErrorException("operateByLinkId", "operate", message));
        String operateColumn = "The operate column of table with 'operateByLinkId' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table ->
                OptionalUtils.ofEmpty(table.getLogicColumn(), operateColumn, log, message -> new MybatisTableErrorException("operateByLinkId", "operateColumn", message));
        return MybatisSqlProvider.providingOfLinkId(providerContext, tablename, linkId, tableOptional, OPERATE_SQL_SUPPLY);
    }

    /**
     * <code>operateAllByLinkIds</code>
     * <p>The operate all by link ids method.</p>
     * @param <L>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param linkIdList {@link java.util.Collection} <p>The link id list parameter is <code>Collection</code> type.</p>
     * @param operate {@link java.lang.Integer} <p>The operate parameter is <code>Integer</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.util.Collection
     * @see  java.lang.Integer
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The operate all by link ids return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static <L> String operateAllByLinkIds(ProviderContext providerContext, Collection<L> linkIdList, Integer operate) throws RestException {
        return operateDynamicAllByLinkIds(providerContext, null, linkIdList, operate);
    }

    /**
     * <code>operateDynamicAllByLinkIds</code>
     * <p>The operate dynamic all by link ids method.</p>
     * @param <L>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param linkIdList {@link java.util.Collection} <p>The link id list parameter is <code>Collection</code> type.</p>
     * @param operate {@link java.lang.Integer} <p>The operate parameter is <code>Integer</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  java.util.Collection
     * @see  java.lang.Integer
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The operate dynamic all by link ids return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static <L> String operateDynamicAllByLinkIds(ProviderContext providerContext, String tablename, Collection<L> linkIdList, Integer operate) throws RestException {
        OptionalUtils.ofEmpty(linkIdList, "The link id list param of 'operateAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("operateAllByLinkIds", "linkIdList", message));
        OptionalUtils.ofEmpty(operate, "The operate param of 'operateAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("operateAllByLinkIds", "operate", message));
        String operateColumn = "The operate column of table with 'operateAllByLinkIds' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table ->
                OptionalUtils.ofEmpty(table.getLogicColumn(), operateColumn, log, message -> new MybatisTableErrorException("operateAllByLinkIds", "operateColumn", message));
        return MybatisSqlProvider.providingOfLinkIdAll(providerContext, tablename, linkIdList, tableOptional, OPERATE_SQL_SUPPLY);
    }


}
