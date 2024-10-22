package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisSqlProviderResolver;
import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.rice.mapper.AlertMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

public interface MybatisAlertMapper<I> extends AlertMapper<I> {

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertById(@Param("id") I id, @Param("key") Integer key);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertDynamicById(@Param("tablename") String tablename, @Param("id") I id, @Param("key") Integer key);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertAll(@Param("idList") Collection<I> idList, @Param("key") Integer key);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("key") Integer key);

}
