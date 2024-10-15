package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.MybatisContextHolder;
import io.github.nichetoolkit.mybatis.MybatisTableStyle;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.MybatisTableFactory;
import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.mybatis.stereotype.table.RestProperties;
import io.github.nichetoolkit.mybatis.stereotype.table.*;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class DefaultTableFactory implements MybatisTableFactory {

    private final MybatisTableProperties tableProperties;

    public DefaultTableFactory() {
        this.tableProperties = MybatisContextHolder.tableProperties();
    }

    @Override
    public boolean supports(@NonNull Class<?> entityType) {
        return entityType.isAnnotationPresent(RestEntity.class);
    }

    @Override
    public MybatisTable createTable(@NonNull Class<?> entityType, @Nullable Class<?> identityType, @Nullable Class<?> linkageType, @Nullable Class<?> alertnessType, Chain chain) {
        RestEntity restEntity = AnnotationUtils.getAnnotation(entityType, RestEntity.class);
        Class<?> entityClazz = Optional.ofNullable(restEntity).map(RestEntity::entityType).orElse(null);
        String tableName = Optional.ofNullable(restEntity).map(RestEntity::name).orElse(null);
        String tableComment = Optional.ofNullable(restEntity).map(RestEntity::comment).orElse(null);
        String tableAlias = Optional.ofNullable(restEntity).map(RestEntity::alias).orElse(null);
        MybatisTable mybatisTable;
        if (GeneralUtils.isNotEmpty(entityClazz) && entityClazz != Object.class) {
            mybatisTable = MybatisTable.of(entityClazz, identityType, linkageType, alertnessType, tableProperties.getProperties());
        } else {
            mybatisTable = MybatisTable.of(entityType, identityType, linkageType, alertnessType, tableProperties.getProperties());
        }
        mybatisTable.setComment(tableComment);
        restUniqueKeys(entityType, mybatisTable);
        restUnionKeys(entityType, mybatisTable);
        restLinkKeys(entityType, mybatisTable);
        restAlertKeys(entityType, mybatisTable);
        MybatisTableStyle mybatisStyle = restStyle(entityType, mybatisTable);
        mybatisTable.setStyleName(mybatisStyle.getStyleName());
        tableAlias = GeneralUtils.isEmpty(tableAlias) ? (GeneralUtils.isEmpty(tableName) ? mybatisStyle.tableAlias(entityType) : GeneralUtils.abbreviate(tableName)) : restEntity.alias();
        mybatisTable.setAlias(tableAlias);
        tableName = GeneralUtils.isEmpty(tableName) ? mybatisStyle.tableName(entityType) : restEntity.name();
        mybatisTable.setTable(tableName);
        restResultMap(entityType, mybatisTable);
        restProperties(entityType, mybatisTable);
        restExcludes(entityType, mybatisTable);
        restIgnores(entityType, mybatisTable);
        return mybatisTable;
    }

    public void restUniqueKeys(Class<?> clazz, MybatisTable mybatisTable) {
        /* restUniqueKeys 注解处理 */
        RestUniqueKeys restUniqueKeys = AnnotationUtils.getAnnotation(clazz, RestUniqueKeys.class);
        if (GeneralUtils.isNotEmpty(restUniqueKeys) && GeneralUtils.isNotEmpty(restUniqueKeys.value())) {
            mybatisTable.setUniqueKeys(Arrays.asList(restUniqueKeys.value()));
        }
    }

    public void restUnionKeys(Class<?> clazz, MybatisTable mybatisTable) {
        /* restUnionKeys 注解处理 */
        RestUnionKeys restUnionKeys = AnnotationUtils.getAnnotation(clazz, RestUnionKeys.class);
        if (GeneralUtils.isNotEmpty(restUnionKeys) && GeneralUtils.isNotEmpty(restUnionKeys.value())) {
            mybatisTable.setUnionKeys(Arrays.asList(restUnionKeys.value()));
        }
    }

    public void restLinkKeys(Class<?> clazz, MybatisTable mybatisTable) {
        /* restLinkKeys 注解处理 */
        RestLinkKeys restLinkKeys = AnnotationUtils.getAnnotation(clazz, RestLinkKeys.class);
        if (GeneralUtils.isNotEmpty(restLinkKeys) && GeneralUtils.isNotEmpty(restLinkKeys.value())) {
            mybatisTable.setLinkKeys(Arrays.asList(restLinkKeys.value()));
        }
    }

    public void restAlertKeys(Class<?> clazz, MybatisTable mybatisTable) {
        /* restAlertKeys 注解处理 */
        RestAlertKeys restAlertKeys = AnnotationUtils.getAnnotation(clazz, RestAlertKeys.class);
        if (GeneralUtils.isNotEmpty(restAlertKeys) && GeneralUtils.isNotEmpty(restAlertKeys.value())) {
            mybatisTable.setAlertKeys(Arrays.asList(restAlertKeys.value()));
        }
    }

    public MybatisTableStyle restStyle(Class<?> clazz, MybatisTable mybatisTable) {
        /* restStyle 注解处理 */
        MybatisTableStyle mybatisStyle;
        RestTableStyle restStyle = AnnotationUtils.getAnnotation(clazz, RestTableStyle.class);
        if (GeneralUtils.isNotEmpty(restStyle)) {
            String styleName = restStyle.name();
            StyleType styleType = restStyle.type();
            String catalog = restStyle.catalog();
            String schema = restStyle.schema();
            mybatisTable.setCatalog(GeneralUtils.isEmpty(catalog) ? tableProperties.getCatalog() : catalog);
            mybatisTable.setSchema(GeneralUtils.isEmpty(schema) ? tableProperties.getSchema() : schema);
            if (GeneralUtils.isNotEmpty(styleName)) {
                mybatisStyle = MybatisTableStyle.style(styleName);
            } else if (GeneralUtils.isNotEmpty(styleType)) {
                mybatisTable.setStyleName(styleType.getKey());
                mybatisStyle = MybatisTableStyle.style(styleType);
            } else {
                StyleType defaultStyleType = tableProperties.getStyleType();
                mybatisStyle = MybatisTableStyle.style(defaultStyleType);
            }
        } else {
            StyleType defaultStyleType = tableProperties.getStyleType();
            mybatisStyle = MybatisTableStyle.style(defaultStyleType);
        }
        return mybatisStyle;
    }

    public void restResultMap(Class<?> clazz, MybatisTable mybatisTable) {
        /* restResultMap 注解处理 */
        RestResultMap restResultMap = AnnotationUtils.getAnnotation(clazz, RestResultMap.class);
        if (GeneralUtils.isNotEmpty(restResultMap)) {
            mybatisTable.setResultMap(restResultMap.name());
            mybatisTable.setAutoResultMap(restResultMap.autoResultMap());
        }
    }

    public void restProperties(Class<?> clazz, MybatisTable mybatisTable) {
        /* restResultMap 注解处理 */
        RestProperties restProperties = AnnotationUtils.getAnnotation(clazz, RestProperties.class);
        if (GeneralUtils.isNotEmpty(restProperties) && GeneralUtils.isNotEmpty(restProperties.properties())) {
            mybatisTable.setProperties(restProperties.properties());
        }
        Map<String, String> properties = tableProperties.getProperties();
        if (GeneralUtils.isNotEmpty(properties)) {
            mybatisTable.setProperties(properties);
        }
    }

    public void restExcludes(Class<?> clazz, MybatisTable mybatisTable) {
        /* restIgnores 注解处理 */
        RestExcludes restExcludes = AnnotationUtils.getAnnotation(clazz, RestExcludes.class);
        if (GeneralUtils.isNotEmpty(restExcludes)) {
            if (GeneralUtils.isNotEmpty(restExcludes.fields())) {
                mybatisTable.setExcludeFields(Arrays.asList(restExcludes.fields()));
            }
            if (GeneralUtils.isNotEmpty(restExcludes.fieldTypes())) {
                mybatisTable.setExcludeFieldTypes(Arrays.asList(restExcludes.fieldTypes()));
            }
            if (GeneralUtils.isNotEmpty(restExcludes.superClasses())) {
                mybatisTable.setExcludeSuperClasses(Arrays.asList(restExcludes.superClasses()));
            }
        }
    }

    public void restIgnores(Class<?> clazz, MybatisTable mybatisTable) {
        /* restIgnores 注解处理 */
        RestIgnores restIgnores = AnnotationUtils.getAnnotation(clazz, RestIgnores.class);
        if (GeneralUtils.isNotEmpty(restIgnores)) {
            if (GeneralUtils.isNotEmpty(restIgnores.fields())) {
                mybatisTable.setIgnoreFields(Arrays.asList(restIgnores.fields()));
            }
            if (GeneralUtils.isNotEmpty(restIgnores.fieldTypes())) {
                mybatisTable.setIgnoreFieldTypes(Arrays.asList(restIgnores.fieldTypes()));
            }
            if (GeneralUtils.isNotEmpty(restIgnores.superClasses())) {
                mybatisTable.setIgnoreSuperClasses(Arrays.asList(restIgnores.superClasses()));
            }
        }
    }
}
