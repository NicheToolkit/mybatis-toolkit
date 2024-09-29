package io.github.nichetoolkit.mybatis.stereotype.table;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Documented
@Indexed
public @interface RestEntity {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String comment() default "";

    String alias() default "";

    Class<?> entityType() default Object.class;

    Class<?> identityType() default Object.class;

    Class<?> linkageType() default Object.class;

    Class<?> alertnessType() default Object.class;

}
