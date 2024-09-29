package io.github.nichetoolkit.mybatis.stereotype.table;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Indexed
public @interface RestIgnores {
    @AliasFor("fields")
    String[] value() default {};

    @AliasFor("value")
    String[] fields() default {};

    Class<?>[] fieldTypes() default {};

    Class<?>[] superClasses() default {};
}
