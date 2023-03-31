package io.github.nichetoolkit.mybatis.stereotype.column;


import io.github.nichetoolkit.rice.enums.SortType;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * <p>RestOrderBy</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Documented
@Indexed
public @interface RestOrderBy {
    /**
     * 排序方式
     */
    String value() default "";

    /**
     * 排序方式
     */
    SortType type() default SortType.NONE;

    /**
     * 排序的优先级，数值越小优先级越高
     */
    int priority() default 0;
}
