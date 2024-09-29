package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisCaching;
import io.github.nichetoolkit.mybatis.MybatisEntityMapper;
import io.github.nichetoolkit.mybatis.provider.MybatisFindProvider;
import io.github.nichetoolkit.rice.IdEntity;
import io.github.nichetoolkit.rice.mapper.FindMapper;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;

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
    @SelectProvider(type = MybatisFindProvider.class, method = "findAll")
    List<E> findAll(@Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisFindProvider.class, method = "findDynamicAll")
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
