package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisCaching;
import io.github.nichetoolkit.mybatis.provider.MybatisRemoveLinkProvider;
import io.github.nichetoolkit.mybatis.provider.MybatisRemoveProvider;
import io.github.nichetoolkit.rice.mapper.RemoveLinkMapper;
import io.github.nichetoolkit.rice.mapper.RemoveMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

/**
 * <code>MybatisRemoveLinkMapper</code>
 * <p>The type mybatis remove link mapper interface.</p>
 * @param <I> {@link java.lang.Object} <p>the parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.mapper.RemoveLinkMapper
 * @see io.github.nichetoolkit.mybatis.mapper.MybatisRemoveMapper
 * @since Jdk1.8
 */
public interface MybatisRemoveLinkMapper<I> extends RemoveLinkMapper<I>, MybatisRemoveMapper<I> {

    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisRemoveLinkProvider.class, method = "removeByLinkId")
    Integer removeByLinkId(@Param("linkId") I linkId, @Param("sign") String sign);

    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisRemoveLinkProvider.class, method = "removeDynamicByLinkId")
    Integer removeDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") I linkId, @Param("sign") String sign);

    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisRemoveLinkProvider.class, method = "removeAllByLinkIds")
    Integer removeAllByLinkIds(@Param("linkIdList") Collection<I> linkIdList, @Param("sign") String sign);

    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisRemoveLinkProvider.class, method = "removeDynamicAllByLinkIds")
    Integer removeDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection<I> linkIdList, @Param("sign") String sign);
}
