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
    default Integer alertByLinkId(@Param("linkId") L linkId, @Param("state") S state) {
        return alertByLinkId(linkId, null, state, null);
    }

    @Override
    default Integer alertDynamicByLinkId(@Param("tableName") String tableName, @Param("linkId") L linkId, @Param("state") S state) {
        return alertDynamicByLinkId(tableName, linkId, null, state, null);
    }

    @Override
    default Integer alertAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList, @Param("state") S state) {
        return alertAllByLinkIds(linkIdList, null, state, null);
    }

    @Override
    default Integer alertDynamicAllByLinkIds(@Param("tableName") String tableName, @Param("linkIdList") Collection<L> linkIdList, @Param("state") S state) {
        return alertDynamicAllByLinkIds(tableName, linkIdList, null, state, null);
    }

    @Override
    default Integer alertByLinkId(@Param("linkId") L linkId, @Param("linkName") String linkName, @Param("state") S state) {
        return alertDynamicByLinkId(null, linkId, linkName, state, null);
    }

    @Override
    default Integer alertDynamicByLinkId(@Param("tableName") String tableName, @Param("linkId") L linkId, @Param("linkName") String linkName, @Param("state") S state) {
        return alertDynamicByLinkId(tableName, linkId, linkName, state, null);
    }

    @Override
    default Integer alertAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName, @Param("state") S state) {
        return alertDynamicAllByLinkIds(null, linkIdList, linkName, state);
    }

    @Override
    default Integer alertDynamicAllByLinkIds(@Param("tableName") String tableName, @Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName, @Param("state") S state) {
        return alertDynamicAllByLinkIds(tableName, linkIdList, linkName, state, null);
    }

    @Override
    default Integer alertByLinkId(@Param("linkId") L linkId, @Param("state") S state, @Param("stateName") String stateName) {
        return alertByLinkId(linkId, null, state, stateName);
    }

    @Override
    default Integer alertDynamicByLinkId(@Param("tableName") String tableName, @Param("linkId") L linkId, @Param("state") S state, @Param("stateName") String stateName) {
        return alertDynamicByLinkId(tableName, linkId, null, state, stateName);
    }

    @Override
    default Integer alertAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList, @Param("state") S state, @Param("stateName") String stateName) {
        return alertAllByLinkIds(linkIdList, null, state, stateName);
    }

    @Override
    default Integer alertDynamicAllByLinkIds(@Param("tableName") String tableName, @Param("linkIdList") Collection<L> linkIdList, @Param("state") S state, @Param("stateName") String stateName) {
        return alertDynamicAllByLinkIds(tableName, linkIdList, null, state, stateName);
    }

    @Override
    default Integer alertByLinkId(@Param("linkId") L linkId, @Param("linkName") String linkName, @Param("state") S state, @Param("stateName") String stateName) {
        return alertDynamicByLinkId(null, linkId, linkName, state, stateName);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertDynamicByLinkId(@Param("tableName") String tableName, @Param("linkId") L linkId, @Param("linkName") String linkName, @Param("state") S state, @Param("stateName") String stateName);

    @Override
    default Integer alertAllByLinkIds(@Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName, @Param("state") S state, @Param("stateName") String stateName) {
        return alertDynamicAllByLinkIds(null, linkIdList, linkName, state, stateName);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertDynamicAllByLinkIds(@Param("tableName") String tableName, @Param("linkIdList") Collection<L> linkIdList, @Param("linkName") String linkName, @Param("state") S state, @Param("stateName") String stateName);

}
