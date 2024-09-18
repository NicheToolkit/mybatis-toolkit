package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.provider.MybatisDeleteLinkProvider;
import io.github.nichetoolkit.rice.mapper.DeleteLinkMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

/**
 * <code>MybatisDeleteLinkMapper</code>
 * <p>The type mybatis delete link mapper interface.</p>
 * @param <I> {@link java.lang.Object} <p>the parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.mapper.DeleteLinkMapper
 * @since Jdk1.8
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
