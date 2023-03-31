package io.github.nichetoolkit.mybatis.style;

import io.github.nichetoolkit.mybatis.MybatisField;
import io.github.nichetoolkit.mybatis.MybatisStyle;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.StyleType;

/**
 * <p>DefaultNormalStyle</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
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
        return field.getName();
    }
}
