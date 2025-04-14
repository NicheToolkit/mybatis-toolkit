package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.FindLinkMapper;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Lang;

import java.util.Collection;
import java.util.List;

public interface MybatisFindLinkMapper<E extends RestId<I>, L, I> extends MybatisMapper<E>, MybatisFindMapper<E, I>, FindLinkMapper<E, L, I> {
    @Override
    default List<E> findByLinkId(L linkId) {
        return findByLinkId(linkId, null);
    }

    @Override
    default List<E> findDynamicByLinkId(String tablename, L linkId) {
        return findDynamicByLinkId(tablename, linkId, null);
    }

    @Override
    default List<E> findAllByLinkIds(Collection<L> linkIdList) {
        return findAllByLinkIds(linkIdList, null);
    }

    @Override
    default List<E> findDynamicAllByLinkIds(String tablename, Collection<L> linkIdList) {
        return findDynamicAllByLinkIds(tablename, linkIdList, null);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(MybatisSqlProviderResolver.class)
    List<E> findByLinkId(L linkId, String linkName);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicByLinkId(String tablename, L linkId, String linkName);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(MybatisSqlProviderResolver.class)
    List<E> findAllByLinkIds(Collection<L> linkIdList, String linkName);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicAllByLinkIds(String tablename, Collection<L> linkIdList, String linkName);
}
