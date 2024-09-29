package io.github.nichetoolkit.mybatis.stereotype.column;


import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Documented
@Indexed
public @interface RestColname {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String comment() default "";
}
