package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.error.lack.AccessibleLackError;
import io.github.nichetoolkit.rest.reflect.GenericTypeResolver;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;

@Data
@Accessors(fluent = true)
public class MybatisField {
    protected Class<?> entity;
    protected Class<?> identity;
    protected Field field;

    public MybatisField() {
    }

    public MybatisField(Class<?> entity, Field field) {
        this.entity = entity;
        this.field = field;
        this.field.setAccessible(true);
    }

    public MybatisField(Class<?> entity, Class<?> identity, Field field) {
        this.entity = entity;
        this.identity = identity;
        this.field = field;
        this.field.setAccessible(true);
    }

    public boolean isIdentityField() {
        return Optional.ofNullable(this.identity).isPresent();
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

    public Object get(Object target) {
        try {
            return field.get(target);
        } catch (IllegalAccessException exception) {
            throw new AccessibleLackError("getting field value by reflect, error: " + exception.getMessage(), exception);
        }
    }

    public void set(Object target, Object value) {
        try {
            field.set(target, value);
        } catch (IllegalAccessException exception) {
            throw new AccessibleLackError("setting field value by reflect, error: " + exception.getMessage(), exception);
        }
    }
}
