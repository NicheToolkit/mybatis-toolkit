package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.MybatisStyle;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.MybatisTableFactory;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.mybatis.helper.MybatisHelper;
import io.github.nichetoolkit.mybatis.stereotype.RestProperties;
import io.github.nichetoolkit.mybatis.stereotype.table.*;
import io.github.nichetoolkit.rest.util.GeneralUtils;

import java.util.Map;

/**
 * <p>DefaultMybatisTableFactory</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class DefaultTableFactory implements MybatisTableFactory {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAnnotationPresent(RestEntity.class);
    }

    @Override
    public MybatisTable createTable(Class<?> clazz, Chain chain) {
        RestEntity restEntity = clazz.getAnnotation(RestEntity.class);
        Class<?> entity = restEntity.entity();
        MybatisTable mybatisTable;
        if (GeneralUtils.isNotEmpty(entity)) {
            mybatisTable = MybatisTable.of(entity);
        } else {
            mybatisTable = MybatisTable.of(clazz);
        }
        restIdentities(clazz, mybatisTable);
        MybatisStyle mybatisStyle = restStyle(clazz, mybatisTable);
        mybatisTable.styleName(mybatisStyle.getStyleName());
        mybatisTable.table(GeneralUtils.isEmpty(restEntity.name()) ? mybatisStyle.tableName(clazz) : restEntity.name());
        restResultMap(clazz, mybatisTable);
        restProperties(clazz, mybatisTable);
        restExcludes(clazz, mybatisTable);
        return mybatisTable;
    }

    public void restIdentities(Class<?> clazz, MybatisTable mybatisTable) {
        /** restIdentities 注解处理 */
        RestIdentities restIdentities = clazz.getAnnotation(RestIdentities.class);
        if (GeneralUtils.isNotEmpty(restIdentities)) {
            String[] identities = restIdentities.identities();
            boolean unionIdentity = restIdentities.unionIdentity();
            mybatisTable.identities(identities).unionIdentity(unionIdentity);
        }
    }

    public MybatisStyle restStyle(Class<?> clazz, MybatisTable mybatisTable) {
        /** restStyle 注解处理 */
        MybatisStyle mybatisStyle;
        RestStyle restStyle = clazz.getAnnotation(RestStyle.class);
        if (GeneralUtils.isNotEmpty(restStyle)) {
            String styleName = restStyle.name();
            StyleType styleType = restStyle.type();
            String catalog = restStyle.catalog();
            String schema = restStyle.schema();
            mybatisTable.catalog(GeneralUtils.isEmpty(catalog) ? MybatisHelper.getTableProperties().getCatalog() : catalog);
            mybatisTable.schema(GeneralUtils.isEmpty(schema) ? MybatisHelper.getTableProperties().getSchema() : schema);
            if (GeneralUtils.isNotEmpty(styleName)) {
                mybatisStyle = MybatisStyle.style(styleName);
            } else if (GeneralUtils.isNotEmpty(styleType)) {
                mybatisTable.styleName(styleType.getKey());
                mybatisStyle = MybatisStyle.style(styleType);
            } else {
                StyleType defaultStyleType = MybatisHelper.getTableProperties().getStyleType();
                mybatisStyle = MybatisStyle.style(defaultStyleType);
            }
        } else {
            StyleType defaultStyleType = MybatisHelper.getTableProperties().getStyleType();
            mybatisStyle = MybatisStyle.style(defaultStyleType);
        }
        return mybatisStyle;
    }

    public void restResultMap(Class<?> clazz, MybatisTable mybatisTable) {
        /** restResultMap 注解处理 */
        RestResultMap restResultMap = clazz.getAnnotation(RestResultMap.class);
        if (GeneralUtils.isNotEmpty(restResultMap)) {
            mybatisTable.resultMap(restResultMap.name()).autoResultMap(restResultMap.autoResultMap());
        }
    }

    public void restProperties(Class<?> clazz, MybatisTable mybatisTable) {
        /** restResultMap 注解处理 */
        RestProperties restProperties = clazz.getAnnotation(RestProperties.class);
        if (GeneralUtils.isNotEmpty(restProperties) && GeneralUtils.isNotEmpty(restProperties.properties())) {
            mybatisTable.setProperties(restProperties.properties());
        }
        Map<String, String> properties = MybatisHelper.getTableProperties().getEntity().getProperties();
        if (GeneralUtils.isNotEmpty(properties)) {
            mybatisTable.setProperties(properties);
        }
    }

    public void restExcludes(Class<?> clazz, MybatisTable mybatisTable) {
        /** restExcludes 注解处理 */
        RestExcludes restExcludes = clazz.getAnnotation(RestExcludes.class);
        if (GeneralUtils.isNotEmpty(restExcludes)) {
            String[] fields = restExcludes.fields();
            mybatisTable.excludeFields(fields);
            Class<?>[] classes = restExcludes.fieldTypes();
            mybatisTable.excludeFieldTypes(classes);
            Class<?>[] superClasses = restExcludes.superClasses();
            mybatisTable.excludeSuperClasses(superClasses);
        }
    }
}
