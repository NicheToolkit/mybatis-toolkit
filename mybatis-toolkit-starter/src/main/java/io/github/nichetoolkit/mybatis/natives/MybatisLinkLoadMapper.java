package io.github.nichetoolkit.mybatis.natives;

import io.github.nichetoolkit.mybatis.MybatisMapper;
import io.github.nichetoolkit.mybatis.MybatisSqlProviderResolver;
import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.mybatis.load.RestLoad;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.natives.LinkLoadMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Collection;
import java.util.List;

/**
 * <code>MybatisLinkLoadMapper</code>
 * <p>The mybatis link load mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <L> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestId
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @see io.github.nichetoolkit.rice.mapper.natives.LinkLoadMapper
 * @since Jdk17
 */
public interface MybatisLinkLoadMapper<E extends RestId<I>, L, I> extends MybatisMapper<E>, LinkLoadMapper<E, L, I> {

    @Override
    default List<E> findByLinkIdLoad(@Param("linkId") L linkId, @Param("loadParams") RestLoad... loadParams) {
        return findByLinkIdLoad(linkId, null, loadParams);
    }

    @Override
    default List<E> findDynamicByLinkIdLoad(@Param("tableName") String tableName, @Param("linkId") L linkId, @Param("loadParams") RestLoad... loadParams) {
        return findDynamicByLinkIdLoad(tableName, linkId, null, loadParams);
    }

    @Override
    default List<E> findAllByLinkIdsLoad(@Param("linkIdList") Collection<L> linkIdList, @Param("loadParams") RestLoad... loadParams) {
        return findAllByLinkIdsLoad(linkIdList, null, loadParams);
    }

    @Override
    default List<E> findDynamicAllByLinkIdsLoad(@Param("tableName") String tableName, @Param("linkIdList") Collection<L> linkIdList, @Param("loadParams") RestLoad... loadParams) {
        return findDynamicAllByLinkIdsLoad(tableName, linkIdList, null, loadParams);
    }

    @Override
    default List<E> findByLinkIdLoad(@Param("linkId") L linkId, @Param("linkName") String linkName, @Param("loadParams") RestLoad... loadParams) {
        return findDynamicByLinkIdLoad(null, linkId, linkName, loadParams);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicByLinkIdLoad(@Param("tableName") String tableName, @Param("linkId") L linkId, @Param("linkName") String linkName, @Param("loadParams") RestLoad... loadParams);

    @Override
    default List<E> findAllByLinkIdsLoad(@Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName, @Param("loadParams") RestLoad... loadParams) {
        return findDynamicAllByLinkIdsLoad(null, linkIdList, linkName, loadParams);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicAllByLinkIdsLoad(@Param("tableName") String tableName, @Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName, @Param("loadParams") RestLoad... loadParams);

}
