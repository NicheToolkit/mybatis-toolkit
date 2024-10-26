package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.error.lack.AccessibleLackError;
import io.github.nichetoolkit.rest.reflect.RestGenericTypes;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * <code>MybatisField</code>
 * <p>The mybatis field class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.Setter
 * @see lombok.Getter
 * @see lombok.experimental.Accessors
 * @since Jdk1.8
 */
@Setter
@Getter
@Accessors(fluent = true)
public class MybatisField {
    /**
     * <code>entityType</code>
     * {@link java.lang.Class} <p>The <code>entityType</code> field.</p>
     * @see java.lang.Class
     */
    protected Class<?> entityType;
    /**
     * <code>parentField</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The <code>parentField</code> field.</p>
     */
    protected MybatisField parentField;
    /**
     * <code>field</code>
     * {@link java.lang.reflect.Field} <p>The <code>field</code> field.</p>
     * @see java.lang.reflect.Field
     */
    protected Field field;
    /**
     * <code>isIdentity</code>
     * <p>The <code>isIdentity</code> field.</p>
     */
    protected boolean isIdentity = false;
    /**
     * <code>isLinkage</code>
     * <p>The <code>isLinkage</code> field.</p>
     */
    protected boolean isLinkage = false;
    /**
     * <code>isAlertness</code>
     * <p>The <code>isAlertness</code> field.</p>
     */
    protected boolean isAlertness = false;
    /**
     * <code>ignored</code>
     * <p>The <code>ignored</code> field.</p>
     */
    protected boolean ignored = false;

    /**
     * <code>MybatisField</code>
     * <p>Instantiates a new mybatis field.</p>
     */
    public MybatisField() {
    }

    /**
     * <code>MybatisField</code>
     * <p>Instantiates a new mybatis field.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param field      {@link java.lang.reflect.Field} <p>The field parameter is <code>Field</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.reflect.Field
     */
    public MybatisField(Class<?> entityType, Field field) {
        this.entityType = entityType;
        this.field = field;
        this.field.setAccessible(true);
    }

    /**
     * <code>MybatisField</code>
     * <p>Instantiates a new mybatis field.</p>
     * @param entityType  {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param parentField {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The parent field parameter is <code>MybatisField</code> type.</p>
     * @param field       {@link java.lang.reflect.Field} <p>The field parameter is <code>Field</code> type.</p>
     * @param isIdentity  boolean <p>The is identity parameter is <code>boolean</code> type.</p>
     * @param isLinkage   boolean <p>The is linkage parameter is <code>boolean</code> type.</p>
     * @param isAlertness boolean <p>The is alertness parameter is <code>boolean</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.reflect.Field
     */
    public MybatisField(Class<?> entityType, MybatisField parentField, Field field, boolean isIdentity, boolean isLinkage, boolean isAlertness) {
        this.entityType = entityType;
        this.parentField = parentField;
        this.field = field;
        this.field.setAccessible(true);
        this.isIdentity = isIdentity;
        this.isLinkage = isLinkage;
        this.isAlertness = isAlertness;
    }

    /**
     * <code>isParentNotEmpty</code>
     * <p>The is parent not empty method.</p>
     * @return boolean <p>The is parent not empty return object is <code>boolean</code> type.</p>
     */
    public boolean isParentNotEmpty() {
        return GeneralUtils.isNotEmpty(this.parentField);
    }

    /**
     * <code>prefixOfParent</code>
     * <p>The prefix of parent method.</p>
     * @return {@link java.lang.String} <p>The prefix of parent return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String prefixOfParent() {
        return this.parentField.fieldName();
    }

    /**
     * <code>declaringClass</code>
     * <p>The declaring class method.</p>
     * @return {@link java.lang.Class} <p>The declaring class return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    public Class<?> declaringClass() {
        return field.getDeclaringClass();
    }

    /**
     * <code>fieldName</code>
     * <p>The field name method.</p>
     * @return {@link java.lang.String} <p>The field name return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String fieldName() {
        return field.getName();
    }

    /**
     * <code>fieldType</code>
     * <p>The field type method.</p>
     * @return {@link java.lang.Class} <p>The field type return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    public Class<?> fieldType() {
        return RestGenericTypes.resolveFieldClass(field, entityType);
    }

    /**
     * <code>getAnnotation</code>
     * <p>The get annotation getter method.</p>
     * @param <T>             {@link java.lang.annotation.Annotation} <p>The generic parameter is <code>Annotation</code> type.</p>
     * @param annotationClass {@link java.lang.Class} <p>The annotation class parameter is <code>Class</code> type.</p>
     * @return T <p>The get annotation return object is <code>T</code> type.</p>
     * @see java.lang.annotation.Annotation
     * @see java.lang.Class
     */
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return AnnotationUtils.getAnnotation(this.field, annotationClass);
    }

    /**
     * <code>getAnnotations</code>
     * <p>The get annotations method.</p>
     * @return {@link java.lang.annotation.Annotation} <p>The get annotations return object is <code>Annotation</code> type.</p>
     * @see java.lang.annotation.Annotation
     */
    public Annotation[] getAnnotations() {
        return field.getAnnotations();
    }

    /**
     * <code>get</code>
     * <p>The get method.</p>
     * @param target {@link java.lang.Object} <p>The target parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.Object} <p>The get return object is <code>Object</code> type.</p>
     * @see java.lang.Object
     */
    public Object get(Object target) {
        try {
            return field.get(target);
        } catch (IllegalAccessException exception) {
            throw new AccessibleLackError("getting field value by reflect, error: " + exception.getMessage(), exception);
        }
    }

    /**
     * <code>set</code>
     * <p>The set method.</p>
     * @param target {@link java.lang.Object} <p>The target parameter is <code>Object</code> type.</p>
     * @param value  {@link java.lang.Object} <p>The value parameter is <code>Object</code> type.</p>
     * @see java.lang.Object
     */
    public void set(Object target, Object value) {
        try {
            field.set(target, value);
        } catch (IllegalAccessException exception) {
            throw new AccessibleLackError("setting field value by reflect, error: " + exception.getMessage(), exception);
        }
    }
}
