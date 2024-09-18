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
 * <code>MybatisRemoveLinkProvider</code>
 * <p>The type mybatis remove link provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class MybatisRemoveLinkProvider {


    /**
     * <code>removeByLinkId</code>
     * <p>the by link id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param linkId          I <p>the link id parameter is <code>I</code> type.</p>
     * @param logicSign            {@link java.lang.String} <p>the logicSign parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the by link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String removeByLinkId(ProviderContext providerContext, @Param("linkId") I linkId, @Param("logicSign") String logicSign) throws RestException {
        return removeDynamicByLinkId(providerContext, null, linkId, logicSign);
    }

    /**
     * <code>removeDynamicByLinkId</code>
     * <p>the dynamic by link id method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param linkId          I <p>the link id parameter is <code>I</code> type.</p>
     * @param logicSign            {@link java.lang.String} <p>the logicSign parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic by link id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String removeDynamicByLinkId(ProviderContext providerContext, @Param("tablename") String tablename, @Param("linkId") I linkId, @Param("logicSign") String logicSign) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(linkId), "the link id param of 'removeByLinkId' method cannot be empty!", message -> new MybatisParamErrorException("removeByLinkId", "linkId", message));
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(logicSign), "the logicSign param of 'removeByLinkId' method cannot be empty!", message -> new MybatisParamErrorException("removeByLinkId", "logicSign", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.getLogicColumn()), "the logic column of table with 'removeByLinkId' method cannot be empty!", message -> new MybatisTableErrorException("removeByLinkId", "logicColumn", message));
            return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tablename())
                    + " SET " + table.getLogicColumn().columnEqualsSign()
                    + " WHERE " + table.getLinkColumn().columnEqualsLink();
        });
    }

    /**
     * <code>removeAllByLinkIds</code>
     * <p>the all by link ids method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param linkIdList      {@link java.util.Collection} <p>the link id list parameter is <code>Collection</code> type.</p>
     * @param logicSign            {@link java.lang.String} <p>the logicSign parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the all by link ids return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.util.Collection
     * @see org.apache.ibatis.annotations.Param
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String removeAllByLinkIds(ProviderContext providerContext, @Param("linkIdList") Collection<I> linkIdList, @Param("logicSign") String logicSign) throws RestException {
        return removeDynamicAllByLinkIds(providerContext, null, linkIdList, logicSign);
    }

    /**
     * <code>removeDynamicAllByLinkIds</code>
     * <p>the dynamic all by link ids method.</p>
     * @param <I>             {@link java.lang.Object} <p>the parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>the provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>the tablename parameter is <code>String</code> type.</p>
     * @param linkIdList      {@link java.util.Collection} <p>the link id list parameter is <code>Collection</code> type.</p>
     * @param logicSign            {@link java.lang.String} <p>the logicSign parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>the dynamic all by link ids return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see org.apache.ibatis.annotations.Param
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String removeDynamicAllByLinkIds(ProviderContext providerContext, @Param("tablename") String tablename, @Param("linkIdList") Collection<I> linkIdList, @Param("logicSign") String logicSign) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(linkIdList), "the link id list param of 'removeAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("removeAllByLinkIds", "idList", message));
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(logicSign), "the logicSign param of 'removeAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("removeAllByLinkIds", "logicSign", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.getLogicColumn()), "the logic column of table with 'removeAllByLinkIds' method cannot be empty!", message -> new MybatisTableErrorException("removeAllByLinkIds", "logicColumn", message));
                return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tablename())
                        + " SET " + table.getLogicColumn().columnEqualsSign()
                        + " WHERE " + table.getLinkColumn().getColumnName() + " IN " + foreach("linkIdList", "linkId", ", ", "(", ")", () -> "#{linkId}");

            }
        });
    }


}
