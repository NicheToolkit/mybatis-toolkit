package io.github.nichetoolkit.mybatis.stereotype;

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
@Target(ElementType.TYPE)
public @interface RestProperties {
    /**
     * 属性名集合
     */
    String[] names() default {};

    /**
     * 属性值集合
     */
    String[] values() default {};

    /**
     * 配置的 RestProperty 集合
     */
    RestProperty[] properties() default {};
}
