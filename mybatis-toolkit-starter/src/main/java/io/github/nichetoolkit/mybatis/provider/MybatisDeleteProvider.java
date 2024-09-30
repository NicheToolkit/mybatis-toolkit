package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlProviders;
import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MybatisDeleteProvider implements MybatisProviderResolver {

    public static <I> String deleteById(ProviderContext providerContext, @Param("id") I id) throws RestException {
        return deleteDynamicById(providerContext, null, id);
    }

    public static <I> String deleteDynamicById(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") I id) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(id), "the id param of 'deleteById' method cannot be empty!", message -> new MybatisParamErrorException("deleteById", "id", message));
        return MybatisSqlScript.caching(providerContext, table -> "DELETE FROM " + table.tablename(tablename)
                + " WHERE " + table.identityColumnEqualsProperty());
    }

    public static <I> String deleteAll(ProviderContext providerContext, @Param("idList") Collection<I> idList) throws RestException {
        return deleteDynamicAll(providerContext, null, idList);
    }

    public static <I> String deleteDynamicAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<I> idList) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(idList), "the id list param of 'deleteByAll' method cannot be empty!", message -> new MybatisParamErrorException("deleteByAll", "idList", message));

        return MybatisSqlProviders.providing(providerContext,tablename,idList, new MybatisSqlProviders() {
            @Override
            public <IDENTITY> String provide(String tablename, MybatisTable table, Map<Integer, List<IDENTITY>> identitySliceMap, MybatisSqlScript sqlScript) throws RestException {
                OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.getLogicColumn()), "the logic column of table with 'removeAll' method cannot be empty!", message -> new MybatisTableErrorException("removeAll", "logicColumn", message));
                return "DELETE FROM " + table.tablename(tablename)
                        + " WHERE " + identitiesWhereSql(identitySliceMap,table,sqlScript);

            }
        });
    }

    public static String deleteAllByWhere(ProviderContext providerContext, @Param("whereSql") String whereSql) throws RestException {
        return deleteDynamicAllByWhere(providerContext, null, whereSql);
    }

    public static String deleteDynamicAllByWhere(ProviderContext providerContext, @Param("tablename") String tablename, @Param("whereSql") String whereSql) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(whereSql), "the where sql param of 'deleteAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("deleteAllByWhere", "whereSql", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                return "DELETE FROM " + table.tablename(tablename)
                        + " WHERE 1=1 "
                        + ifTest("whereSql!=null", () -> "${whereSql}");
            }
        });
    }


}
