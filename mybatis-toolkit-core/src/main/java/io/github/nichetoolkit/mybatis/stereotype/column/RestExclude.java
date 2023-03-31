package io.github.nichetoolkit.mybatis.stereotype.column;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * <p>RestExclude</p>
 * 排除字段
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Documented
@Indexed
public @interface RestExclude {

    boolean value() default true;

    /** 可查询 */
    boolean select() default false;

    /** 可插入 */
    boolean insert() default false;

    /** 可更新 */
    boolean update() default false;

}

