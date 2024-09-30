package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.mybatis.provider.MybatisAlertProvider;
import io.github.nichetoolkit.rice.mapper.AlertMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

public interface MybatisAlertMapper<I> extends AlertMapper<I> {

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(type = MybatisAlertProvider.class, method = "alertById")
    Integer alertById(@Param("id") I id, @Param("key") Integer key);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(type = MybatisAlertProvider.class, method = "alertDynamicById")
    Integer alertDynamicById(@Param("tablename") String tablename, @Param("id") I id, @Param("key") Integer key);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(type = MybatisAlertProvider.class, method = "alertAll")
    Integer alertAll(@Param("idList") Collection<I> idList, @Param("key") Integer key);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(type = MybatisAlertProvider.class, method = "alertDynamicAll")
    Integer alertDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("key") Integer key);

}
