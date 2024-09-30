package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.mybatis.provider.MybatisRemoveProvider;
import io.github.nichetoolkit.rice.mapper.RemoveMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

public interface MybatisRemoveMapper<I> extends RemoveMapper<I> {

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(type = MybatisRemoveProvider.class, method = "removeById")
    Integer removeById(@Param("id") I id, @Param("logic") String logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(type = MybatisRemoveProvider.class, method = "removeDynamicById")
    Integer removeDynamicById(@Param("tablename") String tablename, @Param("id") I id, @Param("logic") String logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(type = MybatisRemoveProvider.class, method = "removeAll")
    Integer removeAll(@Param("idList") Collection<I> idList, @Param("logic") String logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(type = MybatisRemoveProvider.class, method = "removeDynamicAll")
    Integer removeDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("logic") String logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(type = MybatisRemoveProvider.class, method = "removeAllByWhere")
    Integer removeAllByWhere(@Param("whereSql") String whereSql, @Param("logic") String logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(type = MybatisRemoveProvider.class, method = "removeDynamicAllByWhere")
    Integer removeDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql, @Param("logic") String logic);
}
