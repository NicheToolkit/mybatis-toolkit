package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.FindMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Collection;
import java.util.List;

/**
 * <code>MybatisFindMapper</code>
 * <p>The mybatis find mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestId
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @see io.github.nichetoolkit.rice.mapper.FindMapper
 * @since Jdk1.8
 */
public interface MybatisFindMapper<E extends RestId<I>, I> extends MybatisMapper<E>, FindMapper<E, I> {

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    E findById(@Param("id") I id);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    E findDynamicById(@Param("tablename") String tablename, @Param("id") I id);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findAll(@Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findAllByWhere(@Param("whereSql") String whereSql);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql);
}
