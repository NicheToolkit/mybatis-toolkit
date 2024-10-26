package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.AlertMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

/**
 * <code>MybatisAlertMapper</code>
 * <p>The mybatis alert mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <S> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestId
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @see io.github.nichetoolkit.rice.mapper.AlertMapper
 * @since Jdk1.8
 */
public interface MybatisAlertMapper<E extends RestId<I>, S, I> extends MybatisMapper<E>, AlertMapper<S, I> {

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertById(@Param("id") I id, @Param("status") S status);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertDynamicById(@Param("tablename") String tablename, @Param("id") I id, @Param("status") S status);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertAll(@Param("idList") Collection<I> idList, @Param("status") S status);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("status") S status);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertAllByWhere(@Param("whereSql") String whereSql, @Param("status") S status);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql, @Param("status") S status);

}
