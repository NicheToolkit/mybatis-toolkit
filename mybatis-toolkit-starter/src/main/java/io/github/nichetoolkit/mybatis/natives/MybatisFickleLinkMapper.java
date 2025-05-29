package io.github.nichetoolkit.mybatis.natives;

import io.github.nichetoolkit.mybatis.MybatisMapper;
import io.github.nichetoolkit.mybatis.MybatisSqlProviderResolver;
import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.mybatis.load.RestLoad;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.natives.FickleLinkMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Collection;
import java.util.List;

public interface MybatisFickleLinkMapper<E extends RestId<I>, L, I> extends MybatisMapper<E>, FickleLinkMapper<E, L, I> {

    @Override
    default List<E> findByLinkIdFickleLoad(@Param("linkId") L linkId, @Param("fickleParams") RestFickle<?>[] fickleParams, @Param("loadParams") RestLoad... loadParams) {
        return findByLinkIdFickleLoad(linkId, null, fickleParams, loadParams);
    }

    @Override
    default List<E> findDynamicByLinkIdFickleLoad(@Param("tablename") String tablename, @Param("id") L linkId, @Param("fickleParams") RestFickle<?>[] fickleParams, @Param("loadParams") RestLoad... loadParams) {
        return findDynamicByLinkIdFickleLoad(tablename, linkId, null, fickleParams, loadParams);
    }

    @Override
    default List<E> findAllByLinkIdsFickleLoad(@Param("linkIdList") Collection<L> linkIdList, @Param("fickleParams") RestFickle<?>[] fickleParams, @Param("loadParams") RestLoad... loadParams) {
        return findAllByLinkIdsFickleLoad(linkIdList, null, fickleParams, loadParams);
    }

    @Override
    default List<E> findDynamicAllByLinkIdsFickleLoad(@Param("tablename") String tablename, @Param("linkIdList") Collection<L> linkIdList, @Param("fickleParams") RestFickle<?>[] fickleParams, @Param("loadParams") RestLoad... loadParams) {
        return findDynamicAllByLinkIdsFickleLoad(tablename, linkIdList, null, fickleParams, loadParams);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findByLinkIdFickleLoad(@Param("linkId") L linkId, @Param("linkName") String linkName, @Param("fickleParams") RestFickle<?>[] fickleParams, @Param("loadParams") RestLoad... loadParams);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicByLinkIdFickleLoad(@Param("tablename") String tablename, @Param("id") L linkId, @Param("linkName") String linkName, @Param("fickleParams") RestFickle<?>[] fickleParams, @Param("loadParams") RestLoad... loadParams);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findAllByLinkIdsFickleLoad(@Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName, @Param("fickleParams") RestFickle<?>[] fickleParams, @Param("loadParams") RestLoad... loadParams);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicAllByLinkIdsFickleLoad(@Param("tablename") String tablename, @Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName, @Param("fickleParams") RestFickle<?>[] fickleParams, @Param("loadParams") RestLoad... loadParams);

}
