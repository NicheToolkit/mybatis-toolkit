package io.github.nichetoolkit.mybatis.style;

import io.github.nichetoolkit.mybatis.MybatisField;
import io.github.nichetoolkit.mybatis.MybatisTableStyle;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.rice.enums.StyleType;
import io.github.nichetoolkit.rest.util.GeneralUtils;

import java.lang.reflect.Field;

/**
 * <code>DefaultNormalStyle</code>
 * <p>The default normal style class.</p>
 * @see  io.github.nichetoolkit.mybatis.MybatisTableStyle
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class DefaultNormalStyle implements MybatisTableStyle {
    @Override
    public StyleType getStyleType() {
        return StyleType.NORMAL;
    }

    @Override
    public String tableName(Class<?> entityType) {
        return entityType.getSimpleName();
    }

    @Override
    public String tableAlias(Class<?> entityType) {
        String tableName = entityType.getSimpleName();
        return GeneralUtils.abbreviate(tableName);
    }

    @Override
    public String columnName(Field field) {
        return field.getName();
    }

    @Override
    public String columnName(MybatisField field) {
        return columnName(field.field());
    }

    @Override
    public String columnName(MybatisTable table, MybatisField field) {
        return columnName(field);
    }
}
