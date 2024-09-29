package io.github.nichetoolkit.mybatis.stereotype.column;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Indexed
public @interface RestOperateKey {
}

