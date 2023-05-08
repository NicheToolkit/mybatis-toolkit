package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.MybatisStyle;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.MybatisTableFactory;
import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.enums.StyleType;
import io.github.nichetoolkit.rice.stereotype.mybatis.RestProperties;
import io.github.nichetoolkit.rice.stereotype.mybatis.table.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Map;

/**
 * <p>DefaultMybatisTableFactory</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class DefaultTableFactory implements MybatisTableFactory {

    private MybatisTableProperties tableProperties;

    @Autowired
    public DefaultTableFactory(MybatisTableProperties tableProperties) {
        this.tableProperties = tableProperties;
    }

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
            mybatisTable = MybatisTable.of(entity, tableProperties.getProperties());
        } else {
            mybatisTable = MybatisTable.of(clazz, tableProperties.getProperties());
        }
        restUnionKeys(clazz, mybatisTable);
        restLinkKeys(clazz, mybatisTable);
        MybatisStyle mybatisStyle = restStyle(clazz, mybatisTable);
        mybatisTable.setStyleName(mybatisStyle.getStyleName());
        mybatisTable.setTable(GeneralUtils.isEmpty(restEntity.name()) ? mybatisStyle.tableName(clazz) : restEntity.name());
        restResultMap(clazz, mybatisTable);
        restProperties(clazz, mybatisTable);
        restExcludes(clazz, mybatisTable);
        return mybatisTable;
    }

    public void restUnionKeys(Class<?> clazz, MybatisTable mybatisTable) {
        /** restUnionKeys 注解处理 */
        RestUnionKeys restUnionKeys = clazz.getAnnotation(RestUnionKeys.class);
        if (GeneralUtils.isNotEmpty(restUnionKeys)) {
            mybatisTable.setUnionKeys(Arrays.asList(restUnionKeys.unionKeys()));
            mybatisTable.setUnionIdentity(restUnionKeys.unionIdentity());
        }
    }

    public void restLinkKeys(Class<?> clazz, MybatisTable mybatisTable) {
        /** restLinkKeys 注解处理 */
        RestLinkKeys restLinkKeys = clazz.getAnnotation(RestLinkKeys.class);
        if (GeneralUtils.isNotEmpty(restLinkKeys)) {
            mybatisTable.setLinkKeys(Arrays.asList(restLinkKeys.linkKeys()));
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
            mybatisTable.setCatalog(GeneralUtils.isEmpty(catalog) ? tableProperties.getCatalog() : catalog);
            mybatisTable.setSchema(GeneralUtils.isEmpty(schema) ? tableProperties.getSchema() : schema);
            if (GeneralUtils.isNotEmpty(styleName)) {
                mybatisStyle = MybatisStyle.style(styleName);
            } else if (GeneralUtils.isNotEmpty(styleType)) {
                mybatisTable.setStyleName(styleType.getKey());
                mybatisStyle = MybatisStyle.style(styleType);
            } else {
                StyleType defaultStyleType = tableProperties.getStyleType();
                mybatisStyle = MybatisStyle.style(defaultStyleType);
            }
        } else {
            StyleType defaultStyleType = tableProperties.getStyleType();
            mybatisStyle = MybatisStyle.style(defaultStyleType);
        }
        return mybatisStyle;
    }

    public void restResultMap(Class<?> clazz, MybatisTable mybatisTable) {
        /** restResultMap 注解处理 */
        RestResultMap restResultMap = clazz.getAnnotation(RestResultMap.class);
        if (GeneralUtils.isNotEmpty(restResultMap)) {
            mybatisTable.setResultMap(restResultMap.name());
            mybatisTable.setAutoResultMap(restResultMap.autoResultMap());
        }
    }

    public void restProperties(Class<?> clazz, MybatisTable mybatisTable) {
        /** restResultMap 注解处理 */
        RestProperties restProperties = clazz.getAnnotation(RestProperties.class);
        if (GeneralUtils.isNotEmpty(restProperties) && GeneralUtils.isNotEmpty(restProperties.properties())) {
            mybatisTable.setProperties(restProperties.properties());
        }
        Map<String, String> properties = tableProperties.getProperties();
        if (GeneralUtils.isNotEmpty(properties)) {
            mybatisTable.setProperties(properties);
        }
    }

    public void restExcludes(Class<?> clazz, MybatisTable mybatisTable) {
        /** restExcludes 注解处理 */
        RestExcludes restExcludes = clazz.getAnnotation(RestExcludes.class);
        if (GeneralUtils.isNotEmpty(restExcludes)) {
            String[] fields = restExcludes.fields();
            mybatisTable.setExcludeFields(Arrays.asList(fields));
            Class<?>[] classes = restExcludes.fieldTypes();
            mybatisTable.setExcludeFieldTypes(Arrays.asList(classes));
            Class<?>[] superClasses = restExcludes.superClasses();
            mybatisTable.setExcludeSuperClasses(Arrays.asList(superClasses));
        }
    }
}
