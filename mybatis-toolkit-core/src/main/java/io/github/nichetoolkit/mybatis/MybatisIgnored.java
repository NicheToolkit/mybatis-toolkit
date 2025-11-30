package io.github.nichetoolkit.mybatis;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <code>MybatisIgnored</code>
 * <p>The mybatis ignored class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.Setter
 * @see lombok.Getter
 * @see lombok.experimental.Accessors
 * @since Jdk17
 */
@Setter
@Getter
@Accessors(fluent = true)
public class MybatisIgnored {
    /**
     * <code>fields</code>
     * {@link java.util.List} <p>The <code>fields</code> field.</p>
     * @see java.util.List
     */
    private List<String> fields;
    /**
     * <code>fieldTypes</code>
     * {@link java.util.List} <p>The <code>fieldTypes</code> field.</p>
     * @see java.util.List
     */
    private List<Class<?>> fieldTypes;
    /**
     * <code>superClasses</code>
     * {@link java.util.List} <p>The <code>superClasses</code> field.</p>
     * @see java.util.List
     */
    private List<Class<?>> superClasses;


    /**
     * <code>isIgnoreSuperClass</code>
     * <p>The is ignore super class method.</p>
     * @param superClass {@link java.lang.Class} <p>The super class parameter is <code>Class</code> type.</p>
     * @return boolean <p>The is ignore super class return object is <code>boolean</code> type.</p>
     * @see java.lang.Class
     */
    public boolean isIgnoreSuperClass(Class<?> superClass) {
        if (this.superClasses != null) {
            for (Class<?> clazz : this.superClasses) {
                if (clazz == superClass) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <code>isIgnoreField</code>
     * <p>The is ignore field method.</p>
     * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @return boolean <p>The is ignore field return object is <code>boolean</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    public boolean isIgnoreField(MybatisField field) {
        if (this.fieldTypes != null) {
            Class<?> fieldType = field.fieldType();
            for (Class<?> clazz : this.fieldTypes) {
                if (clazz == fieldType) {
                    return true;
                }
            }
        }
        if (this.fields != null) {
            String fieldName = field.fieldName();
            for (String ignoreField : this.fields) {
                if (ignoreField.equals(fieldName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
