package io.github.nichetoolkit.mybatis.stereotype.table;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Indexed
public @interface RestLinkage {
}
