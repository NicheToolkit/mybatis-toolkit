package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisMapper;
import io.github.nichetoolkit.mybatis.MybatisSqlProviderResolver;
import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.DeleteMapper;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

public interface MybatisDeleteMapper<E extends RestId<I>, I> extends MybatisMapper<E>, DeleteMapper<I> {

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(MybatisSqlProviderResolver.class)
    Integer deleteById(@Param("id") I id);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(MybatisSqlProviderResolver.class)
    Integer deleteDynamicById(@Param("tablename") String tablename, @Param("id") I id);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(MybatisSqlProviderResolver.class)
    Integer deleteAll(@Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(MybatisSqlProviderResolver.class)
    Integer deleteDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(MybatisSqlProviderResolver.class)
    Integer deleteAllByWhere(@Param("whereSql") String whereSql);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(MybatisSqlProviderResolver.class)
    Integer deleteDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql);
}
