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
 * <code>MybatisRemoveLinkProvider</code>
 * <p>The mybatis remove link provider class.</p>
 * @see  io.github.nichetoolkit.mybatis.MybatisSqlProvider
 * @see  lombok.extern.slf4j.Slf4j
 * @see  org.springframework.stereotype.Component
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Slf4j
@Component
public class MybatisRemoveLinkProvider implements MybatisSqlProvider {

    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB,DatabaseType.SQLITE);
    }

    /**
     * <code>removeByLinkId</code>
     * <p>The remove by link id method.</p>
     * @param <L>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param linkId L <p>The link id parameter is <code>L</code> type.</p>
     * @param logic {@link java.lang.Object} <p>The logic parameter is <code>Object</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.Object
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The remove by link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static <L> String removeByLinkId(ProviderContext providerContext, L linkId, Object logic) throws RestException {
        return removeDynamicByLinkId(providerContext, null, linkId, logic);
    }

    /**
     * <code>removeDynamicByLinkId</code>
     * <p>The remove dynamic by link id method.</p>
     * @param <L>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param linkId L <p>The link id parameter is <code>L</code> type.</p>
     * @param logic {@link java.lang.Object} <p>The logic parameter is <code>Object</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  java.lang.Object
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The remove dynamic by link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static <L> String removeDynamicByLinkId(ProviderContext providerContext, String tablename, L linkId, Object logic) throws RestException {
        OptionalUtils.ofEmpty(linkId, "The link id param of 'removeByLinkId' method cannot be empty!", message -> new MybatisParamErrorException("removeByLinkId", "linkId", message));
        OptionalUtils.ofEmpty(logic, "The logic param of 'removeByLinkId' method cannot be empty!", message -> new MybatisParamErrorException("removeByLinkId", "logic", message));
        String logicColumn = "The logic column of table with 'removeByLinkId' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table ->
                OptionalUtils.ofEmpty(table.getLogicColumn(), logicColumn, log, message -> new MybatisTableErrorException("removeByLinkId", "logicColumn", message));
        return MybatisSqlProvider.providingOfLinkId(providerContext, tablename, linkId, tableOptional, REMOVE_SQL_SUPPLY);
    }

    /**
     * <code>removeAllByLinkIds</code>
     * <p>The remove all by link ids method.</p>
     * @param <L>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param linkIdList {@link java.util.Collection} <p>The link id list parameter is <code>Collection</code> type.</p>
     * @param logic {@link java.lang.Object} <p>The logic parameter is <code>Object</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.util.Collection
     * @see  java.lang.Object
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The remove all by link ids return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static <L> String removeAllByLinkIds(ProviderContext providerContext, Collection<L> linkIdList, Object logic) throws RestException {
        return removeDynamicAllByLinkIds(providerContext, null, linkIdList, logic);
    }

    /**
     * <code>removeDynamicAllByLinkIds</code>
     * <p>The remove dynamic all by link ids method.</p>
     * @param <L>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param linkIdList {@link java.util.Collection} <p>The link id list parameter is <code>Collection</code> type.</p>
     * @param logic {@link java.lang.Object} <p>The logic parameter is <code>Object</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  java.util.Collection
     * @see  java.lang.Object
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The remove dynamic all by link ids return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static <L> String removeDynamicAllByLinkIds(ProviderContext providerContext, String tablename, Collection<L> linkIdList, Object logic) throws RestException {
        OptionalUtils.ofEmpty(linkIdList, "The link id list param of 'removeAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("removeAllByLinkIds", "linkIdList", message));
        OptionalUtils.ofEmpty(logic, "The logic param of 'removeAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("removeAllByLinkIds", "logic", message));
        String logicColumn = "The logic column of table with 'removeAllByLinkIds' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table ->
                OptionalUtils.ofEmpty(table.getLogicColumn(), logicColumn, log, message -> new MybatisTableErrorException("removeAllByLinkIds", "logicColumn", message));
        return MybatisSqlProvider.providingOfLinkIdAll(providerContext, tablename, linkIdList, tableOptional, REMOVE_SQL_SUPPLY);
    }

}
