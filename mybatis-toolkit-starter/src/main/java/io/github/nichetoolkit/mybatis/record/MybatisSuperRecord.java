
package io.github.nichetoolkit.mybatis.record;

import io.github.nichetoolkit.mybatis.MybatisEntityMapper;
import io.github.nichetoolkit.mybatis.mapper.MybatisSuperMapper;
import io.github.nichetoolkit.rice.IdEntity;

public interface MybatisSuperRecord<M extends MybatisSuperMapper<E, I>, E extends IdEntity<I>, I> extends MybatisEntityMapper<E> {

    default M superMapper() {
    return MybatisRecordProvider.<M,E, I>defaultInstance().superMapper(clazz());
  }

}
