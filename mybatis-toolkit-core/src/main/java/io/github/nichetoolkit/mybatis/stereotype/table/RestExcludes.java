package io.github.nichetoolkit.mybatis.stereotype.table;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * <code>RestExcludes</code>
 * <p>The type rest excludes interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.lang.annotation.Annotation
 * @see java.lang.annotation.Retention
 * @see java.lang.annotation.Target
 * @see java.lang.annotation.Documented
 * @see org.springframework.stereotype.Indexed
 * @since Jdk1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Indexed
public @interface RestExcludes {

    /**
     * <code>value</code>
     * <p>the method.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor("fields")
    String[] value() default {};

    /**
     * <code>fields</code>
     * <p>the method.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor("value")
    String[] fields() default {};


    /**
     * <code>ignores</code>
     * <p>the method.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    String[] ignores() default {};

    /**
     * <code>fieldTypes</code>
     * <p>the types method.</p>
     * @return {@link java.lang.Class} <p>the types return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    Class<?>[] fieldTypes() default {};

    /**
     * <code>superClasses</code>
     * <p>the classes method.</p>
     * @return {@link java.lang.Class} <p>the classes return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    Class<?>[] superClasses() default {};
}
