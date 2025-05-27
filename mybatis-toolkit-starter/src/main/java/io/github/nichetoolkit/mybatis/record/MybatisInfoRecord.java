
package io.github.nichetoolkit.mybatis.record;

import io.github.nichetoolkit.mybatis.MybatisMapper;
import io.github.nichetoolkit.mybatis.MybatisInfoMapper;
import io.github.nichetoolkit.mybatis.DefaultMapperFactory;
import io.github.nichetoolkit.rice.InfoEntity;

/**
 * <code>MybatisInfoRecord</code>
 * <p>The mybatis info record interface.</p>
 * @param <M> {@link io.github.nichetoolkit.mybatis.MybatisInfoMapper} <p>The generic parameter is <code>MybatisInfoMapper</code> type.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.InfoEntity} <p>The generic parameter is <code>InfoEntity</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisInfoMapper
 * @see io.github.nichetoolkit.rice.InfoEntity
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @since Jdk1.8
 */
public interface MybatisInfoRecord<M extends MybatisInfoMapper<E, I>, E extends InfoEntity<I>, I> extends MybatisMapper<E> {

    /**
     * <code>infoMapper</code>
     * <p>The info mapper method.</p>
     * @return M <p>The info mapper return object is <code>M</code> type.</p>
     */
    default M infoMapper() {
        return DefaultMapperFactory.<M, E, I>defaultInstance().superMapper(entityType());
    }

}
