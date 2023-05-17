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
 * <p>MybatisRemoveProvider</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisRemoveProvider {


    public static <I> String removeById(ProviderContext providerContext, @Param("id") I id, @Param("sign") String sign) throws RestException {
        return removeDynamicById(providerContext, null, id, sign);
    }

    public static <I> String removeDynamicById(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") I id, @Param("sign") String sign) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(id), "the id param of 'removeById' method cannot be empty!", message -> new MybatisTableErrorException("removeById", "id", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(sign), "the sign param of 'removeById' method cannot be empty!", message -> new MybatisParamErrorException("removeById", "sign", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.getLogicColumn()), "the logic column of table with 'removeById' method cannot be empty!", message -> new MybatisTableErrorException("removeById", "logicColumn", message));
            OptionalHelper.trueable(table.isUseUnionKey(), "the union keys of table with 'removeById' method is unsupported!", message -> new MybatisUnsupportedErrorException("removeById", "unionKeys", message));
            return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tableName())
                    + " SET " + table.getLogicColumn().columnEqualsSign()
                    + " WHERE " + table.getIdentityColumn().columnEqualsProperty();
        });
    }

    public static <I> String removeAll(ProviderContext providerContext, @Param("idList") Collection<I> idList, @Param("sign") String sign) throws RestException {
        return removeDynamicAll(providerContext, null, idList, sign);
    }

    public static <I> String removeDynamicAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("sign") String sign) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(idList), "the id list param of 'removeAll' method cannot be empty!", message -> new MybatisParamErrorException("removeAll", "idList", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(sign), "the sign param of 'removeAll' method cannot be empty!", message -> new MybatisParamErrorException("removeAll", "sign", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.getLogicColumn()), "the logic column of table with 'removeAll' method cannot be empty!", message -> new MybatisTableErrorException("removeAll", "logicColumn", message));
                OptionalHelper.trueable(table.isUseUnionKey(), "the union keys of table with 'removeAll' method is unsupported!", message -> new MybatisUnsupportedErrorException("removeAll", "unionKeys", message));
                return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tableName())
                        + " SET " + table.getLogicColumn().columnEqualsSign()
                        + " WHERE " + table.getIdentityColumn().getColumnName() + " IN " + foreach("idList", "id", ", ", "(", ")", () -> table.getIdentityColumn().variable());

            }
        });
    }

    public static String removeAllByWhere(ProviderContext providerContext, @Param("whereSql") String whereSql, @Param("sign") String sign) throws RestException {
        return removeDynamicAllByWhere(providerContext, null, whereSql, sign);
    }

    public static String removeDynamicAllByWhere(ProviderContext providerContext, @Param("tablename") String tablename, @Param("whereSql") String whereSql, @Param("sign") String sign) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(whereSql), "the where sql param of 'removeAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("removeAllByWhere", "whereSql", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(sign), "the sign param of 'removeAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("removeAllByWhere", "sign", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.getLogicColumn()), "the logic column of table with 'removeAllByWhere' method cannot be empty!", message -> new MybatisTableErrorException("removeAllByWhere", "logicColumn", message));
                OptionalHelper.trueable(table.isUseUnionKey(), "the union keys of table with 'removeAllByWhere' method is unsupported!", message -> new MybatisUnsupportedErrorException("removeAllByWhere", "unionKeys", message));
                return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tableName())
                        + " SET " + table.getLogicColumn().columnEqualsSign()
                        + " WHERE 1=1 "
                        + ifTest("whereSql!=null", () -> "${whereSql}");
            }
        });
    }


}
