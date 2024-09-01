package io.github.nichetoolkit.mybatis.stereotype.column;


import io.github.nichetoolkit.mybatis.enums.SortType;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * <code>RestSortType</code>
 * <p>The type rest sort type interface.</p>
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
public @interface RestSortType {
    /**
     * <code>value</code>
     * <p>the method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.enums.SortType} <p>the return object is <code>SortType</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.SortType
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor("type")
    SortType value() default SortType.NONE;

    /**
     * <code>type</code>
     * <p>the method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.enums.SortType} <p>the return object is <code>SortType</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.SortType
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor("value")
    SortType type() default SortType.NONE;

    /**
     * <code>priority</code>
     * <p>the method.</p>
     * @return int <p>the return object is <code>int</code> type.</p>
     */
    int priority() default 0;
}
