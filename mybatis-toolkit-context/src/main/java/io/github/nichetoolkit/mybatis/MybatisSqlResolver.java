package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.defaults.DefaultSqlResolver;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Documented
@Indexed
public @interface MybatisSqlResolver {
    Class<? extends DefaultSqlResolver>[] value() default {};
}
