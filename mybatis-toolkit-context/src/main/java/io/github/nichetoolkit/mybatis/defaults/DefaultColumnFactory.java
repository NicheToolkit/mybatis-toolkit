package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.*;
import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.mybatis.stereotype.column.*;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.apache.ibatis.type.JdbcType;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DefaultColumnFactory implements MybatisColumnFactory {

    private final MybatisTableProperties tableProperties;

    public DefaultColumnFactory() {
        this.tableProperties = MybatisContextHolder.tableProperties();
    }

    private boolean excludeSupport(MybatisTable table, MybatisField field) {
        String fieldName = field.fieldName();
        List<String> globalExcludes = this.tableProperties.getExcludes();
        if (GeneralUtils.isNotEmpty(globalExcludes) && globalExcludes.contains(fieldName)) {
            /* 当前字段属于 需要排除的字段名称 返回 false */
            return false;
        }
        /* 排除RestExclude 注解的字段 以及 RestExcludes 注解的字段 */
        RestExclude restExclude = field.getAnnotation(RestExclude.class);
        if (GeneralUtils.isNotEmpty(restExclude)) {
            /* 当前字段被 RestExclude 修饰 且 exclude值为 true 时 返回 false */
            return false;
        }
        List<String> excludeFields = table.getExcludeFields();
        if (GeneralUtils.isNotEmpty(excludeFields) && excludeFields.contains(fieldName)) {
            /* 当前字段属于 需要排除的字段名称 返回 false */
            return false;
        }
        Class<?> fieldType = field.fieldType();
        List<Class<?>> excludeFieldTypes = table.getExcludeFieldTypes();
        if (GeneralUtils.isNotEmpty(excludeFieldTypes) && excludeFieldTypes.contains(fieldType)) {
            /* 当前字段属于 需要排除的字段类型 返回 false */
            return false;
        }
        Class<?> declaringClass = field.declaringClass();
        List<Class<?>> excludeSuperClasses = table.getExcludeSuperClasses();
        if (GeneralUtils.isNotEmpty(excludeSuperClasses)) {
            /* 当前字段属于 需要排除的父类字段 返回 false  */
            return !excludeSuperClasses.contains(declaringClass);
        }
        return true;
    }

    private void ignoreHandle(MybatisTable table, MybatisField field) {
        String fieldName = field.fieldName();
        List<String> globalIgnores = this.tableProperties.getIgnores();
        if (GeneralUtils.isNotEmpty(globalIgnores) && globalIgnores.contains(fieldName)) {
            /* 当前字段属于 需要排除的字段名称 设置ignored*/
            field.ignored(true);
        }
        List<String> ignoreFields = table.getIgnoreFields();
        if (GeneralUtils.isNotEmpty(ignoreFields) && ignoreFields.contains(fieldName)) {
            /* 当前字段属于 需要排除的字段名称 设置ignored */
            field.ignored(true);
        }
        Class<?> fieldType = field.fieldType();
        List<Class<?>> ignoreFieldTypes = table.getIgnoreFieldTypes();
        if (GeneralUtils.isNotEmpty(ignoreFieldTypes) && ignoreFieldTypes.contains(fieldType)) {
            /* 当前字段属于 需要排除的字段类型 设置ignored */
            field.ignored(true);
        }
        Class<?> declaringClass = field.declaringClass();
        List<Class<?>> excludeSuperClasses = table.getExcludeSuperClasses();
        if (GeneralUtils.isNotEmpty(excludeSuperClasses) && excludeSuperClasses.contains(declaringClass)) {
            /* 当前字段属于 需要排除的父类字段 设置ignored  */
            field.ignored(true);
        }
    }

    @Override
    public boolean supports(@NonNull MybatisTable table, @NonNull MybatisField field) {
        ignoreHandle(table, field);
        return excludeSupport(table, field);
    }

    @Override
    public Optional<List<MybatisColumn>> createColumn(@NonNull MybatisTable mybatisTable, @NonNull MybatisField field, Chain chain) {
        /* 默认针对 entity 实体中的所有字段构建 column 数据 */
        MybatisColumn mybatisColumn = MybatisColumn.of(field, this.tableProperties.getProperties());
        boolean fieldIgnored = field.ignored();
        RestColname restName = field.getAnnotation(RestColname.class);
        String columnName = Optional.ofNullable(restName).map(RestColname::name).orElse(null);
        String columnComment = Optional.ofNullable(restName).map(RestColname::comment).orElse(null);
        MybatisTableStyle mybatisStyle = MybatisTableStyle.style(mybatisTable.getStyleName());
        if (GeneralUtils.isNotEmpty(restName)) {
            mybatisColumn.setColumn(GeneralUtils.isEmpty(columnName) ? mybatisStyle.columnName(mybatisTable, field) : restName.name());
            mybatisColumn.setComment(columnComment);
        } else {
            mybatisColumn.setColumn(mybatisStyle.columnName(mybatisTable, field));
        }
        RestOrder restOrder = field.getAnnotation(RestOrder.class);
        if (GeneralUtils.isNotEmpty(restOrder) && !fieldIgnored) {
            if (GeneralUtils.isNotEmpty(restOrder.value())) {
                mybatisColumn.setOrder(restOrder.value());
            }
        }
        RestSelect restSelect = field.getAnnotation(RestSelect.class);
        if (GeneralUtils.isNotEmpty(restSelect) && !fieldIgnored) {
            mybatisColumn.setSelect(restSelect.value());
        }
        RestInsert restInsert = field.getAnnotation(RestInsert.class);
        if (GeneralUtils.isNotEmpty(restInsert) && !fieldIgnored) {
            mybatisColumn.setInsert(restInsert.value());
        }
        RestUpdate restUpdate = field.getAnnotation(RestUpdate.class);
        if (GeneralUtils.isNotEmpty(restUpdate) && !fieldIgnored) {
            mybatisColumn.setUpdate(restUpdate.value());
        }
        RestForceInsert restForceInsert = field.getAnnotation(RestForceInsert.class);
        if (GeneralUtils.isNotEmpty(restForceInsert) && !fieldIgnored) {
            mybatisColumn.setForceInsert(true);
            mybatisColumn.setForceInsertValue(restForceInsert.value());
        }
        RestForceUpdate restForceUpdate = field.getAnnotation(RestForceUpdate.class);
        if (GeneralUtils.isNotEmpty(restForceUpdate) && !fieldIgnored) {
            mybatisColumn.setForceUpdate(true);
            mybatisColumn.setForceUpdateValue(restForceUpdate.value());
        }
        RestIdentityKey restIdentityKey = field.getAnnotation(RestIdentityKey.class);
        if (GeneralUtils.isNotEmpty(restIdentityKey) && !fieldIgnored) {
            mybatisColumn.setIdentityKey(true);
            mybatisColumn.setUpdate(false);
        }
        RestPrimaryKey restPrimaryKey = field.getAnnotation(RestPrimaryKey.class);
        if (GeneralUtils.isNotEmpty(restPrimaryKey) && !fieldIgnored) {
            mybatisColumn.setPrimaryKey(true);
            mybatisColumn.setUpdate(false);
        }
        RestUnionKey restUnionKey = field.getAnnotation(RestUnionKey.class);
        if (GeneralUtils.isNotEmpty(restUnionKey) && !fieldIgnored) {
            mybatisColumn.setUnionKey(true);
        }
        RestUniqueKey restUniqueKey = field.getAnnotation(RestUniqueKey.class);
        if (GeneralUtils.isNotEmpty(restUniqueKey) && !fieldIgnored) {
            mybatisColumn.setUniqueKey(true);
        }
        RestLinkKey restLinkKey = field.getAnnotation(RestLinkKey.class);
        if (GeneralUtils.isNotEmpty(restLinkKey) && !fieldIgnored) {
            mybatisColumn.setLinkKey(true);
        }
        RestAlertKey restAlertKey = field.getAnnotation(RestAlertKey.class);
        if (GeneralUtils.isNotEmpty(restAlertKey) && !fieldIgnored) {
            mybatisColumn.setAlertKey(true);
        }
        RestLogicKey restLogicKey = field.getAnnotation(RestLogicKey.class);
        if (GeneralUtils.isNotEmpty(restLogicKey) && !fieldIgnored) {
            mybatisColumn.setLogicKey(true);
            mybatisColumn.setUpdate(false);
        }
        RestOperateKey restOperate = field.getAnnotation(RestOperateKey.class);
        if (GeneralUtils.isNotEmpty(restOperate) && !fieldIgnored) {
            mybatisColumn.setOperateKey(true);
            mybatisColumn.setUpdate(false);
        }
        RestSortType restSortType = field.getAnnotation(RestSortType.class);
        if (GeneralUtils.isNotEmpty(restSortType) && !fieldIgnored) {
            mybatisColumn.setSortType(restSortType.type());
            mybatisColumn.setPriority(restSortType.priority());
        }

        RestJdbcType restJdbcType = field.getAnnotation(RestJdbcType.class);
        if (GeneralUtils.isNotEmpty(restJdbcType) && !fieldIgnored) {
            mybatisColumn.setJdbcType(restJdbcType.jdbcType());
            mybatisColumn.setTypeHandler(restJdbcType.typeHandler());
            mybatisColumn.setNumericScale(restJdbcType.numericScale());
        } else {
            mybatisColumn.setJdbcType(JdbcType.UNDEFINED);
        }
        if (field.isSpecialIdentity()) {
            mybatisColumn.setUpdate(false);
        }
        return Optional.of(Collections.singletonList(mybatisColumn));
    }
}
