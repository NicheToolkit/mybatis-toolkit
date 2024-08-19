package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisCaching;
import io.github.nichetoolkit.mybatis.provider.MybatisAlertProvider;
import io.github.nichetoolkit.rice.mapper.AlertMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Collection;

/**
 * <p>MybatisAlertMapper</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface MybatisAlertMapper<I> extends AlertMapper<I> {

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisAlertProvider.class, method = "alertById")
    Integer alertById(@Param("id") I id, @Param("key") Integer key);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisAlertProvider.class, method = "alertDynamicById")
    Integer alertDynamicById(@Param("tablename") String tablename, @Param("id") I id, @Param("key") Integer key);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisAlertProvider.class, method = "alertAll")
    Integer alertAll(@Param("idList") Collection<I> idList, @Param("key") Integer key);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisAlertProvider.class, method = "alertDynamicAll")
    Integer alertDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("key") Integer key);

}