package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.helper.OptionalHelper;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Collection;
import java.util.Optional;

/**
 * <code>MybatisDeleteLinkProvider</code>
 * <p>The type mybatis delete link provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class MybatisDeleteLinkProvider {


    /**
     * <code>deleteByLinkId</code>
     * <p>the by link id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param linkId          I <p>the link id parameter is <code>I</code> type.</p>
     * @return {@link java.lang.String} <p>the by link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String deleteByLinkId(ProviderContext providerContext, @Param("linkId") I linkId) throws RestException {
        return deleteDynamicByLinkId(providerContext, null, linkId);
    }

    /**
     * <code>deleteDynamicByLinkId</code>
     * <p>the dynamic by link id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param linkId          I <p>the link id parameter is <code>I</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic by link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String deleteDynamicByLinkId(ProviderContext providerContext, @Param("tablename") String tablename, @Param("linkId") I linkId) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(linkId), "the link id param of 'deleteByLinkId' method cannot be empty!", message -> new MybatisTableErrorException("deleteByLinkId", "linkId", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.getOperateColumn()), "the delete column of table with 'deleteByLinkId' method cannot be empty!", message -> new MybatisTableErrorException("deleteByLinkId", "deleteColumn", message));
            return "DELETE FROM " + Optional.ofNullable(tablename).orElse(table.tablename())
                    + " WHERE " + table.getLinkColumn().columnEqualsLink();
        });
    }

    /**
     * <code>deleteAllByLinkIds</code>
     * <p>the all by link ids method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param linkIdList      {@link java.util.Collection} <p>the link id list parameter is <code>Collection</code> type.</p>
     * @return {@link java.lang.String} <p>the all by link ids return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.util.Collection
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String deleteAllByLinkIds(ProviderContext providerContext, @Param("linkIdList") Collection<I> linkIdList) throws RestException {
        return deleteDynamicAllByLinkIds(providerContext, null, linkIdList);
    }

    /**
     * <code>deleteDynamicAllByLinkIds</code>
     * <p>the dynamic all by link ids method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param linkIdList      {@link java.util.Collection} <p>the link id list parameter is <code>Collection</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic all by link ids return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String deleteDynamicAllByLinkIds(ProviderContext providerContext, @Param("tablename") String tablename, @Param("linkIdList") Collection<I> linkIdList) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(linkIdList), "the link id list param of 'deleteAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("deleteAllByLinkIds", "idList", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.getOperateColumn()), "the delete column of table with 'deleteAllByLinkIds' method cannot be empty!", message -> new MybatisTableErrorException("deleteAllByLinkIds", "deleteColumn", message));
                return "DELETE FROM " + Optional.ofNullable(tablename).orElse(table.tablename())
                        + " WHERE " + table.getLinkColumn().getColumnName() + " IN " + foreach("linkIdList", "linkId", ", ", "(", ")", () -> "#{linkId}");

            }
        });
    }


}
