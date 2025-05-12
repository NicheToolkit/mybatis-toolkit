package io.github.nichetoolkit.mybatis.defaults.handler;

import io.github.nichetoolkit.mybatis.MybatisColumn;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.handler.*;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <code>LoadAutoResultMapHandler</code>
 * <p>The load auto result map handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.handler.AutoResultMapHandler
 * @since Jdk1.8
 */
public class LoadAutoResultMapHandler implements AutoResultMapHandler {

    /**
     * <code>DEFAULT_HANDLER</code>
     * {@link io.github.nichetoolkit.mybatis.handler.AutoResultMapHandler} <p>The constant <code>DEFAULT_HANDLER</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.handler.AutoResultMapHandler
     */
    public static final AutoResultMapHandler DEFAULT_HANDLER = new LoadAutoResultMapHandler();

    /**
     * <code>LoadAutoResultMapHandler</code>
     * <p>Instantiates a new load auto result map handler.</p>
     */
    public LoadAutoResultMapHandler() {
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE + 2;
    }

    @Override
    public boolean supports(MybatisTable mybatisTable) {
        return GeneralUtils.isNotEmpty(mybatisTable.getLoadColumns());
    }

    @Override
    public void autoResultMapHandler(Configuration configuration, MybatisTable mybatisTable, List<ResultMapping> resultMappings) {
        List<MybatisColumn> loadKeyColumns = mybatisTable.loadKeyColumns();
        List<MybatisColumn> loadParamColumns = mybatisTable.loadParamColumns();
        for (Map.Entry<Class<?>, MybatisColumn> entry : mybatisTable.getLoadColumns().entrySet()) {
            Class<?> entryKey = entry.getKey();
            MybatisColumn column = entry.getValue();
            Class<?> fieldType = column.getField().fieldType();
            String property = column.property();
            String columnName = column.columnName();
            ResultMapping.Builder builder = new ResultMapping.Builder(configuration, property, columnName, entryKey);
            if (Collection.class.isAssignableFrom(fieldType)) {
                builder.typeHandler(MultiResultTypeHandler.DEFAULT_HANDLER);
                resultMappings.add(builder.build());
            } else if (fieldType.isArray()) {
                builder.typeHandler(ArrayResultTypeHandler.DEFAULT_HANDLER);
                resultMappings.add(builder.build());
            } else if (fieldType == entryKey) {
                builder.typeHandler(SingleResultTypeHandler.DEFAULT_HANDLER);
                resultMappings.add(builder.build());
            }
        }
    }

}
