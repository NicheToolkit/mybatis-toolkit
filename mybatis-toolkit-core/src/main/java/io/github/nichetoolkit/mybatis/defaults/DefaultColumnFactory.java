package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.*;
import io.github.nichetoolkit.mybatis.stereotype.RestColumn;
import io.github.nichetoolkit.mybatis.stereotype.RestProperties;
import io.github.nichetoolkit.mybatis.stereotype.column.*;
import io.github.nichetoolkit.mybatis.stereotype.RestProperty;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.apache.ibatis.type.JdbcType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * <p>DefaultColumnFactory</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class DefaultColumnFactory implements MybatisColumnFactory {

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
        String[] excludeFields = table.excludeFields();
        if (GeneralUtils.isNotEmpty(excludeFields)) {
            /** 当前字段属于 需要排除的字段名称 返回 false */
            if (Arrays.asList(excludeFields).contains(fieldName)) {
                return false;
            }
        }
        Class<?> fieldType = field.fieldType();
        Class<?>[] excludeFieldTypes = table.excludeFieldTypes();
        if (GeneralUtils.isNotEmpty(excludeFieldTypes)) {
            /** 当前字段属于 需要排除的字段类型 返回 false */
            if (Arrays.asList(excludeFieldTypes).contains(fieldType)) {
                return false;
            }
        }
        Class<?> declaringClass = field.declaringClass();
        Class<?>[] excludeSuperClasses = table.excludeSuperClasses();
        if (GeneralUtils.isNotEmpty(excludeSuperClasses)) {
            /** 当前字段属于 需要排除的父类字段 返回 false  */
            if (Arrays.asList(excludeSuperClasses).contains(declaringClass)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Optional<List<MybatisColumn>> createColumn(MybatisTable table, MybatisField field, Chain chain) {
        /** 默认针对 entity 实体中的所有字段构建 column 数据 */
        MybatisColumn mybatisColumn = MybatisColumn.of(field);
        RestAlias restAlias = field.getAnnotation(RestAlias.class);
        MybatisStyle mybatisStyle = MybatisStyle.style(table.styleName());
        if (GeneralUtils.isNotEmpty(restAlias)) {
            mybatisColumn.columnName(GeneralUtils.isEmpty(restAlias.name()) ? mybatisStyle.columnName(table, field) : restAlias.name());
        } else {
            mybatisColumn.columnName(mybatisStyle.columnName(table, field));
        }
        RestIdentity restIdentity = field.getAnnotation(RestIdentity.class);
        if (GeneralUtils.isNotEmpty(restIdentity)) {

        }
        RestPrimaryKey restPrimaryKey = field.getAnnotation(RestPrimaryKey.class);
        if (GeneralUtils.isNotEmpty(restPrimaryKey)) {
            mybatisColumn.primaryKey(restPrimaryKey.value());
        }
        RestUnionKey restUnionKey = field.getAnnotation(RestUnionKey.class);
        if (GeneralUtils.isNotEmpty(restUnionKey)) {
            mybatisColumn.unionKey(restUnionKey.value()).unionIndex(restUnionKey.index());
        }
        RestSortType restSortType = field.getAnnotation(RestSortType.class);
        if (GeneralUtils.isNotEmpty(restSortType)) {
            mybatisColumn.sortType(restSortType.type()).priority(restSortType.priority());
        }
        RestExclude restExclude = field.getAnnotation(RestExclude.class);
        if (GeneralUtils.isNotEmpty(restExclude)) {
            mybatisColumn.select(restExclude.select()).insert(restExclude.insert()).update(restExclude.update());
        }
        RestJdbcType restJdbcType = field.getAnnotation(RestJdbcType.class);
        if (GeneralUtils.isNotEmpty(restJdbcType)) {
            mybatisColumn.jdbcType(restJdbcType.jdbcType()).typeHandler(restJdbcType.typeHandler()).numericScale(restJdbcType.numericScale());
        } else {
            mybatisColumn.jdbcType(JdbcType.UNDEFINED);
        }
        /** restProperty 注解处理 */
        RestProperty restProperty = field.getAnnotation(RestProperty.class);
        if (GeneralUtils.isNotEmpty(restProperty) && GeneralUtils.isNotEmpty(restProperty.name())) {
            mybatisColumn.setProperty(restProperty);
        }
        return Optional.of(Collections.singletonList(mybatisColumn));
    }
}
