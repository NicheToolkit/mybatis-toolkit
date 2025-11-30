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
 * @param <E> {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <L> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestId
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @see io.github.nichetoolkit.mybatis.MybatisOperateMapper
 * @see io.github.nichetoolkit.rice.mapper.OperateLinkMapper
 * @since Jdk17
 */
public interface MybatisOperateLinkMapper<E extends RestId<I>, L, I> extends MybatisMapper<E>, MybatisOperateMapper<E, I>, OperateLinkMapper<L, I> {

    @Override
    default Integer operateByLinkId(@Param("linkId") L linkId, @Param("operate") Integer operate) {
        return operateByLinkId(linkId, null, operate);
    }

    @Override
    default Integer operateDynamicByLinkId(@Param("tableName") String tableName, @Param("linkId") L linkId, @Param("operate") Integer operate) {
        return operateDynamicByLinkId(tableName, linkId, null, operate);
    }

    @Override
    default Integer operateAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList, @Param("operate") Integer operate) {
        return operateAllByLinkIds(linkIdList, null, operate);
    }

    @Override
    default Integer operateDynamicAllByLinkIds(@Param("tableName") String tableName, @Param("linkIdList") Collection<L> linkIdList, @Param("operate") Integer operate) {
        return operateDynamicAllByLinkIds(tableName, linkIdList, null, operate);
    }

    @Override
    default Integer operateByLinkId(@Param("linkId") L linkId, @Param("linkName") String linkName, @Param("operate") Integer operate) {
        return operateDynamicByLinkId(null, linkId, linkName, operate);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer operateDynamicByLinkId(@Param("tableName") String tableName, @Param("linkId") L linkId, @Param("linkName") String linkName, @Param("operate") Integer operate);

    @Override
    default Integer operateAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName, @Param("operate") Integer operate) {
        return operateDynamicAllByLinkIds(null, linkIdList, linkName, operate);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer operateDynamicAllByLinkIds(@Param("tableName") String tableName, @Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName, @Param("operate") Integer operate);
}
