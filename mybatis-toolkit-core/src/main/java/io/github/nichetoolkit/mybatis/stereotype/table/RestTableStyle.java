package io.github.nichetoolkit.mybatis.stereotype.table;

import io.github.nichetoolkit.mybatis.enums.StyleType;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Indexed
public @interface RestTableStyle {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String catalog() default "";

    String schema() default "";

    StyleType type() default StyleType.LOWER_UNDERLINE;

}
