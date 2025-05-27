package io.github.nichetoolkit.mybatis.handler;

import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.rest.util.GeneralUtils;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

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
    @SuppressWarnings(value = "unchecked")
    protected <E> E parseResult(Class<E> entityType,List<?> result) throws SQLException {
        if (GeneralUtils.isNotEmpty(result)) {
            return (E) result.get(0);
        }
        return null;
    }


}
