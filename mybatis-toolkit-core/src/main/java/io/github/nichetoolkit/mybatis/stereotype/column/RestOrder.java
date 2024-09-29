package io.github.nichetoolkit.mybatis.stereotype.column;


import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Documented
@Indexed
public @interface RestOrder {
    int value() default 0;
}
