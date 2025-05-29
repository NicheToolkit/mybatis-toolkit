package io.github.nichetoolkit.mybatis.natives;

import io.github.nichetoolkit.mybatis.MybatisMapper;
import io.github.nichetoolkit.mybatis.MybatisSqlProviderResolver;
import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.mybatis.load.RestLoad;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.natives.LinkLoadMapper;
import io.github.nichetoolkit.rice.mapper.natives.NameLoadMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Collection;
import java.util.List;

public interface MybatisNameLoadMapper<E extends RestId<I>, I> extends MybatisMapper<E>, NameLoadMapper<E, I> {

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findByNameLoad(@Param("name") String name, @Param("logic") Object logic, @Param("loadParams") RestLoad... loadParams);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicByNameLoad(@Param("tablename") String tablename, @Param("name") String name, @Param("logic") Object logic, @Param("loadParams") RestLoad... loadParams);

}
