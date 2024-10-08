package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.mybatis.provider.PostgresInfoProvider;
import io.github.nichetoolkit.rice.InfoEntity;
import io.github.nichetoolkit.rice.mapper.InfoMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface MybatisInfoMapper<E extends InfoEntity<I>, I> extends MybatisSuperMapper<E, I>, InfoMapper<E, I> {

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(type = PostgresInfoProvider.class, method = "findByName")
    List<E> findByName(@Param("name") String name, @Param("logic") String logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(type = PostgresInfoProvider.class, method = "findDynamicByName")
    List<E> findDynamicByName(@Param("tablename") String tablename, @Param("name") String name, @Param("logic") String logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(type = PostgresInfoProvider.class, method = "findByNameAndNotId")
    List<E> findByNameAndNotId(@Param("name") String name, @Param("id") I id, @Param("logic") String logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(type = PostgresInfoProvider.class, method = "findDynamicByNameAndNotId")
    List<E> findDynamicByNameAndNotId(@Param("tablename") String tablename, @Param("name") String name, @Param("id") I id, @Param("logic") String logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(type = PostgresInfoProvider.class, method = "findByEntity")
    List<E> findByEntity(@Param("entity") E entity, @Param("logic") String logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(type = PostgresInfoProvider.class, method = "findDynamicByEntity")
    List<E> findDynamicByEntity(@Param("tablename") String tablename, @Param("entity") E entity, @Param("logic") String logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(type = PostgresInfoProvider.class, method = "findByEntityAndNotId")
    List<E> findByEntityAndNotId(@Param("entity") E entity, @Param("id") I id, @Param("logic") String logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(type = PostgresInfoProvider.class, method = "findDynamicByEntityAndNotId")
    List<E> findDynamicByEntityAndNotId(@Param("tablename") String tablename, @Param("entity") E entity, @Param("id") I id, @Param("logic") String logic);
}
