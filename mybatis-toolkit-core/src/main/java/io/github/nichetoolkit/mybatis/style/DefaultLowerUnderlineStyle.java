package io.github.nichetoolkit.mybatis.style;

import io.github.nichetoolkit.mybatis.MybatisField;
import io.github.nichetoolkit.mybatis.MybatisStyle;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.rest.util.GeneralUtils;

/**
 * <p>DefaultLowerUnderlineStyle</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
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
    public String columnName(MybatisTable table, MybatisField field) {
        return GeneralUtils.underline(field.getName());
    }
}
