package io.github.nichetoolkit.mybatis.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>RestExclude</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RestExcludes {
    /**
     * 排除指定父类的所有字段
     */
    Class<?>[] superClasses() default {};

    /**
     * 排除指定类型的字段
     */
    Class<?>[] fieldTypes() default {};

    /**
     * 排除指定字段名的字段
     */
    String[] fields() default {};

    /**
     * 排除指定注解的字段
     */
    RestExclude[] excludes() default {};
}
