package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.mybatis.load.RestLoad;
import io.github.nichetoolkit.mybatis.load.RestParam;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.actuator.ConsumerActuator;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * <code>MybatisFindParamProvider</code>
 * <p>The mybatis find param provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisSqlProvider
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.stereotype.Component
 * @since Jdk17
 */
@Slf4j
@Component
public class MybatisFindParamProvider implements MybatisSqlProvider {

    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB, DatabaseType.SQLITE);
    }

    /**
     * <code>findDynamicAllByIdOrParams</code>
     * <p>The find dynamic all by id or params method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param id              I <p>The id parameter is <code>I</code> type.</p>
     * @param params          {@link io.github.nichetoolkit.mybatis.load.RestParam} <p>The params parameter is <code>RestParam</code> type.</p>
     * @return {@link java.lang.String} <p>The find dynamic all by id or params return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.load.RestParam
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String findDynamicAllByIdOrParams(ProviderContext providerContext, String tableName, I id, RestParam[] params) throws RestException {
        return findDynamicAllLoadByIdOrParams(providerContext, tableName, id, params, null);
    }

    /**
     * <code>findDynamicAllLoadByIdOrParams</code>
     * <p>The find dynamic all load by id or params method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param id              I <p>The id parameter is <code>I</code> type.</p>
     * @param params          {@link io.github.nichetoolkit.mybatis.load.RestParam} <p>The params parameter is <code>RestParam</code> type.</p>
     * @param loadParams      {@link io.github.nichetoolkit.mybatis.load.RestLoad} <p>The load params parameter is <code>RestLoad</code> type.</p>
     * @return {@link java.lang.String} <p>The find dynamic all load by id or params return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.load.RestParam
     * @see io.github.nichetoolkit.mybatis.load.RestLoad
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String findDynamicAllLoadByIdOrParams(ProviderContext providerContext, String tableName, I id, RestParam[] params, RestLoad[] loadParams) throws RestException {
        OptionalUtils.ofTrue(GeneralUtils.isEmpty(id) && GeneralUtils.isEmpty(params), "The id & param of 'findAllByIdOrParams' method cannot be empty!", log, message -> new MybatisParamErrorException("findAllByIdOrParams", "id", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), "The select columns of table with 'findDynamicAllByIdOrParams' method cannot be empty!", message -> new MybatisTableErrorException("findDynamicAllByIdOrParams", "selectColumns", message));
        return MybatisSqlProvider.providingOfIdOrParams(providerContext, tableName, id, tableOptional, params, loadParams, ENTRY_SQL_SUPPLY);
    }

}
