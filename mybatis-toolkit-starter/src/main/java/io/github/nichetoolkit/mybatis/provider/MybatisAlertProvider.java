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
 * <code>MybatisAlertProvider</code>
 * <p>The type mybatis alert provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class MybatisAlertProvider {


    /**
     * <code>alertById</code>
     * <p>the by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param id              I <p>the id parameter is <code>I</code> type.</p>
     * @param key             {@link java.lang.Integer} <p>the key parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>the by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.Integer
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String alertById(ProviderContext providerContext, @Param("id") I id, @Param("key") Integer key) throws RestException {
        return alertDynamicById(providerContext, null, id, key);
    }

    /**
     * <code>alertDynamicById</code>
     * <p>the dynamic by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param id              I <p>the id parameter is <code>I</code> type.</p>
     * @param key             {@link java.lang.Integer} <p>the key parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.Integer
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String alertDynamicById(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") I id, @Param("key") Integer key) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(id), "the id param of 'alertById' method cannot be empty!", message -> new MybatisParamErrorException("alertById", "id", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(key), "the key param of 'alertById' method cannot be empty!", message -> new MybatisParamErrorException("alertById", "key", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.getAlertColumn()), "the alert column of table with 'alertById' method cannot be empty!", message -> new MybatisTableErrorException("alertById", "alertColumn", message));
            OptionalHelper.trueable(table.isUseUnionKey(), "the union keys of table with 'alertById' method is unsupported!", message -> new MybatisUnsupportedErrorException("alertById", "unionKeys", message));
            return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tablename())
                    + " SET " + table.getAlertColumn().columnEqualsKey()
                    + " WHERE " + table.getIdentityColumn().columnEqualsProperty();
        });
    }

    /**
     * <code>alertAll</code>
     * <p>the all method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>the id list parameter is <code>Collection</code> type.</p>
     * @param key             {@link java.lang.Integer} <p>the key parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>the all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.util.Collection
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.Integer
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String alertAll(ProviderContext providerContext, @Param("idList") Collection<I> idList, @Param("key") Integer key) throws RestException {
        return alertDynamicAll(providerContext, null, idList, key);
    }

    /**
     * <code>alertDynamicAll</code>
     * <p>the dynamic all method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>the id list parameter is <code>Collection</code> type.</p>
     * @param key             {@link java.lang.Integer} <p>the key parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see java.util.Collection
     * @see java.lang.Integer
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String alertDynamicAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("key") Integer key) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(idList), "the id list param of 'alertAll' method cannot be empty!", message -> new MybatisParamErrorException("alertAll", "idList", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(key), "the key param of 'alertAll' method cannot be empty!", message -> new MybatisParamErrorException("alertAll", "key", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.getAlertColumn()), "the alert column of table with 'alertAll' method cannot be empty!", message -> new MybatisTableErrorException("alertAll", "alertColumn", message));
                OptionalHelper.trueable(table.isUseUnionKey(), "the union keys of table with 'alertAll' method is unsupported!", message -> new MybatisUnsupportedErrorException("alertAll", "unionKeys", message));
                return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tablename())
                        + " SET " + table.getAlertColumn().columnEqualsKey()
                        + " WHERE " + table.getIdentityColumn().getColumnName() + " IN " + foreach("idList", "id", ", ", "(", ")", () -> table.getIdentityColumn().variable());

            }
        });
    }

}
