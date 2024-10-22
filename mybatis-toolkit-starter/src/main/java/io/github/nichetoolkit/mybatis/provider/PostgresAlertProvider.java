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
public class PostgresAlertProvider implements MybatisSqlProvider {

    @Override
    public DatabaseType databaseType() {
        return DatabaseType.POSTGRESQL;
    }

    public <I> String alertById(ProviderContext providerContext, I id, Integer key) throws RestException {
        return alertDynamicById(providerContext, null, id, key);
    }

    public <I> String alertDynamicById(ProviderContext providerContext, String tablename, I id, Integer key) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(id), "The id param of 'alertById' method cannot be empty!", message -> new MybatisParamErrorException("alertById", "id", message));
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(key), "The key param of 'alertById' method cannot be empty!", message -> new MybatisParamErrorException("alertById", "key", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> {
            OptionalUtils.ofEmpty(table.getAlertColumn(), "The alert column of table with 'alertById' method cannot be empty!", message -> new MybatisTableErrorException("alertById", "alertColumn", message));
        };
        return MybatisSqlProvider.providingOfIdAlert(providerContext, tablename, id, key, tableOptional, ALERT_SQL_SUPPLY);
    }

    public <I> String alertAll(ProviderContext providerContext, Collection<I> idList, Integer key) throws RestException {
        return alertDynamicAll(providerContext, null, idList, key);
    }

    public <I> String alertDynamicAll(ProviderContext providerContext, String tablename, Collection<I> idList, Integer key) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(idList), "The id list param of 'alertAll' method cannot be empty!", message -> new MybatisParamErrorException("alertAll", "idList", message));
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(key), "The key param of 'alertAll' method cannot be empty!", message -> new MybatisParamErrorException("alertAll", "key", message));
        ConsumerActuator<MybatisTable> tableOptional = table -> {
            OptionalUtils.ofEmpty(table.getAlertColumn(), "The alert column of table with 'alertAll' method cannot be empty!", message -> new MybatisTableErrorException("alertAll", "alertColumn", message));
        };
        return MybatisSqlProvider.providingOfAllAlert(providerContext, tablename, idList, key, tableOptional, ALERT_SQL_SUPPLY);
    }


}
