
package io.github.nichetoolkit.mybatis.record;

import io.github.nichetoolkit.mybatis.MybatisMapper;
import io.github.nichetoolkit.mybatis.MybatisMapperFactory;
import io.github.nichetoolkit.mybatis.MybatisSuperMapper;
import io.github.nichetoolkit.rice.IdEntity;

/**
 * <code>MybatisSuperRecord</code>
 * <p>The mybatis super record interface.</p>
 * @param <M>  {@link io.github.nichetoolkit.mybatis.MybatisSuperMapper} <p>The generic parameter is <code>MybatisSuperMapper</code> type.</p>
 * @param <E>  {@link io.github.nichetoolkit.rice.IdEntity} <p>The generic parameter is <code>IdEntity</code> type.</p>
 * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @see  io.github.nichetoolkit.mybatis.MybatisSuperMapper
 * @see  io.github.nichetoolkit.rice.IdEntity
 * @see  io.github.nichetoolkit.mybatis.MybatisMapper
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface MybatisSuperRecord<M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> extends MybatisMapper<E> {

    /**
     * <code>superMapper</code>
     * <p>The super mapper method.</p>
     * @return M <p>The super mapper return object is <code>M</code> type.</p>
     */
    default M superMapper() {
    return MybatisMapperFactory.<M,E, I>defaultInstance().superMapper(entityType());
  }

}
