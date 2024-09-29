package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisCaching;
import io.github.nichetoolkit.mybatis.MybatisEntityMapper;
import io.github.nichetoolkit.mybatis.provider.MybatisSaveProvider;
import io.github.nichetoolkit.rice.IdEntity;
import io.github.nichetoolkit.rice.mapper.SaveMapper;
import io.github.nichetoolkit.rice.mapper.SuperMapper;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

public interface MybatisSaveMapper<E extends IdEntity<I>, I> extends MybatisEntityMapper<E>, SaveMapper<E,I> {

    @Override
    @Lang(MybatisCaching.class)
    @InsertProvider(type = MybatisSaveProvider.class, method = "save")
    Integer save(@Param("entity") E entity);

    @Override
    @Lang(MybatisCaching.class)
    @InsertProvider(type = MybatisSaveProvider.class, method = "saveDynamic")
    Integer saveDynamic(@Param("tablename") String tablename, @Param("entity") E entity);

    @Override
    @Lang(MybatisCaching.class)
    @InsertProvider(type = MybatisSaveProvider.class, method = "saveAll")
    Integer saveAll(@Param("entityList") Collection<E> entityList);

    @Override
    @Lang(MybatisCaching.class)
    @InsertProvider(type = MybatisSaveProvider.class, method = "saveDynamicAll")
    Integer saveDynamicAll(@Param("tablename") String tablename, @Param("entityList") Collection<E> entityList);
}
