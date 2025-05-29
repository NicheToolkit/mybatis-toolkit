package io.github.nichetoolkit.mybatis.handler;

import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.rest.util.GeneralUtils;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.List;

/**
 * <code>LoadArrayTypeHandler</code>
 * <p>The load array type handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.handler.LoadResultTypeHandler
 * @since Jdk1.8
 */
public class LoadArrayTypeHandler extends LoadResultTypeHandler {

    /**
     * <code>LoadArrayTypeHandler</code>
     * <p>Instantiates a new load array type handler.</p>
     * @param superTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The super table parameter is <code>MybatisTable</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    LoadArrayTypeHandler(MybatisTable superTable) {
        super(superTable);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    protected <E> E[]  parseResult(Class<E> entityType,List<?> result) throws SQLException {
        if (GeneralUtils.isNotEmpty(result)) {
            E[] entityArray = (E[]) Array.newInstance(entityType);
            result.toArray(entityArray);
        }
        return null;
    }

}
