package io.github.nichetoolkit.mybatis.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>RestExclude</p>
 * 排除字段
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RestExclude {
    /**
     * 列名，默认空时使用字段名
     */
    String value() default "";
}
