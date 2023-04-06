package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.MybatisStyle;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.MybatisTableFactory;
import io.github.nichetoolkit.mybatis.helper.MybatisHelper;
import io.github.nichetoolkit.mybatis.stereotype.RestProperty;
import io.github.nichetoolkit.mybatis.stereotype.RestTable;
import io.github.nichetoolkit.mybatis.stereotype.table.RestEntity;
import io.github.nichetoolkit.rest.util.GeneralUtils;

/**
 * <p>DefaultMybatisTableFactory</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class DefaultTableFactory implements MybatisTableFactory {

    @Override
    public boolean supports(Class<?> clazz) {
        RestEntity restEntity = clazz.getAnnotation(RestEntity.class);
        return GeneralUtils.isNotEmpty(restEntity) && Object.class != restEntity.entity();
    }

    @Override
    public MybatisTable createTable(Class<?> clazz, Chain chain) {
        RestEntity restEntity = clazz.getAnnotation(RestEntity.class);

        MybatisTable mybatisTable = MybatisTable.of(clazz)
                .table(table.value().isEmpty() ? MybatisStyle.style(table.styleName()).tableName(clazz) : table.value())
                .catalog(table.catalog().isEmpty() ? MybatisHelper.getTableProperties().getCatalog() : table.catalog())
                .schema(table.schema().isEmpty() ? MybatisHelper.getTableProperties().getSchema() : table.schema())
                .styleName(table.styleName())
                .resultMap(table.resultMap())
                .autoResultMap(table.autoResultMap())
                .excludeSuperClasses(table.excludeSuperClasses())
                .excludeFieldTypes(table.excludeFieldTypes())
                .excludeFields(table.excludeFields());
        for (RestProperty property : table.properties()) {
            mybatisTable.setProperty(property);
        }
        return mybatisTable;
    }
}
