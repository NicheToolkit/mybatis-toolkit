package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.builder.SqlBuilder;
import io.github.nichetoolkit.mybatis.fickle.FickleField;
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
 * @see  lombok.Setter
 * @see  lombok.Getter
 * @see  lombok.experimental.Accessors
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Setter
@Getter
@Accessors(fluent = true)
public class MybatisField {
    /**
     * <code>entityType</code>
     * {@link java.lang.Class} <p>The <code>entityType</code> field.</p>
     * @see  java.lang.Class
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
     * @see  java.lang.reflect.Field
     */
    protected Field field;

    /**
     * <code>fickleField</code>
     * {@link io.github.nichetoolkit.mybatis.fickle.FickleField} <p>The <code>fickleField</code> field.</p>
     * @see  io.github.nichetoolkit.mybatis.fickle.FickleField
     */
    protected FickleField<?> fickleField;
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
     * <code>isFickleness</code>
     * <p>The <code>isFickleness</code> field.</p>
     */
    protected boolean isFickleness = false;

    /**
     * <code>isFickleField</code>
     * <p>The <code>isFickleField</code> field.</p>
     */
    protected boolean isFickleField = false;
    /**
     * <code>ignored</code>
     * <p>The <code>ignored</code> field.</p>
     */
    protected boolean ignored = false;

    /**
     * <code>selectIgnored</code>
     * <p>The <code>selectIgnored</code> field.</p>
     */
    protected boolean selectIgnored = false;

    /**
     * <code>insetIgnored</code>
     * <p>The <code>insetIgnored</code> field.</p>
     */
    protected boolean insetIgnored = false;

    /**
     * <code>updateIgnored</code>
     * <p>The <code>updateIgnored</code> field.</p>
     */
    protected boolean updateIgnored = false;

    /**
     * <code>MybatisField</code>
     * <p>Instantiates a new mybatis field.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param field {@link java.lang.reflect.Field} <p>The field parameter is <code>Field</code> type.</p>
     * @see  java.lang.Class
     * @see  java.lang.reflect.Field
     */
    private MybatisField(Class<?> entityType, Field field) {
        this.entityType = entityType;
        this.field = field;
        this.field.setAccessible(true);
    }

    /**
     * <code>MybatisField</code>
     * <p>Instantiates a new mybatis field.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param parentField {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The parent field parameter is <code>MybatisField</code> type.</p>
     * @param fickleField {@link io.github.nichetoolkit.mybatis.fickle.FickleField} <p>The fickle field parameter is <code>FickleField</code> type.</p>
     * @param isFickleField boolean <p>The is fickle field parameter is <code>boolean</code> type.</p>
     * @see  java.lang.Class
     * @see  io.github.nichetoolkit.mybatis.fickle.FickleField
     */
    private MybatisField(Class<?> entityType, MybatisField parentField, FickleField<?> fickleField, boolean isFickleField) {
        this.entityType = entityType;
        this.parentField = parentField;
        this.fickleField = fickleField;
        this.isFickleField = isFickleField;
    }

    /**
     * <code>MybatisField</code>
     * <p>Instantiates a new mybatis field.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param parentField {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The parent field parameter is <code>MybatisField</code> type.</p>
     * @param field {@link java.lang.reflect.Field} <p>The field parameter is <code>Field</code> type.</p>
     * @param isIdentity boolean <p>The is identity parameter is <code>boolean</code> type.</p>
     * @param isLinkage boolean <p>The is linkage parameter is <code>boolean</code> type.</p>
     * @param isAlertness boolean <p>The is alertness parameter is <code>boolean</code> type.</p>
     * @param isFickleness boolean <p>The is fickleness parameter is <code>boolean</code> type.</p>
     * @see  java.lang.Class
     * @see  java.lang.reflect.Field
     */
    private MybatisField(Class<?> entityType, MybatisField parentField, Field field, boolean isIdentity, boolean isLinkage, boolean isAlertness, boolean isFickleness) {
        this.entityType = entityType;
        this.parentField = parentField;
        this.field = field;
        this.field.setAccessible(true);
        this.isIdentity = isIdentity;
        this.isLinkage = isLinkage;
        this.isAlertness = isAlertness;
        this.isFickleness = isFickleness;
    }

    /**
     * <code>of</code>
     * <p>The of method.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param field {@link java.lang.reflect.Field} <p>The field parameter is <code>Field</code> type.</p>
     * @see  java.lang.Class
     * @see  java.lang.reflect.Field
     * @return  {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The of return object is <code>MybatisField</code> type.</p>
     */
    protected static MybatisField of(Class<?> entityType, Field field) {
        return new MybatisField(entityType, field);
    }

