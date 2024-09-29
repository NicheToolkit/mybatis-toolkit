package io.github.nichetoolkit.mybatis.style;

import io.github.nichetoolkit.mybatis.MybatisField;
import io.github.nichetoolkit.mybatis.MybatisTableStyle;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.rest.util.GeneralUtils;

public class DefaultUpperUnderlineStyle implements MybatisTableStyle {
    @Override
    public StyleType getStyleType() {
        return StyleType.UPPER_UNDERLINE;
    }

    @Override
    public String tableName(Class<?> clazz) {
        return GeneralUtils.underline(clazz.getSimpleName()).toUpperCase();
    }

    @Override
    public String tableAlias(Class<?> clazz) {
        String tableName = clazz.getSimpleName();
        return GeneralUtils.abbreviate(tableName,0,true).toUpperCase();
    }

    @Override
    public String columnName(MybatisTable table, MybatisField field) {
        return GeneralUtils.underline(field.fieldName()).toUpperCase();
    }

}
