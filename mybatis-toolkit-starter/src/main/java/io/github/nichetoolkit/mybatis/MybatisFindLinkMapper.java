package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.FindLinkMapper;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Collection;
import java.util.List;

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
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findByLinkId(@Param("linkId") L linkId, @Param("linkName") String linkName);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") L linkId, @Param("linkName") String linkName);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName);
}
