package io.github.nichetoolkit.mybatis.handler;

import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rest.util.JsonUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class FickleMapTypeHandler extends BaseTypeHandler<Object> {

    public static final FickleMapTypeHandler DEFAULT_HANDLER = new FickleMapTypeHandler();

    private static final MapType MAP_TYPE = TypeFactory.defaultInstance().constructMapType(Map.class, String.class, RestFickle.class);

    public FickleMapTypeHandler() {
    }

    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
//        ps.setArray(i, parameter);
    }

    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return JsonUtils.parseMap(json,MAP_TYPE);
    }

    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return JsonUtils.parseMap(json,MAP_TYPE);
    }

    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return JsonUtils.parseMap(json,MAP_TYPE);
    }
}
