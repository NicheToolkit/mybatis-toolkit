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
public class PostgresDeleteProvider implements MybatisSqlProvider {

    @Override
    public DatabaseType databaseType() {
        return DatabaseType.POSTGRESQL;
    }

    public static <I> String deleteById(ProviderContext providerContext, I id) throws RestException {
        return deleteDynamicById(providerContext, null, id);
    }

    public static <I> String deleteDynamicById(ProviderContext providerContext, String tablename, I id) throws RestException {
        OptionalUtils.ofEmpty(id, "The id param of 'deleteById' method cannot be empty!", message -> new MybatisParamErrorException("deleteById", "id", message));
        return MybatisSqlProvider.providingOfId(providerContext, tablename, id, table -> {
        }, DELETE_SQL_SUPPLY);
    }

    public static <I> String deleteAll(ProviderContext providerContext, Collection<I> idList) throws RestException {
        return deleteDynamicAll(providerContext, null, idList);
    }

    public static <I> String deleteDynamicAll(ProviderContext providerContext, String tablename, Collection<I> idList) throws RestException {
        OptionalUtils.ofEmpty(idList, "The id list param of 'deleteByAll' method cannot be empty!", message -> new MybatisParamErrorException("deleteByAll", "idList", message));
        return MybatisSqlProvider.providingOfAll(providerContext, tablename, idList, table -> {
        }, DELETE_SQL_SUPPLY);
    }

    public static String deleteAllByWhere(ProviderContext providerContext, String whereSql) throws RestException {
        return deleteDynamicAllByWhere(providerContext, null, whereSql);
    }

    public static String deleteDynamicAllByWhere(ProviderContext providerContext, String tablename, String whereSql) throws RestException {
        OptionalUtils.ofEmpty(whereSql, "The where sql param of 'deleteAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("deleteAllByWhere", "whereSql", message));
        return MybatisSqlProvider.providingOfWhere(providerContext, tablename, whereSql, table -> {
        }, DELETE_SQL_SUPPLY);
    }


}
