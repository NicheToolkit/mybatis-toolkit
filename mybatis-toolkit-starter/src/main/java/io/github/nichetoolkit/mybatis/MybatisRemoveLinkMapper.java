package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.RemoveLinkMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

/**
 * <code>MybatisRemoveLinkMapper</code>
 * <p>The mybatis remove link mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <L> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestId
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @see io.github.nichetoolkit.rice.mapper.RemoveLinkMapper
 * @see io.github.nichetoolkit.mybatis.MybatisRemoveMapper
 * @since Jdk1.8
 */
public interface MybatisRemoveLinkMapper<E extends RestId<I>,L,I> extends MybatisMapper<E>, RemoveLinkMapper<L,I>, MybatisRemoveMapper<E,I> {

    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer removeByLinkId(@Param("linkId") L linkId, @Param("logic") Object logic);

    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer removeDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") L linkId, @Param("logic") Object logic);

    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer removeAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList, @Param("logic") Object logic);

    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer removeDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection<L> linkIdList, @Param("logic") Object logic);
}
