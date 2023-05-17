package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisUnsupportedErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.helper.OptionalHelper;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Collection;
import java.util.Optional;

/**
 * <p>MybatisOperateProvider</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisOperateProvider {


    public static <I> String operateById(ProviderContext providerContext, @Param("id") I id, @Param("operate") Integer operate) throws RestException {
        return operateDynamicById(providerContext, null, id, operate);
    }

    public static <I> String operateDynamicById(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") I id, @Param("operate") Integer operate) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(id), "the id param of 'operateById' method cannot be empty!", message -> new MybatisTableErrorException("operateById", "id", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(operate), "the operate param of 'operateById' method cannot be empty!", message -> new MybatisParamErrorException("operateById", "operate", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.getOperateColumn()), "the operate column of table with 'operateById' method cannot be empty!", message -> new MybatisTableErrorException("operateById", "operateColumn", message));
            OptionalHelper.trueable(table.isUseUnionKey(), "the union keys of table with 'operateById' method is unsupported!", message -> new MybatisUnsupportedErrorException("operateById", "unionKeys", message));
            return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tableName())
                    + " SET " + table.getOperateColumn().columnEqualsOperate()
                    + " WHERE " + table.getIdentityColumn().columnEqualsProperty();
        });
    }

    public static <I> String operateAll(ProviderContext providerContext, @Param("idList") Collection<I> idList, @Param("operate") Integer operate) throws RestException {
        return operateDynamicAll(providerContext, null, idList, operate);
    }

    public static <I> String operateDynamicAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("operate") Integer operate) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(idList), "the id list param of 'operateAll' method cannot be empty!", message -> new MybatisParamErrorException("operateAll", "idList", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(operate), "the operate param of 'operateAll' method cannot be empty!", message -> new MybatisParamErrorException("operateAll", "operate", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.getOperateColumn()), "the operate column of table with 'operateAll' method cannot be empty!", message -> new MybatisTableErrorException("operateAll", "operateColumn", message));
                OptionalHelper.trueable(table.isUseUnionKey(), "the union keys of table with 'operateAll' method is unsupported!", message -> new MybatisUnsupportedErrorException("operateAll", "unionKeys", message));
                return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tableName())
                        + " SET " + table.getOperateColumn().columnEqualsOperate()
                        + " WHERE " + table.getIdentityColumn().getColumnName() + " IN " + foreach("idList", "id", ", ", "(", ")", () -> table.getIdentityColumn().variable());

            }
        });
    }

    public static String operateAllByWhere(ProviderContext providerContext, @Param("whereSql") String whereSql, @Param("operate") Integer operate) throws RestException {
        return operateDynamicAllByWhere(providerContext, null, whereSql, operate);
    }

    public static String operateDynamicAllByWhere(ProviderContext providerContext, @Param("tablename") String tablename, @Param("whereSql") String whereSql, @Param("operate") Integer operate) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(whereSql), "the where sql param of 'operateAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("operateAllByWhere", "whereSql", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(operate), "the operate param of 'operateAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("operateAllByWhere", "operate", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.getOperateColumn()), "the operate column of table with 'operateAllByWhere' method cannot be empty!", message -> new MybatisTableErrorException("operateAllByWhere", "operateColumn", message));
                OptionalHelper.trueable(table.isUseUnionKey(), "the union keys of table with 'operateAllByWhere' method is unsupported!", message -> new MybatisUnsupportedErrorException("operateAllByWhere", "unionKeys", message));
                return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tableName())
                        + " SET " + table.getOperateColumn().columnEqualsOperate()
                        + " WHERE 1=1 "
                        + ifTest("whereSql!=null", () -> "${whereSql}");
            }
        });
    }


}
