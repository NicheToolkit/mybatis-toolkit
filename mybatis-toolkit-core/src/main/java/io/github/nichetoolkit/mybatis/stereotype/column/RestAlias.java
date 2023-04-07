package io.github.nichetoolkit.mybatis.stereotype.column;


import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * <p>RestAlias</p>
 * 属性或列别名 优先级 RestProperty > RestAlias > RestColumn
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Documented
@Indexed
public @interface RestAlias {
    /**
     * 列名，默认空时使用字段名
     */
    @AliasFor("name")
    String value() default "";

    /**
     * 列名，默认空时使用字段名
     */
    @AliasFor("value")
    String name() default "";

    /**
     * 备注
     */
    String remark() default "";
}
