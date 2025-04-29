package io.github.nichetoolkit.mybatis.handler;

import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rest.util.JsonUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.util.List;

/**
 * <code>FickleListTypeHandler</code>
 * <p>The fickle list type handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see org.apache.ibatis.type.BaseTypeHandler
 * @since Jdk1.8
 */
public class FickleListTypeHandler extends BaseTypeHandler<Object> {
    /**
     * <code>DEFAULT_HANDLER</code>
     * {@link io.github.nichetoolkit.mybatis.handler.FickleListTypeHandler} <p>The constant <code>DEFAULT_HANDLER</code> field.</p>
     */
    public static final FickleListTypeHandler DEFAULT_HANDLER = new FickleListTypeHandler();

    /**
     * <code>COLLECTION_TYPE</code>
     * {@link com.fasterxml.jackson.databind.type.CollectionType} <p>The constant <code>COLLECTION_TYPE</code> field.</p>
     * @see com.fasterxml.jackson.databind.type.CollectionType
     */
    private static final CollectionType COLLECTION_TYPE = TypeFactory.defaultInstance().constructCollectionType(List.class, RestFickle.OfRestFickle.class);

    /**
     * <code>FickleListTypeHandler</code>
     * <p>Instantiates a new fickle list type handler.</p>
     */
    public FickleListTypeHandler() {
    }

    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        throw new SQLException("The 'setNonNullParameter' method of 'FickleListTypeHandler' is not supported");
    }

    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return JsonUtils.parseList(json,COLLECTION_TYPE);
    }

    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return JsonUtils.parseList(json,COLLECTION_TYPE);
    }

    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return JsonUtils.parseList(json,COLLECTION_TYPE);
    }
}
