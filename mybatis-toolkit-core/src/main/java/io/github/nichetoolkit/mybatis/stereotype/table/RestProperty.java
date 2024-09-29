package io.github.nichetoolkit.mybatis.stereotype.table;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
@Documented
@Indexed
public @interface RestProperty {

    String name() default "";

    String value() default "";

}
