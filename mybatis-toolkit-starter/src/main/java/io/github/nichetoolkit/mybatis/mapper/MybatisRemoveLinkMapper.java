package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisSqlProviderResolver;
import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.rice.mapper.RemoveLinkMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

public interface MybatisRemoveLinkMapper<L,I> extends RemoveLinkMapper<L,I>, MybatisRemoveMapper<I> {

    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer removeByLinkId(@Param("linkId") L linkId, @Param("logic") String logic);

    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer removeDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") L linkId, @Param("logic") String logic);

    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer removeAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList, @Param("logic") String logic);

    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer removeDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection<L> linkIdList, @Param("logic") String logic);
}
