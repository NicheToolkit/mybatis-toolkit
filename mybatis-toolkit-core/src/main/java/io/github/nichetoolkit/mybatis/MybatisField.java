package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.error.lack.AccessibleLackError;
import io.github.nichetoolkit.rest.reflect.GenericTypeResolver;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Data
@Accessors(fluent = true)
public class MybatisField {
    protected Class<?> entity;
    protected Field field;

    public MybatisField() {
    }

    public MybatisField(Class<?> entity, Field field) {
        this.entity = entity;
        this.field = field;
        this.field.setAccessible(true);
    }

    public Class<?> declaringClass() {
        return field.getDeclaringClass();
    }

    public String fieldName() {
        return field.getName();
    }

    public Class<?> fieldType() {
        return GenericTypeResolver.resolveFieldClass(field, entity);
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return AnnotationUtils.getAnnotation(this.field, annotationClass);
    }

    public Annotation[] getAnnotations() {
        return field.getAnnotations();
    }

    public Object get(Object key) {
        try {
            return field.get(key);
        } catch (IllegalAccessException exception) {
            throw new AccessibleLackError("getting field value by reflect, error: " + exception.getMessage(),exception);
        }
    }

    public void set(Object key, Object value) {
        try {
            field.set(key, value);
        } catch (IllegalAccessException exception) {
            throw new AccessibleLackError("setting field value by reflect, error: " + exception.getMessage(),exception);
        }
    }
}