    /**
     * <code>of</code>
     * <p>The of method.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param parentField {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The parent field parameter is <code>MybatisField</code> type.</p>
     * @param field {@link java.lang.reflect.Field} <p>The field parameter is <code>Field</code> type.</p>
     * @param isIdentity boolean <p>The is identity parameter is <code>boolean</code> type.</p>
     * @param isLinkage boolean <p>The is linkage parameter is <code>boolean</code> type.</p>
     * @param isAlertness boolean <p>The is alertness parameter is <code>boolean</code> type.</p>
     * @param isFickleness boolean <p>The is fickleness parameter is <code>boolean</code> type.</p>
     * @see  java.lang.Class
     * @see  java.lang.reflect.Field
     * @return  {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The of return object is <code>MybatisField</code> type.</p>
     */
    protected static MybatisField of(Class<?> entityType, MybatisField parentField, Field field, boolean isIdentity, boolean isLinkage, boolean isAlertness, boolean isFickleness) {
        return new MybatisField(entityType, parentField, field, isIdentity, isLinkage, isAlertness, isFickleness);
    }

    /**
     * <code>ofIdentity</code>
     * <p>The of identity method.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param parentField {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The parent field parameter is <code>MybatisField</code> type.</p>
     * @param field {@link java.lang.reflect.Field} <p>The field parameter is <code>Field</code> type.</p>
     * @see  java.lang.Class
     * @see  java.lang.reflect.Field
     * @return  {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The of identity return object is <code>MybatisField</code> type.</p>
     */
    protected static MybatisField ofIdentity(Class<?> entityType, MybatisField parentField, Field field) {
        return new MybatisField(entityType, parentField, field, true, false, false, false);
    }

    /**
     * <code>ofLinkage</code>
     * <p>The of linkage method.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param parentField {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The parent field parameter is <code>MybatisField</code> type.</p>
     * @param field {@link java.lang.reflect.Field} <p>The field parameter is <code>Field</code> type.</p>
     * @see  java.lang.Class
     * @see  java.lang.reflect.Field
     * @return  {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The of linkage return object is <code>MybatisField</code> type.</p>
     */
    protected static MybatisField ofLinkage(Class<?> entityType, MybatisField parentField, Field field) {
        return new MybatisField(entityType, parentField, field, false, true, false, false);
    }

    /**
     * <code>ofAlertness</code>
     * <p>The of alertness method.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param parentField {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The parent field parameter is <code>MybatisField</code> type.</p>
     * @param field {@link java.lang.reflect.Field} <p>The field parameter is <code>Field</code> type.</p>
     * @see  java.lang.Class
     * @see  java.lang.reflect.Field
     * @return  {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The of alertness return object is <code>MybatisField</code> type.</p>
     */
    protected static MybatisField ofAlertness(Class<?> entityType, MybatisField parentField, Field field) {
        return new MybatisField(entityType, parentField, field, false, false, true, false);
    }

    /**
     * <code>ofFickleness</code>
     * <p>The of fickleness method.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param field {@link java.lang.reflect.Field} <p>The field parameter is <code>Field</code> type.</p>
     * @see  java.lang.Class
     * @see  java.lang.reflect.Field
     * @return  {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The of fickleness return object is <code>MybatisField</code> type.</p>
     */
    protected static MybatisField ofFickleness(Class<?> entityType, Field field) {
        return new MybatisField(entityType, null, field, false, false, false, true);
    }

    /**
     * <code>ofFickleField</code>
     * <p>The of fickle field method.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param parentField {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The parent field parameter is <code>MybatisField</code> type.</p>
     * @param fickleField {@link io.github.nichetoolkit.mybatis.fickle.FickleField} <p>The fickle field parameter is <code>FickleField</code> type.</p>
     * @see  java.lang.Class
     * @see  io.github.nichetoolkit.mybatis.fickle.FickleField
     * @return  {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The of fickle field return object is <code>MybatisField</code> type.</p>
     */
    protected static MybatisField ofFickleField(Class<?> entityType, MybatisField parentField, FickleField<?> fickleField) {
        return new MybatisField(entityType, parentField, fickleField, true);
    }

