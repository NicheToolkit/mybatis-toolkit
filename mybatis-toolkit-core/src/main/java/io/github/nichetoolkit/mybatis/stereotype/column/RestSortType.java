package io.github.nichetoolkit.mybatis.stereotype.column;


import io.github.nichetoolkit.mybatis.enums.SortType;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Documented
@Indexed
public @interface RestSortType {
    @AliasFor("type")
    SortType value() default SortType.NONE;

    @AliasFor("value")
    SortType type() default SortType.NONE;

    int priority() default 0;
}
