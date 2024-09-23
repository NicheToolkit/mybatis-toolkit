package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisCaching;
import io.github.nichetoolkit.mybatis.MybatisEntityMapper;
import io.github.nichetoolkit.mybatis.provider.MybatisFindProvider;
import io.github.nichetoolkit.rice.IdEntity;
import io.github.nichetoolkit.rice.mapper.FindMapper;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;

/**
 * <code>MybatisFindMapper</code>
 * <p>The type mybatis find mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.IdEntity} <p>the generic parameter is <code>IdEntity</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>the parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.IdEntity
 * @see io.github.nichetoolkit.mybatis.MybatisEntityMapper
 * @see io.github.nichetoolkit.rice.mapper.FindMapper
 * @since Jdk1.8
 */
public interface MybatisFindMapper<E extends IdEntity<I>, I> extends MybatisEntityMapper<E>, FindMapper<E, I> {

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisFindProvider.class, method = "findById")
    E findById(@Param("id") I id);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisFindProvider.class, method = "findDynamicById")
    E findDynamicById(@Param("tablename") String tablename, @Param("id") I id);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisFindProvider.class, method = "findByAll")
    List<E> findAll(@Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisFindProvider.class, method = "findDynamicByAll")
    List<E> findDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisFindProvider.class, method = "findAllByWhere")
    List<E> findAllByWhere(@Param("whereSql") String whereSql);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisFindProvider.class, method = "findDynamicAllByWhere")
    List<E> findDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql);
}
