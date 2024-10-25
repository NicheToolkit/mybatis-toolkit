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
public class PostgresOperateLinkProvider implements MybatisSqlProvider {

    @Override
    public DatabaseType databaseType() {
        return DatabaseType.POSTGRESQL;
    }

    public static <L> String operateByLinkId(ProviderContext providerContext, L linkId, Integer operate) throws RestException {
        return operateDynamicByLinkId(providerContext, null, linkId, operate);
    }

    public static <L> String operateDynamicByLinkId(ProviderContext providerContext,  String tablename,  L linkId, Integer operate) throws RestException {
        OptionalUtils.ofEmpty(linkId, "The link id param of 'operateByLinkId' method cannot be empty!", message -> new MybatisTableErrorException("operateByLinkId", "linkId", message));
        OptionalUtils.ofEmpty(operate, "The operate param of 'operateByLinkId' method cannot be empty!", message -> new MybatisParamErrorException("operateByLinkId", "operate", message));
        String operateColumn = "The operate column of table with 'operateByLinkId' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table ->
                OptionalUtils.ofEmpty(table.getLogicColumn(), operateColumn, log, message -> new MybatisTableErrorException("operateByLinkId", "operateColumn", message));
        return MybatisSqlProvider.providingOfLinkId(providerContext, tablename, linkId, tableOptional, OPERATE_SQL_SUPPLY);
    }

    public static <L> String operateAllByLinkIds(ProviderContext providerContext, Collection<L> linkIdList, Integer operate) throws RestException {
        return operateDynamicAllByLinkIds(providerContext, null, linkIdList, operate);
    }

    public static <L> String operateDynamicAllByLinkIds(ProviderContext providerContext, String tablename, Collection<L> linkIdList, Integer operate) throws RestException {
        OptionalUtils.ofEmpty(linkIdList, "The link id list param of 'operateAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("operateAllByLinkIds", "linkIdList", message));
        OptionalUtils.ofEmpty(operate, "The operate param of 'operateAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("operateAllByLinkIds", "operate", message));
        String operateColumn = "The operate column of table with 'operateAllByLinkIds' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table ->
                OptionalUtils.ofEmpty(table.getLogicColumn(), operateColumn, log, message -> new MybatisTableErrorException("operateAllByLinkIds", "operateColumn", message));
        return MybatisSqlProvider.providingOfLinkIdAll(providerContext, tablename, linkIdList, tableOptional, OPERATE_SQL_SUPPLY);
    }


}
