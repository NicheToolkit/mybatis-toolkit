package io.github.nichetoolkit.mybatis.defaults.handler;

import tools.jackson.databind.JavaType;
import tools.jackson.databind.type.CollectionType;
import tools.jackson.databind.type.MapType;
import io.github.nichetoolkit.mybatis.MybatisColumn;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.handler.*;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

import java.util.List;

/**
 * <code>FickleAutoResultMapHandler</code>
 * <p>The fickle auto result map handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.handler.AutoResultMapHandler
 * @since Jdk17
 */
public class FickleAutoResultMapHandler implements AutoResultMapHandler {

    /**
     * <code>DEFAULT_HANDLER</code>
     * {@link io.github.nichetoolkit.mybatis.handler.AutoResultMapHandler} <p>The constant <code>DEFAULT_HANDLER</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.handler.AutoResultMapHandler
     */
    public static final AutoResultMapHandler DEFAULT_HANDLER = new FickleAutoResultMapHandler();

    /**
     * <code>FickleAutoResultMapHandler</code>
     * <p>Instantiates a new fickle auto result map handler.</p>
     */
    public FickleAutoResultMapHandler() {
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE + 1;
    }

    @Override
    public boolean supports(MybatisTable mybatisTable) {
        return GeneralUtils.isNotEmpty(mybatisTable.getFickleValueColumn());
    }

    @Override
    public void autoResultMapHandler(Configuration configuration, MybatisTable mybatisTable, List<ResultMapping> resultMappings) {
        MybatisColumn column = mybatisTable.getFickleValueColumn();
        JavaType fickleType = column.getFickleType();
        String property = column.property();
        String columnName = column.columnName();
        ResultMapping.Builder builder = new ResultMapping.Builder(configuration, property, columnName, Object.class);
        if (fickleType instanceof MapType) {
            builder.typeHandler(FickleResultTypeHandler.fickleMapHandler());
        } else if (fickleType instanceof CollectionType) {
            builder.typeHandler(FickleResultTypeHandler.fickleListHandler());
        } else {
            builder.typeHandler(FickleResultTypeHandler.fickleArrayHandler());
        }
        resultMappings.add(builder.build());
    }

}
