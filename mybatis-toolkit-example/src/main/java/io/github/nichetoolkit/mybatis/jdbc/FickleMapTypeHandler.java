package io.github.nichetoolkit.mybatis.jdbc;

import io.github.nichetoolkit.mybatis.fickle.FickleField;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FickleMapTypeHandler extends BaseTypeHandler<Map<String,FickleField<?>>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<String, FickleField<?>> parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public Map<String, FickleField<?>> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, FickleField<?>> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, FickleField<?>> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return Collections.emptyMap();
    }
}
