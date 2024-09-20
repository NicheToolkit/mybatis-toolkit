package io.github.nichetoolkit.mybatis.stereotype;

import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.mybatis.stereotype.table.*;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * <code>RestTable</code>
 * <p>The type rest table interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.lang.annotation.Annotation
 * @see java.lang.annotation.Retention
 * @see java.lang.annotation.Target
 * @see java.lang.annotation.Documented
 * @see io.github.nichetoolkit.mybatis.stereotype.table.RestEntity
 * @see io.github.nichetoolkit.mybatis.stereotype.table.RestStyle
 * @see io.github.nichetoolkit.mybatis.stereotype.table.RestResultMap
 * @see io.github.nichetoolkit.mybatis.stereotype.RestProperties
 * @see io.github.nichetoolkit.mybatis.stereotype.table.RestExcludes
 * @see io.github.nichetoolkit.mybatis.stereotype.table.RestUnionKeys
 * @see io.github.nichetoolkit.mybatis.stereotype.table.RestLinkKeys
 * @see io.github.nichetoolkit.mybatis.stereotype.table.RestUniqueKeys
 * @see io.github.nichetoolkit.mybatis.stereotype.table.RestAlertKeys
 * @since Jdk1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@RestEntity
@RestStyle
@RestResultMap
@RestProperties
@RestExcludes
@RestUnionKeys
@RestLinkKeys
@RestUniqueKeys
@RestAlertKeys
public @interface RestTable {
    /**
     * <code>value</code>
     * <p>the method.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestEntity.class, attribute = "name")
    String value() default "";

    /**
     * <code>comment</code>
     * <p>the method.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestEntity.class, attribute = "comment")
    String comment() default "";

    /**
     * <code>alias</code>
     * <p>the method.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestEntity.class, attribute = "alias")
    String alias() default "";

    /**
     * <code>entityType</code>
     * <p>the type method.</p>
     * @return {@link java.lang.Class} <p>the type return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestEntity.class, attribute = "entityType")
    Class<?> entityType() default Object.class;

    /**
     * <code>identityType</code>
     * <p>the type method.</p>
     * @return {@link java.lang.Class} <p>the type return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestEntity.class, attribute = "identityType")
    Class<?> identityType() default Object.class;

    /**
     * <code>unionKeys</code>
     * <p>the keys method.</p>
     * @return {@link java.lang.String} <p>the keys return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestUnionKeys.class, attribute = "unionKeys")
    String[] unionKeys() default {};

    /**
     * <code>unionIdentity</code>
     * <p>the identity method.</p>
     * @return boolean <p>the identity return object is <code>boolean</code> type.</p>
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestUnionKeys.class, attribute = "unionIdentity")
    boolean unionIdentity() default false;

    /**
     * <code>alertKeys</code>
     * <p>the keys method.</p>
     * @return {@link java.lang.String} <p>the keys return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestAlertKeys.class, attribute = "alertKeys")
    String[] alertKeys() default {};

    /**
     * <code>linkKeys</code>
     * <p>the keys method.</p>
     * @return {@link java.lang.String} <p>the keys return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestLinkKeys.class, attribute = "linkKeys")
    String[] linkKeys() default {};

    /**
     * <code>uniqueKeys</code>
     * <p>the keys method.</p>
     * @return {@link java.lang.String} <p>the keys return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestUniqueKeys.class, attribute = "uniqueKeys")
    String[] uniqueKeys() default {};

    /**
     * <code>catalog</code>
     * <p>the method.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestStyle.class, attribute = "catalog")
    String catalog() default "";

    /**
     * <code>schema</code>
     * <p>the method.</p>
     * @return {@link java.lang.String} <p>the return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestStyle.class, attribute = "schema")
    String schema() default "";

    /**
     * <code>styleName</code>
     * <p>the name method.</p>
     * @return {@link java.lang.String} <p>the name return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestStyle.class, attribute = "name")
    String styleName() default "";

    /**
     * <code>styleType</code>
     * <p>the type method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.enums.StyleType} <p>the type return object is <code>StyleType</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.StyleType
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestStyle.class, attribute = "type")
    StyleType styleType() default StyleType.LOWER_UNDERLINE;

    /**
     * <code>resultMap</code>
     * <p>the map method.</p>
     * @return {@link java.lang.String} <p>the map return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestResultMap.class, attribute = "name")
    String resultMap() default "";

    /**
     * <code>autoResultMap</code>
     * <p>the result map method.</p>
     * @return boolean <p>the result map return object is <code>boolean</code> type.</p>
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestResultMap.class, attribute = "autoResultMap")
    boolean autoResultMap() default true;

    /**
     * <code>properties</code>
     * <p>the method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.stereotype.RestProperty} <p>the return object is <code>RestProperty</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.stereotype.RestProperty
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestProperties.class, attribute = "properties")
    RestProperty[] properties() default {};

    /**
     * <code>excludeFields</code>
     * <p>the fields method.</p>
     * @return {@link java.lang.String} <p>the fields return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestExcludes.class, attribute = "fields")
    String[] excludeFields() default {};

    /**
     * <code>excludeFieldTypes</code>
     * <p>the field types method.</p>
     * @return {@link java.lang.Class} <p>the field types return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestExcludes.class, attribute = "fieldTypes")
    Class<?>[] excludeFieldTypes() default {};

    /**
     * <code>excludeSuperClasses</code>
     * <p>the super classes method.</p>
     * @return {@link java.lang.Class} <p>the super classes return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     * @see org.springframework.core.annotation.AliasFor
     */
    @AliasFor(annotation = RestExcludes.class, attribute = "superClasses")
    Class<?>[] excludeSuperClasses() default {};
}
