
package io.github.nichetoolkit.mybatis.advice;

import io.github.nichetoolkit.mybatis.MybatisEntityMapper;
import io.github.nichetoolkit.mybatis.mapper.MybatisSuperMapper;
import io.github.nichetoolkit.rice.IdEntity;

/**
 * <p>SuperMapperAdvice</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface SuperMapperAdvice<M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> extends MybatisEntityMapper<E> {

  default M superMapper() {
    return MybatisMapperAdvice.<M,E, I>defaultInstance().superMapper(clazz());
  }

}
