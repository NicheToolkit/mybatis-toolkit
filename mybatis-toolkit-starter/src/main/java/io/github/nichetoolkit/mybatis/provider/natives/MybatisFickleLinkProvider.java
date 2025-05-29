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
public class MybatisFickleLinkProvider implements MybatisSqlProvider {
    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB, DatabaseType.SQLITE);
    }

    public static <L> String findByLinkIdFickleLoad(ProviderContext providerContext, L linkId, String linkName, RestFickle<?>[] fickleParams, RestLoad[] loadParams) throws RestException {
        return findDynamicByLinkIdFickleLoad(providerContext, null, linkId, linkName, fickleParams, loadParams);
    }

    public static <L> String findDynamicByLinkIdFickleLoad(ProviderContext providerContext, String tablename, L linkId, String linkName, RestFickle<?>[] fickleParams, RestLoad[] loadParams) throws RestException {
        OptionalUtils.ofEmpty(linkId, "The link id param of 'findByLinkIdFickleLoad' method cannot be empty!", log, message -> new MybatisParamErrorException("findByLinkIdFickleLoad", "linkId", message));
        String selectColumns = "The select columns of table with 'findByLinkIdFickleLoad' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), selectColumns, log,
                message -> new MybatisTableErrorException("findByLinkIdFickleLoad", "selectColumns", message));
        return MybatisSqlProvider.providingOfLinkId(providerContext, tablename, linkId, linkName, tableOptional, fickleParams, loadParams, ENTRY_SQL_SUPPLY);
    }

    public static <L> String findAllByLinkIdsFickleLoad(ProviderContext providerContext, Collection<L> linkIdList, String linkName, RestFickle<?>[] fickleParams, RestLoad[] loadParams) throws RestException {
        return findDynamicAllByLinkIdsFickleLoad(providerContext, null, linkIdList, linkName, fickleParams, loadParams);
    }

    public static <L> String findDynamicAllByLinkIdsFickleLoad(ProviderContext providerContext, String tablename, Collection<L> linkIdList, String linkName, RestFickle<?>[] fickleParams, RestLoad[] loadParams) throws RestException {
        OptionalUtils.ofEmpty(linkIdList, "The link id list param of 'findAllByLinkIdsFickleLoad' method cannot be empty!", log, message -> new MybatisParamErrorException("findAllByLinkIdsFickleLoad", "linkIdList", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), "The select columns of table with 'findAllByLinkIdsFickleLoad' method cannot be empty!", message -> new MybatisTableErrorException("findAllByLinkIdsFickleLoad", "selectColumns", message));
        return MybatisSqlProvider.providingOfLinkIdAll(providerContext, tablename, linkIdList, linkName, tableOptional, fickleParams, loadParams, ENTRY_SQL_SUPPLY);
    }
}
