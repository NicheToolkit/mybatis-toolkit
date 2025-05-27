package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.mybatis.load.RestParam;
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
public class MybatisFindParamProvider implements MybatisSqlProvider {

    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB, DatabaseType.SQLITE);
    }

    public static <I> String findAllByIdOrParams(ProviderContext providerContext, I id, RestParam[] params) throws RestException {
        return findDynamicAllByIdOrParams(providerContext, null, id, params);
    }

    public static <I> String findDynamicAllByIdOrParams(ProviderContext providerContext, String tablename, I id, RestParam[] params) throws RestException {
        OptionalUtils.ofEmpty(id, "The id param of 'findAllByIdOrParams' method cannot be empty!", log, message -> new MybatisParamErrorException("findAllByIdOrParams", "id", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), "The select columns of table with 'findDynamicAllByIdOrParams' method cannot be empty!", message -> new MybatisTableErrorException("findDynamicAllByIdOrParams", "selectColumns", message));
        return MybatisSqlProvider.providingOfIdOrParams(providerContext, tablename, id, tableOptional, params, SELECT_SQL_SUPPLY);
    }

}
