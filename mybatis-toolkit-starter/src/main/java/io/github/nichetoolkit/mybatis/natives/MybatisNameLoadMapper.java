package io.github.nichetoolkit.mybatis.natives;

import io.github.nichetoolkit.mybatis.MybatisMapper;
import io.github.nichetoolkit.mybatis.MybatisSqlProviderResolver;
import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
import io.github.nichetoolkit.mybatis.load.RestLoad;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.natives.LinkLoadMapper;
import io.github.nichetoolkit.rice.mapper.natives.NameLoadMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Collection;
import java.util.List;

/**
 * <code>MybatisNameLoadMapper</code>
 * <p>The mybatis name load mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestId
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @see io.github.nichetoolkit.rice.mapper.natives.NameLoadMapper
 * @since Jdk1.8
 */
public interface MybatisNameLoadMapper<E extends RestId<I>, I> extends MybatisMapper<E>, NameLoadMapper<E, I> {

    @Override
    default List<E> findByNameLoad(@Param("name") String name, @Param("logic") Object logic, @Param("loadParams") RestLoad... loadParams) {
        return findDynamicByNameLoad(null, name, logic, loadParams);
    }

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<E> findDynamicByNameLoad(@Param("tablename") String tablename, @Param("name") String name, @Param("logic") Object logic, @Param("loadParams") RestLoad... loadParams);

}
