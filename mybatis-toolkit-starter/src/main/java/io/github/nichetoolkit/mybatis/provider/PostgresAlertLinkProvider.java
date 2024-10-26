package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.rest.RestException;
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

    public static <L, S> String alertByLinkId(ProviderContext providerContext, L linkId, S status) throws RestException {
        return alertDynamicByLinkId(providerContext, null, linkId, status);
    }

    public static <L, S> String alertDynamicByLinkId(ProviderContext providerContext, String tablename, L linkId, S status) throws RestException {
        OptionalUtils.ofEmpty(linkId, "The link id param of 'alertByLinkId' method cannot be empty!", message -> new MybatisParamErrorException("alertByLinkId", "linkId", message));
        OptionalUtils.ofEmpty(status, "The status param of 'alertByLinkId' method cannot be empty!", message -> new MybatisParamErrorException("alertByLinkId", "status", message));
        return MybatisSqlProvider.providingOfLinkId(providerContext, tablename, linkId, status, ALERT_SQL_SUPPLY);
    }

    public static <L, S> String alertAllByLinkIds(ProviderContext providerContext, Collection<L> linkIdList, S status) throws RestException {
        return alertDynamicAllByLinkIds(providerContext, null, linkIdList, status);
    }

    public static <L, S> String alertDynamicAllByLinkIds(ProviderContext providerContext, String tablename, Collection<L> linkIdList, S status) throws RestException {
        OptionalUtils.ofEmpty(linkIdList, "The link id list param of 'alertAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("alertAllByLinkIds", "linkIdList", message));
        OptionalUtils.ofEmpty(status, "The status param of 'alertAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("alertAllByLinkIds", "status", message));
        return MybatisSqlProvider.providingOfLinkIdAll(providerContext, tablename, linkIdList, status, ALERT_SQL_SUPPLY);
    }

}
