package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.AlertMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

public interface MybatisAlertMapper<E extends RestId<I>, S, I> extends MybatisMapper<E>, AlertMapper<S, I> {

    @Override
    default Integer alertById(@Param("id") I id, @Param("status") S status) {
        return alertDynamicById(null, id, status);
    }

    @Override
    default Integer alertDynamicById(@Param("tableName") String tableName, @Param("id") I id, @Param("status") S status) {
        return alertDynamicById(tableName, id, status, null);
    }

    @Override
    default Integer alertAll(@Param("idList") Collection<I> idList, @Param("status") S status) {
        return alertDynamicAll(null, idList, status);
    }

    @Override
    default Integer alertDynamicAll(@Param("tableName") String tableName, @Param("idList") Collection<I> idList, @Param("status") S status) {
        return alertDynamicAll(tableName, idList, status, null);
    }

    @Override
    default Integer alertAllByWhere(@Param("whereSql") String whereSql, @Param("status") S status) {
        return alertDynamicAllByWhere(null, whereSql, status);
    }

    @Override
    default Integer alertDynamicAllByWhere(@Param("tableName") String tableName, @Param("whereSql") String whereSql, @Param("status") S status) {
        return alertDynamicAllByWhere(tableName, whereSql, status, null);
    }

    @Override
    default Integer alertById(@Param("id") I id, @Param("status") S status, @Param("statusName") String statusName) {
        return alertDynamicById(null, id, status, statusName);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertDynamicById(@Param("tableName") String tableName, @Param("id") I id, @Param("status") S status, @Param("statusName") String statusName);


    @Override
    default Integer alertAll(@Param("idList") Collection<I> idList, @Param("status") S status, @Param("statusName") String statusName) {
        return alertDynamicAll(null, idList, status, statusName);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertDynamicAll(@Param("tableName") String tableName, @Param("idList") Collection<I> idList, @Param("status") S status, @Param("statusName") String statusName);

    @Override
    default Integer alertAllByWhere(@Param("whereSql") String whereSql, @Param("status") S status, @Param("statusName") String statusName) {
        return alertDynamicAllByWhere(null, whereSql, status, statusName);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @UpdateProvider(MybatisSqlProviderResolver.class)
    Integer alertDynamicAllByWhere(@Param("tableName") String tableName, @Param("whereSql") String whereSql, @Param("status") S status, @Param("statusName") String statusName);

}
