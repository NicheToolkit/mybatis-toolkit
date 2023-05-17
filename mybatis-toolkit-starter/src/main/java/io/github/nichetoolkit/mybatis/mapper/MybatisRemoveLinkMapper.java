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
 * <p>MybatisRemoveLinkMapper</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
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
