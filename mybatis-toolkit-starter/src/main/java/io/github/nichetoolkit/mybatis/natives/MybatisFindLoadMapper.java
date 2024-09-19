package io.github.nichetoolkit.mybatis.natives;

import io.github.nichetoolkit.mybatis.MybatisCaching;
import io.github.nichetoolkit.mybatis.provider.natives.MybatisFindLoadProvider;
import io.github.nichetoolkit.rice.IdEntity;
import io.github.nichetoolkit.rice.mapper.natives.FindLoadMapper;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface MybatisFindLoadMapper<E extends IdEntity<I>, I> extends FindLoadMapper<E,I> {

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisFindLoadProvider.class, method = "findByIdLoad")
    E findByIdLoad(@Param("id") I id, @Param("loadParams") Boolean... loadParams);

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisFindLoadProvider.class, method = "findDynamicByIdLoad")
    E findDynamicByIdLoad(@Param("tablename") String tablename, @Param("id") I id, @Param("loadParams") Boolean... loadParams);

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisFindLoadProvider.class, method = "findAllLoad")
    List<E> findAllLoad(@Param("idList") Collection<I> idList, @Param("loadParams") Boolean... loadParams);

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisFindLoadProvider.class, method = "findDynamicAllLoad")
    List<E> findDynamicAllLoad(@Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("loadParams") Boolean... loadParams);

}
