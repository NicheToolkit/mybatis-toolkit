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
 * <code>MybatisAlertFieldProvider</code>
 * <p>The type mybatis alert field provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class MybatisAlertFieldProvider {


    /**
     * <code>alertFieldById</code>
     * <p>the field by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param id              I <p>the id parameter is <code>I</code> type.</p>
     * @param field           {@link java.lang.String} <p>the field parameter is <code>String</code> type.</p>
     * @param key             {@link java.lang.Integer} <p>the key parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>the field by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.String
     * @see java.lang.Integer
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String alertFieldById(ProviderContext providerContext, @Param("id") I id, @Param("field") String field, @Param("key") Integer key) throws RestException {
        return alertDynamicFieldById(providerContext, null, id, field, key);
    }

    /**
     * <code>alertDynamicFieldById</code>
     * <p>the dynamic field by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param id              I <p>the id parameter is <code>I</code> type.</p>
     * @param field           {@link java.lang.String} <p>the field parameter is <code>String</code> type.</p>
     * @param key             {@link java.lang.Integer} <p>the key parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic field by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.Integer
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String alertDynamicFieldById(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") I id, @Param("field") String field, @Param("key") Integer key) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(id), "the id param of 'alertFieldById' method cannot be empty!", message -> new MybatisParamErrorException("alertFieldById", "id", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(key), "the field param of 'alertFieldById' method cannot be empty!", message -> new MybatisParamErrorException("alertFieldById", "field", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(key), "the key param of 'alertFieldById' method cannot be empty!", message -> new MybatisParamErrorException("alertFieldById", "key", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalHelper.trueable(table.isUseUnionKey(), "the union keys of table with 'alertFieldById' method is unsupported!", message -> new MybatisUnsupportedErrorException("alertFieldById", "unionKeys", message));
            return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tablename())
                    + " SET ${field} = ${key} "
                    + " WHERE " + table.getIdentityColumn().columnEqualsProperty();
        });
    }

    /**
     * <code>alertFieldAll</code>
     * <p>the field all method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>the id list parameter is <code>Collection</code> type.</p>
     * @param field           {@link java.lang.String} <p>the field parameter is <code>String</code> type.</p>
     * @param key             {@link java.lang.Integer} <p>the key parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>the field all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.util.Collection
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.String
     * @see java.lang.Integer
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String alertFieldAll(ProviderContext providerContext, @Param("idList") Collection<I> idList, @Param("field") String field, @Param("key") Integer key) throws RestException {
        return alertDynamicFieldAll(providerContext, null, idList, field, key);
    }

    /**
     * <code>alertDynamicFieldAll</code>
     * <p>the dynamic field all method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>the id list parameter is <code>Collection</code> type.</p>
     * @param field           {@link java.lang.String} <p>the field parameter is <code>String</code> type.</p>
     * @param key             {@link java.lang.Integer} <p>the key parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic field all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see java.util.Collection
     * @see java.lang.Integer
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String alertDynamicFieldAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("field") String field, @Param("key") Integer key) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(idList), "the id list param of 'alertFieldAll' method cannot be empty!", message -> new MybatisParamErrorException("alertFieldAll", "idList", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(field), "the field param of 'alertFieldAll' method cannot be empty!", message -> new MybatisParamErrorException("alertFieldAll", "field", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(key), "the key param of 'alertFieldAll' method cannot be empty!", message -> new MybatisParamErrorException("alertFieldAll", "key", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalHelper.trueable(table.isUseUnionKey(), "the union keys of table with 'alertFieldAll' method is unsupported!", message -> new MybatisUnsupportedErrorException("alertFieldAll", "unionKeys", message));
                return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tablename())
                        + " SET ${field} = ${key} "
                        + " WHERE " + table.getIdentityColumn().getColumnName() + " IN " + foreach("idList", "id", ", ", "(", ")", () -> table.getIdentityColumn().variable());

            }
        });
    }

}
