package io.github.nichetoolkit.mybatis.stereotype;


import io.github.nichetoolkit.rice.enums.SortType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>RestOrderBy</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RestOrderBy {
    /**
     * 排序方式
     */
    String orderBy() default "";

    /**
     * 排序方式
     */
    SortType SortType() default SortType.NONE;

    /**
     * 排序的优先级，数值越小优先级越高
     */
    int priority() default 0;
}
