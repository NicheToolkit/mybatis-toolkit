package io.github.nichetoolkit.mybatis.handler;

import io.github.nichetoolkit.mybatis.MybatisTable;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;

/**
 * <code>LoadResultTypeHandler</code>
 * <p>The load result type handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see org.apache.ibatis.type.BaseTypeHandler
 * @since Jdk1.8
 */
public abstract class LoadResultTypeHandler extends BaseTypeHandler<Object> {
    /**
     * <code>superTable</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The <code>superTable</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    protected final MybatisTable superTable;

    /**
     * <code>LoadResultTypeHandler</code>
     * <p>Instantiates a new load result type handler.</p>
     * @param superTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The super table parameter is <code>MybatisTable</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    public LoadResultTypeHandler(MybatisTable superTable) {
        this.superTable = superTable;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        throw new SQLException("The 'setNonNullParameter' method of 'LoadResultTypeHandler' is not supported");
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return parseResultJson(json,rs,columnName);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        throw new SQLException("The 'getNullableResult' method of 'LoadResultTypeHandler' with 'columnIndex' is not supported");
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        throw new SQLException("The 'getNullableResult' method of 'LoadResultTypeHandler' with 'callableStatement' is not supported");
    }

    abstract Object parseResultJson(String json, ResultSet rs, String columnName) throws SQLException;

    /**
     * <code>multiResultHandler</code>
     * <p>The multi result handler method.</p>
     * @param superTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The super table parameter is <code>MybatisTable</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.handler.LoadResultTypeHandler} <p>The multi result handler return object is <code>LoadResultTypeHandler</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    public static LoadResultTypeHandler multiResultHandler(MybatisTable superTable) {
        return new LoadMultiTypeHandler(superTable);
    }

    /**
     * <code>singleResultHandler</code>
     * <p>The single result handler method.</p>
     * @param superTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The super table parameter is <code>MybatisTable</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.handler.LoadResultTypeHandler} <p>The single result handler return object is <code>LoadResultTypeHandler</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    public static LoadResultTypeHandler singleResultHandler(MybatisTable superTable) {
        return new LoadSingleTypeHandler(superTable);
    }

    /**
     * <code>arrayResultHandler</code>
     * <p>The array result handler method.</p>
     * @param superTable {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The super table parameter is <code>MybatisTable</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.handler.LoadResultTypeHandler} <p>The array result handler return object is <code>LoadResultTypeHandler</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    public static LoadResultTypeHandler arrayResultHandler(MybatisTable superTable) {
        return new LoadArrayTypeHandler(superTable);
    }

}
