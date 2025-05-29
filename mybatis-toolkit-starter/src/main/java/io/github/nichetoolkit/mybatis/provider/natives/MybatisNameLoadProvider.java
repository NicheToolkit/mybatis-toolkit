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

@Slf4j
@Component
public class MybatisNameLoadProvider implements MybatisSqlProvider {
    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB, DatabaseType.SQLITE);
    }

    public static <L> String findByNameLoad(ProviderContext providerContext, String name, Object logic, RestLoad[] loadParams) throws RestException {
        return findDynamicByNameLoad(providerContext, null, name, logic, loadParams);
    }

    public static <L> String findDynamicByNameLoad(ProviderContext providerContext, String tablename, String name, Object logic, RestLoad[] loadParams) throws RestException {
        OptionalUtils.ofEmpty(name, "The name param of 'findByNameLoad' method cannot be empty!", log, message -> new MybatisParamErrorException("findByNameLoad", "name", message));
        String selectColumns = "The select columns of table with 'findByNameLoad' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), selectColumns, log,
                message -> new MybatisTableErrorException("findByNameLoad", "selectColumns", message));
        return MybatisSqlProvider.providingOfName(providerContext, tablename, name, tableOptional, loadParams, ENTRY_SQL_SUPPLY);
    }

}
