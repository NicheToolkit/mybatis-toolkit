package io.github.nichetoolkit.mybatis.provider;

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
import java.util.Optional;

/**
 * <code>MybatisOperateLinkProvider</code>
 * <p>The type mybatis operate link provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class MybatisOperateLinkProvider {


    /**
     * <code>operateByLinkId</code>
     * <p>the by link id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param linkId          I <p>the link id parameter is <code>I</code> type.</p>
     * @param operate         {@link java.lang.Integer} <p>the operate parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>the by link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.Integer
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String operateByLinkId(ProviderContext providerContext, @Param("linkId") I linkId, @Param("operate") Integer operate) throws RestException {
        return operateDynamicByLinkId(providerContext, null, linkId, operate);
    }

    /**
     * <code>operateDynamicByLinkId</code>
     * <p>the dynamic by link id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param linkId          I <p>the link id parameter is <code>I</code> type.</p>
     * @param operate         {@link java.lang.Integer} <p>the operate parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic by link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.Integer
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String operateDynamicByLinkId(ProviderContext providerContext, @Param("tablename") String tablename, @Param("linkId") I linkId, @Param("operate") Integer operate) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(linkId), "the link id param of 'operateByLinkId' method cannot be empty!", message -> new MybatisTableErrorException("operateByLinkId", "linkId", message));
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(operate), "the operate param of 'operateByLinkId' method cannot be empty!", message -> new MybatisParamErrorException("operateByLinkId", "operate", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.getOperateColumn()), "the operate column of table with 'operateByLinkId' method cannot be empty!", message -> new MybatisTableErrorException("operateByLinkId", "operateColumn", message));
            return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tablename())
                    + " SET " + table.getOperateColumn().columnEqualsOperate()
                    + " WHERE " + table.getLinkColumn().columnEqualsLink();
        });
    }

    /**
     * <code>operateAllByLinkIds</code>
     * <p>the all by link ids method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param linkIdList      {@link java.util.Collection} <p>the link id list parameter is <code>Collection</code> type.</p>
     * @param operate         {@link java.lang.Integer} <p>the operate parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>the all by link ids return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.util.Collection
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.Integer
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String operateAllByLinkIds(ProviderContext providerContext, @Param("linkIdList") Collection<I> linkIdList, @Param("operate") Integer operate) throws RestException {
        return operateDynamicAllByLinkIds(providerContext, null, linkIdList, operate);
    }

    /**
     * <code>operateDynamicAllByLinkIds</code>
     * <p>the dynamic all by link ids method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param linkIdList      {@link java.util.Collection} <p>the link id list parameter is <code>Collection</code> type.</p>
     * @param operate         {@link java.lang.Integer} <p>the operate parameter is <code>Integer</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic all by link ids return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see java.util.Collection
     * @see java.lang.Integer
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String operateDynamicAllByLinkIds(ProviderContext providerContext, @Param("tablename") String tablename, @Param("linkIdList") Collection<I> linkIdList, @Param("operate") Integer operate) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(linkIdList), "the link id list param of 'operateAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("operateAllByLinkIds", "idList", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(operate), "the operate param of 'operateAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("operateAllByLinkIds", "operate", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.getOperateColumn()), "the operate column of table with 'operateAllByLinkIds' method cannot be empty!", message -> new MybatisTableErrorException("operateAllByLinkIds", "operateColumn", message));
                return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tablename())
                        + " SET " + table.getOperateColumn().columnEqualsOperate()
                        + " WHERE " + table.getLinkColumn().getColumnName() + " IN " + foreach("linkIdList", "linkId", ", ", "(", ")", () -> "#{linkId}");

            }
        });
    }


}
