package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.MybatisTableStyle;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.MybatisTableFactory;
import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.rest.util.ContextUtils;
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

/**
 * <code>DefaultTableFactory</code>
 * <p>The type default table factory class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisTableFactory
 * @since Jdk1.8
 */
public class DefaultTableFactory implements MybatisTableFactory {

    private final MybatisTableProperties tableProperties;

    /**
     * <code>DefaultTableFactory</code>
     * Instantiates a new default table factory.
     */
    public DefaultTableFactory() {
        this.tableProperties = ContextUtils.getBean(MybatisTableProperties.class);
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
        mybatisTable.setAutoResultMap(true);
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

    /**
     * <code>restUniqueKeys</code>
     * <p>the unique keys method.</p>
     * @param clazz        {@link java.lang.Class} <p>the clazz parameter is <code>Class</code> type.</p>
     * @param mybatisTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the mybatis table parameter is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    public void restUniqueKeys(Class<?> clazz, MybatisTable mybatisTable) {
        /* restUniqueKeys 注解处理 */
        RestUniqueKeys restUniqueKeys = AnnotationUtils.getAnnotation(clazz, RestUniqueKeys.class);
        if (GeneralUtils.isNotEmpty(restUniqueKeys) && GeneralUtils.isNotEmpty(restUniqueKeys.value())) {
            mybatisTable.setUniqueKeys(Arrays.asList(restUniqueKeys.value()));
        }
    }

    /**
     * <code>restUnionKeys</code>
     * <p>the union keys method.</p>
     * @param clazz        {@link java.lang.Class} <p>the clazz parameter is <code>Class</code> type.</p>
     * @param mybatisTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the mybatis table parameter is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    public void restUnionKeys(Class<?> clazz, MybatisTable mybatisTable) {
        /* restUnionKeys 注解处理 */
        RestUnionKeys restUnionKeys = AnnotationUtils.getAnnotation(clazz, RestUnionKeys.class);
        if (GeneralUtils.isNotEmpty(restUnionKeys) && GeneralUtils.isNotEmpty(restUnionKeys.value())) {
            mybatisTable.setUnionKeys(Arrays.asList(restUnionKeys.value()));
        }
    }

    /**
     * <code>restLinkKeys</code>
     * <p>the link keys method.</p>
     * @param clazz        {@link java.lang.Class} <p>the clazz parameter is <code>Class</code> type.</p>
     * @param mybatisTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the mybatis table parameter is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    public void restLinkKeys(Class<?> clazz, MybatisTable mybatisTable) {
        /* restLinkKeys 注解处理 */
        RestLinkKeys restLinkKeys = AnnotationUtils.getAnnotation(clazz, RestLinkKeys.class);
        if (GeneralUtils.isNotEmpty(restLinkKeys) && GeneralUtils.isNotEmpty(restLinkKeys.value())) {
            mybatisTable.setLinkKeys(Arrays.asList(restLinkKeys.value()));
        }
    }

    /**
     * <code>restAlertKeys</code>
     * <p>the alert keys method.</p>
     * @param clazz        {@link java.lang.Class} <p>the clazz parameter is <code>Class</code> type.</p>
     * @param mybatisTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the mybatis table parameter is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    public void restAlertKeys(Class<?> clazz, MybatisTable mybatisTable) {
        /* restAlertKeys 注解处理 */
        RestAlertKeys restAlertKeys = AnnotationUtils.getAnnotation(clazz, RestAlertKeys.class);
        if (GeneralUtils.isNotEmpty(restAlertKeys) && GeneralUtils.isNotEmpty(restAlertKeys.value())) {
            mybatisTable.setAlertKeys(Arrays.asList(restAlertKeys.value()));
        }
    }

    /**
     * <code>restStyle</code>
     * <p>the style method.</p>
     * @param clazz        {@link java.lang.Class} <p>the clazz parameter is <code>Class</code> type.</p>
     * @param mybatisTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the mybatis table parameter is <code>MybatisTable</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTableStyle} <p>the style return object is <code>MybatisTableStyle</code> type.</p>
     * @see java.lang.Class
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see io.github.nichetoolkit.mybatis.MybatisTableStyle
     */
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

    /**
     * <code>restResultMap</code>
     * <p>the result map method.</p>
     * @param clazz        {@link java.lang.Class} <p>the clazz parameter is <code>Class</code> type.</p>
     * @param mybatisTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the mybatis table parameter is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    public void restResultMap(Class<?> clazz, MybatisTable mybatisTable) {
        /* restResultMap 注解处理 */
        RestResultMap restResultMap = AnnotationUtils.getAnnotation(clazz, RestResultMap.class);
        if (GeneralUtils.isNotEmpty(restResultMap)) {
            mybatisTable.setResultMap(restResultMap.name());
            mybatisTable.setAutoResultMap(restResultMap.autoResultMap());
        }
    }

    /**
     * <code>restProperties</code>
     * <p>the properties method.</p>
     * @param clazz        {@link java.lang.Class} <p>the clazz parameter is <code>Class</code> type.</p>
     * @param mybatisTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the mybatis table parameter is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
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

    /**
     * <code>restExcludes</code>
     * <p>the excludes method.</p>
     * @param clazz        {@link java.lang.Class} <p>the clazz parameter is <code>Class</code> type.</p>
     * @param mybatisTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the mybatis table parameter is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
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

    /**
     * <code>restIgnores</code>
     * <p>the ignores method.</p>
     * @param clazz        {@link java.lang.Class} <p>the clazz parameter is <code>Class</code> type.</p>
     * @param mybatisTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the mybatis table parameter is <code>MybatisTable</code> type.</p>
     * @see java.lang.Class
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
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
