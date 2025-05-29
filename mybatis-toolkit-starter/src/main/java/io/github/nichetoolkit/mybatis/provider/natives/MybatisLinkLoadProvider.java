package io.github.nichetoolkit.mybatis.provider.natives;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.mybatis.load.RestLoad;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.actuator.ConsumerActuator;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class MybatisLinkLoadProvider implements MybatisSqlProvider {
    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB, DatabaseType.SQLITE);
    }

    public static <L> String findByLinkIdLoad(ProviderContext providerContext, L linkId, String linkName, RestLoad[] loadParams) throws RestException {
        return findDynamicByLinkIdLoad(providerContext, null, linkId, linkName, loadParams);
    }

    public static <L> String findDynamicByLinkIdLoad(ProviderContext providerContext, String tablename, L linkId, String linkName, RestLoad[] loadParams) throws RestException {
        OptionalUtils.ofEmpty(linkId, "The link id param of 'findByLinkIdLoad' method cannot be empty!", log, message -> new MybatisParamErrorException("findByLinkIdLoad", "linkId", message));
        String selectColumns = "The select columns of table with 'findByLinkIdLoad' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), selectColumns, log,
                message -> new MybatisTableErrorException("findByLinkIdLoad", "selectColumns", message));
        return MybatisSqlProvider.providingOfLinkId(providerContext, tablename, linkId, linkName, tableOptional, loadParams, ENTRY_SQL_SUPPLY);
    }

    public static <L> String findAllByLinkIdsLoad(ProviderContext providerContext, Collection<L> linkIdList, String linkName, RestLoad[] loadParams) throws RestException {
        return findDynamicAllByLinkIdsLoad(providerContext, null, linkIdList, linkName, loadParams);
    }

    public static <L> String findDynamicAllByLinkIdsLoad(ProviderContext providerContext, String tablename, Collection<L> linkIdList, String linkName, RestLoad[] loadParams) throws RestException {
        OptionalUtils.ofEmpty(linkIdList, "The link id list param of 'findAllByLinkIdsLoad' method cannot be empty!", log, message -> new MybatisParamErrorException("findAllByLinkIdsLoad", "linkIdList", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), "The select columns of table with 'findAllByLinkIdsLoad' method cannot be empty!", message -> new MybatisTableErrorException("findAllByLinkIdsLoad", "selectColumns", message));
        return MybatisSqlProvider.providingOfLinkIdAll(providerContext, tablename, linkIdList, linkName, tableOptional, loadParams, ENTRY_SQL_SUPPLY);
    }
}
