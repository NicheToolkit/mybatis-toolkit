package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.load.RestLoad;
import io.github.nichetoolkit.mybatis.load.RestParam;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.FindParamMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <code>MybatisFindParamMapper</code>
 * <p>The mybatis find param mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestId
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @see io.github.nichetoolkit.rice.mapper.FindParamMapper
 * @since Jdk17
 */
public interface MybatisFindParamMapper<E extends RestId<I>, I> extends MybatisMapper<E>, FindParamMapper<E, I> {

    @Override
    default List<E> findAllByIdOrParams(@Param("id") I id, @Param("params") RestParam... params) {
        return findDynamicAllByIdOrParams(null, id, params);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicAllByIdOrParams(@Param("tableName") String tableName, @Param("id") I id, @Param("params") RestParam... params);

    @Override
    default List<E> findAllLoadByIdOrParams(@Param("id") I id, @Param("params") RestParam[] params, @Param("loadParams") RestLoad... loadParams) {
        return findDynamicAllLoadByIdOrParams(null, id, params, loadParams);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicAllLoadByIdOrParams(@Param("tableName") String tableName, @Param("id") I id, @Param("params") RestParam[] params, @Param("loadParams") RestLoad... loadParams);


}
