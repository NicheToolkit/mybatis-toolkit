package io.github.nichetoolkit.mybatis.provider.natives;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.mybatis.load.RestLoad;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.actuator.ConsumerActuator;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * <code>MybatisFilterLoadProvider</code>
 * <p>The mybatis filter load provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisSqlProvider
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.stereotype.Component
 * @since Jdk1.8
 */
@Slf4j
@Component
public class MybatisFilterLoadProvider implements MybatisSqlProvider {
    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB, DatabaseType.SQLITE);
    }

    /**
     * <code>findAllByLoadWhere</code>
     * <p>The find all by load where method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param whereSql        {@link java.lang.String} <p>The where sql parameter is <code>String</code> type.</p>
     * @param loadParams      {@link io.github.nichetoolkit.mybatis.load.RestLoad} <p>The load params parameter is <code>RestLoad</code> type.</p>
     * @return {@link java.lang.String} <p>The find all by load where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.load.RestLoad
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String findAllByLoadWhere(ProviderContext providerContext, String whereSql, RestLoad[] loadParams) throws RestException {
        return findDynamicAllByLoadWhere(providerContext, null, whereSql, loadParams);
    }

    /**
     * <code>findDynamicAllByLoadWhere</code>
     * <p>The find dynamic all by load where method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param whereSql        {@link java.lang.String} <p>The where sql parameter is <code>String</code> type.</p>
     * @param loadParams      {@link io.github.nichetoolkit.mybatis.load.RestLoad} <p>The load params parameter is <code>RestLoad</code> type.</p>
     * @return {@link java.lang.String} <p>The find dynamic all by load where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.load.RestLoad
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String findDynamicAllByLoadWhere(ProviderContext providerContext, String tablename, String whereSql, RestLoad[] loadParams) throws RestException {
        OptionalUtils.ofEmpty(whereSql, "The where sql param of 'findAllByLoadWhere' method cannot be empty!", log, message -> new MybatisParamErrorException("findAllByLoadWhere", "whereSql", message));
        String selectColumns = "The select columns of table with 'findAllByLoadWhere' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> {
            OptionalUtils.ofEmpty(table.selectColumns(), selectColumns, log, message -> new MybatisTableErrorException("findAllByLoadWhere", "selectColumns", message));
        };
        return MybatisSqlProvider.providingOfWhere(providerContext, tablename, whereSql, tableOptional, loadParams, ENTRY_SQL_SUPPLY);
    }


}
