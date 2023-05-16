package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisCaching;
import io.github.nichetoolkit.mybatis.provider.MybatisRemoveProvider;
import io.github.nichetoolkit.rice.IdEntity;
import io.github.nichetoolkit.rice.mapper.RemoveMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

/**
 * <p>MybatisMapper</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface MybatisRemoveMapper<E extends IdEntity<I>, I> extends RemoveMapper<I> {

    @Override
    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisRemoveProvider.class, method = "removeById")
    Integer removeById(@Param("id") I id, @Param("sign") String sign);

    @Override
    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisRemoveProvider.class, method = "removeDynamicById")
    Integer removeDynamicById(@Param("tablename") String tablename, @Param("id") I id, @Param("sign") String sign);

    @Override
    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisRemoveProvider.class, method = "removeAll")
    Integer removeAll(@Param("idList") Collection<I> idList, @Param("sign") String sign);

    @Override
    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisRemoveProvider.class, method = "removeDynamicAll")
    Integer removeDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("sign") String sign);

    @Override
    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisRemoveProvider.class, method = "removeAllByWhere")
    Integer removeAllByWhere(@Param("whereSql") String whereSql, @Param("sign") String sign);

    @Override
    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisRemoveProvider.class, method = "removeDynamicAllByWhere")
    Integer removeDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql, @Param("sign") String sign);
}
