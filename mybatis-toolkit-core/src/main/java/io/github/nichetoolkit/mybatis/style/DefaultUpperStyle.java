package io.github.nichetoolkit.mybatis.style;

import io.github.nichetoolkit.mybatis.MybatisField;
import io.github.nichetoolkit.mybatis.MybatisStyle;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.StyleType;

/**
 * <p>DefaultUpperStyle</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class DefaultUpperStyle implements MybatisStyle {
    @Override
    public StyleType getStyleType() {
        return StyleType.UPPER;
    }

    @Override
    public String tableName(Class<?> clazz) {
        return clazz.getSimpleName().toUpperCase();
    }

    @Override
    public String columnName(MybatisTable table, MybatisField field) {
        return field.fieldName().toUpperCase();
    }
}
