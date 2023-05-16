package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisCaching;
import io.github.nichetoolkit.mybatis.MybatisEntityMapper;
import io.github.nichetoolkit.mybatis.provider.MybatisSuperProvider;
import io.github.nichetoolkit.rice.IdEntity;
import io.github.nichetoolkit.rice.mapper.SuperMapper;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;

/**
 * <p>MybatisMapper</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface MybatisSuperMapper<E extends IdEntity<I>, I> extends MybatisEntityMapper<E>, SuperMapper<E,I> {

    @Override
    @Lang(MybatisCaching.class)
    @InsertProvider(type = MybatisSuperProvider.class, method = "save")
    Integer save(@Param("entity") E entity);

    @Override
    @Lang(MybatisCaching.class)
    @InsertProvider(type = MybatisSuperProvider.class, method = "saveDynamic")
    Integer saveDynamic(@Param("tablename") String tablename, @Param("entity") E entity);

    @Override
    @Lang(MybatisCaching.class)
    @InsertProvider(type = MybatisSuperProvider.class, method = "saveAll")
    Integer saveAll(@Param("entityList") Collection<E> entityList);

    @Override
    @Lang(MybatisCaching.class)
    @InsertProvider(type = MybatisSuperProvider.class, method = "saveDynamicAll")
    Integer saveDynamicAll(@Param("tablename") String tablename, @Param("entityList") Collection<E> entityList);

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisSuperProvider.class, method = "deleteById")
    Integer deleteById(@Param("id") I id);

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisSuperProvider.class, method = "deleteDynamicById")
    Integer deleteDynamicById(@Param("tablename") String tablename, @Param("id") I id);

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisSuperProvider.class, method = "deleteByAll")
    Integer deleteAll(@Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisSuperProvider.class, method = "deleteDynamicByAll")
    Integer deleteDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisSuperProvider.class, method = "findById")
    E findById(@Param("id") I id);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisSuperProvider.class, method = "findDynamicById")
    E findDynamicById(@Param("tablename") String tablename, @Param("id") I id);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisSuperProvider.class, method = "findByAll")
    List<E> findAll(@Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisSuperProvider.class, method = "findDynamicByAll")
    List<E> findDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisSuperProvider.class, method = "findAllByWhere")
    List<E> findAllByWhere(@Param("whereSql") String whereSql);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisSuperProvider.class, method = "findDynamicAllByWhere")
    List<E> findDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql);

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisSuperProvider.class, method = "deleteAllByWhere")
    Integer deleteAllByWhere(@Param("whereSql") String whereSql);

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisSuperProvider.class, method = "deleteDynamicAllByWhere")
    Integer deleteDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql);
}
