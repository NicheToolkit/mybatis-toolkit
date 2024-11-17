package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * <code>PostgresAlertLinkProvider</code>
 * <p>The postgres alert link provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisSqlProvider
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.stereotype.Component
 * @since Jdk1.8
 */
@Slf4j
@Component
public class MybatisAlertLinkProvider implements MybatisSqlProvider {

    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB);
    }

    /**
     * <code>alertByLinkId</code>
     * <p>The alert by link id method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param linkId          L <p>The link id parameter is <code>L</code> type.</p>
     * @param status          S <p>The status parameter is <code>S</code> type.</p>
     * @return {@link java.lang.String} <p>The alert by link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <L, S> String alertByLinkId(ProviderContext providerContext, L linkId, S status) throws RestException {
        return alertDynamicByLinkId(providerContext, null, linkId, status);
    }

    /**
     * <code>alertDynamicByLinkId</code>
     * <p>The alert dynamic by link id method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param linkId          L <p>The link id parameter is <code>L</code> type.</p>
     * @param status          S <p>The status parameter is <code>S</code> type.</p>
     * @return {@link java.lang.String} <p>The alert dynamic by link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <L, S> String alertDynamicByLinkId(ProviderContext providerContext, String tablename, L linkId, S status) throws RestException {
        OptionalUtils.ofEmpty(linkId, "The link id param of 'alertByLinkId' method cannot be empty!", message -> new MybatisParamErrorException("alertByLinkId", "linkId", message));
        OptionalUtils.ofEmpty(status, "The status param of 'alertByLinkId' method cannot be empty!", message -> new MybatisParamErrorException("alertByLinkId", "status", message));
        return MybatisSqlProvider.providingOfLinkId(providerContext, tablename, linkId, status, ALERT_SQL_SUPPLY);
    }

    /**
     * <code>alertAllByLinkIds</code>
     * <p>The alert all by link ids method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param linkIdList      {@link java.util.Collection} <p>The link id list parameter is <code>Collection</code> type.</p>
     * @param status          S <p>The status parameter is <code>S</code> type.</p>
     * @return {@link java.lang.String} <p>The alert all by link ids return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.util.Collection
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <L, S> String alertAllByLinkIds(ProviderContext providerContext, Collection<L> linkIdList, S status) throws RestException {
        return alertDynamicAllByLinkIds(providerContext, null, linkIdList, status);
    }

    /**
     * <code>alertDynamicAllByLinkIds</code>
     * <p>The alert dynamic all by link ids method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param linkIdList      {@link java.util.Collection} <p>The link id list parameter is <code>Collection</code> type.</p>
     * @param status          S <p>The status parameter is <code>S</code> type.</p>
     * @return {@link java.lang.String} <p>The alert dynamic all by link ids return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <L, S> String alertDynamicAllByLinkIds(ProviderContext providerContext, String tablename, Collection<L> linkIdList, S status) throws RestException {
        OptionalUtils.ofEmpty(linkIdList, "The link id list param of 'alertAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("alertAllByLinkIds", "linkIdList", message));
        OptionalUtils.ofEmpty(status, "The status param of 'alertAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("alertAllByLinkIds", "status", message));
        return MybatisSqlProvider.providingOfLinkIdAll(providerContext, tablename, linkIdList, status, ALERT_SQL_SUPPLY);
    }

}
