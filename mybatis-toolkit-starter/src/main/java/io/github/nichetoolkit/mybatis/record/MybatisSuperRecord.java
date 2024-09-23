
package io.github.nichetoolkit.mybatis.record;

import io.github.nichetoolkit.mybatis.MybatisEntityMapper;
import io.github.nichetoolkit.mybatis.mapper.MybatisSuperMapper;
import io.github.nichetoolkit.rice.IdEntity;

/**
 * <code>MybatisSuperRecord</code>
 * <p>The type mybatis super record interface.</p>
 * @param <M> {@link MybatisSuperMapper} <p>the generic parameter is <code>MybatisSuperMapper</code> type.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.IdEntity} <p>the generic parameter is <code>IdEntity</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>the parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see MybatisSuperMapper
 * @see io.github.nichetoolkit.rice.IdEntity
 * @see io.github.nichetoolkit.mybatis.MybatisEntityMapper
 * @since Jdk1.8
 */
public interface MybatisSuperRecord<M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> extends MybatisEntityMapper<E> {

    /**
     * <code>superMapper</code>
     * <p>the mapper method.</p>
     * @return M <p>the mapper return object is <code>M</code> type.</p>
     */
    default M superMapper() {
    return MybatisRecordProvider.<M,E, I>defaultInstance().superMapper(clazz());
  }

}
