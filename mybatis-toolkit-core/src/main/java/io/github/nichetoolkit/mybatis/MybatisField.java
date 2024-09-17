package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.error.lack.AccessibleLackError;
import io.github.nichetoolkit.rest.resolver.RestGenericTypeResolver;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * <code>MybatisField</code>
 * <p>The type mybatis field class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.Data
 * @see lombok.experimental.Accessors
 * @since Jdk1.8
 */
@Data
@Accessors(fluent = true)
public class MybatisField {
    /**
     * <code>clazz</code>
     * {@link java.lang.Class} <p>the <code>clazz</code> field.</p>
     * @see java.lang.Class
     */
    protected Class<?> clazz;
    /**
     * <code>field</code>
     * {@link java.lang.reflect.Field} <p>the <code>field</code> field.</p>
     * @see java.lang.reflect.Field
     */
    protected Field field;

    /**
     * <code>MybatisField</code>
     * Instantiates a new mybatis field.
     */
    public MybatisField() {
    }

    /**
     * <code>MybatisField</code>
     * Instantiates a new mybatis field.
     * @param clazz {@link java.lang.Class} <p>the clazz parameter is <code>Class</code> type.</p>
     * @param field {@link java.lang.reflect.Field} <p>the field parameter is <code>Field</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.reflect.Field
     */
    public MybatisField(Class<?> clazz, Field field) {
        this.clazz = clazz;
        this.field = field;
        this.field.setAccessible(true);
    }

    /**
     * <code>declaringClass</code>
     * <p>the class method.</p>
     * @return {@link java.lang.Class} <p>the class return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    public Class<?> declaringClass() {
        return field.getDeclaringClass();
    }

    /**
     * <code>fieldName</code>
     * <p>the name method.</p>
     * @return {@link java.lang.String} <p>the name return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String fieldName() {
        return field.getName();
    }

    /**
     * <code>fieldType</code>
     * <p>the type method.</p>
     * @return {@link java.lang.Class} <p>the type return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    public Class<?> fieldType() {
        return RestGenericTypeResolver.resolveClass(field, clazz);
    }

    /**
     * <code>getAnnotation</code>
     * <p>the annotation getter method.</p>
     * @param <T>             {@link java.lang.annotation.Annotation} <p>the generic parameter is <code>Annotation</code> type.</p>
     * @param annotationClass {@link java.lang.Class} <p>the annotation class parameter is <code>Class</code> type.</p>
     * @return T <p>the annotation return object is <code>T</code> type.</p>
     * @see java.lang.annotation.Annotation
     * @see java.lang.Class
     */
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return AnnotationUtils.getAnnotation(this.field, annotationClass);
    }

    /**
     * <code>getAnnotations</code>
     * <p>the annotations method.</p>
     * @return {@link java.lang.annotation.Annotation} <p>the annotations return object is <code>Annotation</code> type.</p>
     * @see java.lang.annotation.Annotation
     */
    public Annotation[] getAnnotations() {
        return field.getAnnotations();
    }

    /**
     * <code>get</code>
     * <p>the method.</p>
     * @param key {@link java.lang.Object} <p>the key parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.Object} <p>the return object is <code>Object</code> type.</p>
     * @see java.lang.Object
     */
    public Object get(Object key) {
        try {
            return field.get(key);
        } catch (IllegalAccessException exception) {
            throw new AccessibleLackError("getting field value by reflect, error: " + exception.getMessage(),exception);
        }
    }

    /**
     * <code>set</code>
     * <p>the method.</p>
     * @param key   {@link java.lang.Object} <p>the key parameter is <code>Object</code> type.</p>
     * @param value {@link java.lang.Object} <p>the value parameter is <code>Object</code> type.</p>
     * @see java.lang.Object
     */
    public void set(Object key, Object value) {
        try {
            field.set(key, value);
        } catch (IllegalAccessException exception) {
            throw new AccessibleLackError("setting field value by reflect, error: " + exception.getMessage(),exception);
        }
    }
}
