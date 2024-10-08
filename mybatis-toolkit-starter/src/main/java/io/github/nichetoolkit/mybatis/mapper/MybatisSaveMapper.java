//package io.github.nichetoolkit.mybatis.mapper;
//
//import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
//import io.github.nichetoolkit.mybatis.MybatisMapper;
//import io.github.nichetoolkit.mybatis.provider.MybatisSaveProvider;
//import io.github.nichetoolkit.rice.IdEntity;
//import io.github.nichetoolkit.rice.mapper.SaveMapper;
//import org.apache.ibatis.annotations.InsertProvider;
//import org.apache.ibatis.annotations.Lang;
//import org.apache.ibatis.annotations.Param;
//
//import java.util.Collection;
//
//public interface MybatisSaveMapper<E extends IdEntity<I>, I> extends MybatisMapper<E>, SaveMapper<E,I> {
//
//    @Override
//    @Lang(MybatisSqlSourceCaching.class)
//    @InsertProvider(type = MybatisSaveProvider.class, method = "save")
//    Integer save(@Param("entity") E entity);
//
//    @Override
//    @Lang(MybatisSqlSourceCaching.class)
//    @InsertProvider(type = MybatisSaveProvider.class, method = "saveDynamic")
//    Integer saveDynamic(@Param("tablename") String tablename, @Param("entity") E entity);
//
//    @Override
//    @Lang(MybatisSqlSourceCaching.class)
//    @InsertProvider(type = MybatisSaveProvider.class, method = "saveAll")
//    Integer saveAll(@Param("entityList") Collection<E> entityList);
//
//    @Override
//    @Lang(MybatisSqlSourceCaching.class)
//    @InsertProvider(type = MybatisSaveProvider.class, method = "saveDynamicAll")
//    Integer saveDynamicAll(@Param("tablename") String tablename, @Param("entityList") Collection<E> entityList);
//}
