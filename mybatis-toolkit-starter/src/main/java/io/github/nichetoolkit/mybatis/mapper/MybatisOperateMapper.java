package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisCaching;
import io.github.nichetoolkit.mybatis.provider.MybatisOperateProvider;
import io.github.nichetoolkit.rice.mapper.OperateMapper;
import io.github.nichetoolkit.rice.mapper.RemoveMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

/**
 * <p>MybatisMapper</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface MybatisOperateMapper<I> extends OperateMapper<I> {

    @Override
    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisOperateProvider.class, method = "operateById")
    Integer operateById(@Param("id") I id, @Param("operate") Integer operate);

    @Override
    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisOperateProvider.class, method = "operateDynamicById")
    Integer operateDynamicById(@Param("tablename") String tablename, @Param("id") I id, @Param("operate") Integer operate);

    @Override
    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisOperateProvider.class, method = "operateAll")
    Integer operateAll(@Param("idList") Collection<I> idList, @Param("operate") Integer operate);

    @Override
    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisOperateProvider.class, method = "operateDynamicAll")
    Integer operateDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("operate") Integer operate);

    @Override
    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisOperateProvider.class, method = "operateAllByWhere")
    Integer operateAllByWhere(@Param("whereSql") String whereSql, @Param("operate") Integer operate);

    @Override
    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisOperateProvider.class, method = "operateDynamicAllByWhere")
    Integer operateDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql, @Param("operate") Integer operate);
}
