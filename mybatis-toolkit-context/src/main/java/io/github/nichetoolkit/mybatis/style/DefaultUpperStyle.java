package io.github.nichetoolkit.mybatis.style;

import io.github.nichetoolkit.mybatis.MybatisField;
import io.github.nichetoolkit.mybatis.MybatisTableStyle;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.rest.util.GeneralUtils;

public class DefaultUpperStyle implements MybatisTableStyle {
    @Override
    public StyleType getStyleType() {
        return StyleType.UPPER;
    }

    @Override
    public String tableName(Class<?> clazz) {
        return clazz.getSimpleName().toUpperCase();
    }

    @Override
    public String tableAlias(Class<?> clazz) {
        String tableName = clazz.getSimpleName();
        return GeneralUtils.abbreviate(tableName).toUpperCase();
    }

    @Override
    public String columnName(MybatisTable table, MybatisField field) {
        return field.fieldName().toUpperCase();
    }
}
