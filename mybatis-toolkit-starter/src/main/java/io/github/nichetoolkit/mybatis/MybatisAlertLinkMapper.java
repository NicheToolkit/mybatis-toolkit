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
 * @param <E> {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <L> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @param <S> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestId
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @see io.github.nichetoolkit.mybatis.MybatisAlertMapper
 * @see io.github.nichetoolkit.rice.mapper.AlertLinkMapper
 * @since Jdk1.8
 */
public interface MybatisAlertLinkMapper<E extends RestId<I>, L, S, I> extends MybatisMapper<E>, MybatisAlertMapper<E, S, I>, AlertLinkMapper<L, S, I> {

    @Override
    default Integer alertByLinkId(@Param("linkId") L linkId, @Param("status") S status) {
        return alertByLinkId(linkId, null, status, null);
    }

    @Override
    default Integer alertDynamicByLinkId(@Param("tableName") String tableName, @Param("linkId") L linkId, @Param("status") S status) {
        return alertDynamicByLinkId(tableName, linkId, null, status, null);
    }

    @Override
    default Integer alertAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList, @Param("status") S status) {
        return alertAllByLinkIds(linkIdList, null, status, null);
    }

    @Override
    default Integer alertDynamicAllByLinkIds(@Param("tableName") String tableName, @Param("linkIdList") Collection<L> linkIdList, @Param("status") S status) {
        return alertDynamicAllByLinkIds(tableName, linkIdList, null, status, null);
    }

    @Override
    default Integer alertByLinkId(@Param("linkId") L linkId, @Param("linkName") String linkName, @Param("status") S status) {
        return alertDynamicByLinkId(null, linkId, linkName, status, null);
    }

    @Override
    default Integer alertDynamicByLinkId(@Param("tableName") String tableName, @Param("linkId") L linkId, @Param("linkName") String linkName, @Param("status") S status) {
        return alertDynamicByLinkId(tableName, linkId, linkName, status, null);
    }

    @Override
    default Integer alertAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName, @Param("status") S status) {
        return alertDynamicAllByLinkIds(null, linkIdList, linkName, status);
    }

    @Override
    default Integer alertDynamicAllByLinkIds(@Param("tableName") String tableName, @Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName, @Param("status") S status) {
        return alertDynamicAllByLinkIds(tableName, linkIdList, linkName, status, null);
    }

    @Override
    default Integer alertByLinkId(@Param("linkId") L linkId, @Param("status") S status, @Param("statusName") String statusName) {
        return alertByLinkId(linkId, null, status, statusName);
    }

    @Override
    default Integer alertDynamicByLinkId(@Param("tableName") String tableName, @Param("linkId") L linkId, @Param("status") S status, @Param("statusName") String statusName) {
        return alertDynamicByLinkId(tableName, linkId, null, status, statusName);
    }

    @Override
    default Integer alertAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList, @Param("status") S status, @Param("statusName") String statusName) {
        return alertAllByLinkIds(linkIdList, null, status, statusName);
    }

    @Override
    default Integer alertDynamicAllByLinkIds(@Param("tableName") String tableName, @Param("linkIdList") Collection<L> linkIdList, @Param("status") S status, @Param("statusName") String statusName) {
        return alertDynamicAllByLinkIds(tableName, linkIdList, null, status, statusName);
    }

    @Override
    default Integer alertByLinkId(@Param("linkId") L linkId, @Param("linkName") String linkName, @Param("status") S status, @Param("statusName") String statusName) {
        return alertDynamicByLinkId(null, linkId, linkName, status, statusName);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertDynamicByLinkId(@Param("tableName") String tableName, @Param("linkId") L linkId, @Param("linkName") String linkName, @Param("status") S status, @Param("statusName") String statusName);

    @Override
    default Integer alertAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName, @Param("status") S status, @Param("statusName") String statusName) {
        return alertDynamicAllByLinkIds(null, linkIdList, linkName, status, statusName);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertDynamicAllByLinkIds(@Param("tableName") String tableName, @Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName, @Param("status") S status, @Param("statusName") String statusName);

}
