package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.IdEntity;
import io.github.nichetoolkit.rice.mapper.SuperMapper;

public interface MybatisSuperMapper<E extends IdEntity<I>, I> extends MybatisEntityMapper<E>, MybatisSaveMapper<E, I>, MybatisFindMapper<E, I>, MybatisDeleteMapper<E, I>, SuperMapper<E, I> {
}
