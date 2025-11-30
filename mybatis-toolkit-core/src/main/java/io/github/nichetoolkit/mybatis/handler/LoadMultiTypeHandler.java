package io.github.nichetoolkit.mybatis.handler;

import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.rest.util.GeneralUtils;

import java.sql.*;
import java.util.*;

/**
 * <code>LoadMultiTypeHandler</code>
 * <p>The load multi type handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.handler.LoadResultTypeHandler
 * @since Jdk17
 */
public class LoadMultiTypeHandler extends LoadResultTypeHandler {

    /**
     * <code>LoadMultiTypeHandler</code>
     * <p>Instantiates a new load multi type handler.</p>
     * @param superTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The super table parameter is <code>MybatisTable</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    LoadMultiTypeHandler(MybatisTable superTable) {
        super(superTable);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    protected <E> List<E> parseResult(Class<E> entityType, List<?> result) throws SQLException {
        if (GeneralUtils.isNotEmpty(result)) {
            return (List<E>) result;
        }
        return null;
    }


}
