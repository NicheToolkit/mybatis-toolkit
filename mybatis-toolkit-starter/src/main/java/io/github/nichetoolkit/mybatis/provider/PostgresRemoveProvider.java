package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.actuator.ConsumerActuator;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.stereotype.Component;
import java.util.Collection;

@Slf4j
@Component
public class PostgresRemoveProvider implements MybatisSqlProvider {

    @Override
    public DatabaseType databaseType() {
        return DatabaseType.POSTGRESQL;
    }

    public static <I> String removeById(ProviderContext providerContext, I id, Object logic) throws RestException {
        return removeDynamicById(providerContext, null, id, logic);
    }

    public static <I> String removeDynamicById(ProviderContext providerContext, String tablename, I id, Object logic) throws RestException {
        OptionalUtils.ofEmpty(id, "The id param of 'removeById' method cannot be empty!", message -> new MybatisTableErrorException("removeById", "id", message));
        OptionalUtils.ofEmpty(logic, "The logic param of 'removeById' method cannot be empty!", message -> new MybatisParamErrorException("removeById", "logic", message));
        String logicColumn = "The logic column of table with 'removeById' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table ->
                OptionalUtils.ofEmpty(table.getLogicColumn(), logicColumn, log, message -> new MybatisTableErrorException("removeById", "logicColumn", message));
        return MybatisSqlProvider.providingOfIdRemove(providerContext, tablename, id, logic, tableOptional, REMOVE_SQL_SUPPLY);
    }

    public static <I> String removeAll(ProviderContext providerContext, Collection<I> idList,  Object logic) throws RestException {
        return removeDynamicAll(providerContext, null, idList, logic);
    }

    public static <I> String removeDynamicAll(ProviderContext providerContext,  String tablename, Collection<I> idList, Object logic) throws RestException {
        OptionalUtils.ofEmpty(idList, "The id list param of 'removeAll' method cannot be empty!", message -> new MybatisParamErrorException("removeAll", "idList", message));
        OptionalUtils.ofEmpty(logic, "The logic param of 'removeAll' method cannot be empty!", message -> new MybatisParamErrorException("removeAll", "logic", message));
        String logicColumn = "The logic column of table with 'removeAll' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table ->
                OptionalUtils.ofEmpty(table.getLogicColumn(), logicColumn, log, message -> new MybatisTableErrorException("removeAll", "logicColumn", message));
        return MybatisSqlProvider.providingOfAll(providerContext, tablename, idList, tableOptional, REMOVE_SQL_SUPPLY);
    }

    public static String removeAllByWhere(ProviderContext providerContext, String whereSql,Object logic) throws RestException {
        return removeDynamicAllByWhere(providerContext, null, whereSql, logic);
    }

    public static String removeDynamicAllByWhere(ProviderContext providerContext,String tablename, String whereSql, Object logic) throws RestException {
        OptionalUtils.ofEmpty(whereSql, "The where sql param of 'removeAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("removeAllByWhere", "whereSql", message));
        OptionalUtils.ofEmpty(logic, "The logic param of 'removeAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("removeAllByWhere", "logic", message));
        String logicColumn = "The logic column of table with 'removeAllByWhere' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table ->
                OptionalUtils.ofEmpty(table.getLogicColumn(), logicColumn, log, message -> new MybatisTableErrorException("removeAllByWhere", "logicColumn", message));
        return MybatisSqlProvider.providingOfWhere(providerContext, tablename, whereSql, tableOptional, REMOVE_SQL_SUPPLY);
    }

}
