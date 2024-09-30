package io.github.nichetoolkit.mybatis.style;

import io.github.nichetoolkit.mybatis.MybatisField;
import io.github.nichetoolkit.mybatis.MybatisTableStyle;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.rest.util.GeneralUtils;

public class DefaultLowerStyle implements MybatisTableStyle {
    @Override
    public StyleType getStyleType() {
        return StyleType.LOWER;
    }

    @Override
    public String tableName(Class<?> entityType) {
        return entityType.getSimpleName().toLowerCase();
    }

    @Override
    public String tableAlias(Class<?> entityType) {
        String tableName = entityType.getSimpleName();;
        return GeneralUtils.abbreviate(tableName);
    }

    @Override
    public String columnName(MybatisTable table, MybatisField field) {
        return field.fieldName().toLowerCase();
    }
}
