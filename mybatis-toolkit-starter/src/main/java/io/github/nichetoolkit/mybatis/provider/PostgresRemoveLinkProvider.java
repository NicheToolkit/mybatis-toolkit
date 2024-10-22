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
public class PostgresRemoveLinkProvider implements MybatisSqlProvider {

    @Override
    public DatabaseType databaseType() {
        return DatabaseType.POSTGRESQL;
    }

    public static <L> String removeByLinkId(ProviderContext providerContext, L linkId, String logic) throws RestException {
        return removeDynamicByLinkId(providerContext, null, linkId, logic);
    }

    public static <L> String removeDynamicByLinkId(ProviderContext providerContext, String tablename, L linkId, String logic) throws RestException {
        OptionalUtils.ofEmpty(linkId, "The link id param of 'removeByLinkId' method cannot be empty!", message -> new MybatisParamErrorException("removeByLinkId", "linkId", message));
        OptionalUtils.ofEmpty(logic, "The logic param of 'removeByLinkId' method cannot be empty!", message -> new MybatisParamErrorException("removeByLinkId", "logic", message));
        String logicColumn = "The logic column of table with 'removeByLinkId' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table ->
                OptionalUtils.ofEmpty(table.getLogicColumn(), logicColumn, log, message -> new MybatisTableErrorException("removeByLinkId", "logicColumn", message));
        return MybatisSqlProvider.providingOfLinkIdRemove(providerContext, tablename, linkId, logic, tableOptional, REMOVE_SQL_SUPPLY);
    }

    public static <L> String removeAllByLinkIds(ProviderContext providerContext, Collection<L> linkIdList, String logic) throws RestException {
        return removeDynamicAllByLinkIds(providerContext, null, linkIdList, logic);
    }

    public static <L> String removeDynamicAllByLinkIds(ProviderContext providerContext, String tablename, Collection<L> linkIdList, String logic) throws RestException {
        OptionalUtils.ofEmpty(linkIdList, "The link id list param of 'removeAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("removeAllByLinkIds", "idList", message));
        OptionalUtils.ofEmpty(logic, "The logic param of 'removeAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("removeAllByLinkIds", "logic", message));
        String logicColumn = "The logic column of table with 'removeAllByLinkIds' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table ->
                OptionalUtils.ofEmpty(table.getLogicColumn(), logicColumn, log, message -> new MybatisTableErrorException("removeAllByLinkIds", "logicColumn", message));
        return MybatisSqlProvider.providingOfLinkIdAllRemove(providerContext, tablename, linkIdList, logic, tableOptional, REMOVE_SQL_SUPPLY);
    }

}
