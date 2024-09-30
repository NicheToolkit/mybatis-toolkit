package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.*;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MybatisFindProvider implements MybatisProviderResolver {

    public static <I> String findById(ProviderContext providerContext, I id) throws RestException {
        return findDynamicById(providerContext, null, id);
    }

    public static <I> String findDynamicById(ProviderContext providerContext, String tablename, I id) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(id), "the id param of 'findById' method cannot be empty!", message -> new MybatisParamErrorException("findById", "id", message));
        return MybatisSqlProviders.providing(providerContext, tablename, id, new MybatisSqlProviders() {
            @Override
            public <IDENTITY> String provide(String tablename, MybatisTable table, Map<Integer, List<IDENTITY>> identitySliceMap, MybatisSqlScript sqlScript) throws RestException {
                OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findById' method cannot be empty!", message -> new MybatisTableErrorException("findById", "selectColumns", message));
                return "SELECT " + table.selectColumnSql()
                        + " FROM " + table.tablename(tablename)
                        + identityWhereSql(table, sqlScript);

            }
        });
    }

    public static <I> String findAll(ProviderContext providerContext, Collection<I> idList) throws RestException {
        return findDynamicAll(providerContext, null, idList);
    }

    @SuppressWarnings("Duplicates")
    public static <I> String findDynamicAll(ProviderContext providerContext, String tablename, Collection<I> idList) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(idList), "the id list param of 'findByAll' method cannot be empty!", message -> new MybatisParamErrorException("findByAll", "idList", message));
        return MybatisSqlProviders.providing(providerContext, tablename, idList, new MybatisSqlProviders() {
            @Override
            public <IDENTITY> String provide(String tablename, MybatisTable table, Map<Integer, List<IDENTITY>> identitySliceMap, MybatisSqlScript sqlScript) throws RestException {
                OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findByAll' method cannot be empty!", message -> new MybatisTableErrorException("findByAll", "selectColumns", message));
                return "SELECT " + table.selectColumnSql()
                        + " FROM " + table.tablename(tablename)
                        + identitiesWhereSql(identitySliceMap, table, sqlScript);

            }
        });
    }

    public static String findAllByWhere(ProviderContext providerContext, String whereSql) throws RestException {
        return findDynamicAllByWhere(providerContext, null, whereSql);
    }

    public static String findDynamicAllByWhere(ProviderContext providerContext, String tablename, String whereSql) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(whereSql), "the where sql param of 'findAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("findAllByWhere", "whereSql", message));
        return MybatisSqlScript.caching(providerContext, (table, sqlScript) -> {
            OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findAllByWhere' method cannot be empty!", message -> new MybatisTableErrorException("findAllByWhere", "selectColumns", message));
            return "SELECT " + table.selectColumnSql()
                    + " FROM " + table.tablename(tablename)
                    + " WHERE 1=1 "
                    + sqlScript.ifTest("whereSql!=null", () -> "${whereSql}");
        });
    }

}
