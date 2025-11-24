package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.AlertMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

public interface MybatisAlertMapper<E extends RestId<I>, S, I> extends MybatisMapper<E>, AlertMapper<S, I> {

    @Override
    default Integer alertById(@Param("id") I id, @Param("state") S state) {
        return alertDynamicById(null, id, state);
    }

    @Override
    default Integer alertDynamicById(@Param("tableName") String tableName, @Param("id") I id, @Param("state") S state) {
        return alertDynamicById(tableName, id, state, null);
    }

    @Override
    default Integer alertAll(@Param("idList") Collection<I> idList, @Param("state") S state) {
        return alertDynamicAll(null, idList, state);
    }

    @Override
    default Integer alertDynamicAll(@Param("tableName") String tableName, @Param("idList") Collection<I> idList, @Param("state") S state) {
        return alertDynamicAll(tableName, idList, state, null);
    }

    @Override
    default Integer alertAllByWhere(@Param("whereSql") String whereSql, @Param("state") S state) {
        return alertDynamicAllByWhere(null, whereSql, state);
    }

    @Override
    default Integer alertDynamicAllByWhere(@Param("tableName") String tableName, @Param("whereSql") String whereSql, @Param("state") S state) {
        return alertDynamicAllByWhere(tableName, whereSql, state, null);
    }

    @Override
    default Integer alertById(@Param("id") I id, @Param("state") S state, @Param("stateName") String stateName) {
        return alertDynamicById(null, id, state, stateName);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertDynamicById(@Param("tableName") String tableName, @Param("id") I id, @Param("state") S state, @Param("stateName") String stateName);


    @Override
    default Integer alertAll(@Param("idList") Collection<I> idList, @Param("state") S state, @Param("stateName") String stateName) {
        return alertDynamicAll(null, idList, state, stateName);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertDynamicAll(@Param("tableName") String tableName, @Param("idList") Collection<I> idList, @Param("state") S state, @Param("stateName") String stateName);

    @Override
    default Integer alertAllByWhere(@Param("whereSql") String whereSql, @Param("state") S state, @Param("stateName") String stateName) {
        return alertDynamicAllByWhere(null, whereSql, state, stateName);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertDynamicAllByWhere(@Param("tableName") String tableName, @Param("whereSql") String whereSql, @Param("state") S state, @Param("stateName") String stateName);

}
