package io.github.nichetoolkit.mybatis.stereotype;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Component
@Mapper
public @interface RestMapper {
    @AliasFor(
            annotation = Component.class
    )
    String value() default "";

    Class<?> entityType() default Object.class;

    Class<?> identityType() default Object.class;

    Class<?> linkageType() default Object.class;

    Class<?> alertnessType() default Object.class;
}
