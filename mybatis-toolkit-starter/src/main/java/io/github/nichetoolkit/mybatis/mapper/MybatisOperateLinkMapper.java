package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisCaching;
import io.github.nichetoolkit.mybatis.provider.MybatisOperateLinkProvider;
import io.github.nichetoolkit.rice.mapper.OperateLinkMapper;
import io.github.nichetoolkit.rice.mapper.OperateMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

/**
 * <p>MybatisOperateLinkMapper</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface MybatisOperateLinkMapper<I> extends OperateLinkMapper<I>, MybatisOperateMapper<I> {

    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisOperateLinkProvider.class, method = "operateByLinkId")
    Integer operateByLinkId(@Param("linkId") I linkId, @Param("operate") Integer operate);

    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisOperateLinkProvider.class, method = "operateDynamicByLinkId")
    Integer operateDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") I linkId, @Param("operate") Integer operate);

    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisOperateLinkProvider.class, method = "operateAllByLinkIds")
    Integer operateAllByLinkIds(@Param("linkIdList") Collection<I> linkIdList, @Param("operate") Integer operate);

    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisOperateLinkProvider.class, method = "operateDynamicAllByLinkIds")
    Integer operateDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection<I> linkIdList, @Param("operate") Integer operate);
}
