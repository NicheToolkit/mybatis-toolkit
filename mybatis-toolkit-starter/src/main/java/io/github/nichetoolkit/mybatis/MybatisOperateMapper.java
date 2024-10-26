package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.OperateMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

/**
 * <code>MybatisOperateMapper</code>
 * <p>The mybatis operate mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestId
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @see io.github.nichetoolkit.rice.mapper.OperateMapper
 * @since Jdk1.8
 */
public interface MybatisOperateMapper<E extends RestId<I>, I> extends MybatisMapper<E>, OperateMapper<I> {

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer operateById(@Param("id") I id, @Param("operate") Integer operate);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer operateDynamicById(@Param("tablename") String tablename, @Param("id") I id, @Param("operate") Integer operate);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer operateAll(@Param("idList") Collection<I> idList, @Param("operate") Integer operate);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer operateDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("operate") Integer operate);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer operateAllByWhere(@Param("whereSql") String whereSql, @Param("operate") Integer operate);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer operateDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql, @Param("operate") Integer operate);
}
