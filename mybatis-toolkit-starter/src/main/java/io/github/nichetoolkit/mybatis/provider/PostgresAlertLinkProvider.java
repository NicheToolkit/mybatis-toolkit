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
public class PostgresAlertLinkProvider implements MybatisSqlProvider {

    @Override
    public DatabaseType databaseType() {
        return DatabaseType.POSTGRESQL;
    }

    public <L,S> String alertByLinkId(ProviderContext providerContext, L linkId, S status) throws RestException {
        return alertDynamicByLinkId(providerContext, null, linkId, status);
    }

    public <L,S> String alertDynamicByLinkId(ProviderContext providerContext, String tablename, L linkId, S status) throws RestException {
        OptionalUtils.ofEmpty(linkId, "The link id param of 'alertByLinkId' method cannot be empty!", message -> new MybatisParamErrorException("alertByLinkId", "linkId", message));
        OptionalUtils.ofEmpty(status, "The status param of 'alertByLinkId' method cannot be empty!", message -> new MybatisParamErrorException("alertByLinkId", "status", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.getAlertColumn(), "The alert column of table with 'alertByLinkId' method cannot be empty!", message -> new MybatisTableErrorException("alertByLinkId", "alertColumn", message));
        return MybatisSqlProvider.providingOfLinkIdAlert(providerContext, tablename, linkId, status, tableOptional, ALERT_SQL_SUPPLY);
    }

    public <L,S> String alertAllByLinkIds(ProviderContext providerContext, Collection<L> linkIdList, S status) throws RestException {
        return alertDynamicAllByLinkIds(providerContext, null, linkIdList, status);
    }

    public <L,S> String alertDynamicAllByLinkIds(ProviderContext providerContext, String tablename, Collection<L> linkIdList, S status) throws RestException {
        OptionalUtils.ofEmpty(linkIdList, "The link id list param of 'alertAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("alertAllByLinkIds", "linkIdList", message));
        OptionalUtils.ofEmpty(status, "The status param of 'alertAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("alertAllByLinkIds", "status", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.getAlertColumn(), "The alert column of table with 'alertAllByLinkIds' method cannot be empty!", message -> new MybatisTableErrorException("alertAllByLinkIds", "alertColumn", message));
        return MybatisSqlProvider.providingOfLinkIdAllAlert(providerContext, tablename, linkIdList, status, tableOptional, ALERT_SQL_SUPPLY);
    }

}
