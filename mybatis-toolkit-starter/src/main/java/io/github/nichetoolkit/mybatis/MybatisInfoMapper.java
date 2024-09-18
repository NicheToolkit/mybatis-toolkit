package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.provider.MybatisInfoProvider;
import io.github.nichetoolkit.rice.InfoEntity;
import io.github.nichetoolkit.rice.mapper.InfoMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <code>MybatisInfoMapper</code>
 * <p>The type mybatis info mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.InfoEntity} <p>the generic parameter is <code>InfoEntity</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>the parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.InfoEntity
 * @see MybatisSuperMapper
 * @see io.github.nichetoolkit.rice.mapper.InfoMapper
 * @since Jdk1.8
 */
public interface MybatisInfoMapper<E extends InfoEntity<I>, I> extends MybatisSuperMapper<E, I>, InfoMapper<E, I> {

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisInfoProvider.class, method = "findByName")
    List<E> findByName(@Param("name") String name, @Param("logicValue") String logicValue);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisInfoProvider.class, method = "findDynamicByName")
    List<E> findDynamicByName(@Param("tablename") String tablename, @Param("name") String name, @Param("logicValue") String logicValue);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisInfoProvider.class, method = "findByNameAndNotId")
    List<E> findByNameAndNotId(@Param("name") String name, @Param("id") I id, @Param("logicValue") String logicValue);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisInfoProvider.class, method = "findDynamicByNameAndNotId")
    List<E> findDynamicByNameAndNotId(@Param("tablename") String tablename, @Param("name") String name, @Param("id") I id, @Param("logicValue") String logicValue);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisInfoProvider.class, method = "findByEntity")
    List<E> findByEntity(@Param("entity") E entity, @Param("logicValue") String logicValue);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisInfoProvider.class, method = "findDynamicByEntity")
    List<E> findDynamicByEntity(@Param("tablename") String tablename, @Param("entity") E entity, @Param("logicValue") String logicValue);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisInfoProvider.class, method = "findByEntityAndNotId")
    List<E> findByEntityAndNotId(@Param("entity") E entity, @Param("id") I id, @Param("logicValue") String logicValue);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisInfoProvider.class, method = "findDynamicByEntityAndNotId")
    List<E> findDynamicByEntityAndNotId(@Param("tablename") String tablename, @Param("entity") E entity, @Param("id") I id, @Param("logicValue") String logicValue);
}
