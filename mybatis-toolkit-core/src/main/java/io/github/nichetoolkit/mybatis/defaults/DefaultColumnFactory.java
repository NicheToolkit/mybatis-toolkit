package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.*;
import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.stereotype.mybatis.RestIdentity;
import io.github.nichetoolkit.rice.stereotype.mybatis.RestProperty;
import io.github.nichetoolkit.rice.stereotype.mybatis.column.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * <p>DefaultColumnFactory</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class DefaultColumnFactory implements MybatisColumnFactory {

    private MybatisTableProperties tableProperties;

    @Autowired
    public DefaultColumnFactory(MybatisTableProperties tableProperties) {
        this.tableProperties = tableProperties;
    }

    @Override
    public boolean supports(MybatisTable table, MybatisField field) {
        /** 排除RestExclude 注解的字段 以及 RestExcludes 注解的字段 */
        RestExclude restExclude = field.getAnnotation(RestExclude.class);
        if (GeneralUtils.isNotEmpty(restExclude)) {
            /** 当前字段被 RestExclude 修饰 且 exclude值为 true 时 返回 false */
            if (restExclude.exclude()) {
                return false;
            }
        }
        String fieldName = field.fieldName();
        List<String> excludeFields = table.getExcludeFields();
        if (GeneralUtils.isNotEmpty(excludeFields)) {
            /** 当前字段属于 需要排除的字段名称 返回 false */
            if (excludeFields.contains(fieldName)) {
                return false;
            }
        }
        Class<?> fieldType = field.fieldType();
        List<Class<?>> excludeFieldTypes = table.getExcludeFieldTypes();
        if (GeneralUtils.isNotEmpty(excludeFieldTypes)) {
            /** 当前字段属于 需要排除的字段类型 返回 false */
            if (excludeFieldTypes.contains(fieldType)) {
                return false;
            }
        }
        Class<?> declaringClass = field.declaringClass();
        List<Class<?>> excludeSuperClasses = table.getExcludeSuperClasses();
        if (GeneralUtils.isNotEmpty(excludeSuperClasses)) {
            /** 当前字段属于 需要排除的父类字段 返回 false  */
            if (excludeSuperClasses.contains(declaringClass)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Optional<List<MybatisColumn>> createColumn(MybatisTable table, MybatisField field, Chain chain) {
        /** 默认针对 entity 实体中的所有字段构建 column 数据 */
        MybatisColumn mybatisColumn = MybatisColumn.of(field,tableProperties.getProperties());
        RestName restName = field.getAnnotation(RestName.class);
        MybatisStyle mybatisStyle = MybatisStyle.style(table.getStyleName());
        if (GeneralUtils.isNotEmpty(restName)) {
            mybatisColumn.setColumnName(GeneralUtils.isEmpty(restName.name()) ? mybatisStyle.columnName(table, field) : restName.name());
        } else {
            mybatisColumn.setColumnName(mybatisStyle.columnName(table, field));
        }
        RestIdentity restIdentity = field.getAnnotation(RestIdentity.class);
        if (GeneralUtils.isNotEmpty(restIdentity)) {
            mybatisColumn.setIdentity(true);
        }
        RestPrimaryKey restPrimaryKey = field.getAnnotation(RestPrimaryKey.class);
        if (GeneralUtils.isNotEmpty(restPrimaryKey)) {
            mybatisColumn.setPrimaryKey(restPrimaryKey.value());
        }
        RestUnionKey restUnionKey = field.getAnnotation(RestUnionKey.class);
        if (GeneralUtils.isNotEmpty(restUnionKey)) {
            mybatisColumn.setUnionKey(restUnionKey.value());
            mybatisColumn.setUnionIndex(restUnionKey.index());
        }
        RestLinkKey restLinkKey = field.getAnnotation(RestLinkKey.class);
        if (GeneralUtils.isNotEmpty(restLinkKey)) {
            mybatisColumn.setLinkKey(restLinkKey.value());
        }
        RestSortType restSortType = field.getAnnotation(RestSortType.class);
        if (GeneralUtils.isNotEmpty(restSortType)) {
            mybatisColumn.setSortType(restSortType.type());
            mybatisColumn.setPriority(restSortType.priority());
        }
        RestInsert restInsert = field.getAnnotation(RestInsert.class);
        if (GeneralUtils.isNotEmpty(restInsert)) {
            mybatisColumn.setInsert(restInsert.insert());
        }
        RestUpdate restUpdate = field.getAnnotation(RestUpdate.class);
        if (GeneralUtils.isNotEmpty(restUpdate)) {
            mybatisColumn.setUpdate(restUpdate.update());
        }
        RestForce restForce = field.getAnnotation(RestForce.class);
        if (GeneralUtils.isNotEmpty(restForce)) {
            mybatisColumn.setForce(true);
            mybatisColumn.setForceValue(restForce.value());
            mybatisColumn.setUpdate(false);
        }
        RestSelect restSelect = field.getAnnotation(RestSelect.class);
        if (GeneralUtils.isNotEmpty(restSelect)) {
            mybatisColumn.setSelect(restSelect.select());
        }
        RestJdbcType restJdbcType = field.getAnnotation(RestJdbcType.class);
        if (GeneralUtils.isNotEmpty(restJdbcType)) {
            mybatisColumn.setJdbcType(restJdbcType.jdbcType());
            mybatisColumn.setTypeHandler(restJdbcType.typeHandler());
            mybatisColumn.setNumericScale(restJdbcType.numericScale());
        } else {
            mybatisColumn.setJdbcType(JdbcType.UNDEFINED);
        }
        /** restProperty 注解处理 */
        RestProperty restProperty = field.getAnnotation(RestProperty.class);
        if (GeneralUtils.isNotEmpty(restProperty) && GeneralUtils.isNotEmpty(restProperty.name())) {
            mybatisColumn.setProperty(restProperty);
        }
        return Optional.of(Collections.singletonList(mybatisColumn));
    }
}
