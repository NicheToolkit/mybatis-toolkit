package io.github.nichetoolkit.mybatis.stereotype.table;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
@Documented
@Indexed
public @interface RestProperties {
    @AliasFor("properties")
    RestProperty[] value() default {};

    @AliasFor("value")
    RestProperty[] properties() default {};
}
