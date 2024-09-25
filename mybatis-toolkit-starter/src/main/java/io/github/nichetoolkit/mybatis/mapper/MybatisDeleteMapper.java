package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisCaching;
import io.github.nichetoolkit.mybatis.MybatisEntityMapper;
import io.github.nichetoolkit.mybatis.provider.MybatisDeleteProvider;
import io.github.nichetoolkit.rice.IdEntity;
import io.github.nichetoolkit.rice.mapper.DeleteMapper;
import org.apache.ibatis.annotations.*;

import java.util.Collection;

/**
 * <code>MybatisDeleteMapper</code>
 * <p>The type mybatis delete mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.IdEntity} <p>the generic parameter is <code>IdEntity</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>the parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.IdEntity
 * @see io.github.nichetoolkit.mybatis.MybatisEntityMapper
 * @see io.github.nichetoolkit.rice.mapper.DeleteMapper
 * @since Jdk1.8
 */
public interface MybatisDeleteMapper<E extends IdEntity<I>, I> extends MybatisEntityMapper<E>, DeleteMapper<I> {

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisDeleteProvider.class, method = "deleteById")
    Integer deleteById(@Param("id") I id);

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisDeleteProvider.class, method = "deleteDynamicById")
    Integer deleteDynamicById(@Param("tablename") String tablename, @Param("id") I id);

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisDeleteProvider.class, method = "deleteAll")
    Integer deleteAll(@Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisDeleteProvider.class, method = "deleteDynamicAll")
    Integer deleteDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisDeleteProvider.class, method = "deleteAllByWhere")
    Integer deleteAllByWhere(@Param("whereSql") String whereSql);

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisDeleteProvider.class, method = "deleteDynamicAllByWhere")
    Integer deleteDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql);
}
