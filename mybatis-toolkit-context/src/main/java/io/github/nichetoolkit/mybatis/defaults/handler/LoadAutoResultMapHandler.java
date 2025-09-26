package io.github.nichetoolkit.mybatis.defaults.handler;

import io.github.nichetoolkit.mybatis.MybatisColumn;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.consts.EntityConstants;
import io.github.nichetoolkit.mybatis.handler.*;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
            Class<?> entryType = entry.getKey();
            MybatisColumn column = entry.getValue();
            Class<?> fieldType = column.getField().fieldType();
            Set<String> loadKeys = column.getLoadKeys();
            MybatisColumn loadKey = destineColumnOfLoadKey(loadKeys, entryType, loadKeyColumns);
            MybatisColumn loadParam = destineColumnOfLoadKey(loadKeys, entryType, loadParamColumns);
            if (GeneralUtils.isEmpty(loadKey) && GeneralUtils.isEmpty(loadParam)) {
                continue;
            }
            String columnName = EntityConstants.LOADS;
            if (GeneralUtils.isNotEmpty(loadKey)) {
                columnName = loadKey.columnName();
            }
            column.addLoadKey(columnName);
            String property = column.property();
            ResultMapping.Builder builder = new ResultMapping.Builder(configuration, property, columnName, entryType);
            if (Collection.class.isAssignableFrom(fieldType)) {
                builder.typeHandler(LoadResultTypeHandler.multiResultHandler(mybatisTable));
                resultMappings.add(builder.build());
            } else if (fieldType.isArray()) {
                builder.typeHandler(LoadResultTypeHandler.arrayResultHandler(mybatisTable));
                resultMappings.add(builder.build());
            } else if (fieldType == entryType) {
                builder.typeHandler(LoadResultTypeHandler.singleResultHandler(mybatisTable));
                resultMappings.add(builder.build());
            }
        }
    }

    /**
     * <code>destineColumnOfLoadKey</code>
     * <p>The destine column of load key method.</p>
     * @param keys           {@link java.util.Set} <p>The keys parameter is <code>Set</code> type.</p>
     * @param type           {@link java.lang.Class} <p>The type parameter is <code>Class</code> type.</p>
     * @param loadKeyColumns {@link java.util.List} <p>The load key columns parameter is <code>List</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>The destine column of load key return object is <code>MybatisColumn</code> type.</p>
     * @see java.util.Set
     * @see java.lang.Class
     * @see java.util.List
     * @see io.github.nichetoolkit.mybatis.MybatisColumn
     */
    private MybatisColumn destineColumnOfLoadKey(Set<String> keys, Class<?> type, List<MybatisColumn> loadKeyColumns) {
        for (MybatisColumn mybatisColumn : loadKeyColumns) {
            List<Class<?>> loadTypes = mybatisColumn.getLoadTypes();
            if (loadTypes.contains(type)) {
                return mybatisColumn;
            }
            Set<String> loadKeys = mybatisColumn.getLoadKeys();
            for (String key : keys) {
                if (loadKeys.contains(key)) {
                    return mybatisColumn;
                }
            }
        }
        return null;
    }

}
