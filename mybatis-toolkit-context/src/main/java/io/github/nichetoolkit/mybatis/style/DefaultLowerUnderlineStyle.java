package io.github.nichetoolkit.mybatis.style;

import io.github.nichetoolkit.mybatis.MybatisField;
import io.github.nichetoolkit.mybatis.MybatisStyle;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.rest.util.GeneralUtils;

/**
 * <code>DefaultLowerUnderlineStyle</code>
 * <p>The type default lower underline style class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisStyle
 * @since Jdk1.8
 */
public class DefaultLowerUnderlineStyle implements MybatisStyle {
    @Override
    public StyleType getStyleType() {
        return StyleType.LOWER_UNDERLINE;
    }

    @Override
    public String tableName(Class<?> clazz) {
        return GeneralUtils.underline(clazz.getSimpleName());
    }

    @Override
    public String tableAlias(Class<?> clazz) {
        String tableName = clazz.getSimpleName();
        return GeneralUtils.abbreviate(tableName,0,true);
    }

    @Override
    public String columnName(MybatisTable table, MybatisField field) {
        return GeneralUtils.underline(field.fieldName());
    }
}
