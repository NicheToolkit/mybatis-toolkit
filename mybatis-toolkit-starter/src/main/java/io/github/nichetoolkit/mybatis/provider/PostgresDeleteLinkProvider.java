package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component
public class PostgresDeleteLinkProvider implements MybatisSqlProvider {

    @Override
    public DatabaseType databaseType() {
        return DatabaseType.POSTGRESQL;
    }

    public static <L> String deleteByLinkId(ProviderContext providerContext, L linkId) throws RestException {
        return deleteDynamicByLinkId(providerContext, null, linkId);
    }

    public static <L> String deleteDynamicByLinkId(ProviderContext providerContext, String tablename, L linkId) throws RestException {
        OptionalUtils.ofEmpty(linkId, "The link id param of 'deleteByLinkId' method cannot be empty!", message -> new MybatisTableErrorException("deleteByLinkId", "linkId", message));
        return MybatisSqlProvider.providingOfLinkId(providerContext, tablename, linkId, table -> {}, DELETE_SQL_SUPPLY);
    }

    public static <L> String deleteAllByLinkIds(ProviderContext providerContext, Collection<L> linkIdList) throws RestException {
        return deleteDynamicAllByLinkIds(providerContext, null, linkIdList);
    }

    public static <L> String deleteDynamicAllByLinkIds(ProviderContext providerContext, String tablename, Collection<L> linkIdList) throws RestException {
        OptionalUtils.ofEmpty(linkIdList, "The link id list param of 'deleteAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("deleteAllByLinkIds", "linkIdList", message));
        return MybatisSqlProvider.providingOfLinkIdAll(providerContext, tablename, linkIdList, table -> {}, DELETE_SQL_SUPPLY);
    }


}
