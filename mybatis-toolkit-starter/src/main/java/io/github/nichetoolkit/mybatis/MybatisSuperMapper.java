package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.RestField;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.SuperMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <code>MybatisSuperMapper</code>
 * <p>The mybatis super mapper interface.</p>
 * @param <E>  {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @see  io.github.nichetoolkit.rice.RestId
 * @see  io.github.nichetoolkit.mybatis.MybatisMapper
 * @see  io.github.nichetoolkit.mybatis.MybatisSaveMapper
 * @see  io.github.nichetoolkit.mybatis.MybatisFindMapper
 * @see  io.github.nichetoolkit.mybatis.MybatisDeleteMapper
 * @see  io.github.nichetoolkit.rice.mapper.SuperMapper
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface MybatisSuperMapper<E extends RestId<I>, I> extends MybatisMapper<E>, MybatisSaveMapper<E, I>, MybatisFindMapper<E, I>, MybatisDeleteMapper<E, I>, SuperMapper<E, I> {
    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<String> findColumns();

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    void createIndex(@Param("field") RestField<?> field);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    void dropIndex(@Param("field") RestField<?> field);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    void modifyColumn(@Param("field") RestField<?> field);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    void addColumn(@Param("field") RestField<?> field);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    void dropColumn(@Param("field") RestField<?> field);
}

