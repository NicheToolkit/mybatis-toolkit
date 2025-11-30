package io.github.nichetoolkit.mybatis.provider.natives;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
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
 * <code>MybatisLinkFickleProvider</code>
 * <p>The mybatis link fickle provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisSqlProvider
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.stereotype.Component
 * @since Jdk17
 */
@Slf4j
@Component
public class MybatisLinkFickleProvider implements MybatisSqlProvider {
    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB, DatabaseType.SQLITE);
    }

    /**
     * <code>findDynamicByLinkIdFickle</code>
     * <p>The find dynamic by link id fickle method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param linkId          L <p>The link id parameter is <code>L</code> type.</p>
     * @param linkName        {@link java.lang.String} <p>The link name parameter is <code>String</code> type.</p>
     * @param fickleParams    {@link io.github.nichetoolkit.mybatis.fickle.RestFickle} <p>The fickle params parameter is <code>RestFickle</code> type.</p>
     * @return {@link java.lang.String} <p>The find dynamic by link id fickle return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.fickle.RestFickle
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <L> String findDynamicByLinkIdFickle(ProviderContext providerContext, String tableName, L linkId, String linkName, RestFickle<?>[] fickleParams) throws RestException {
        OptionalUtils.ofEmpty(linkId, "The link id param of 'findByLinkIdFickle' method cannot be empty!", log, message -> new MybatisParamErrorException("findByLinkIdFickle", "linkId", message));
        String selectColumns = "The select columns of table with 'findByLinkIdFickle' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), selectColumns, log,
                message -> new MybatisTableErrorException("findByLinkIdFickle", "selectColumns", message));
        return MybatisSqlProvider.providingOfLinkId(providerContext, tableName, linkId, linkName, tableOptional, fickleParams, ENTRY_SQL_SUPPLY);
    }

    /**
     * <code>findDynamicAllByLinkIdsFickle</code>
     * <p>The find dynamic all by link ids fickle method.</p>
     * @param <L>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param linkIdList      {@link java.util.Collection} <p>The link id list parameter is <code>Collection</code> type.</p>
     * @param linkName        {@link java.lang.String} <p>The link name parameter is <code>String</code> type.</p>
     * @param fickleParams    {@link io.github.nichetoolkit.mybatis.fickle.RestFickle} <p>The fickle params parameter is <code>RestFickle</code> type.</p>
     * @return {@link java.lang.String} <p>The find dynamic all by link ids fickle return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.github.nichetoolkit.mybatis.fickle.RestFickle
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <L> String findDynamicAllByLinkIdsFickle(ProviderContext providerContext, String tableName, Collection<L> linkIdList, String linkName, RestFickle<?>[] fickleParams) throws RestException {
        OptionalUtils.ofEmpty(linkIdList, "The link id list param of 'findAllByLinkIdsFickle' method cannot be empty!", log, message -> new MybatisParamErrorException("findAllByLinkIdsFickle", "linkIdList", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), "The select columns of table with 'findAllByLinkIdsFickle' method cannot be empty!", message -> new MybatisTableErrorException("findAllByLinkIdsFickle", "selectColumns", message));
        return MybatisSqlProvider.providingOfLinkIdAll(providerContext, tableName, linkIdList, linkName, tableOptional, fickleParams, ENTRY_SQL_SUPPLY);
    }
}
