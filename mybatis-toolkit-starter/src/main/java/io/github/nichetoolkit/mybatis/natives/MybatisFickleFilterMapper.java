package io.github.nichetoolkit.mybatis.natives;

import io.github.nichetoolkit.mybatis.MybatisMapper;
import io.github.nichetoolkit.mybatis.MybatisSqlProviderResolver;
import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.mybatis.load.RestLoad;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.natives.FickleFilterMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <code>MybatisFickleFilterMapper</code>
 * <p>The mybatis fickle filter mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestId
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @see io.github.nichetoolkit.rice.mapper.natives.FickleFilterMapper
 * @since Jdk1.8
 */
public interface MybatisFickleFilterMapper<E extends RestId<I>, I> extends MybatisMapper<E>, FickleFilterMapper<E, I> {

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findAllByFickleLoadWhere(@Param("whereSql") String whereSql, @Param("fickleParams") RestFickle<?>[] fickleParams, @Param("loadParams") RestLoad... loadParams);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicAllByFickleLoadWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql, @Param("fickleParams") RestFickle<?>[] fickleParams, @Param("loadParams") RestLoad... loadParams);

}
