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
public class PostgresAlertProvider implements MybatisSqlProvider {

    @Override
    public DatabaseType databaseType() {
        return DatabaseType.POSTGRESQL;
    }

    public static <I, S> String alertById(ProviderContext providerContext, I id, S status) throws RestException {
        return alertDynamicById(providerContext, null, id, status);
    }

    public static <I, S> String alertDynamicById(ProviderContext providerContext, String tablename, I id, S status) throws RestException {
        OptionalUtils.ofEmpty(id, "The id param of 'alertById' method cannot be empty!", message -> new MybatisParamErrorException("alertById", "id", message));
        OptionalUtils.ofEmpty(status, "The status param of 'alertById' method cannot be empty!", message -> new MybatisParamErrorException("alertById", "status", message));
        return MybatisSqlProvider.providingOfId(providerContext, tablename, id, status, ALERT_SQL_SUPPLY);
    }

    public static <I, S> String alertAll(ProviderContext providerContext, Collection<I> idList, S status) throws RestException {
        return alertDynamicAll(providerContext, null, idList, status);
    }

    public static <I, S> String alertDynamicAll(ProviderContext providerContext, String tablename, Collection<I> idList, S status) throws RestException {
        OptionalUtils.ofEmpty(idList, "The id list param of 'alertAll' method cannot be empty!", message -> new MybatisParamErrorException("alertAll", "idList", message));
        OptionalUtils.ofEmpty(status, "The status param of 'alertAll' method cannot be empty!", message -> new MybatisParamErrorException("alertAll", "status", message));
        return MybatisSqlProvider.providingOfAll(providerContext, tablename, idList, status, ALERT_SQL_SUPPLY);
    }

    public static <I, S> String alertAllByWhere(ProviderContext providerContext, String whereSql, S status) throws RestException {
        return alertDynamicAllByWhere(providerContext, null, whereSql, status);
    }

    public static <I, S> String alertDynamicAllByWhere(ProviderContext providerContext, String tablename, String whereSql, S status) throws RestException {
        OptionalUtils.ofEmpty(whereSql, "The where sql param of 'alertAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("alertAllByWhere", "whereSql", message));
        OptionalUtils.ofEmpty(status, "The status param of 'alertAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("alertAllByWhere", "status", message));
        return MybatisSqlProvider.providingOfWhere(providerContext, tablename, whereSql, status, ALERT_SQL_SUPPLY);
    }

}
