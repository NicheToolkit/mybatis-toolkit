package io.github.nichetoolkit.mybatis.stereotype.table;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Indexed
public @interface RestResultMap {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    boolean autoResultMap() default true;

}
