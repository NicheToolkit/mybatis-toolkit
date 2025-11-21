package io.github.nichetoolkit.mybatis.natives;

import io.github.nichetoolkit.mybatis.MybatisMapper;
import io.github.nichetoolkit.mybatis.MybatisSqlProviderResolver;
import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.natives.FindFickleMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Collection;
import java.util.List;

/**
 * <code>MybatisFindFickleMapper</code>
 * <p>The mybatis find fickle mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestId
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @see io.github.nichetoolkit.rice.mapper.natives.FindFickleMapper
 * @since Jdk1.8
 */
public interface MybatisFindFickleMapper<E extends RestId<I>, I> extends MybatisMapper<E>, FindFickleMapper<E, I> {

    @Override
    default E findByIdFickle(@Param("id") I id, @Param("fickleParams") RestFickle<?>... fickleParams) {
        return findDynamicByIdFickle(null, id, fickleParams);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    E findDynamicByIdFickle(@Param("tableName") String tableName, @Param("id") I id, @Param("fickleParams") RestFickle<?>... fickleParams);

    @Override
    default List<E> findAllFickle(@Param("idList") Collection<I> idList, @Param("fickleParams") RestFickle<?>... fickleParams) {
        return findDynamicAllFickle(null, idList, fickleParams);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicAllFickle(@Param("tableName") String tableName, @Param("idList") Collection<I> idList, @Param("fickleParams") RestFickle<?>... fickleParams);

}
