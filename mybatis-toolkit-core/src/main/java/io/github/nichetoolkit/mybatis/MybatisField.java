package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.error.lack.AccessibleLackError;
import io.github.nichetoolkit.rest.reflect.RestGenericTypes;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Setter
@Getter
@Accessors(fluent = true)
public class MybatisField {
    protected Class<?> entityType;
    protected Field field;
    protected boolean isSpecialIdentity = false;
    protected boolean ignored = false;

    public MybatisField() {
    }

    public MybatisField(Class<?> entityType, Field field) {
        this.entityType = entityType;
        this.field = field;
        this.field.setAccessible(true);
    }

    public MybatisField(Class<?> entityType, Class<?> identityType, Field field) {
        this.entityType = entityType;
        this.isSpecialIdentity = identityType != null;
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
        return RestGenericTypes.resolveFieldClass(field, entityType);
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
