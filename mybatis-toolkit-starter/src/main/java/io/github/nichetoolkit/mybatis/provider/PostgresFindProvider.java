package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.*;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.actuator.ConsumerActuator;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PostgresFindProvider implements MybatisSqlProvider {

    @Override
    public DatabaseType databaseType() {
        return DatabaseType.POSTGRESQL;
    }

    private static final MybatisSqlSupply FIND_BY_ID = (tablename, table, whereSql, sqlScript) ->
            "SELECT " + table.sqlOfSelectColumns() + " FROM " + table.tablename(tablename) + whereSql;

    private static final MybatisSqlSupply FIND_ALL = (tablename, table, whereSql, sqlScript) ->
            "SELECT " + table.sqlOfSelectColumns() + " FROM " + table.tablename(tablename) + whereSql;

    private static final MybatisSqlSupply FIND_ALL_BY_WHERE = (tablename, table, whereSql, sqlScript) ->
            "SELECT " + table.sqlOfSelectColumns() + " FROM " + table.tablename(tablename)
                    + " WHERE 1=1 " + whereSql;

    public <I> String findById(ProviderContext providerContext, I id) throws RestException {
        return findDynamicById(providerContext, null, id);
    }

    public <I> String findDynamicById(ProviderContext providerContext, String tablename, I id) throws RestException {
        OptionalUtils.ofEmpty(id, "the id param of 'findById' method cannot be empty!",
                message -> new MybatisParamErrorException("findById", "id", message));
        String selectColumns = "the select columns of table with 'findById' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), selectColumns,
                message -> new MybatisTableErrorException("findById", "selectColumns", message));
        return MybatisSqlProvider.providing(providerContext, tablename, id, tableOptional, FIND_BY_ID);
    }

    public <I> String findAll(ProviderContext providerContext, Collection<I> idList) throws RestException {
        return findDynamicAll(providerContext, null, idList);
    }

    public <I> String findDynamicAll(ProviderContext providerContext, String tablename, Collection<I> idList) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(idList), "the id list param of 'findByAll' method cannot be empty!", message -> new MybatisParamErrorException("findByAll", "idList", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), "the select columns of table with 'findByAll' method cannot be empty!", message -> new MybatisTableErrorException("findByAll", "selectColumns", message));
        return MybatisSqlProvider.providing(providerContext, tablename, idList, tableOptional, FIND_ALL);
    }

    public String findAllByWhere(ProviderContext providerContext, String whereSql) throws RestException {
        return findDynamicAllByWhere(providerContext, null, whereSql);
    }

    public String findDynamicAllByWhere(ProviderContext providerContext, String tablename, String whereSql) throws RestException {
        OptionalUtils.ofEmpty(whereSql, "the where sql param of 'findAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("findAllByWhere", "whereSql", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), "the select columns of table with 'findAllByWhere' method cannot be empty!", message -> new MybatisTableErrorException("findAllByWhere", "selectColumns", message));
        return MybatisSqlProvider.providing(providerContext, tablename, whereSql, tableOptional, FIND_ALL_BY_WHERE);
    }


}
