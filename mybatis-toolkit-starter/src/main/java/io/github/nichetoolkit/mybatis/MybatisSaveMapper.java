package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.SaveMapper;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * <code>MybatisSaveMapper</code>
 * <p>The mybatis save mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestId
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @see io.github.nichetoolkit.rice.mapper.SaveMapper
 * @since Jdk1.8
 */
public interface MybatisSaveMapper<E extends RestId<I>, I> extends MybatisMapper<E>, SaveMapper<E, I> {

    @Override
    default Integer save(@Param("entity") E entity) {
        return saveDynamic(null, entity);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @InsertProvider(MybatisSqlProviderResolver.class)
    Integer saveDynamic(@Param("tablename") String tablename, @Param("entity") E entity);

    @Override
    default Integer saveAll(@Param("entityList") Collection<E> entityList) {
        return saveDynamicAll(null, entityList);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @InsertProvider(MybatisSqlProviderResolver.class)
    Integer saveDynamicAll(@Param("tablename") String tablename, @Param("entityList") Collection<E> entityList);
}
