package io.github.nichetoolkit.mybatis.handler;

import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;

public class FickleArrayTypeHandler extends BaseTypeHandler<Object> {

    public static final FickleArrayTypeHandler DEFAULT_HANDLER = new FickleArrayTypeHandler();

    private static final ArrayType ARRAY_TYPE = TypeFactory.defaultInstance().constructArrayType(RestFickle.class);

    public FickleArrayTypeHandler() {
    }

    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
//        ps.setArray(i, parameter);
    }


    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.extractArray(rs.getArray(columnName));
    }

    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.extractArray(rs.getArray(columnIndex));
    }

    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.extractArray(cs.getArray(columnIndex));
    }

    protected Object extractArray(Array array) throws SQLException {
        if (array == null) {
            return null;
        } else {
            Object result = array.getArray();
            array.free();
            return result;
        }
    }

}
