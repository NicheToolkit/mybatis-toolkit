package io.github.nichetoolkit.mybatis.stereotype;

import io.github.nichetoolkit.mybatis.MybatisSqlResolver;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Documented
@Indexed
public @interface RestSqlResolver {
    Class<? extends MybatisSqlResolver>[] value() default {};
}
