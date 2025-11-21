package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.DeleteLinkMapper;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * <code>MybatisDeleteLinkMapper</code>
 * <p>The mybatis delete link mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <L> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestId
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @see io.github.nichetoolkit.mybatis.MybatisDeleteMapper
 * @see io.github.nichetoolkit.rice.mapper.DeleteLinkMapper
 * @since Jdk1.8
 */
public interface MybatisDeleteLinkMapper<E extends RestId<I>, L, I> extends MybatisMapper<E>, MybatisDeleteMapper<E, I>, DeleteLinkMapper<L, I> {

    @Override
    default Integer deleteByLinkId(@Param("linkId") L linkId) {
        return deleteByLinkId(linkId, null);
    }

    @Override
    default Integer deleteDynamicByLinkId(@Param("tableName") String tableName, @Param("linkId") L linkId) {
        return deleteDynamicByLinkId(tableName, linkId, null);
    }

    @Override
    default Integer deleteAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList) {
        return deleteAllByLinkIds(linkIdList, null);
    }

    @Override
    default Integer deleteDynamicAllByLinkIds(@Param("tableName") String tableName, @Param("linkIdList") Collection<L> linkIdList) {
        return deleteDynamicAllByLinkIds(tableName, linkIdList, null);
    }

    @Override
    default Integer deleteByLinkId(@Param("linkId") L linkId, @Param("linkName") String linkName) {
        return deleteDynamicByLinkId(null, linkId, linkName);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(MybatisSqlProviderResolver.class)
    Integer deleteDynamicByLinkId(@Param("tableName") String tableName, @Param("linkId") L linkId, @Param("linkName") String linkName);

    @Override
    default Integer deleteAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName) {
        return deleteDynamicAllByLinkIds(null, linkIdList, linkName);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(MybatisSqlProviderResolver.class)
    Integer deleteDynamicAllByLinkIds(@Param("tableName") String tableName, @Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName);
}
