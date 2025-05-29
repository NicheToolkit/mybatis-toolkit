package io.github.nichetoolkit.mybatis.provider.natives;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
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
public class MybatisLinkFickleProvider implements MybatisSqlProvider {
    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB, DatabaseType.SQLITE);
    }

    public static <L> String findDynamicByLinkIdFickle(ProviderContext providerContext, String tablename, L linkId, String linkName, RestFickle<?>[] fickleParams) throws RestException {
        OptionalUtils.ofEmpty(linkId, "The link id param of 'findByLinkIdFickle' method cannot be empty!", log, message -> new MybatisParamErrorException("findByLinkIdFickle", "linkId", message));
        String selectColumns = "The select columns of table with 'findByLinkIdFickle' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), selectColumns, log,
                message -> new MybatisTableErrorException("findByLinkIdFickle", "selectColumns", message));
        return MybatisSqlProvider.providingOfLinkId(providerContext, tablename, linkId, linkName, tableOptional, fickleParams, ENTRY_SQL_SUPPLY);
    }

    public static <L> String findDynamicAllByLinkIdsFickle(ProviderContext providerContext, String tablename, Collection<L> linkIdList, String linkName, RestFickle<?>[] fickleParams) throws RestException {
        OptionalUtils.ofEmpty(linkIdList, "The link id list param of 'findAllByLinkIdsFickle' method cannot be empty!", log, message -> new MybatisParamErrorException("findAllByLinkIdsFickle", "linkIdList", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), "The select columns of table with 'findAllByLinkIdsFickle' method cannot be empty!", message -> new MybatisTableErrorException("findAllByLinkIdsFickle", "selectColumns", message));
        return MybatisSqlProvider.providingOfLinkIdAll(providerContext, tablename, linkIdList, linkName, tableOptional, fickleParams, ENTRY_SQL_SUPPLY);
    }
}
