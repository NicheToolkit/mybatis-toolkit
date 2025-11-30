package io.github.nichetoolkit.mybatis.natives;

import io.github.nichetoolkit.mybatis.MybatisMapper;
import io.github.nichetoolkit.mybatis.MybatisSqlProviderResolver;
import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.natives.FilterFickleMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <code>MybatisFilterFickleMapper</code>
 * <p>The mybatis filter fickle mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestId
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @see io.github.nichetoolkit.rice.mapper.natives.FilterFickleMapper
 * @since Jdk17
 */
public interface MybatisFilterFickleMapper<E extends RestId<I>, I> extends MybatisMapper<E>, FilterFickleMapper<E, I> {

    @Override
    default List<E> findAllByFickleWhere(@Param("whereSql") String whereSql, @Param("fickleParams") RestFickle<?>... fickleParams) {
        return findDynamicAllByFickleWhere(null, whereSql, fickleParams);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicAllByFickleWhere(@Param("tableName") String tableName, @Param("whereSql") String whereSql, @Param("fickleParams") RestFickle<?>... fickleParams);

}
