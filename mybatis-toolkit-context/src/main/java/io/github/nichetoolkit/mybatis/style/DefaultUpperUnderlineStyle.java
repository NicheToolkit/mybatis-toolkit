package io.github.nichetoolkit.mybatis.style;

import io.github.nichetoolkit.mybatis.MybatisField;
import io.github.nichetoolkit.mybatis.MybatisTableStyle;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;

/**
 * <code>DefaultUpperUnderlineStyle</code>
 * <p>The default upper underline style class.</p>
 * @see  io.github.nichetoolkit.mybatis.MybatisTableStyle
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class DefaultUpperUnderlineStyle implements MybatisTableStyle {
    @Override
    public StyleType getStyleType() {
        return StyleType.UPPER_UNDERLINE;
    }

    @Override
    public String tableName(Class<?> entityType) {
        return GeneralUtils.underline(entityType.getSimpleName()).toUpperCase();
    }

    @Override
    public String tableAlias(Class<?> entityType) {
        String tableName = entityType.getSimpleName();
        return GeneralUtils.abbreviate(tableName,0,true).toUpperCase();
    }

    @Override
    public String columnName(@NonNull String fieldName) {
        return GeneralUtils.underline(fieldName).toUpperCase();
    }

    @Override
    public String columnName(Field field) {
        return GeneralUtils.underline(field.getName()).toUpperCase();
    }

    @Override
    public String columnName(MybatisField field) {
        return columnName(field.field());
    }

    @Override
    public String columnName(RestFickle<?> field) {
        return GeneralUtils.underline(field.getName()).toUpperCase();
    }
}
