package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.mybatis.provider.MybatisDeleteLinkProvider;
import io.github.nichetoolkit.rice.mapper.DeleteLinkMapper;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

public interface MybatisDeleteLinkMapper<I> extends DeleteLinkMapper<I> {

    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(type = MybatisDeleteLinkProvider.class, method = "deleteByLinkId")
    Integer deleteByLinkId(@Param("linkId") I linkId);

    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(type = MybatisDeleteLinkProvider.class, method = "deleteDynamicByLinkId")
    Integer deleteDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") I linkId);

    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(type = MybatisDeleteLinkProvider.class, method = "deleteAllByLinkIds")
    Integer deleteAllByLinkIds(@Param("linkIdList") Collection<I> linkIdList);

    @Lang(MybatisSqlSourceCaching.class)
    @DeleteProvider(type = MybatisDeleteLinkProvider.class, method = "deleteDynamicAllByLinkIds")
    Integer deleteDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection<I> linkIdList);
}
