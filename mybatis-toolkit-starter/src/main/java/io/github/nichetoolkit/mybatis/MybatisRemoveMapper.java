package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.RemoveMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

/**
 * <code>MybatisRemoveMapper</code>
 * <p>The mybatis remove mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestId
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @see io.github.nichetoolkit.rice.mapper.RemoveMapper
 * @since Jdk17
 */
public interface MybatisRemoveMapper<E extends RestId<I>, I> extends MybatisMapper<E>, RemoveMapper<I> {

    @Override
    default Integer removeById(@Param("id") I id, @Param("logic") Object logic) {
        return removeDynamicById(null, id, logic);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer removeDynamicById(@Param("tableName") String tableName, @Param("id") I id, @Param("logic") Object logic);

    @Override
    default Integer removeAll(@Param("idList") Collection<I> idList, @Param("logic") Object logic) {
        return removeDynamicAll(null, idList, logic);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer removeDynamicAll(@Param("tableName") String tableName, @Param("idList") Collection<I> idList, @Param("logic") Object logic);

    @Override
    default Integer removeAllByWhere(@Param("whereSql") String whereSql, @Param("logic") Object logic) {
        return removeDynamicAllByWhere(null, whereSql, logic);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer removeDynamicAllByWhere(@Param("tableName") String tableName, @Param("whereSql") String whereSql, @Param("logic") Object logic);
}
