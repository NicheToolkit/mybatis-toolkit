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
import java.util.List;

/**
 * <code>MybatisFilterFickleProvider</code>
 * <p>The mybatis filter fickle provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisSqlProvider
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.stereotype.Component
 * @since Jdk17
 */
@Slf4j
@Component
public class MybatisFilterFickleProvider implements MybatisSqlProvider {
    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB, DatabaseType.SQLITE);
    }

    /**
     * <code>findDynamicAllByFickleWhere</code>
     * <p>The find dynamic all by fickle where method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param whereSql        {@link java.lang.String} <p>The where sql parameter is <code>String</code> type.</p>
     * @param fickleParams    {@link io.github.nichetoolkit.mybatis.fickle.RestFickle} <p>The fickle params parameter is <code>RestFickle</code> type.</p>
     * @return {@link java.lang.String} <p>The find dynamic all by fickle where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.fickle.RestFickle
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String findDynamicAllByFickleWhere(ProviderContext providerContext, String tableName, String whereSql, RestFickle<?>[] fickleParams) throws RestException {
        OptionalUtils.ofEmpty(whereSql, "The where sql param of 'findAllByFickleWhere' method cannot be empty!", log, message -> new MybatisParamErrorException("findAllByFickleWhere", "whereSql", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), "The select columns of table with 'findAllByFickleWhere' method cannot be empty!", log, message -> new MybatisTableErrorException("findAllByFickleWhere", "selectColumns", message));
        return MybatisSqlProvider.providingOfWhere(providerContext, tableName, whereSql, tableOptional, fickleParams, ENTRY_SQL_SUPPLY);
    }
}
