package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisCaching;
import io.github.nichetoolkit.mybatis.MybatisEntityMapper;
import io.github.nichetoolkit.mybatis.provider.MybatisInfoProvider;
import io.github.nichetoolkit.mybatis.provider.MybatisProvider;
import io.github.nichetoolkit.rice.IdEntity;
import io.github.nichetoolkit.rice.InfoEntity;
import io.github.nichetoolkit.rice.mapper.InfoMapper;
import io.github.nichetoolkit.rice.mapper.SuperMapper;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;

/**
 * <p>MybatisInfoMapper</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface MybatisInfoMapper<E extends InfoEntity<I>, I> extends MybatisMapper<E, I>, InfoMapper<E, I> {

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisInfoProvider.class, method = "findByName")
    List<E> findByName(@Param("name") String name, @Param("sign") String sign);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisInfoProvider.class, method = "findDynamicByName")
    List<E> findDynamicByName(@Param("tablename") String tablename, @Param("name") String name, @Param("sign") String sign);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisInfoProvider.class, method = "findByNameAndNotId")
    List<E> findByNameAndNotId(@Param("name") String name, @Param("id") I id, @Param("sign") String sign);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisInfoProvider.class, method = "findDynamicByNameAndNotId")
    List<E> findDynamicByNameAndNotId(@Param("tablename") String tablename, @Param("name") String name, @Param("id") I id, @Param("sign") String sign);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisInfoProvider.class, method = "findByEntity")
    List<E> findByEntity(@Param("entity") E entity, @Param("sign") String sign);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisInfoProvider.class, method = "findDynamicByEntity")
    List<E> findDynamicByEntity(@Param("tablename") String tablename, @Param("entity") E entity, @Param("sign") String sign);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisInfoProvider.class, method = "findByEntityAndNotId")
    List<E> findByEntityAndNotId(@Param("entity") E entity, @Param("id") I id, @Param("sign") String sign);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisInfoProvider.class, method = "findDynamicByEntityAndNotId")
    List<E> findDynamicByEntityAndNotId(@Param("tablename") String tablename, @Param("entity") E entity, @Param("id") I id, @Param("sign") String sign);
}
