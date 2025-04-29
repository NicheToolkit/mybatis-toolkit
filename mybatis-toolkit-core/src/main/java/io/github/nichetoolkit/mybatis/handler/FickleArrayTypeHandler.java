package io.github.nichetoolkit.mybatis.handler;

import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rest.util.JsonUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;

/**
 * <code>FickleArrayTypeHandler</code>
 * <p>The fickle array type handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see org.apache.ibatis.type.BaseTypeHandler
 * @since Jdk1.8
 */
public class FickleArrayTypeHandler extends BaseTypeHandler<Object> {

    /**
     * <code>DEFAULT_HANDLER</code>
     * {@link io.github.nichetoolkit.mybatis.handler.FickleArrayTypeHandler} <p>The constant <code>DEFAULT_HANDLER</code> field.</p>
     */
    public static final FickleArrayTypeHandler DEFAULT_HANDLER = new FickleArrayTypeHandler();

    /**
     * <code>ARRAY_TYPE</code>
     * {@link com.fasterxml.jackson.databind.type.ArrayType} <p>The constant <code>ARRAY_TYPE</code> field.</p>
     * @see com.fasterxml.jackson.databind.type.ArrayType
     */
    private static final ArrayType ARRAY_TYPE = TypeFactory.defaultInstance().constructArrayType(RestFickle.OfRestFickle.class);

    /**
     * <code>FickleArrayTypeHandler</code>
     * <p>Instantiates a new fickle array type handler.</p>
     */
    public FickleArrayTypeHandler() {
    }

    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        throw new SQLException("The 'setNonNullParameter' method of 'FickleArrayTypeHandler' is not supported");
    }


    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return JsonUtils.parseArray(json, ARRAY_TYPE);
    }

    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return JsonUtils.parseArray(json, ARRAY_TYPE);
    }

    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return JsonUtils.parseArray(json, ARRAY_TYPE);
    }

}
