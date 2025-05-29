package io.github.nichetoolkit.mybatis.provider.natives;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.mybatis.load.RestLoad;
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
 * <code>MybatisFickleLoadProvider</code>
 * <p>The mybatis fickle load provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisSqlProvider
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.stereotype.Component
 * @since Jdk1.8
 */
@Slf4j
@Component
public class MybatisFickleLoadProvider implements MybatisSqlProvider {
    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB, DatabaseType.SQLITE);
    }

    /**
     * <code>findDynamicByIdFickleLoad</code>
     * <p>The find dynamic by id fickle load method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param id              I <p>The id parameter is <code>I</code> type.</p>
     * @param fickleParams    {@link io.github.nichetoolkit.mybatis.fickle.RestFickle} <p>The fickle params parameter is <code>RestFickle</code> type.</p>
     * @param loadParams      {@link io.github.nichetoolkit.mybatis.load.RestLoad} <p>The load params parameter is <code>RestLoad</code> type.</p>
     * @return {@link java.lang.String} <p>The find dynamic by id fickle load return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.fickle.RestFickle
     * @see io.github.nichetoolkit.mybatis.load.RestLoad
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String findDynamicByIdFickleLoad(ProviderContext providerContext, String tablename, I id, RestFickle<?>[] fickleParams, RestLoad[] loadParams) throws RestException {
        OptionalUtils.ofEmpty(id, "The id param of 'findByIdFickleLoad' method cannot be empty!", log, message -> new MybatisParamErrorException("findByIdFickleLoad", "id", message));
        String selectColumns = "The select columns of table with 'findByIdFickleLoad' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), selectColumns, log,
                message -> new MybatisTableErrorException("findByIdFickleLoad", "selectColumns", message));
        return MybatisSqlProvider.providingOfId(providerContext, tablename, id, tableOptional, fickleParams, loadParams, ENTRY_SQL_SUPPLY);
    }

    /**
     * <code>findDynamicAllFickleLoad</code>
     * <p>The find dynamic all fickle load method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @param fickleParams    {@link io.github.nichetoolkit.mybatis.fickle.RestFickle} <p>The fickle params parameter is <code>RestFickle</code> type.</p>
     * @param loadParams      {@link io.github.nichetoolkit.mybatis.load.RestLoad} <p>The load params parameter is <code>RestLoad</code> type.</p>
     * @return {@link java.lang.String} <p>The find dynamic all fickle load return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.github.nichetoolkit.mybatis.fickle.RestFickle
     * @see io.github.nichetoolkit.mybatis.load.RestLoad
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String findDynamicAllFickleLoad(ProviderContext providerContext, String tablename, Collection<I> idList, RestFickle<?>[] fickleParams, RestLoad[] loadParams) throws RestException {
        OptionalUtils.ofEmpty(idList, "The id list param of 'findAllFickleLoad' method cannot be empty!", log, message -> new MybatisParamErrorException("findAllFickleLoad", "idList", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), "The select columns of table with 'findAllFickleLoad' method cannot be empty!", message -> new MybatisTableErrorException("findAllFickleLoad", "selectColumns", message));
        return MybatisSqlProvider.providingOfAll(providerContext, tablename, idList, tableOptional, fickleParams, loadParams, ENTRY_SQL_SUPPLY);
    }
}
