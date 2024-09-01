package io.github.nichetoolkit.mybatis.stereotype;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * <code>RestProperties</code>
 * <p>The type rest properties interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.lang.annotation.Annotation
 * @see java.lang.annotation.Retention
 * @see java.lang.annotation.Target
 * @see java.lang.annotation.Documented
 * @see org.springframework.stereotype.Indexed
 * @since Jdk1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
@Documented
@Indexed
public @interface RestProperties {
    /**
     * <code>value</code>
     * <p>the method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.stereotype.RestProperty} <p>the return object is <code>RestProperty</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.stereotype.RestProperty
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor("properties")
    RestProperty[] value() default {};

    /**
     * <code>properties</code>
     * <p>the method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.stereotype.RestProperty} <p>the return object is <code>RestProperty</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.stereotype.RestProperty
     */
    RestProperty[] properties() default {};
}
