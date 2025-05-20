package io.github.nichetoolkit.mybatis.handler;

import io.github.nichetoolkit.mybatis.MybatisColumn;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.consts.EntityConstants;
import io.github.nichetoolkit.mybatis.load.RestLoad;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.JsonUtils;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * <code>LoadMultiTypeHandler</code>
 * <p>The load multi type handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.handler.LoadResultTypeHandler
 * @since Jdk1.8
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
    Object parseResultJson(String json, ResultSet rs, String columnName) throws SQLException {
        String loadsJson = rs.getString(EntityConstants.LOADS);
        List<RestLoad.OfRestLoad> restLoads = JsonUtils.parseList(loadsJson, RestLoad.OfRestLoad.class);
        Map<Class<?>, MybatisColumn> loadColumns = superTable.getLoadColumns();
        if (GeneralUtils.isEmpty(restLoads)) {
            return null;
        }

        return null;
    }

//    MybatisColumn destineColumnOfLoadKey() {
//
//    }

}
