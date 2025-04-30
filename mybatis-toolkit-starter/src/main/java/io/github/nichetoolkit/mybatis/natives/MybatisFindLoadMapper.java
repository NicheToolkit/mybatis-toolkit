package io.github.nichetoolkit.mybatis.natives;

import io.github.nichetoolkit.mybatis.MybatisMapper;
import io.github.nichetoolkit.mybatis.MybatisSqlProviderResolver;
import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.mybatis.load.RestLoad;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.natives.FindLoadMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Collection;
import java.util.List;

/**
 * <code>MybatisFindLoadMapper</code>
 * <p>The mybatis find load mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestId
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @see io.github.nichetoolkit.rice.mapper.natives.FindLoadMapper
 * @since Jdk1.8
 */
public interface MybatisFindLoadMapper <E extends RestId<I>, I> extends MybatisMapper<E>, FindLoadMapper<E, I> {

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    E findByIdLoad(@Param("id") I id, @Param("loadParams") RestLoad... loadParams);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    E findDynamicByIdLoad(@Param("tablename") String tablename, @Param("id") I id, @Param("loadParams") RestLoad... loadParams);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findAllLoad(@Param("idList") Collection<I> idList, @Param("loadParams") RestLoad... loadParams);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicAllLoad(@Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("loadParams") RestLoad... loadParams);

}
