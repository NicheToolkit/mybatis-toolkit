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
 * <code>MybatisFindProvider</code>
 * <p>The type mybatis find provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class MybatisFindProvider {

    /**
     * <code>findById</code>
     * <p>the by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param id              I <p>the id parameter is <code>I</code> type.</p>
     * @return {@link java.lang.String} <p>the by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String findById(ProviderContext providerContext, @Param("id") I id) throws RestException {
        return findDynamicById(providerContext, null, id);
    }

    /**
     * <code>findDynamicById</code>
     * <p>the dynamic by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param id              I <p>the id parameter is <code>I</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String findDynamicById(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") I id) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(id), "the id param of 'findById' method cannot be empty!", message -> new MybatisParamErrorException("findById", "id", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findById' method cannot be empty!", message -> new MybatisTableErrorException("findById", "selectColumns", message));
            OptionalUtils.trueable(table.isUseUnionKey(), "the union keys of table with 'findById' method is unsupported!", message -> new MybatisUnsupportedErrorException("findById", "unionKeys", message));
            return "SELECT " + table.selectColumnList()
                    + " FROM " + table.tablename(tablename)
                    + " WHERE " + table.identityColumnEqualsProperty();
        });

    }

    /**
     * <code>findByAll</code>
     * <p>the by all method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>the id list parameter is <code>Collection</code> type.</p>
     * @return {@link java.lang.String} <p>the by all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.util.Collection
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String findByAll(ProviderContext providerContext, @Param("idList") Collection<I> idList) throws RestException {
        return findDynamicByAll(providerContext, null, idList);
    }

    /**
     * <code>findDynamicByAll</code>
     * <p>the dynamic by all method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>the id list parameter is <code>Collection</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic by all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String findDynamicByAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<I> idList) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(idList), "the id list param of 'findByAll' method cannot be empty!", message -> new MybatisParamErrorException("findByAll", "idList", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findByAll' method cannot be empty!", message -> new MybatisTableErrorException("findByAll", "selectColumns", message));
                OptionalUtils.trueable(table.isUseUnionKey(), "the union keys of table with 'findByAll' method is unsupported!", message -> new MybatisUnsupportedErrorException("findByAll", "unionKeys", message));
                return "SELECT " + table.selectColumnList()
                        + " FROM " + table.tablename(tablename)
                        + " WHERE " + table.getIdentityColumn().columnName() + " IN " + foreach("idList", "id", ", ", "(", ")", () -> table.getIdentityColumn().variable());

            }
        });
    }

    /**
     * <code>findAllByWhere</code>
     * <p>the all by where method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param whereSql        {@link java.lang.String} <p>the where sql parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the all by where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String findAllByWhere(ProviderContext providerContext, @Param("whereSql") String whereSql) throws RestException {
        return findDynamicAllByWhere(providerContext, null, whereSql);
    }

    /**
     * <code>findDynamicAllByWhere</code>
     * <p>the dynamic all by where method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param whereSql        {@link java.lang.String} <p>the where sql parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic all by where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String findDynamicAllByWhere(ProviderContext providerContext, @Param("tablename") String tablename, @Param("whereSql") String whereSql) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(whereSql), "the where sql param of 'findAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("findAllByWhere", "whereSql", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findAllByWhere' method cannot be empty!", message -> new MybatisTableErrorException("findAllByWhere", "selectColumns", message));
                return "SELECT " + table.selectColumnList()
                        + " FROM " + table.tablename(tablename)
                        + " WHERE 1=1 "
                        + ifTest("whereSql!=null", () -> "${whereSql}");
            }
        });
    }

}
