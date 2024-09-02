package io.github.nichetoolkit.mybatis.wrapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <code>RestSqlWrapper</code>
 * <p>The type rest sql wrapper interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.lang.annotation.Annotation
 * @see java.lang.annotation.Retention
 * @see java.lang.annotation.Target
 * @since Jdk1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.PARAMETER})
public @interface RestSqlWrapper {
    /**
     * <code>value</code>
     * <p>the method.</p>
     * @return {@link java.lang.Class} <p>the return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    Class<? extends AnnotationSqlWrapper>[] value() default {};
}
