package io.github.nichetoolkit.mybatis.handler;

import io.github.nichetoolkit.mybatis.MybatisTable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <code>LoadSingleTypeHandler</code>
 * <p>The load single type handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.handler.LoadResultTypeHandler
 * @since Jdk1.8
 */
public class LoadSingleTypeHandler extends LoadResultTypeHandler {

    /**
     * <code>LoadSingleTypeHandler</code>
     * <p>Instantiates a new load single type handler.</p>
     * @param superTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The super table parameter is <code>MybatisTable</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    LoadSingleTypeHandler(MybatisTable superTable) {
        super(superTable);
    }

    @Override
    Object parseResultJson(String json, ResultSet rs, String columnName) throws SQLException {
        return null;
    }

}
