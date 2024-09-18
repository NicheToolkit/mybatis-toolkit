package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.provider.MybatisRemoveProvider;
import io.github.nichetoolkit.rice.mapper.RemoveMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Collection;

/**
 * <code>MybatisRemoveMapper</code>
 * <p>The type mybatis remove mapper interface.</p>
 * @param <I> {@link java.lang.Object} <p>the parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.mapper.RemoveMapper
 * @since Jdk1.8
 */
public interface MybatisRemoveMapper<I> extends RemoveMapper<I> {

    @Override
    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisRemoveProvider.class, method = "removeById")
    Integer removeById(@Param("id") I id, @Param("logicSign") String logicSign);

    @Override
    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisRemoveProvider.class, method = "removeDynamicById")
    Integer removeDynamicById(@Param("tablename") String tablename, @Param("id") I id, @Param("logicSign") String logicSign);

    @Override
    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisRemoveProvider.class, method = "removeAll")
    Integer removeAll(@Param("idList") Collection<I> idList, @Param("logicSign") String logicSign);

    @Override
    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisRemoveProvider.class, method = "removeDynamicAll")
    Integer removeDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("logicSign") String logicSign);

    @Override
    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisRemoveProvider.class, method = "removeAllByWhere")
    Integer removeAllByWhere(@Param("whereSql") String whereSql, @Param("logicSign") String logicSign);

    @Override
    @Lang(MybatisCaching.class)
    @UpdateProvider(type = MybatisRemoveProvider.class, method = "removeDynamicAllByWhere")
    Integer removeDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql, @Param("logicSign") String logicSign);
}
