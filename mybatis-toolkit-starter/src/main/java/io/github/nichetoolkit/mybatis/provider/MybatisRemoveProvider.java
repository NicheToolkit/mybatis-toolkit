package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisUnsupportedErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Collection;
import java.util.Optional;

/**
 * <code>MybatisRemoveProvider</code>
 * <p>The type mybatis remove provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class MybatisRemoveProvider {


    /**
     * <code>removeById</code>
     * <p>the by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param id              I <p>the id parameter is <code>I</code> type.</p>
     * @param sign            {@link java.lang.String} <p>the sign parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String removeById(ProviderContext providerContext, @Param("id") I id, @Param("sign") String sign) throws RestException {
        return removeDynamicById(providerContext, null, id, sign);
    }

    /**
     * <code>removeDynamicById</code>
     * <p>the dynamic by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param id              I <p>the id parameter is <code>I</code> type.</p>
     * @param sign            {@link java.lang.String} <p>the sign parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String removeDynamicById(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") I id, @Param("sign") String sign) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(id), "the id param of 'removeById' method cannot be empty!", message -> new MybatisTableErrorException("removeById", "id", message));
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(sign), "the sign param of 'removeById' method cannot be empty!", message -> new MybatisParamErrorException("removeById", "sign", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.getLogicColumn()), "the logic column of table with 'removeById' method cannot be empty!", message -> new MybatisTableErrorException("removeById", "logicColumn", message));
            OptionalUtils.trueable(table.isUseUnionKey(), "the union keys of table with 'removeById' method is unsupported!", message -> new MybatisUnsupportedErrorException("removeById", "unionKeys", message));
            return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tablename())
                    + " SET " + table.getLogicColumn().columnEqualsSign()
                    + " WHERE " + table.getIdentityColumn().columnEqualsProperty();
        });
    }

    /**
     * <code>removeAll</code>
     * <p>the all method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>the id list parameter is <code>Collection</code> type.</p>
     * @param sign            {@link java.lang.String} <p>the sign parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.util.Collection
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String removeAll(ProviderContext providerContext, @Param("idList") Collection<I> idList, @Param("sign") String sign) throws RestException {
        return removeDynamicAll(providerContext, null, idList, sign);
    }

    /**
     * <code>removeDynamicAll</code>
     * <p>the dynamic all method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>the id list parameter is <code>Collection</code> type.</p>
     * @param sign            {@link java.lang.String} <p>the sign parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String removeDynamicAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("sign") String sign) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(idList), "the id list param of 'removeAll' method cannot be empty!", message -> new MybatisParamErrorException("removeAll", "idList", message));
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(sign), "the sign param of 'removeAll' method cannot be empty!", message -> new MybatisParamErrorException("removeAll", "sign", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.getLogicColumn()), "the logic column of table with 'removeAll' method cannot be empty!", message -> new MybatisTableErrorException("removeAll", "logicColumn", message));
                OptionalUtils.trueable(table.isUseUnionKey(), "the union keys of table with 'removeAll' method is unsupported!", message -> new MybatisUnsupportedErrorException("removeAll", "unionKeys", message));
                return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tablename())
                        + " SET " + table.getLogicColumn().columnEqualsSign()
                        + " WHERE " + table.getIdentityColumn().getColumnName() + " IN " + foreach("idList", "id", ", ", "(", ")", () -> table.getIdentityColumn().variable());

            }
        });
    }

    /**
     * <code>removeAllByWhere</code>
     * <p>the all by where method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param whereSql        {@link java.lang.String} <p>the where sql parameter is <code>String</code> type.</p>
     * @param sign            {@link java.lang.String} <p>the sign parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the all by where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String removeAllByWhere(ProviderContext providerContext, @Param("whereSql") String whereSql, @Param("sign") String sign) throws RestException {
        return removeDynamicAllByWhere(providerContext, null, whereSql, sign);
    }

    /**
     * <code>removeDynamicAllByWhere</code>
     * <p>the dynamic all by where method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param whereSql        {@link java.lang.String} <p>the where sql parameter is <code>String</code> type.</p>
     * @param sign            {@link java.lang.String} <p>the sign parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic all by where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String removeDynamicAllByWhere(ProviderContext providerContext, @Param("tablename") String tablename, @Param("whereSql") String whereSql, @Param("sign") String sign) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(whereSql), "the where sql param of 'removeAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("removeAllByWhere", "whereSql", message));
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(sign), "the sign param of 'removeAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("removeAllByWhere", "sign", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.getLogicColumn()), "the logic column of table with 'removeAllByWhere' method cannot be empty!", message -> new MybatisTableErrorException("removeAllByWhere", "logicColumn", message));
                OptionalUtils.trueable(table.isUseUnionKey(), "the union keys of table with 'removeAllByWhere' method is unsupported!", message -> new MybatisUnsupportedErrorException("removeAllByWhere", "unionKeys", message));
                return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tablename())
                        + " SET " + table.getLogicColumn().columnEqualsSign()
                        + " WHERE 1=1 "
                        + ifTest("whereSql!=null", () -> "${whereSql}");
            }
        });
    }


}
