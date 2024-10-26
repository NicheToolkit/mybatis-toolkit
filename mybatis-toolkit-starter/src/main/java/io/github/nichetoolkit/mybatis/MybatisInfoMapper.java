package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestInfo;
import io.github.nichetoolkit.rice.mapper.InfoMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <code>MybatisInfoMapper</code>
 * <p>The mybatis info mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.RestInfo} <p>The generic parameter is <code>RestInfo</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestInfo
 * @see io.github.nichetoolkit.mybatis.MybatisSuperMapper
 * @see io.github.nichetoolkit.rice.mapper.InfoMapper
 * @since Jdk1.8
 */
public interface MybatisInfoMapper<E extends RestInfo<I>, I> extends MybatisSuperMapper<E, I>, InfoMapper<E, I> {

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findByName(@Param("name") String name, @Param("logic") Object logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicByName(@Param("tablename") String tablename, @Param("name") String name, @Param("logic") Object logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findByNameAndNotId(@Param("name") String name, @Param("id") I id, @Param("logic") Object logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicByNameAndNotId(@Param("tablename") String tablename, @Param("name") String name, @Param("id") I id, @Param("logic") Object logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findByEntityUnique(@Param("entity") E entity, @Param("logic") Object logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicByEntityUnique(@Param("tablename") String tablename, @Param("entity") E entity, @Param("logic") Object logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findByEntityUniqueAndNotId(@Param("entity") E entity, @Param("id") I id, @Param("logic") Object logic);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicByEntityUniqueAndNotId(@Param("tablename") String tablename, @Param("entity") E entity, @Param("id") I id, @Param("logic") Object logic);
}
