package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisMapper;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.SuperMapper;

//public interface MybatisSuperMapper<E extends RestId<I>, I> extends MybatisMapper<E>, MybatisSaveMapper<E, I>, MybatisFindMapper<E, I>, MybatisDeleteMapper<E, I>, SuperMapper<E, I> {
//}
public interface MybatisSuperMapper<E extends RestId<I>, I> extends MybatisMapper<E>,MybatisFindMapper<E, I>, SuperMapper<E, I> {
}
