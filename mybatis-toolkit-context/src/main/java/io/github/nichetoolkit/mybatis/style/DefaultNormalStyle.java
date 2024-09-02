package io.github.nichetoolkit.mybatis.style;

import io.github.nichetoolkit.mybatis.MybatisField;
import io.github.nichetoolkit.mybatis.MybatisStyle;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.StyleType;

/**
 * <code>DefaultNormalStyle</code>
 * <p>The type default normal style class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisStyle
 * @since Jdk1.8
 */
public class DefaultNormalStyle implements MybatisStyle {
    @Override
    public StyleType getStyleType() {
        return StyleType.NORMAL;
    }

    @Override
    public String tableName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

    @Override
    public String columnName(MybatisTable table, MybatisField field) {
        return field.fieldName();
    }
}
