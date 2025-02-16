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
 * <code>MybatisAlertLinkProvider</code>
 * <p>The mybatis alert link provider class.</p>
 * @see  MybatisSqlProvider
 * @see  Slf4j
 * @see  Component
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Slf4j
@Component
public class MybatisTableProvider implements MybatisSqlProvider {

    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB,DatabaseType.SQLITE);
    }

    public static String tableColumns(ProviderContext providerContext, String tablename) throws RestException {
        OptionalUtils.ofEmpty(tablename, "The tablename param of 'tableColumns' method cannot be empty!", message -> new MybatisParamErrorException("tableColumns", "tablename", message));
        return MybatisSqlProvider.providingOfTablename(providerContext, tablename);
    }

}
