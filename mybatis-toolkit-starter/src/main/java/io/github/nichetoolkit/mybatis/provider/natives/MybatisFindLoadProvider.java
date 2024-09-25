package io.github.nichetoolkit.mybatis.provider.natives;

import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MybatisFindLoadProvider {

    public static <I> String findByIdLoad(ProviderContext providerContext, I id, Boolean... loadParams) throws RestException {
        return findDynamicByIdLoad(providerContext, null, id, loadParams);
    }

    @SuppressWarnings("Duplicates")
    public static <I> String findDynamicByIdLoad(ProviderContext providerContext, String tablename, I id, Boolean... loadParams) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(id), "the id param of 'findByIdLoad' method cannot be empty!", message -> new MybatisParamErrorException("findById", "id", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findById' method cannot be empty!", message -> new MybatisTableErrorException("findById", "selectColumns", message));
            return "SELECT " + table.selectAliasColumnList()
                    + " FROM " + table.tablenameAsAlias(tablename)
                    + " WHERE " + table.identityColumnEqualsProperty();
        });

    }

    public static <I> String findAllLoad(ProviderContext providerContext, Collection<I> idList, Boolean... loadParams) throws RestException {
        return findDynamicAllLoad(providerContext, null, idList, loadParams);
    }

    @SuppressWarnings("Duplicates")
    public static <I> String findDynamicAllLoad(ProviderContext providerContext, String tablename, Collection<I> idList, Boolean... loadParams) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(idList), "the id list param of 'findByAllLoad' method cannot be empty!", message -> new MybatisParamErrorException("findByAll", "idList", message));
        return MybatisSqlProvider.providing(providerContext, tablename, idList, new MybatisSqlProvider() {
            @Override
            public <IDENTITY> String provide(String tablename, MybatisTable table, Map<Integer, List<IDENTITY>> identitySliceMap, MybatisSqlScript sqlScript) throws RestException {
                OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findByAll' method cannot be empty!", message -> new MybatisTableErrorException("findByAll", "selectColumns", message));
                return "SELECT " + table.selectColumnList()
                        + " FROM " + table.tablename(tablename)
                        + " WHERE " + identitiesWhereSql(identitySliceMap, table, sqlScript);

            }
        });
    }

}
