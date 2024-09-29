package io.github.nichetoolkit.mybatis.wrapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.PARAMETER})
public @interface RestSqlWrapper {
    Class<? extends AnnotationSqlWrapper>[] value() default {};
}
