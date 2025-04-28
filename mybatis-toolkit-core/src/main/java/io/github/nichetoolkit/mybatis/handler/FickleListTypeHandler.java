package io.github.nichetoolkit.mybatis.handler;

import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rest.util.JsonUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.util.List;

public class FickleListTypeHandler extends BaseTypeHandler<Object> {
    public static final FickleListTypeHandler DEFAULT_HANDLER = new FickleListTypeHandler();

    private static final CollectionType COLLECTION_TYPE = TypeFactory.defaultInstance().constructCollectionType(List.class, RestFickle.class);

    public FickleListTypeHandler() {
    }

    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
//        ps.setArray(i, parameter);
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
