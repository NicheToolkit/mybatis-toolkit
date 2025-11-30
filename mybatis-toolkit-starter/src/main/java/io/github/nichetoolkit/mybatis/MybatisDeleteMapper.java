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
 * @since Jdk17
 */
public interface MybatisDeleteMapper<E extends RestId<I>, I> extends MybatisMapper<E>, DeleteMapper<I> {

    @Override
    default Integer deleteById(@Param("id") I id) {
        return deleteDynamicById(null, id);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(MybatisSqlProviderResolver.class)
    Integer deleteDynamicById(@Param("tableName") String tableName, @Param("id") I id);

    @Override
    default Integer deleteAll(@Param("idList") Collection<I> idList) {
        return deleteDynamicAll(null, idList);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(MybatisSqlProviderResolver.class)
    Integer deleteDynamicAll(@Param("tableName") String tableName, @Param("idList") Collection<I> idList);

    @Override
    default Integer deleteAllByWhere(@Param("whereSql") String whereSql) {
        return deleteDynamicAllByWhere(null, whereSql);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(MybatisSqlProviderResolver.class)
    Integer deleteDynamicAllByWhere(@Param("tableName") String tableName, @Param("whereSql") String whereSql);
}
