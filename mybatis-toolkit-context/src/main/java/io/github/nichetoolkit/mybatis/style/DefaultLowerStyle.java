package io.github.nichetoolkit.mybatis.style;

import io.github.nichetoolkit.mybatis.MybatisField;
import io.github.nichetoolkit.mybatis.MybatisTableStyle;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.rest.util.GeneralUtils;

import java.lang.reflect.Field;

/**
 * <code>DefaultLowerStyle</code>
 * <p>The default lower style class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisTableStyle
 * @since Jdk1.8
 */
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
    public String columnName(Field field) {
        return field.getName().toLowerCase();
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
