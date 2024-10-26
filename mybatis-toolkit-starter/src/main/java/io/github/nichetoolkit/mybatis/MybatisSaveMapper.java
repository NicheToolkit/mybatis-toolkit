package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.SaveMapper;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

public interface MybatisSaveMapper<E extends RestId<I>, I> extends MybatisMapper<E>, SaveMapper<E,I> {

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @InsertProvider(MybatisSqlProviderResolver.class)
    Integer save(@Param("entity") E entity);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @InsertProvider(MybatisSqlProviderResolver.class)
    Integer saveDynamic(@Param("tablename") String tablename, @Param("entity") E entity);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @InsertProvider(MybatisSqlProviderResolver.class)
    Integer saveAll(@Param("entityList") Collection<E> entityList);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @InsertProvider(MybatisSqlProviderResolver.class)
    Integer saveDynamicAll(@Param("tablename") String tablename, @Param("entityList") Collection<E> entityList);
}
