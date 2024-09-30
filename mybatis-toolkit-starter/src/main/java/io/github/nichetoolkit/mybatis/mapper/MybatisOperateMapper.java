package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.mybatis.provider.MybatisOperateProvider;
import io.github.nichetoolkit.rice.mapper.OperateMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

public interface MybatisOperateMapper<I> extends OperateMapper<I> {

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(type = MybatisOperateProvider.class, method = "operateById")
    Integer operateById(@Param("id") I id, @Param("operate") Integer operate);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(type = MybatisOperateProvider.class, method = "operateDynamicById")
    Integer operateDynamicById(@Param("tablename") String tablename, @Param("id") I id, @Param("operate") Integer operate);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(type = MybatisOperateProvider.class, method = "operateAll")
    Integer operateAll(@Param("idList") Collection<I> idList, @Param("operate") Integer operate);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(type = MybatisOperateProvider.class, method = "operateDynamicAll")
    Integer operateDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("operate") Integer operate);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(type = MybatisOperateProvider.class, method = "operateAllByWhere")
    Integer operateAllByWhere(@Param("whereSql") String whereSql, @Param("operate") Integer operate);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(type = MybatisOperateProvider.class, method = "operateDynamicAllByWhere")
    Integer operateDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql, @Param("operate") Integer operate);
}
