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
 * <code>MybatisFindLinkProvider</code>
 * <p>The mybatis find link provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisSqlProvider
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.stereotype.Component
 * @since Jdk1.8
 */
@Slf4j
@Component
public class MybatisFindLinkProvider implements MybatisSqlProvider {

    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB, DatabaseType.SQLITE);
    }

    /**
     * <code>findByLinkId</code>
     * <p>The find by link id method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param linkId          L <p>The link id parameter is <code>L</code> type.</p>
     * @param linkName        {@link java.lang.String} <p>The link name parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The find by link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <L> String findByLinkId(ProviderContext providerContext, L linkId, String linkName) throws RestException {
        return findDynamicByLinkId(providerContext, null, linkId, linkName);
    }

    /**
     * <code>findDynamicByLinkId</code>
     * <p>The find dynamic by link id method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param linkId          L <p>The link id parameter is <code>L</code> type.</p>
     * @param linkName        {@link java.lang.String} <p>The link name parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The find dynamic by link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <L> String findDynamicByLinkId(ProviderContext providerContext, String tablename, L linkId, String linkName) throws RestException {
        OptionalUtils.ofEmpty(linkId, "The link id param of 'findByLinkId' method cannot be empty!", log, message -> new MybatisParamErrorException("findByLinkId", "linkId", message));
        String selectColumns = "The select columns of table with 'findByLinkId' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), selectColumns, log,
                message -> new MybatisTableErrorException("findByLinkId", "selectColumns", message));
        return MybatisSqlProvider.providingOfLinkId(providerContext, tablename, linkId, linkName, tableOptional, SELECT_SQL_SUPPLY);
    }

    /**
     * <code>findAllByLinkIds</code>
     * <p>The find all by link ids method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param linkIdList      {@link java.util.Collection} <p>The link id list parameter is <code>Collection</code> type.</p>
     * @param linkName        {@link java.lang.String} <p>The link name parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The find all by link ids return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.util.Collection
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <L> String findAllByLinkIds(ProviderContext providerContext, Collection<L> linkIdList, String linkName) throws RestException {
        return findDynamicAllByLinkIds(providerContext, null, linkIdList, linkName);
    }

    /**
     * <code>findDynamicAllByLinkIds</code>
     * <p>The find dynamic all by link ids method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param linkIdList      {@link java.util.Collection} <p>The link id list parameter is <code>Collection</code> type.</p>
     * @param linkName        {@link java.lang.String} <p>The link name parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The find dynamic all by link ids return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <L> String findDynamicAllByLinkIds(ProviderContext providerContext, String tablename, Collection<L> linkIdList, String linkName) throws RestException {
        OptionalUtils.ofEmpty(linkIdList, "The link id list param of 'findAllByLinkIds' method cannot be empty!", log, message -> new MybatisParamErrorException("findAllByLinkIds", "linkIdList", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), "The select columns of table with 'findAllByLinkIds' method cannot be empty!", message -> new MybatisTableErrorException("findAllByLinkIds", "selectColumns", message));
        return MybatisSqlProvider.providingOfLinkIdAll(providerContext, tablename, linkIdList, linkName, tableOptional, SELECT_SQL_SUPPLY);
    }

}
