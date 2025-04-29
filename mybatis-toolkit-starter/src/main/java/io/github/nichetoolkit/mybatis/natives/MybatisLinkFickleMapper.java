package io.github.nichetoolkit.mybatis.natives;

import io.github.nichetoolkit.mybatis.MybatisMapper;
import io.github.nichetoolkit.mybatis.MybatisSqlProviderResolver;
import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.natives.LinkFickleMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Collection;
import java.util.List;

public interface MybatisLinkFickleMapper<E extends RestId<I>, L, I> extends MybatisMapper<E>, LinkFickleMapper<E, L, I> {

    @Override
    default List<E> findByLinkIdFickle(@Param("linkId") L linkId, @Param("fickleParams") RestFickle<?>... fickleParams) {
        return findByLinkIdFickle(linkId, null, fickleParams);
    }

    @Override
    default List<E> findDynamicByLinkIdFickle(@Param("tablename") String tablename, @Param("linkId") L linkId, @Param("fickleParams") RestFickle<?>... fickleParams) {
        return findDynamicByLinkIdFickle(tablename, linkId, null, fickleParams);
    }

    @Override
    default List<E> findAllByLinkIdsFickle(@Param("linkIdList") Collection<L> linkIdList, @Param("fickleParams") RestFickle<?>... fickleParams) {
        return findAllByLinkIdsFickle(linkIdList, null, fickleParams);
    }

    @Override
    default List<E> findDynamicAllByLinkIdsFickle(@Param("tablename") String tablename, @Param("linkIdList") Collection<L> linkIdList, @Param("fickleParams") RestFickle<?>... fickleParams) {
        return findDynamicAllByLinkIdsFickle(tablename, linkIdList, null, fickleParams);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findByLinkIdFickle(@Param("linkId") L linkId, @Param("linkName") String linkName, @Param("fickleParams") RestFickle<?>... fickleParams);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicByLinkIdFickle(@Param("tablename") String tablename, @Param("linkId") L linkId, @Param("linkName") String linkName, @Param("fickleParams") RestFickle<?>... fickleParams);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findAllByLinkIdsFickle(@Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName, @Param("fickleParams") RestFickle<?>... fickleParams);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicAllByLinkIdsFickle(@Param("tablename") String tablename, @Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName, @Param("fickleParams") RestFickle<?>... fickleParams);

}
