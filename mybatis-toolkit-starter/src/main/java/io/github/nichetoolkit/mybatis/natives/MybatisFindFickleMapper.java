package io.github.nichetoolkit.mybatis.natives;

import io.github.nichetoolkit.mybatis.MybatisMapper;
import io.github.nichetoolkit.mybatis.MybatisSqlProviderResolver;
import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.natives.FindFickleMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Collection;
import java.util.List;

public interface MybatisFindFickleMapper<E extends RestId<I>, I> extends MybatisMapper<E>, FindFickleMapper<E, I> {

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    E findByIdFickle(@Param("id") I id, @Param("fickleParams") String... fickleParams);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    E findDynamicByIdFickle(@Param("tablename") String tablename, @Param("id") I id, @Param("fickleParams") String... fickleParams);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findAllFickle(@Param("idList") Collection<I> idList, @Param("fickleParams") String... fickleParams);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicAllFickle(@Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("fickleParams") String... fickleParams);

}
