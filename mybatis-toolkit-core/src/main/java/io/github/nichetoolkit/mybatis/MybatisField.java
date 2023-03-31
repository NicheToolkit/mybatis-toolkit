package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.defaults.MybatisGenericTypeResolver;
import io.github.nichetoolkit.rest.error.lack.AccessibleLackError;
import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * <p>MybatisField</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Data
@Accessors(fluent = true)
public class MybatisField {
    /** 实体类型 */
    protected Class<?> clazz;
    /** 字段 */
    protected Field field;

    public MybatisField() {
    }

    public MybatisField(Class<?> clazz, Field field) {
        this.clazz = clazz;
        this.field = field;
        this.field.setAccessible(true);
    }

    public Class<?> getDeclaringClass() {
        return field.getDeclaringClass();
    }

    public String getName() {
        return field.getName();
    }

    public Class<?> getType() {
        return MybatisGenericTypeResolver.resolveFieldClass(field, clazz);
    }

    /**
     * 获取字段上的指定注解信息
     * @param annotationClass 注解类型
     * @return 注解信息
     */
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return field.getAnnotation(annotationClass);
    }

    /**
     * 获取字段上的全部注解信息
     * @return 注解信息
     */
    public Annotation[] getAnnotations() {
        return field.getAnnotations();
    }

    /**
     * 字段上是否配置了指定的注解
     * @param annotationClass 注解类型
     * @return 字段上是否配置了指定的注解
     */
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return field.isAnnotationPresent(annotationClass);
    }

    /**
     * 反射获取字段值
     * @param key 对象
     * @return 字段值
     */
    public Object get(Object key) {
        try {
            return field.get(key);
        } catch (IllegalAccessException exception) {
            throw new AccessibleLackError("getting field value by reflect, error: " + exception.getMessage(),exception);
        }
    }

    /**
     * 反射设置字段值
     * @param key   对象
     * @param value 字段值
     */
    public void set(Object key, Object value) {
        try {
            field.set(key, value);
        } catch (IllegalAccessException exception) {
            throw new AccessibleLackError("setting field value by reflect, error: " + exception.getMessage(),exception);
        }
    }
}
