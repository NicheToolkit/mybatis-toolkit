package io.github.nichetoolkit.mybatis.stereotype;

import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.mybatis.stereotype.table.*;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Indexed

@RestEntity
@RestTableStyle
@RestIgnores
@RestResultMap
@RestProperties
@RestExcludes
@RestUnionKeys
@RestLinkKeys
@RestUniqueKeys
@RestAlertKeys
public @interface RestTable {
    @AliasFor(annotation = RestEntity.class, attribute = "name")
    String value() default "";

    @AliasFor(annotation = RestEntity.class, attribute = "comment")
    String comment() default "";

    @AliasFor(annotation = RestEntity.class, attribute = "alias")
    String alias() default "";

    @AliasFor(annotation = RestEntity.class, attribute = "entityType")
    Class<?> entityType() default Object.class;

    @AliasFor(annotation = RestEntity.class, attribute = "identityType")
    Class<?> identityType() default Object.class;

    @AliasFor(annotation = RestEntity.class, attribute = "linkageType")
    Class<?> linkageType() default Object.class;

    @AliasFor(annotation = RestEntity.class, attribute = "alertnessType")
    Class<?> alertnessType() default Object.class;

    @AliasFor(annotation = RestTableStyle.class, attribute = "catalog")
    String catalog() default "";

    @AliasFor(annotation = RestTableStyle.class, attribute = "schema")
    String schema() default "";

    @AliasFor(annotation = RestTableStyle.class, attribute = "name")
    String styleName() default "";

    @AliasFor(annotation = RestTableStyle.class, attribute = "type")
    StyleType styleType() default StyleType.LOWER_UNDERLINE;

    @AliasFor(annotation = RestUnionKeys.class, attribute = "value")
    String[] unionKeys() default {};

    @AliasFor(annotation = RestAlertKeys.class, attribute = "value")
    String[] alertKeys() default {};

    @AliasFor(annotation = RestLinkKeys.class, attribute = "value")
    String[] linkKeys() default {};

    @AliasFor(annotation = RestUniqueKeys.class, attribute = "value")
    String[] uniqueKeys() default {};

    @AliasFor(annotation = RestExcludes.class, attribute = "fields")
    String[] excludeFields() default {};

    @AliasFor(annotation = RestExcludes.class, attribute = "fieldTypes")
    Class<?>[] excludeFieldTypes() default {};

    @AliasFor(annotation = RestExcludes.class, attribute = "superClasses")
    Class<?>[] excludeSuperClasses() default {};

    @AliasFor(annotation = RestIgnores.class, attribute = "fields")
    String[] ignoreFields() default {};

    @AliasFor(annotation = RestIgnores.class, attribute = "fieldTypes")
    Class<?>[] ignoreFieldTypes() default {};

    @AliasFor(annotation = RestIgnores.class, attribute = "superClasses")
    Class<?>[] ignoreSuperClasses() default {};

    @AliasFor(annotation = RestResultMap.class, attribute = "name")
    String resultMap() default "";

    @AliasFor(annotation = RestResultMap.class, attribute = "autoResultMap")
    boolean autoResultMap() default true;

    @AliasFor(annotation = RestProperties.class, attribute = "properties")
    RestProperty[] properties() default {};
}
