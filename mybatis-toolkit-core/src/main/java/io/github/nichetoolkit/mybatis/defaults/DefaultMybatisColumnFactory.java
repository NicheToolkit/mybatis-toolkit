package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.*;
import io.github.nichetoolkit.mybatis.stereotype.RestColumn;
import io.github.nichetoolkit.mybatis.stereotype.RestExclude;
import io.github.nichetoolkit.mybatis.stereotype.RestProperty;
import io.github.nichetoolkit.rest.RestException;
import org.apache.ibatis.type.JdbcType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * <p>DefaultMybatisColumnFactory</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class DefaultMybatisColumnFactory implements MybatisColumnFactory {

    @Override
    public Optional<List<MybatisColumn>> createMybatisColumn(MybatisTable mybatisTable, MybatisField field, Chain chain) {
        if (field.isAnnotationPresent(RestColumn.class)) {
            RestColumn restColumn = field.getAnnotation(RestColumn.class);
            MybatisColumn mybatisColumn = MybatisColumn.of(field)
                    .column(restColumn.value().isEmpty() ? MybatisStyle.style(mybatisTable.styleName()).columnName(mybatisTable, field) : restColumn.value())
                    .pkable(restColumn.pkable())
                    .orderBy(restColumn.orderBy())
                    .priority(restColumn.priority())
                    .selectable(restColumn.selectable())
                    .insertable(restColumn.insertable())
                    .updatable(restColumn.updatable())
                    .jdbcType(restColumn.jdbcType())
                    .typeHandler(restColumn.typeHandler())
                    .numericScale(restColumn.numericScale());
            for (RestProperty Property : restColumn.properties()) {
                mybatisColumn.setProperty(Property);
            }
            return Optional.of(Collections.singletonList(mybatisColumn));
        } else if (!field.isAnnotationPresent(RestExclude.class)) {
            return Optional.of(Collections.singletonList(MybatisColumn.of(field)
                    .column(MybatisStyle.style(mybatisTable.styleName()).columnName(mybatisTable, field))
                    .numericScale("")
                    .jdbcType(JdbcType.UNDEFINED)));
        }
        return Optional.empty();
    }
}
