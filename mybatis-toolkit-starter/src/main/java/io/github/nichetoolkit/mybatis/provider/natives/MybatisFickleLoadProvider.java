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

@Slf4j
@Component
public class MybatisFickleLoadProvider implements MybatisSqlProvider {
    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB, DatabaseType.SQLITE);
    }

    public static <I> String findByIdFickleLoad(ProviderContext providerContext, I id, RestFickle<?>[] fickleParams, RestLoad[] loadParams) throws RestException {
        return findDynamicByIdFickleLoad(providerContext, null, id, fickleParams, loadParams);
    }

    public static <I> String findDynamicByIdFickleLoad(ProviderContext providerContext, String tablename, I id, RestFickle<?>[] fickleParams, RestLoad[] loadParams) throws RestException {
        OptionalUtils.ofEmpty(id, "The id param of 'findByIdFickleLoad' method cannot be empty!", log, message -> new MybatisParamErrorException("findByIdFickleLoad", "id", message));
        String selectColumns = "The select columns of table with 'findByIdFickleLoad' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), selectColumns, log,
                message -> new MybatisTableErrorException("findByIdFickleLoad", "selectColumns", message));
        return MybatisSqlProvider.providingOfId(providerContext, tablename, id, tableOptional, fickleParams, loadParams, ENTRY_SQL_SUPPLY);
    }

    public static <I> String findAllFickleLoad(ProviderContext providerContext, Collection<I> idList, RestFickle<?>[] fickleParams, RestLoad[] loadParams) throws RestException {
        return findDynamicAllFickleLoad(providerContext, null, idList, fickleParams, loadParams);
    }

    public static <I> String findDynamicAllFickleLoad(ProviderContext providerContext, String tablename, Collection<I> idList, RestFickle<?>[] fickleParams, RestLoad[] loadParams) throws RestException {
        OptionalUtils.ofEmpty(idList, "The id list param of 'findAllFickleLoad' method cannot be empty!", log, message -> new MybatisParamErrorException("findAllFickleLoad", "idList", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), "The select columns of table with 'findAllFickleLoad' method cannot be empty!", message -> new MybatisTableErrorException("findAllFickleLoad", "selectColumns", message));
        return MybatisSqlProvider.providingOfAll(providerContext, tablename, idList, tableOptional, fickleParams, loadParams, ENTRY_SQL_SUPPLY);
    }
}
