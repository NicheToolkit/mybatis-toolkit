package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.DeleteMapper;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * <code>MybatisDeleteMapper</code>
 * <p>The mybatis delete mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestId
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @see io.github.nichetoolkit.rice.mapper.DeleteMapper
 * @since Jdk1.8
 */
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
