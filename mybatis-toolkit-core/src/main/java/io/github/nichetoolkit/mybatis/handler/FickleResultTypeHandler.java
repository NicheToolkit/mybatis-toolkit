package io.github.nichetoolkit.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <code>FickleResultTypeHandler</code>
 * <p>The fickle result type handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see org.apache.ibatis.type.BaseTypeHandler
 * @since Jdk17
 */
public abstract class FickleResultTypeHandler extends BaseTypeHandler<Object> {

    /**
     * <code>FickleResultTypeHandler</code>
     * <p>Instantiates a new fickle result type handler.</p>
     */
    public FickleResultTypeHandler() {
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        throw new SQLException("The 'setNonNullParameter' method of 'FickleResultTypeHandler' is not supported");
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return parseResultJson(json);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return parseResultJson(json);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return parseResultJson(json);
    }

    /**
     * <code>parseResultJson</code>
     * <p>The parse result json method.</p>
     * @param json {@link java.lang.String} <p>The json parameter is <code>String</code> type.</p>
     * @return {@link java.lang.Object} <p>The parse result json return object is <code>Object</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Object
     */
    abstract Object parseResultJson(String json);

    /**
     * <code>fickleMapHandler</code>
     * <p>The fickle map handler method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.handler.FickleResultTypeHandler} <p>The fickle map handler return object is <code>FickleResultTypeHandler</code> type.</p>
     */
    public static FickleResultTypeHandler fickleMapHandler() {
        return new FickleMapTypeHandler();
    }

    /**
     * <code>fickleListHandler</code>
     * <p>The fickle list handler method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.handler.FickleResultTypeHandler} <p>The fickle list handler return object is <code>FickleResultTypeHandler</code> type.</p>
     */
    public static FickleResultTypeHandler fickleListHandler() {
        return new FickleListTypeHandler();
    }

    /**
     * <code>fickleArrayHandler</code>
     * <p>The fickle array handler method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.handler.FickleResultTypeHandler} <p>The fickle array handler return object is <code>FickleResultTypeHandler</code> type.</p>
     */
    public static FickleResultTypeHandler fickleArrayHandler() {
        return new FickleArrayTypeHandler();
    }

}
