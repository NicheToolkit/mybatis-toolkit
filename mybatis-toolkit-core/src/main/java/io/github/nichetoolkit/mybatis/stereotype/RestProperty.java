package io.github.nichetoolkit.mybatis.stereotype;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>RestProperty</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface RestProperty {
    /**
     * 属性key
     */
    @AliasFor("key")
    String name() default "";

    /**
     * 属性key
     */
    @AliasFor("name")
    String key() default "";

    /**
     * 属性值
     */
    String value();
}
