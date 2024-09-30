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

public class MybatisRemoveProvider implements MybatisProviderResolver {


    public static <I> String removeById(ProviderContext providerContext, @Param("id") I id, @Param("logicSign") String logicSign) throws RestException {
        return removeDynamicById(providerContext, null, id, logicSign);
    }

    public static <I> String removeDynamicById(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") I id, @Param("logicSign") String logicSign) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(id), "the id param of 'removeById' method cannot be empty!", message -> new MybatisTableErrorException("removeById", "id", message));
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(logicSign), "the logicSign param of 'removeById' method cannot be empty!", message -> new MybatisParamErrorException("removeById", "logicSign", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.getLogicColumn()), "the logic column of table with 'removeById' method cannot be empty!", message -> new MybatisTableErrorException("removeById", "logicColumn", message));
            return "UPDATE " + table.tablename(tablename)
                    + " SET " + table.getLogicColumn().columnEqualsLogic()
                    + " WHERE " + table.identityColumnEqualsProperty();
        });
    }

    public static <I> String removeAll(ProviderContext providerContext, @Param("idList") Collection<I> idList, @Param("logicSign") String logicSign) throws RestException {
        return removeDynamicAll(providerContext, null, idList, logicSign);
    }

    public static <I> String removeDynamicAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("logicSign") String logicSign) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(idList), "the id list param of 'removeAll' method cannot be empty!", message -> new MybatisParamErrorException("removeAll", "idList", message));
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(logicSign), "the logicSign param of 'removeAll' method cannot be empty!", message -> new MybatisParamErrorException("removeAll", "logicSign", message));
        return MybatisSqlProviders.providing(providerContext,tablename,idList, new MybatisSqlProviders() {
            @Override
            public <IDENTITY> String provide(String tablename, MybatisTable table, Map<Integer, List<IDENTITY>> identitySliceMap, MybatisSqlScript sqlScript) throws RestException {
                OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.getLogicColumn()), "the logic column of table with 'removeAll' method cannot be empty!", message -> new MybatisTableErrorException("removeAll", "logicColumn", message));
                return "UPDATE " + table.tablename(tablename)
                        + " SET " + table.getLogicColumn().columnEqualsLogic()
                        + " WHERE " + identitiesWhereSql(identitySliceMap,table,sqlScript);

            }
        });

    }

    public static String removeAllByWhere(ProviderContext providerContext, @Param("whereSql") String whereSql, @Param("logicSign") String logicSign) throws RestException {
        return removeDynamicAllByWhere(providerContext, null, whereSql, logicSign);
    }

    public static String removeDynamicAllByWhere(ProviderContext providerContext, @Param("tablename") String tablename, @Param("whereSql") String whereSql, @Param("logicSign") String logicSign) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(whereSql), "the where sql param of 'removeAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("removeAllByWhere", "whereSql", message));
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(logicSign), "the logicSign param of 'removeAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("removeAllByWhere", "logicSign", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.getLogicColumn()), "the logic column of table with 'removeAllByWhere' method cannot be empty!", message -> new MybatisTableErrorException("removeAllByWhere", "logicColumn", message));
                return "UPDATE " + table.tablename(tablename)
                        + " SET " + table.getLogicColumn().columnEqualsLogic()
                        + " WHERE 1=1 "
                        + ifTest("whereSql!=null", () -> "${whereSql}");
            }
        });
    }


}
