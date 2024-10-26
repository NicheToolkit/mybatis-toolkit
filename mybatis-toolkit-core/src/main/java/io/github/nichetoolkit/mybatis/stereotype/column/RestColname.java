package io.github.nichetoolkit.mybatis.stereotype.column;


import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * <code>RestColname</code>
 * <p>The rest colname interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.lang.annotation.Annotation
 * @see java.lang.annotation.Retention
 * @see java.lang.annotation.Target
 * @see java.lang.annotation.Documented
 * @see org.springframework.stereotype.Indexed
 * @since Jdk1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Documented
@Indexed
public @interface RestColname {
    /**
     * <code>value</code>
     * <p>The value method.</p>
     * @return {@link java.lang.String} <p>The value return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor("name")
    String value() default "";

    /**
     * <code>name</code>
     * <p>The name method.</p>
     * @return {@link java.lang.String} <p>The name return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor("value")
    String name() default "";

    /**
     * <code>comment</code>
     * <p>The comment method.</p>
     * @return {@link java.lang.String} <p>The comment return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    String comment() default "";
}
