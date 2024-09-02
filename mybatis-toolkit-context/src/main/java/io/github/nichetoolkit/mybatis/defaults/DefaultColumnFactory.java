package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.*;
import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.rest.util.ContextUtils;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.mybatis.stereotype.RestIdentity;
import io.github.nichetoolkit.mybatis.stereotype.RestProperty;
import io.github.nichetoolkit.mybatis.stereotype.column.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * <code>DefaultColumnFactory</code>
 * <p>The type default column factory class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisColumnFactory
 * @since Jdk1.8
 */
public class DefaultColumnFactory implements MybatisColumnFactory {

    private final MybatisTableProperties tableProperties;

    /**
     * <code>DefaultColumnFactory</code>
     * Instantiates a new default column factory.
     */
    public DefaultColumnFactory() {
        this.tableProperties = ContextUtils.getBean(MybatisTableProperties.class);
    }

    @Override
    public boolean supports(MybatisTable table, MybatisField field) {
        /** 排除RestExclude 注解的字段 以及 RestExcludes 注解的字段 */
        RestExclude restExclude = field.getAnnotation(RestExclude.class);
        String fieldName = field.fieldName();
        List<String> excludeIgnoreKeys= table.getExcludeIgnoreKeys();
        if (GeneralUtils.isNotEmpty(excludeIgnoreKeys)) {
            /** 当前字段属于 需要排除的字段类型 返回 false */
            if (excludeIgnoreKeys.contains(fieldName)) {
                return true;
            }
        }
        if (GeneralUtils.isNotEmpty(restExclude)) {
            /** 当前字段被 RestExclude 修饰 且 exclude值为 true 时 返回 false */
            if (restExclude.exclude()) {
                return false;
            }
        }
        List<String> excludeFields = table.getExcludeFields();
        if (GeneralUtils.isNotEmpty(excludeFields)) {
            /** 当前字段属于 需要排除的字段名称 返回 false */
            if (excludeFields.contains(fieldName)) {
                return false;
            }
        }
        List<String> excludeGlobals = tableProperties.getExcludes();
        if (GeneralUtils.isNotEmpty(excludeGlobals)) {
            /** 当前字段属于 需要排除的字段名称 返回 false */
            if (excludeGlobals.contains(fieldName)) {
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
            return !excludeSuperClasses.contains(declaringClass);
        }
        return true;
    }

    @Override
    public Optional<List<MybatisColumn>> createColumn(MybatisTable mybatisTable, MybatisField field, Chain chain) {
        /** 默认针对 entity 实体中的所有字段构建 column 数据 */
        MybatisColumn mybatisColumn = MybatisColumn.of(field,tableProperties.getProperties());
        RestName restName = field.getAnnotation(RestName.class);
        MybatisStyle mybatisStyle = MybatisStyle.style(mybatisTable.getStyleName());
        if (GeneralUtils.isNotEmpty(restName)) {
            mybatisColumn.setColumnName(GeneralUtils.isEmpty(restName.name()) ? mybatisStyle.columnName(mybatisTable, field) : restName.name());
        } else {
            mybatisColumn.setColumnName(mybatisStyle.columnName(mybatisTable, field));
        }
        RestOrder restOrder = field.getAnnotation(RestOrder.class);
        if (GeneralUtils.isNotEmpty(restOrder)) {
            if (GeneralUtils.isNotEmpty(restOrder.order())) {
                mybatisColumn.setOrder(restOrder.order());
            }
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
            if (GeneralUtils.isNotEmpty(restUnionKey.index())) {
                mybatisColumn.setUnionIndex(restUnionKey.index());
            }
        }
        RestUniqueKey restUniqueKey = field.getAnnotation(RestUniqueKey.class);
        if (GeneralUtils.isNotEmpty(restUniqueKey)) {
            mybatisColumn.setUnique(true);
        }
        RestLinkKey restLinkKey = field.getAnnotation(RestLinkKey.class);
        if (GeneralUtils.isNotEmpty(restLinkKey)) {
            mybatisColumn.setLinkKey(restLinkKey.value());
        }
        RestAlertKey restAlertKey = field.getAnnotation(RestAlertKey.class);
        if (GeneralUtils.isNotEmpty(restAlertKey)) {
            mybatisColumn.setAlertKey(restAlertKey.value());
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
        RestForceInsert restForceInsert = field.getAnnotation(RestForceInsert.class);
        if (GeneralUtils.isNotEmpty(restForceInsert)) {
            mybatisColumn.setForceInsert(true);
            mybatisColumn.setForceInsertValue(restForceInsert.value());
        }
        RestForceUpdate restForceUpdate = field.getAnnotation(RestForceUpdate.class);
        if (GeneralUtils.isNotEmpty(restForceUpdate)) {
            mybatisColumn.setForceUpdate(true);
            mybatisColumn.setForceUpdateValue(restForceUpdate.value());
        }
        RestLogic restLogic = field.getAnnotation(RestLogic.class);
        if (GeneralUtils.isNotEmpty(restLogic)) {
            mybatisColumn.setLogic(true);
        }
        RestOperate restOperate = field.getAnnotation(RestOperate.class);
        if (GeneralUtils.isNotEmpty(restOperate)) {
            mybatisColumn.setOperate(true);
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
