package io.github.nichetoolkit.mybatis.stereotype.column;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * <code>RestUnionKey</code>
 * <p>The type rest union key interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.lang.annotation.Annotation
 * @see java.lang.annotation.Retention
 * @see java.lang.annotation.Target
 * @see java.lang.annotation.Documented
 * @see org.springframework.stereotype.Indexed
 * @since Jdk1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Documented
@Indexed
public @interface RestUnionKey {
    /**
     * <code>value</code>
     * <p>the method.</p>
     * @return boolean <p>the return object is <code>boolean</code> type.</p>
     */
    boolean value() default true;

    /**
     * <code>index</code>
     * <p>the method.</p>
     * @return int <p>the return object is <code>int</code> type.</p>
     */
    int index() default 0;

}
