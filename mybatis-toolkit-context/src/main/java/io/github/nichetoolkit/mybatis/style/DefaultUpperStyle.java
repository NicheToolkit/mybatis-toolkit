package io.github.nichetoolkit.mybatis.style;

import io.github.nichetoolkit.mybatis.MybatisField;
import io.github.nichetoolkit.mybatis.MybatisTableStyle;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;

/**
 * <code>DefaultUpperStyle</code>
 * <p>The default upper style class.</p>
 * @see  io.github.nichetoolkit.mybatis.MybatisTableStyle
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class DefaultUpperStyle implements MybatisTableStyle {
    @Override
    public StyleType getStyleType() {
        return StyleType.UPPER;
    }

    @Override
    public String tableName(Class<?> entityType) {
        return entityType.getSimpleName().toUpperCase();
    }

    @Override
    public String tableAlias(Class<?> entityType) {
        String tableName = entityType.getSimpleName();
        return GeneralUtils.abbreviate(tableName).toUpperCase();
    }

    @Override
    public String columnName(@NonNull String fieldName) {
        return fieldName.toUpperCase();
    }

    @Override
    public String columnName(Field field) {
        return field.getName().toUpperCase();
    }

    @Override
    public String columnName(MybatisField field) {
        return columnName(field.field());
    }

    @Override
    public String columnName(RestFickle<?> field) {
        return field.getName().toUpperCase();
    }

}
