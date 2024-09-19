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
 * <code>MybatisOperateProvider</code>
 * <p>The type mybatis operate provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class MybatisOperateProvider {


    /**
     * <code>operateById</code>
     * <p>the by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param id              I <p>the id parameter is <code>I</code> type.</p>
     * @param operate         {@link java.lang.Integer} <p>the operate parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>the by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.Integer
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String operateById(ProviderContext providerContext, @Param("id") I id, @Param("operate") Integer operate) throws RestException {
        return operateDynamicById(providerContext, null, id, operate);
    }

    /**
     * <code>operateDynamicById</code>
     * <p>the dynamic by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param id              I <p>the id parameter is <code>I</code> type.</p>
     * @param operate         {@link java.lang.Integer} <p>the operate parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.Integer
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String operateDynamicById(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") I id, @Param("operate") Integer operate) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(id), "the id param of 'operateById' method cannot be empty!", message -> new MybatisTableErrorException("operateById", "id", message));
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(operate), "the operate param of 'operateById' method cannot be empty!", message -> new MybatisParamErrorException("operateById", "operate", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.getOperateColumn()), "the operate column of table with 'operateById' method cannot be empty!", message -> new MybatisTableErrorException("operateById", "operateColumn", message));
            OptionalUtils.trueable(table.isUseUnionKey(), "the union keys of table with 'operateById' method is unsupported!", message -> new MybatisUnsupportedErrorException("operateById", "unionKeys", message));
            return "UPDATE " + table.tablename(tablename)
                    + " SET " + table.getOperateColumn().columnEqualsOperate()
                    + " WHERE " + table.identityColumnEqualsProperty();
        });
    }

    /**
     * <code>operateAll</code>
     * <p>the all method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>the id list parameter is <code>Collection</code> type.</p>
     * @param operate         {@link java.lang.Integer} <p>the operate parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>the all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.util.Collection
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.Integer
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String operateAll(ProviderContext providerContext, @Param("idList") Collection<I> idList, @Param("operate") Integer operate) throws RestException {
        return operateDynamicAll(providerContext, null, idList, operate);
    }

    /**
     * <code>operateDynamicAll</code>
     * <p>the dynamic all method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>the id list parameter is <code>Collection</code> type.</p>
     * @param operate         {@link java.lang.Integer} <p>the operate parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see java.util.Collection
     * @see java.lang.Integer
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String operateDynamicAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("operate") Integer operate) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(idList), "the id list param of 'operateAll' method cannot be empty!", message -> new MybatisParamErrorException("operateAll", "idList", message));
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(operate), "the operate param of 'operateAll' method cannot be empty!", message -> new MybatisParamErrorException("operateAll", "operate", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.getOperateColumn()), "the operate column of table with 'operateAll' method cannot be empty!", message -> new MybatisTableErrorException("operateAll", "operateColumn", message));
                OptionalUtils.trueable(table.isUseUnionKey(), "the union keys of table with 'operateAll' method is unsupported!", message -> new MybatisUnsupportedErrorException("operateAll", "unionKeys", message));
                return "UPDATE " + table.tablename(tablename)
                        + " SET " + table.getOperateColumn().columnEqualsOperate()
                        + " WHERE " + table.getIdentityColumn().columnName() + " IN " + foreach("idList", "id", ", ", "(", ")", () -> table.getIdentityColumn().variable());

            }
        });
    }

    /**
     * <code>operateAllByWhere</code>
     * <p>the all by where method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param whereSql        {@link java.lang.String} <p>the where sql parameter is <code>String</code> type.</p>
     * @param operate         {@link java.lang.Integer} <p>the operate parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>the all by where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.Integer
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String operateAllByWhere(ProviderContext providerContext, @Param("whereSql") String whereSql, @Param("operate") Integer operate) throws RestException {
        return operateDynamicAllByWhere(providerContext, null, whereSql, operate);
    }

    /**
     * <code>operateDynamicAllByWhere</code>
     * <p>the dynamic all by where method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param whereSql        {@link java.lang.String} <p>the where sql parameter is <code>String</code> type.</p>
     * @param operate         {@link java.lang.Integer} <p>the operate parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic all by where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.Integer
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String operateDynamicAllByWhere(ProviderContext providerContext, @Param("tablename") String tablename, @Param("whereSql") String whereSql, @Param("operate") Integer operate) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(whereSql), "the where sql param of 'operateAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("operateAllByWhere", "whereSql", message));
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(operate), "the operate param of 'operateAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("operateAllByWhere", "operate", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.getOperateColumn()), "the operate column of table with 'operateAllByWhere' method cannot be empty!", message -> new MybatisTableErrorException("operateAllByWhere", "operateColumn", message));
                OptionalUtils.trueable(table.isUseUnionKey(), "the union keys of table with 'operateAllByWhere' method is unsupported!", message -> new MybatisUnsupportedErrorException("operateAllByWhere", "unionKeys", message));
                return "UPDATE " + table.tablename(tablename)
                        + " SET " + table.getOperateColumn().columnEqualsOperate()
                        + " WHERE 1=1 "
                        + ifTest("whereSql!=null", () -> "${whereSql}");
            }
        });
    }


}
