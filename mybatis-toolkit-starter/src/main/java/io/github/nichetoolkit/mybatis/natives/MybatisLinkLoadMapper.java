package io.github.nichetoolkit.mybatis.natives;

import io.github.nichetoolkit.mybatis.MybatisMapper;
import io.github.nichetoolkit.mybatis.MybatisSqlProviderResolver;
import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.mybatis.load.RestLoad;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.natives.FindLoadMapper;
import io.github.nichetoolkit.rice.mapper.natives.LinkLoadMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Collection;
import java.util.List;

public interface MybatisLinkLoadMapper<E extends RestId<I>, L, I> extends MybatisMapper<E>, LinkLoadMapper<E, L, I> {

    @Override
    default List<E> findByLinkIdLoad(@Param("linkId") L linkId, @Param("loadParams") RestLoad... loadParams) {
        return findByLinkIdLoad(linkId, null, loadParams);
    }

    @Override
    default List<E> findDynamicByLinkIdLoad(@Param("tablename") String tablename, @Param("linkId") L linkId, @Param("loadParams") RestLoad... loadParams) {
        return findDynamicByLinkIdLoad(tablename, linkId, null, loadParams);
    }

    @Override
    default List<E> findAllByLinkIdsLoad(@Param("linkIdList") Collection<L> linkIdList, @Param("loadParams") RestLoad... loadParams) {
        return findAllByLinkIdsLoad(linkIdList, null, loadParams);
    }

    @Override
    default List<E> findDynamicAllByLinkIdsLoad(@Param("tablename") String tablename, @Param("linkIdList") Collection<L> linkIdList, @Param("loadParams") RestLoad... loadParams) {
        return findDynamicAllByLinkIdsLoad(tablename, linkIdList, null, loadParams);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findByLinkIdLoad(@Param("linkId") L linkId, @Param("linkName") String linkName, @Param("loadParams") RestLoad... loadParams);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicByLinkIdLoad(@Param("tablename") String tablename, @Param("linkId") L linkId, @Param("linkName") String linkName, @Param("loadParams") RestLoad... loadParams);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findAllByLinkIdsLoad(@Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName, @Param("loadParams") RestLoad... loadParams);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicAllByLinkIdsLoad(@Param("tablename") String tablename, @Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName, @Param("loadParams") RestLoad... loadParams);

}
