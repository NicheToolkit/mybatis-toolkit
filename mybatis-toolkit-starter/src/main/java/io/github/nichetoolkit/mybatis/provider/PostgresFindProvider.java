package io.github.nichetoolkit.mybatis.provider;

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

import java.util.Collection;

@Slf4j
@Component
public class PostgresFindProvider implements MybatisSqlProvider {

    @Override
    public DatabaseType databaseType() {
        return DatabaseType.POSTGRESQL;
    }

    public static <I> String findById(ProviderContext providerContext, I id) throws RestException {
        return findDynamicById(providerContext, null, id);
    }

    public static <I> String findDynamicById(ProviderContext providerContext, String tablename, I id) throws RestException {
        OptionalUtils.ofEmpty(id, "The id param of 'findById' method cannot be empty!", log, message -> new MybatisParamErrorException("findById", "id", message));
        String selectColumns = "The select columns of table with 'findById' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), selectColumns, log,
                message -> new MybatisTableErrorException("findById", "selectColumns", message));
        return MybatisSqlProvider.providing(providerContext, tablename, id, tableOptional, SELECT_SQL_SUPPLY);
    }

    public static <I> String findAll(ProviderContext providerContext, Collection<I> idList) throws RestException {
        return findDynamicAll(providerContext, null, idList);
    }

    public static <I> String findDynamicAll(ProviderContext providerContext, String tablename, Collection<I> idList) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(idList), "The id list param of 'findByAll' method cannot be empty!", log, message -> new MybatisParamErrorException("findByAll", "idList", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), "The select columns of table with 'findByAll' method cannot be empty!", message -> new MybatisTableErrorException("findByAll", "selectColumns", message));
        return MybatisSqlProvider.providing(providerContext, tablename, idList, tableOptional, SELECT_SQL_SUPPLY);
    }

    public static String findAllByWhere(ProviderContext providerContext, String whereSql) throws RestException {
        return findDynamicAllByWhere(providerContext, null, whereSql);
    }

    public static String findDynamicAllByWhere(ProviderContext providerContext, String tablename, String whereSql) throws RestException {
        OptionalUtils.ofEmpty(whereSql, "The where sql param of 'findAllByWhere' method cannot be empty!", log, message -> new MybatisParamErrorException("findAllByWhere", "whereSql", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), "The select columns of table with 'findAllByWhere' method cannot be empty!", log, message -> new MybatisTableErrorException("findAllByWhere", "selectColumns", message));
        return MybatisSqlProvider.providing(providerContext, tablename, whereSql, tableOptional, SELECT_SQL_SUPPLY);
    }


}
