package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.OperateLinkMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

/**
 * <code>MybatisOperateLinkMapper</code>
 * <p>The mybatis operate link mapper interface.</p>
 * @param <E>  {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <L>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @see  io.github.nichetoolkit.rice.RestId
 * @see  io.github.nichetoolkit.mybatis.MybatisMapper
 * @see  io.github.nichetoolkit.rice.mapper.OperateLinkMapper
 * @see  io.github.nichetoolkit.mybatis.MybatisOperateMapper
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface MybatisOperateLinkMapper<E extends RestId<I>, L, I> extends MybatisMapper<E>, MybatisOperateMapper<E, I>, OperateLinkMapper<L, I> {

    @Override
    default Integer operateByLinkId(@Param("linkId") L linkId, @Param("operate") Integer operate) {
        return operateByLinkId(linkId, null, operate);
    }

    @Override
    default Integer operateDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") L linkId, @Param("operate") Integer operate) {
        return operateDynamicByLinkId(tablename, linkId, null, operate);
    }

    @Override
    default Integer operateAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList, @Param("operate") Integer operate) {
        return operateAllByLinkIds(linkIdList, null, operate);
    }

    @Override
    default Integer operateDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection<L> linkIdList, @Param("operate") Integer operate) {
        return operateDynamicAllByLinkIds(tablename, linkIdList, null, operate);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer operateByLinkId(L linkId, String linkName, Integer operate);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer operateDynamicByLinkId(String tablename, L linkId, String linkName, Integer operate);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer operateAllByLinkIds(Collection<L> linkIdList, String linkName, Integer operate);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer operateDynamicAllByLinkIds(String tablename, Collection<L> linkIdList, String linkName, Integer operate);
}
