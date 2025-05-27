package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.mapper.SuperMapper;

/**
 * <code>MybatisSuperMapper</code>
 * <p>The mybatis super mapper interface.</p>
 * @param <E>  {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @see  io.github.nichetoolkit.rice.RestId
 * @see  io.github.nichetoolkit.mybatis.MybatisMapper
 * @see  io.github.nichetoolkit.mybatis.MybatisSaveMapper
 * @see  io.github.nichetoolkit.mybatis.MybatisFindMapper
 * @see  io.github.nichetoolkit.mybatis.MybatisDeleteMapper
 * @see  io.github.nichetoolkit.rice.mapper.SuperMapper
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface MybatisSuperMapper<E extends RestId<I>, I> extends MybatisMapper<E>, MybatisSaveMapper<E, I>, MybatisFindMapper<E, I>,MybatisFindParamMapper<E, I>, MybatisDeleteMapper<E, I>, MybatisColumnMapper<E, I>, SuperMapper<E, I> {

}

