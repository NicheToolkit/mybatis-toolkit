package io.github.nichetoolkit.mybatis.defaults.handler;

import io.github.nichetoolkit.mybatis.MybatisColumn;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.handler.AutoResultMapHandler;
import io.github.nichetoolkit.mybatis.handler.FickleArrayTypeHandler;
import io.github.nichetoolkit.rest.error.lack.ConfigureLackError;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * <code>DefaultAutoResultMapHandler</code>
 * <p>The default auto result map handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.handler.AutoResultMapHandler
 * @since Jdk1.8
 */
public class DefaultAutoResultMapHandler implements AutoResultMapHandler {

    /**
     * <code>DEFAULT_HANDLER</code>
     * {@link io.github.nichetoolkit.mybatis.handler.AutoResultMapHandler} <p>The constant <code>DEFAULT_HANDLER</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.handler.AutoResultMapHandler
     */
    public static final AutoResultMapHandler DEFAULT_HANDLER = new DefaultAutoResultMapHandler();


    /**
     * <code>DefaultAutoResultMapHandler</code>
     * <p>Instantiates a new default auto result map handler.</p>
     */
    public DefaultAutoResultMapHandler() {
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    @Override
    public boolean supports(MybatisTable mybatisTable) {
        return GeneralUtils.isNotEmpty(mybatisTable.selectColumns());
    }

    @Override
    public void autoResultMapHandler(Configuration configuration, MybatisTable mybatisTable, List<ResultMapping> resultMappings) {
        List<MybatisColumn> selectColumns = mybatisTable.selectColumns();
        for (MybatisColumn column : selectColumns) {
            String columnName = column.columnName();
            /* 去掉可能存在的分隔符，例如：`order` */
            Matcher matcher = MybatisTable.DELIMITER.matcher(columnName);
            if (matcher.find()) {
                columnName = matcher.group(1);
            }
            String property = column.property();
            if ((column.isSpecialIdentity() || column.isSpecialLinkage() || column.isSpecialAlertness()) && column.isParentNotEmpty()) {
                property = column.property(column.ofParentPrefix(true));
            }
            ResultMapping.Builder builder = new ResultMapping.Builder(configuration, property, columnName, column.javaType());
            if (column.getJdbcType() != null && column.getJdbcType() != JdbcType.UNDEFINED) {
                builder.jdbcType(column.getJdbcType());
            }
            if (column.getTypeHandler() != null && column.getTypeHandler() != UnknownTypeHandler.class) {
                try {
                    TypeHandler<?> typeHandlerInstance = AutoResultMapHandler.getTypeHandlerInstance(column.javaType(), column.getTypeHandler());
                    builder.typeHandler(typeHandlerInstance);
                } catch (TypeException exception) {
                    throw new ConfigureLackError(exception);
                }
            }
            List<ResultFlag> flags = new ArrayList<>();
            if (column.isPrimaryKey() || column.isUnionKey() || column.isIdentityKey()) {
                flags.add(ResultFlag.ID);
            }
            builder.flags(flags);
            resultMappings.add(builder.build());
        }
    }

}
