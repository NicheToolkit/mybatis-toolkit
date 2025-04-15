package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.RemoveLinkMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

public interface MybatisRemoveLinkMapper<E extends RestId<I>, L, I> extends MybatisMapper<E>, MybatisRemoveMapper<E, I>, RemoveLinkMapper<L, I> {

    @Override
    default Integer removeByLinkId(@Param("linkId") L linkId, @Param("logic") Object logic) {
        return removeByLinkId(linkId, null, logic);
    }

    @Override
    default Integer removeDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") L linkId, @Param("logic") Object logic) {
        return removeDynamicByLinkId(tablename, linkId, null, logic);
    }

    @Override
    default Integer removeAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList, @Param("logic") Object logic) {
        return removeAllByLinkIds(linkIdList, null, logic);
    }

    @Override
    default Integer removeDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection<L> linkIdList, @Param("logic") Object logic) {
        return removeDynamicAllByLinkIds(tablename, linkIdList, null, logic);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer removeByLinkId(@Param("linkId") L linkId, @Param("linkName") String linkName, @Param("logic") Object logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer removeDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") L linkId, @Param("linkName") String linkName, @Param("logic") Object logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer removeAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName, @Param("logic") Object logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer removeDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName, @Param("logic") Object logic);
}
