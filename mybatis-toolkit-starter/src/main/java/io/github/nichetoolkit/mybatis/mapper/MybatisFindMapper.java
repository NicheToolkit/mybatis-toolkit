package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisSqlProviderResolver;
import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.mybatis.MybatisMapper;
import io.github.nichetoolkit.rice.IdEntity;
import io.github.nichetoolkit.rice.mapper.FindMapper;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;

public interface MybatisFindMapper<E extends IdEntity<I>, I> extends MybatisMapper<E>, FindMapper<E, I> {

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(type = MybatisSqlProviderResolver.class)
    E findById(@Param("id") I id);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(type = MybatisSqlProviderResolver.class)
    E findDynamicById(@Param("tablename") String tablename, @Param("id") I id);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(type = MybatisSqlProviderResolver.class)
    List<E> findAll(@Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(type = MybatisSqlProviderResolver.class)
    List<E> findDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(type = MybatisSqlProviderResolver.class)
    List<E> findAllByWhere(@Param("whereSql") String whereSql);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(type = MybatisSqlProviderResolver.class)
    List<E> findDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql);
}
