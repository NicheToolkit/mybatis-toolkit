package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.mybatis.MybatisMapper;
import io.github.nichetoolkit.mybatis.provider.MybatisDeleteProvider;
import io.github.nichetoolkit.rice.IdEntity;
import io.github.nichetoolkit.rice.mapper.DeleteMapper;
import org.apache.ibatis.annotations.*;

import java.util.Collection;

public interface MybatisDeleteMapper<E extends IdEntity<I>, I> extends MybatisMapper<E>, DeleteMapper<I> {

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(type = MybatisDeleteProvider.class, method = "deleteById")
    Integer deleteById(@Param("id") I id);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(type = MybatisDeleteProvider.class, method = "deleteDynamicById")
    Integer deleteDynamicById(@Param("tablename") String tablename, @Param("id") I id);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(type = MybatisDeleteProvider.class, method = "deleteAll")
    Integer deleteAll(@Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(type = MybatisDeleteProvider.class, method = "deleteDynamicAll")
    Integer deleteDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(type = MybatisDeleteProvider.class, method = "deleteAllByWhere")
    Integer deleteAllByWhere(@Param("whereSql") String whereSql);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(type = MybatisDeleteProvider.class, method = "deleteDynamicAllByWhere")
    Integer deleteDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql);
}