    /**
     * <code>ofFickleness</code>
     * <p>The of fickleness method.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @param parentField {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The parent field parameter is <code>MybatisField</code> type.</p>
     * @param field {@link java.lang.reflect.Field} <p>The field parameter is <code>Field</code> type.</p>
     * @see  java.lang.Class
     * @see  java.lang.reflect.Field
     * @return  {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The of fickleness return object is <code>MybatisField</code> type.</p>
     */
    protected static MybatisField ofFickleness(Class<?> entityType, MybatisField parentField, Field field) {
        return new MybatisField(entityType, parentField, field, false, false, false, true);
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
     * @return  {@link java.lang.String} <p>The prefix of parent return object is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public String prefixOfParent() {
        return prefixOfParent(false);
    }

    /**
     * <code>prefixOfParent</code>
     * <p>The prefix of parent method.</p>
     * @param isParent boolean <p>The is parent parameter is <code>boolean</code> type.</p>
     * @return  {@link java.lang.String} <p>The prefix of parent return object is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public String prefixOfParent(boolean isParent) {
        SqlBuilder sqlBuilder = SqlBuilder.sqlBuilder();
        if (this.parentField != null) {
            sqlBuilder.append(this.parentField.prefixOfParent(true));
        }
        if (isParent) {
            sqlBuilder.append(this.fieldName()).period();
        }
        return sqlBuilder.toString();
    }

    /**
     * <code>declaringClass</code>
     * <p>The declaring class method.</p>
     * @return  {@link java.lang.Class} <p>The declaring class return object is <code>Class</code> type.</p>
     * @see  java.lang.Class
     */
    public Class<?> declaringClass() {
        return this.field.getDeclaringClass();
    }

    /**
     * <code>fieldName</code>
     * <p>The field name method.</p>
     * @return  {@link java.lang.String} <p>The field name return object is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public String fieldName() {
        return this.isFickleField ? GeneralUtils.isNotEmpty(this.fickleField.getName()) ? this.fickleField.getName() : this.fickleField.getKey() : this.field.getName();
    }

    /**
     * <code>fieldType</code>
     * <p>The field type method.</p>
     * @return  {@link java.lang.Class} <p>The field type return object is <code>Class</code> type.</p>
     * @see  java.lang.Class
     */
    public Class<?> fieldType() {
        return field.getType();
    }

    /**
     * <code>fieldClass</code>
     * <p>The field class method.</p>
     * @return  {@link java.lang.Class} <p>The field class return object is <code>Class</code> type.</p>
     * @see  java.lang.Class
     */
    public Class<?> fieldClass() {
        return RestGenericTypes.resolveFieldClass(field, entityType);
    }

    /**
     * <code>isAnnotationPresent</code>
     * <p>The is annotation present method.</p>
     * @param <T>  {@link java.lang.annotation.Annotation} <p>The generic parameter is <code>Annotation</code> type.</p>
     * @param annotationClass {@link java.lang.Class} <p>The annotation class parameter is <code>Class</code> type.</p>
     * @see  java.lang.annotation.Annotation
     * @see  java.lang.Class
     * @return boolean <p>The is annotation present return object is <code>boolean</code> type.</p>
     */
    public <T extends Annotation> boolean isAnnotationPresent(Class<T> annotationClass) {
        return GeneralUtils.isNotEmpty(getAnnotation(annotationClass));
    }

    /**
     * <code>getAnnotation</code>
     * <p>The get annotation getter method.</p>
     * @param <T>  {@link java.lang.annotation.Annotation} <p>The generic parameter is <code>Annotation</code> type.</p>
     * @param annotationClass {@link java.lang.Class} <p>The annotation class parameter is <code>Class</code> type.</p>
     * @see  java.lang.annotation.Annotation
     * @see  java.lang.Class
     * @return T <p>The get annotation return object is <code>T</code> type.</p>
     */
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return AnnotationUtils.getAnnotation(this.field, annotationClass);
    }

    /**
     * <code>getAnnotations</code>
     * <p>The get annotations method.</p>
     * @return  {@link java.lang.annotation.Annotation} <p>The get annotations return object is <code>Annotation</code> type.</p>
     * @see  java.lang.annotation.Annotation
     */
    public Annotation[] getAnnotations() {
        return field.getAnnotations();
    }

    /**
     * <code>get</code>
     * <p>The get method.</p>
     * @param target {@link java.lang.Object} <p>The target parameter is <code>Object</code> type.</p>
     * @see  java.lang.Object
     * @return  {@link java.lang.Object} <p>The get return object is <code>Object</code> type.</p>
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
     * @param value {@link java.lang.Object} <p>The value parameter is <code>Object</code> type.</p>
     * @see  java.lang.Object
     */
    public void set(Object target, Object value) {
        try {
            field.set(target, value);
        } catch (IllegalAccessException exception) {
            throw new AccessibleLackError("setting field value by reflect, error: " + exception.getMessage(), exception);
        }
    }
}
