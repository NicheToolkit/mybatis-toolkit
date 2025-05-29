package io.github.nichetoolkit.mybatis.natives;

import io.github.nichetoolkit.mybatis.MybatisMapper;
import io.github.nichetoolkit.mybatis.MybatisSqlProviderResolver;
import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.mybatis.load.RestLoad;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.natives.FickleLoadMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Collection;
import java.util.List;

public interface MybatisFickleLoadMapper<E extends RestId<I>, L, I> extends MybatisMapper<E>, FickleLoadMapper<E, I> {

    @Override
    default E findByIdFickleLoad(@Param("id") I id, @Param("fickleParams") RestFickle<?>[] fickleParams, @Param("loadParams") RestLoad... loadParams) {
        return findDynamicByIdFickleLoad(null, id, fickleParams, loadParams);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    E findDynamicByIdFickleLoad(@Param("tablename") String tablename, @Param("id") I id, @Param("fickleParams") RestFickle<?>[] fickleParams, @Param("loadParams") RestLoad... loadParams);

    @Override
    default List<E> findAllFickleLoad(@Param("idList") Collection<I> idList, @Param("fickleParams") RestFickle<?>[] fickleParams, @Param("loadParams") RestLoad... loadParams) {
        return findDynamicAllFickleLoad(null, idList, fickleParams, loadParams);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicAllFickleLoad(@Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("fickleParams") RestFickle<?>[] fickleParams, @Param("loadParams") RestLoad... loadParams);

}
