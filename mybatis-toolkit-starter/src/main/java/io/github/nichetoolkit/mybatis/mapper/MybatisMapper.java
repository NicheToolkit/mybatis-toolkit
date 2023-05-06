package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisCaching;
import io.github.nichetoolkit.mybatis.MybatisEntityMapper;
import io.github.nichetoolkit.mybatis.provider.MybatisProvider;
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
public interface MybatisMapper<E extends IdEntity<I>, I> extends MybatisEntityMapper<E>, SuperMapper<E,I> {

    @Override
    @Lang(MybatisCaching.class)
    @InsertProvider(type = MybatisProvider.class, method = "save")
    Integer save(@Param("entity") E entity);

    @Override
    @Lang(MybatisCaching.class)
    @InsertProvider(type = MybatisProvider.class, method = "save")
    Integer save(@Param("tablename") String tablename, @Param("entity") E entity);

    @Override
    @Lang(MybatisCaching.class)
    @InsertProvider(type = MybatisProvider.class, method = "saveAll")
    Integer saveAll(@Param("entityList") Collection<E> entityList);

    @Override
    @Lang(MybatisCaching.class)
    @InsertProvider(type = MybatisProvider.class, method = "saveAll")
    Integer saveAll(@Param("tablename") String tablename, @Param("entityList") Collection<E> entityList);

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisProvider.class, method = "deleteById")
    Integer deleteById(@Param("id") I id);

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisProvider.class, method = "deleteById")
    Integer deleteById(@Param("tablename") String tablename, @Param("id") I id);

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisProvider.class, method = "deleteByAll")
    Integer deleteAll(@Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisProvider.class, method = "deleteByAll")
    Integer deleteAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisCaching.class)
    @InsertProvider(type = MybatisProvider.class, method = "findById")
    E findById(@Param("id") I id);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisProvider.class, method = "findById")
    E findById(@Param("tablename") String tablename, @Param("id") I id);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisProvider.class, method = "findByAll")
    List<E> findAll(@Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisProvider.class, method = "findByAll")
    List<E> findAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisProvider.class, method = "findAllByWhere")
    List<E> findAllByWhere(@Param("whereSql") String whereSql);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisProvider.class, method = "findAllByWhere")
    List<E> findAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql);

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisProvider.class, method = "deleteAllByWhere")
    Integer deleteAllByWhere(@Param("whereSql") String whereSql);

    @Override
    @Lang(MybatisCaching.class)
    @DeleteProvider(type = MybatisProvider.class, method = "deleteAllByWhere")
    Integer deleteAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql);
}
