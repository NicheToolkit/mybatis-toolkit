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
public class PostgresOperateProvider implements MybatisSqlProvider {

    @Override
    public DatabaseType databaseType() {
        return DatabaseType.POSTGRESQL;
    }

    public static <I> String operateById(ProviderContext providerContext, I id, Integer operate) throws RestException {
        return operateDynamicById(providerContext, null, id, operate);
    }

    public static <I> String operateDynamicById(ProviderContext providerContext, String tablename, I id, Integer operate) throws RestException {
        OptionalUtils.ofEmpty(id, "the id param of 'operateById' method cannot be empty!", message -> new MybatisTableErrorException("operateById", "id", message));
        OptionalUtils.ofEmpty(operate, "the operate param of 'operateById' method cannot be empty!", message -> new MybatisParamErrorException("operateById", "operate", message));
        String operateColumn = "The operate column of table with 'operateById' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table ->
                OptionalUtils.ofEmpty(table.getOperateColumn(), operateColumn, log, message -> new MybatisTableErrorException("operateById", "operateColumn", message));
        return MybatisSqlProvider.providingOfIdOperate(providerContext, tablename, id, operate, tableOptional, OPERATE_SQL_SUPPLY);
    }

    public static <I> String operateAll(ProviderContext providerContext, Collection<I> idList, Integer operate) throws RestException {
        return operateDynamicAll(providerContext, null, idList, operate);
    }

    public static <I> String operateDynamicAll(ProviderContext providerContext, String tablename, Collection<I> idList, Integer operate) throws RestException {
        OptionalUtils.ofEmpty(idList, "the id list param of 'operateAll' method cannot be empty!", message -> new MybatisParamErrorException("operateAll", "idList", message));
        OptionalUtils.ofEmpty(operate, "the operate param of 'operateAll' method cannot be empty!", message -> new MybatisParamErrorException("operateAll", "operate", message));
        String operateColumn = "The operate column of table with 'operateAll' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table ->
                OptionalUtils.ofEmpty(table.getOperateColumn(), operateColumn, log, message -> new MybatisTableErrorException("operateAll", "operateColumn", message));
        return MybatisSqlProvider.providingOfAllOperate(providerContext, tablename, idList, operate, tableOptional, OPERATE_SQL_SUPPLY);
    }

    public static String operateAllByWhere(ProviderContext providerContext, String whereSql, Integer operate) throws RestException {
        return operateDynamicAllByWhere(providerContext, null, whereSql, operate);
    }

    public static String operateDynamicAllByWhere(ProviderContext providerContext, String tablename, String whereSql, Integer operate) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(whereSql), "the where sql param of 'operateAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("operateAllByWhere", "whereSql", message));
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(operate), "the operate param of 'operateAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("operateAllByWhere", "operate", message));
        String operateColumn = "The operate column of table with 'operateAllByWhere' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table ->
                OptionalUtils.ofEmpty(table.getOperateColumn(), operateColumn, log, message -> new MybatisTableErrorException("operateAllByWhere", "operateColumn", message));
        return MybatisSqlProvider.providingOfWhereOperate(providerContext, tablename, whereSql, operate, tableOptional, OPERATE_SQL_SUPPLY);
    }


}
