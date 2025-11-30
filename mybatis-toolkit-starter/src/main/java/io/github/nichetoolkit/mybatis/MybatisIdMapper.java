package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestId;

/**
 * <code>MybatisIdMapper</code>
 * <p>The mybatis id mapper interface.</p>
 * @param <E> {@link io.github.nichetoolkit.rice.RestId} <p>The generic parameter is <code>RestId</code> type.</p>
 * @param <I> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestId
 * @see io.github.nichetoolkit.mybatis.MybatisSuperMapper
 * @since Jdk17
 */
public interface MybatisIdMapper<E extends RestId<I>, I> extends MybatisSuperMapper<E, I> {
}

