package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.AlertLinkMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

/**
 * <code>MybatisAlertLinkMapper</code>
 * <p>The mybatis alert link mapper interface.</p>
 * @param <E>  {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <L>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @param <S>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @see  io.github.nichetoolkit.rice.RestId
 * @see  io.github.nichetoolkit.mybatis.MybatisMapper
 * @see  io.github.nichetoolkit.mybatis.MybatisAlertMapper
 * @see  io.github.nichetoolkit.rice.mapper.AlertLinkMapper
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface MybatisAlertLinkMapper<E extends RestId<I>, L, S, I> extends MybatisMapper<E>, MybatisAlertMapper<E, S, I>, AlertLinkMapper<L, S, I> {

    @Override
    default Integer alertByLinkId(@Param("linkId") L linkId, @Param("status") S status) {
        return alertByLinkId(linkId, null, status);
    }

    @Override
    default Integer alertDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") L linkId, @Param("status") S status) {
        return alertDynamicByLinkId(tablename, linkId, null, status);
    }

    @Override
    default Integer alertAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList, @Param("status") S status) {
        return alertAllByLinkIds(linkIdList, null, status);
    }

    @Override
    default Integer alertDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection<L> linkIdList, @Param("status") S status) {
        return alertDynamicAllByLinkIds(tablename, linkIdList, null, status);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertByLinkId(L linkId, String linkName, S status);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertDynamicByLinkId(String tablename, L linkId, String linkName, S status);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertAllByLinkIds(Collection<L> linkIdList, String linkName, S status);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertDynamicAllByLinkIds(String tablename, Collection<L> linkIdList, String linkName, S status);

}
