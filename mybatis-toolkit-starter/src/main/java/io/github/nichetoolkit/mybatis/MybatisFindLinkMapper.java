package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.FindLinkMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Collection;
import java.util.List;

/**
 * <code>MybatisFindLinkMapper</code>
 * <p>The mybatis find link mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <L> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestId
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @see io.github.nichetoolkit.mybatis.MybatisFindMapper
 * @see io.github.nichetoolkit.rice.mapper.FindLinkMapper
 * @since Jdk1.8
 */
public interface MybatisFindLinkMapper<E extends RestId<I>, L, I> extends MybatisMapper<E>, MybatisFindMapper<E, I>, FindLinkMapper<E, L, I> {
    @Override
    default List<E> findByLinkId(@Param("linkId") L linkId) {
        return findByLinkId(linkId, null);
    }

    @Override
    default List<E> findDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") L linkId) {
        return findDynamicByLinkId(tablename, linkId, null);
    }

    @Override
    default List<E> findAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList) {
        return findAllByLinkIds(linkIdList, null);
    }

    @Override
    default List<E> findDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection<L> linkIdList) {
        return findDynamicAllByLinkIds(tablename, linkIdList, null);
    }

    @Override
    default List<E> findByLinkId(@Param("linkId") L linkId, @Param("linkName") String linkName) {
        return findDynamicByLinkId(null, linkId, linkName);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") L linkId, @Param("linkName") String linkName);

    @Override
    default List<E> findAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName) {
        return findDynamicAllByLinkIds(null, linkIdList, linkName);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName);
}
