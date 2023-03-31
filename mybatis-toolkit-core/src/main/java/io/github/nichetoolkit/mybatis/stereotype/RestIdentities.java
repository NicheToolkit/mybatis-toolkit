package io.github.nichetoolkit.mybatis.stereotype;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * <p>RestIdentities</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Indexed
public @interface RestIdentities {

    /**
     * 联合主键的字段名称
     */
    @AliasFor("identities")
    String[] value() default {};

    /**
     * 联合主键的字段名称
     */
    @AliasFor("value")
    String[] identities() default {};

    /**
     * 是否将主键注解添加到联合主键
     */
    boolean unionIdentity() default false;

}
