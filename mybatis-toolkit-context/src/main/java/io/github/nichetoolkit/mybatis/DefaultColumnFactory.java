package io.github.nichetoolkit.mybatis;

import com.fasterxml.jackson.databind.JavaType;
import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.mybatis.consts.ScriptConstants;
import io.github.nichetoolkit.mybatis.column.*;
import io.github.nichetoolkit.rest.RestOrder;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.apache.ibatis.type.JdbcType;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * <code>DefaultColumnFactory</code>
 * <p>The default column factory class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisColumnFactory
 * @since Jdk1.8
 */
public class DefaultColumnFactory implements MybatisColumnFactory {

    /**
     * <code>tableProperties</code>
     * {@link io.github.nichetoolkit.mybatis.configure.MybatisTableProperties} <p>The <code>tableProperties</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.configure.MybatisTableProperties
     */
    private final MybatisTableProperties tableProperties;

    /**
     * <code>DefaultColumnFactory</code>
     * <p>Instantiates a new default column factory.</p>
     */
    public DefaultColumnFactory() {
        this.tableProperties = MybatisContextHolder.tableProperties();
    }

    /**
     * <code>excludeSupport</code>
     * <p>The exclude support method.</p>
     * @param table {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @return boolean <p>The exclude support return object is <code>boolean</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    private boolean excludeSupport(MybatisTable table, MybatisField field) {
        String fieldName = field.fieldName();
        if (this.tableProperties.getExcludeUnused()) {
            List<String> globalUnuseds = this.tableProperties.getUnuseds();
            if (GeneralUtils.isNotEmpty(globalUnuseds) && globalUnuseds.contains(fieldName)) {
                /* 当前字段属于 需要排除的字段名称 返回 false */
                return false;
            }
        }
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

    /**
     * <code>ignoreHandle</code>
     * <p>The ignore handle method.</p>
     * @param table {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table parameter is <code>MybatisTable</code> type.</p>
     * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    private void ignoreHandle(MybatisTable table, MybatisField field) {
        String fieldName = field.fieldName();
        /* global field handle */
        globalHandle(fieldName, field);
        /* ignore field handle */
        MybatisIgnored ignored = table.getIgnored();
        ignoreHandle(ignored, fieldName, field);
        /* select ignore field handle */
        MybatisIgnored selectIgnored = table.getSelectIgnored();
        selectHandle(selectIgnored, fieldName, field);
        /* insert ignore field handle */
        MybatisIgnored insertIgnored = table.getInsertIgnored();
        insertHandle(insertIgnored, fieldName, field);
        /* update ignore field handle */
        MybatisIgnored updateIgnored = table.getUpdateIgnored();
        updateHandle(updateIgnored, fieldName, field);
    }

    /**
     * <code>globalHandle</code>
     * <p>The global handle method.</p>
     * @param fieldName {@link java.lang.String} <p>The field name parameter is <code>String</code> type.</p>
     * @param field     {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    private void globalHandle(String fieldName, MybatisField field) {
        List<String> globalIgnores = this.tableProperties.getIgnores();
        if (GeneralUtils.isNotEmpty(globalIgnores) && globalIgnores.contains(fieldName)) {
            /* 当前字段属于 需要排除的字段名称 设置ignored*/
            field.ignored(true);
        }
        List<String> globalSelectIgnores = this.tableProperties.getSelectIgnores();
        if (GeneralUtils.isNotEmpty(globalSelectIgnores) && globalSelectIgnores.contains(fieldName)) {
            /* 当前字段属于 需要排除的字段名称 设置ignored*/
            field.selectIgnored(true);
        }
        List<String> globalInsertIgnores = this.tableProperties.getInsertIgnores();
        if (GeneralUtils.isNotEmpty(globalInsertIgnores) && globalInsertIgnores.contains(fieldName)) {
            /* 当前字段属于 需要排除的字段名称 设置ignored*/
            field.insetIgnored(true);
        }
        List<String> globalUpdateIgnores = this.tableProperties.getUpdateIgnores();
        if (GeneralUtils.isNotEmpty(globalUpdateIgnores) && globalUpdateIgnores.contains(fieldName)) {
            /* 当前字段属于 需要排除的字段名称 设置ignored*/
            field.updateIgnored(true);
        }
    }

    /**
     * <code>ignoreHandle</code>
     * <p>The ignore handle method.</p>
     * @param ignored   {@link io.github.nichetoolkit.mybatis.MybatisIgnored} <p>The ignored parameter is <code>MybatisIgnored</code> type.</p>
     * @param fieldName {@link java.lang.String} <p>The field name parameter is <code>String</code> type.</p>
     * @param field     {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisIgnored
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    private void ignoreHandle(MybatisIgnored ignored, String fieldName, MybatisField field) {
        if (GeneralUtils.isNotEmpty(ignored)) {
            List<String> fields = ignored.fields();
            if (GeneralUtils.isNotEmpty(fields) && fields.contains(fieldName)) {
                /* 当前字段属于 需要排除的字段名称 设置ignored */
                field.ignored(true);
            }
            Class<?> fieldType = field.fieldType();
            List<Class<?>> fieldTypes = ignored.fieldTypes();
            if (GeneralUtils.isNotEmpty(fieldTypes) && fieldTypes.contains(fieldType)) {
                /* 当前字段属于 需要排除的字段类型 设置ignored */
                field.ignored(true);
            }
            Class<?> declaringClass = field.declaringClass();
            List<Class<?>> superClasses = ignored.superClasses();
            if (GeneralUtils.isNotEmpty(superClasses) && superClasses.contains(declaringClass)) {
                /* 当前字段属于 需要排除的父类字段 设置ignored  */
                field.ignored(true);
            }
        }
    }

    /**
     * <code>selectHandle</code>
     * <p>The select handle method.</p>
     * @param selectIgnored {@link io.github.nichetoolkit.mybatis.MybatisIgnored} <p>The select ignored parameter is <code>MybatisIgnored</code> type.</p>
     * @param fieldName     {@link java.lang.String} <p>The field name parameter is <code>String</code> type.</p>
     * @param field         {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisIgnored
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    private void selectHandle(MybatisIgnored selectIgnored, String fieldName, MybatisField field) {
        if (GeneralUtils.isNotEmpty(selectIgnored)) {
            List<String> fields = selectIgnored.fields();
            if (GeneralUtils.isNotEmpty(fields) && fields.contains(fieldName)) {
                /* 当前字段属于 需要排除的字段名称 设置ignored */
                field.selectIgnored(true);
            }
            Class<?> fieldType = field.fieldType();
            List<Class<?>> fieldTypes = selectIgnored.fieldTypes();
            if (GeneralUtils.isNotEmpty(fieldTypes) && fieldTypes.contains(fieldType)) {
                /* 当前字段属于 需要排除的字段类型 设置ignored */
                field.selectIgnored(true);
            }
            Class<?> declaringClass = field.declaringClass();
            List<Class<?>> superClasses = selectIgnored.superClasses();
            if (GeneralUtils.isNotEmpty(superClasses) && superClasses.contains(declaringClass)) {
                /* 当前字段属于 需要排除的父类字段 设置ignored  */
                field.selectIgnored(true);
            }
        }
    }

    /**
     * <code>insertHandle</code>
     * <p>The insert handle method.</p>
     * @param insertIgnored {@link io.github.nichetoolkit.mybatis.MybatisIgnored} <p>The insert ignored parameter is <code>MybatisIgnored</code> type.</p>
     * @param fieldName     {@link java.lang.String} <p>The field name parameter is <code>String</code> type.</p>
     * @param field         {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisIgnored
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    private void insertHandle(MybatisIgnored insertIgnored, String fieldName, MybatisField field) {
        if (GeneralUtils.isNotEmpty(insertIgnored)) {
            List<String> fields = insertIgnored.fields();
            if (GeneralUtils.isNotEmpty(fields) && fields.contains(fieldName)) {
                /* 当前字段属于 需要排除的字段名称 设置ignored */
                field.insetIgnored(true);
            }
            Class<?> fieldType = field.fieldType();
            List<Class<?>> fieldTypes = insertIgnored.fieldTypes();
            if (GeneralUtils.isNotEmpty(fieldTypes) && fieldTypes.contains(fieldType)) {
                /* 当前字段属于 需要排除的字段类型 设置ignored */
                field.insetIgnored(true);
            }
            Class<?> declaringClass = field.declaringClass();
            List<Class<?>> superClasses = insertIgnored.superClasses();
            if (GeneralUtils.isNotEmpty(superClasses) && superClasses.contains(declaringClass)) {
                /* 当前字段属于 需要排除的父类字段 设置ignored  */
                field.insetIgnored(true);
            }
        }
    }


    /**
     * <code>updateHandle</code>
     * <p>The update handle method.</p>
     * @param updateIgnored {@link io.github.nichetoolkit.mybatis.MybatisIgnored} <p>The update ignored parameter is <code>MybatisIgnored</code> type.</p>
     * @param fieldName     {@link java.lang.String} <p>The field name parameter is <code>String</code> type.</p>
     * @param field         {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisIgnored
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    private void updateHandle(MybatisIgnored updateIgnored, String fieldName, MybatisField field) {
        if (GeneralUtils.isNotEmpty(updateIgnored)) {
            List<String> fields = updateIgnored.fields();
            if (GeneralUtils.isNotEmpty(fields) && fields.contains(fieldName)) {
                /* 当前字段属于 需要排除的字段名称 设置ignored */
                field.updateIgnored(true);
            }
            Class<?> fieldType = field.fieldType();
            List<Class<?>> fieldTypes = updateIgnored.fieldTypes();
            if (GeneralUtils.isNotEmpty(fieldTypes) && fieldTypes.contains(fieldType)) {
                /* 当前字段属于 需要排除的字段类型 设置ignored */
                field.updateIgnored(true);
            }
            Class<?> declaringClass = field.declaringClass();
            List<Class<?>> superClasses = updateIgnored.superClasses();
            if (GeneralUtils.isNotEmpty(superClasses) && superClasses.contains(declaringClass)) {
                /* 当前字段属于 需要排除的父类字段 设置ignored  */
                field.updateIgnored(true);
            }
        }
    }

    @Override
    public boolean supports(@NonNull MybatisTable table, @NonNull MybatisField field) {
        ignoreHandle(table, field);
        return excludeSupport(table, field);
    }

    @Override
    public Optional<List<MybatisColumn>> createColumn(@NonNull MybatisTable mybatisTable, @NonNull MybatisField field, Chain chain) {
        return createColumn(mybatisTable, field, chain, null, false, false);
    }


    @Override
    public Optional<List<MybatisColumn>> createColumn(@NonNull MybatisTable mybatisTable, @NonNull MybatisField field, Chain chain, JavaType fickleType, boolean isFickleKey, boolean isFickleValue) {
        /* 默认针对 entity 实体中的所有字段构建 column 数据 */
        MybatisColumn mybatisColumn = MybatisColumn.of(field, this.tableProperties.getProperties());
        boolean fieldIgnored = field.ignored();
        RestName restName = field.getAnnotation(RestName.class);
        String columnName = Optional.ofNullable(restName).map(RestName::name).orElse(null);
        String columnComment = Optional.ofNullable(restName).map(RestName::comment).orElse(null);
        MybatisTableStyle mybatisStyle = MybatisTableStyle.style(mybatisTable.getStyleName());
        if (GeneralUtils.isNotEmpty(restName)) {
            mybatisColumn.setColumn(GeneralUtils.isEmpty(columnName) ? mybatisStyle.columnName(field) : restName.name());
            mybatisColumn.setComment(columnComment);
        } else {
            mybatisColumn.setColumn(mybatisStyle.columnName(field));
        }
        RestOrdered restOrdered = field.getAnnotation(RestOrdered.class);
        if (GeneralUtils.isNotEmpty(restOrdered) && !fieldIgnored) {
            if (GeneralUtils.isNotEmpty(restOrdered.value())) {
                mybatisColumn.setOrder(restOrdered.value());
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
        String datetimeNow = tableProperties.getDatetimeNow();
        RestForceInsert restForceInsert = field.getAnnotation(RestForceInsert.class);
        if (GeneralUtils.isNotEmpty(restForceInsert) && !fieldIgnored) {
            mybatisColumn.setForceInsert(true);
            String forceInsertValue = restForceInsert.value();
            if (ScriptConstants.NOW.equals(forceInsertValue)) {
                mybatisColumn.setForceInsertValue(datetimeNow);
            } else {
                mybatisColumn.setForceInsertValue(forceInsertValue);
            }
        }
        RestForceUpdate restForceUpdate = field.getAnnotation(RestForceUpdate.class);
        if (GeneralUtils.isNotEmpty(restForceUpdate) && !fieldIgnored) {
            mybatisColumn.setForceUpdate(true);
            String forceUpdateValue = restForceUpdate.value();
            if (ScriptConstants.NOW.equals(forceUpdateValue)) {
                mybatisColumn.setForceUpdateValue(datetimeNow);
            } else {
                mybatisColumn.setForceUpdateValue(forceUpdateValue);
            }
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
            if (GeneralUtils.isNotEmpty(restLinkKey.value())) {
                mybatisColumn.setLinkName(restLinkKey.value());
            }
        }
        RestAlertKey restAlertKey = field.getAnnotation(RestAlertKey.class);
        if (GeneralUtils.isNotEmpty(restAlertKey) && !fieldIgnored) {
            mybatisColumn.setAlertKey(true);
            if (GeneralUtils.isNotEmpty(restLinkKey.value())) {
                mybatisColumn.setAlertName(restLinkKey.value());
            }
        }
        RestLogicKey restLogicKey = field.getAnnotation(RestLogicKey.class);
        if (GeneralUtils.isNotEmpty(restLogicKey) && !fieldIgnored) {
            mybatisColumn.setLogicKey(true);
            if (!this.tableProperties.getUpdateLogic()) {
                mybatisColumn.setUpdate(false);
            }
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

        RestLoadParam restLoadParam = field.getAnnotation(RestLoadParam.class);
        if (GeneralUtils.isNotEmpty(restLoadParam) && !fieldIgnored) {
            mybatisColumn.setLoadParam(true);
            if (GeneralUtils.isNotEmpty(restLoadParam.param())) {
                mybatisColumn.setParamName(restLoadParam.param());
            }
            if (GeneralUtils.isNotEmpty(restLoadParam.keys())) {
                mybatisColumn.getLoadKeys().addAll(Arrays.asList(restLoadParam.keys()));
            }
            if (GeneralUtils.isNotEmpty(restLoadParam.types())) {
                mybatisColumn.getLoadTypes().addAll(Arrays.asList(restLoadParam.types()));
            }
        }
        RestLoadKey restLoadKey = field.getAnnotation(RestLoadKey.class);
        if (GeneralUtils.isNotEmpty(restLoadKey) && !fieldIgnored) {
            mybatisColumn.setLoadKey(true);
            /* 覆盖 RestLoadParam LoadValue  */
            if (GeneralUtils.isNotEmpty(restLoadKey.key())) {
                mybatisColumn.addLoadKey(restLoadKey.key());
            }
            if (GeneralUtils.isNotEmpty(restLoadKey.type()) && restLoadKey.type() != Void.class) {
                mybatisColumn.addLoadType(restLoadKey.type());
            }
        }
        RestLoadEntity restLoadEntity = field.getAnnotation(RestLoadEntity.class);
        if (GeneralUtils.isNotEmpty(restLoadEntity) && !fieldIgnored) {
            mybatisColumn.setLoadEntity(true);
            mybatisColumn.setSelect(false);
            mybatisColumn.setInsert(false);
            mybatisColumn.setUpdate(false);
            if (restLoadEntity.recursive()) {
                mybatisColumn.setLoadRecursive(true);
            }
            if (GeneralUtils.isNotEmpty(restLoadEntity.table())) {
                mybatisColumn.setLoadTable(restLoadEntity.table());
            }
            /* 覆盖 RestLoadKey LoadValue  */
            if (GeneralUtils.isNotEmpty(restLoadEntity.key())) {
                mybatisColumn.getLoadKeys().add(restLoadEntity.key());
            }
            /* 默认添加 字段名称为 loadKey */
            mybatisColumn.getLoadKeys().add(mybatisColumn.property());
            mybatisColumn.getLoadKeys().add(mybatisColumn.columnName());
        }

        if (field.isIdentity()) {
            mybatisColumn.setUpdate(false);
        }
        if (field.selectIgnored()) {
            mybatisColumn.setSelect(false);
        }
        if (field.insetIgnored()) {
            mybatisColumn.setInsert(false);
        }
        if (field.updateIgnored()) {
            mybatisColumn.setUpdate(false);
        }
        if (GeneralUtils.isNotEmpty(fickleType) && !fieldIgnored) {
            mybatisColumn.setFickleType(fickleType);
            mybatisColumn.setFickleKey(isFickleKey);
            mybatisColumn.setFickleValue(isFickleValue);
            mybatisColumn.setSelect(false);
            mybatisColumn.setInsert(false);
            mybatisColumn.setUpdate(false);
        }
        return Optional.of(Collections.singletonList(mybatisColumn));
    }
}
