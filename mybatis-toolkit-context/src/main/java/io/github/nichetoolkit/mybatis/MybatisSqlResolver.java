package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.defaults.DefaultSqlResolver;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * <code>MybatisSqlResolver</code>
 * <p>The mybatis sql resolver interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.lang.annotation.Annotation
 * @see java.lang.annotation.Retention
 * @see java.lang.annotation.Target
 * @see java.lang.annotation.Documented
 * @see org.springframework.stereotype.Indexed
 * @since Jdk1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Documented
@Indexed
public @interface MybatisSqlResolver {
    /**
     * <code>value</code>
     * <p>The value method.</p>
     * @return {@link java.lang.Class} <p>The value return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    Class<? extends DefaultSqlResolver>[] value() default {};
}
