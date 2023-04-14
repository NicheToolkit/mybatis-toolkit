package io.github.nichetoolkit.mybatis.stereotype.column;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * <p>RestLinkKey</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Documented
@Indexed
public @interface RestLinkKey {
    /**
     * 默认空时使用字段名
     */
    @AliasFor("name")
    String value() default "";

    /**
     * 默认空时使用字段名
     */
    @AliasFor("value")
    String name() default "";

}

