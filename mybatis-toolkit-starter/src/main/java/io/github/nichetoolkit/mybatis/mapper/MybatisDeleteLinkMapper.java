package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisCaching;
import io.github.nichetoolkit.mybatis.provider.MybatisDeleteLinkProvider;
import io.github.nichetoolkit.rice.mapper.DeleteLinkMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

/**
 * <p>MybatisDeleteLinkMapper</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface MybatisDeleteLinkMapper<I> extends DeleteLinkMapper<I> {

    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisDeleteLinkProvider.class, method = "deleteByLinkId")
    Integer deleteByLinkId(@Param("linkId") I linkId);

    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisDeleteLinkProvider.class, method = "deleteDynamicByLinkId")
    Integer deleteDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") I linkId);

    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisDeleteLinkProvider.class, method = "deleteAllByLinkIds")
    Integer deleteAllByLinkIds(@Param("linkIdList") Collection<I> linkIdList);

    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisDeleteLinkProvider.class, method = "deleteDynamicAllByLinkIds")
    Integer deleteDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection<I> linkIdList);
}
