package io.github.nichetoolkit.mybatis.stereotype;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * <code>RestMapper</code>
 * <p>The type rest mapper interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.lang.annotation.Annotation
 * @see java.lang.annotation.Documented
 * @see java.lang.annotation.Inherited
 * @see java.lang.annotation.Retention
 * @see java.lang.annotation.Target
 * @see org.springframework.stereotype.Component
 * @see org.apache.ibatis.annotations.Mapper
 * @since Jdk1.8
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Component
@Mapper
public @interface RestMapper {
    /**
     * <code>value</code>
     * <p>the method.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(
            annotation = Component.class
    )
    String value() default "";

    /**
     * <code>entityType</code>
     * <p>the type method.</p>
     * @return {@link java.lang.Class} <p>the type return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    Class<?> entityType() default Object.class;

    /**
     * <code>identityType</code>
     * <p>the type method.</p>
     * @return {@link java.lang.Class} <p>the type return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    Class<?> identityType() default Object.class;

}
