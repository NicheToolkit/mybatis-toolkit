package io.github.nichetoolkit.mybatis.stereotype;

import org.apache.ibatis.annotations.Mapper;

import java.lang.annotation.*;

/**
 * <p>RestMapper</p>
 * @author Cyan (snow22314 @ outlook.com)
 * @version v1.0.0
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Mapper
public @interface RestMapper {
}
