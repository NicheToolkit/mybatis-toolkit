package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * <code>PostgresDeleteLinkProvider</code>
 * <p>The postgres delete link provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisSqlProvider
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.stereotype.Component
 * @since Jdk1.8
 */
@Slf4j
@Component
public class PostgresDeleteLinkProvider implements MybatisSqlProvider {

    @Override
    public DatabaseType databaseType() {
        return DatabaseType.POSTGRESQL;
    }

    /**
     * <code>deleteByLinkId</code>
     * <p>The delete by link id method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param linkId          L <p>The link id parameter is <code>L</code> type.</p>
     * @return {@link java.lang.String} <p>The delete by link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <L> String deleteByLinkId(ProviderContext providerContext, L linkId) throws RestException {
        return deleteDynamicByLinkId(providerContext, null, linkId);
    }

    /**
     * <code>deleteDynamicByLinkId</code>
     * <p>The delete dynamic by link id method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param linkId          L <p>The link id parameter is <code>L</code> type.</p>
     * @return {@link java.lang.String} <p>The delete dynamic by link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <L> String deleteDynamicByLinkId(ProviderContext providerContext, String tablename, L linkId) throws RestException {
        OptionalUtils.ofEmpty(linkId, "The link id param of 'deleteByLinkId' method cannot be empty!", message -> new MybatisTableErrorException("deleteByLinkId", "linkId", message));
        return MybatisSqlProvider.providingOfLinkId(providerContext, tablename, linkId, table -> {}, DELETE_SQL_SUPPLY);
    }

    /**
     * <code>deleteAllByLinkIds</code>
     * <p>The delete all by link ids method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param linkIdList      {@link java.util.Collection} <p>The link id list parameter is <code>Collection</code> type.</p>
     * @return {@link java.lang.String} <p>The delete all by link ids return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.util.Collection
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <L> String deleteAllByLinkIds(ProviderContext providerContext, Collection<L> linkIdList) throws RestException {
        return deleteDynamicAllByLinkIds(providerContext, null, linkIdList);
    }

    /**
     * <code>deleteDynamicAllByLinkIds</code>
     * <p>The delete dynamic all by link ids method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param linkIdList      {@link java.util.Collection} <p>The link id list parameter is <code>Collection</code> type.</p>
     * @return {@link java.lang.String} <p>The delete dynamic all by link ids return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <L> String deleteDynamicAllByLinkIds(ProviderContext providerContext, String tablename, Collection<L> linkIdList) throws RestException {
        OptionalUtils.ofEmpty(linkIdList, "The link id list param of 'deleteAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("deleteAllByLinkIds", "linkIdList", message));
        return MybatisSqlProvider.providingOfLinkIdAll(providerContext, tablename, linkIdList, table -> {}, DELETE_SQL_SUPPLY);
    }


}
