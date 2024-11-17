package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rice.RestStyle;

public interface MybatisTableStyle extends RestStyle {

    String tableName(Class<?> entityType);

    String tableAlias(Class<?> entityType);

    String columnName(MybatisField field);

    String columnName(MybatisTable table, MybatisField field);
}
