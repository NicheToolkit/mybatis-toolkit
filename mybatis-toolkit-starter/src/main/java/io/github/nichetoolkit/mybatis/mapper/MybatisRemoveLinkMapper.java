package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisCaching;
import io.github.nichetoolkit.mybatis.provider.MybatisRemoveLinkProvider;
import io.github.nichetoolkit.rice.mapper.RemoveLinkMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

public interface MybatisRemoveLinkMapper<I> extends RemoveLinkMapper<I>, MybatisRemoveMapper<I> {

    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisRemoveLinkProvider.class, method = "removeByLinkId")
    Integer removeByLinkId(@Param("linkId") I linkId, @Param("logic") String logic);

    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisRemoveLinkProvider.class, method = "removeDynamicByLinkId")
    Integer removeDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") I linkId, @Param("logic") String logic);

    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisRemoveLinkProvider.class, method = "removeAllByLinkIds")
    Integer removeAllByLinkIds(@Param("linkIdList") Collection<I> linkIdList, @Param("logic") String logic);

    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisRemoveLinkProvider.class, method = "removeDynamicAllByLinkIds")
    Integer removeDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection<I> linkIdList, @Param("logic") String logic);
}
