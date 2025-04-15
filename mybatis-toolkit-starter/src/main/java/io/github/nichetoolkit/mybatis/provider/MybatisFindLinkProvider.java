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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class MybatisFindLinkProvider implements MybatisSqlProvider {

    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB, DatabaseType.SQLITE);
    }

    public static <L> String findByLinkId(ProviderContext providerContext, L linkId, String linkName) throws RestException {
        return findDynamicByLinkId(providerContext, null, linkId, linkName);
    }

    public static <L> String findDynamicByLinkId(ProviderContext providerContext, String tablename, L linkId, String linkName) throws RestException {
        OptionalUtils.ofEmpty(linkId, "The link id param of 'findByLinkId' method cannot be empty!", log, message -> new MybatisParamErrorException("findByLinkId", "linkId", message));
        String selectColumns = "The select columns of table with 'findByLinkId' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), selectColumns, log,
                message -> new MybatisTableErrorException("findByLinkId", "selectColumns", message));
        return MybatisSqlProvider.providingOfLinkId(providerContext, tablename, linkId, tableOptional, SELECT_SQL_SUPPLY);
    }

    public static <L> String findAllByLinkIds(ProviderContext providerContext, Collection<L> linkIdList, String linkName) throws RestException {
        return findDynamicAllByLinkIds(providerContext, null, linkIdList, linkName);
    }

    public static <L> String findDynamicAllByLinkIds(ProviderContext providerContext, String tablename, Collection<L> linkIdList, String linkName) throws RestException {
        OptionalUtils.ofEmpty(linkIdList, "The link id list param of 'findAllByLinkIds' method cannot be empty!", log, message -> new MybatisParamErrorException("findAllByLinkIds", "linkIdList", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), "The select columns of table with 'findAllByLinkIds' method cannot be empty!", message -> new MybatisTableErrorException("findAllByLinkIds", "selectColumns", message));
        return MybatisSqlProvider.providingOfLinkIdAll(providerContext, tablename, linkIdList, tableOptional, SELECT_SQL_SUPPLY);
    }

}
