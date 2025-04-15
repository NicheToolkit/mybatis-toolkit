package io.github.nichetoolkit.mybatis.provider.natives;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.actuator.ConsumerActuator;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class MybatisFindFickleProvider implements MybatisSqlProvider {
    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB, DatabaseType.SQLITE);
    }

    public static <I> String findByIdFickle(ProviderContext providerContext, I id, String[] fickleParams) throws RestException {
        return findDynamicByIdFickle(providerContext, null, id, fickleParams);
    }

    public static <I> String findDynamicByIdFickle(ProviderContext providerContext, String tablename, I id, String[] fickleParams) throws RestException {
        OptionalUtils.ofEmpty(id, "The id param of 'findByIdFickle' method cannot be empty!", log, message -> new MybatisParamErrorException("findByIdFickle", "id", message));
        String selectColumns = "The select columns of table with 'findByIdFickle' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> {
            if (GeneralUtils.isNotEmpty(fickleParams)) {
                table.fickleColumns(fickleParams);
            } else {
                OptionalUtils.ofEmpty(table.selectColumns(), selectColumns, log, message -> new MybatisTableErrorException("findByIdFickle", "selectColumns", message));
            }
        };
        return MybatisSqlProvider.providingOfId(providerContext, tablename, id,  tableOptional, SELECT_SQL_SUPPLY);
    }

    public static <I> String findAllFickle(ProviderContext providerContext, Collection<I> idList, String[] fickleParams) throws RestException {
        return findDynamicAllFickle(providerContext, null, idList, fickleParams);
    }

    public static <I> String findDynamicAllFickle(ProviderContext providerContext, String tablename, Collection<I> idList, String[] fickleParams) throws RestException {
        OptionalUtils.ofEmpty(idList, "The id list param of 'findAllFickle' method cannot be empty!", log, message -> new MybatisParamErrorException("findAllFickle", "idList", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> {
            if (GeneralUtils.isNotEmpty(fickleParams)) {
                table.fickleColumns(fickleParams);
            } else {
                OptionalUtils.ofEmpty(table.selectColumns(), "The select columns of table with 'findAllFickle' method cannot be empty!", message -> new MybatisTableErrorException("findAllFickle", "selectColumns", message));
            }
        };
        return MybatisSqlProvider.providingOfAll(providerContext, tablename, idList, tableOptional, SELECT_SQL_SUPPLY);
    }
}
