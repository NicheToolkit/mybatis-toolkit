package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rice.DefaultColumnResolver;

/**
 * <code>MybatisColumnResolver</code>
 * <p>The mybatis column resolver class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultColumnResolver
 * @since Jdk17
 */
public class MybatisColumnResolver extends DefaultColumnResolver {

    /**
     * <code>DEFAULT_RESOLVER</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisColumnResolver} <p>The constant <code>DEFAULT_RESOLVER</code> field.</p>
     */
    public static final MybatisColumnResolver DEFAULT_RESOLVER = new MybatisColumnResolver();

    @Override
    public StyleType support() {
        return MybatisContextHolder.defaultStyleType();
    }

    @Override
    public String resolve(String fieldName) throws RestException {
        return MybatisContextHolder.defaultTableStyle().columnName(fieldName);
    }
}
