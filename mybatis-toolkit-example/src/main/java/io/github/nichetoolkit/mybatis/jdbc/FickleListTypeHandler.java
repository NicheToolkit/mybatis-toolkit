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

public class FickleListTypeHandler extends BaseTypeHandler<List<FickleField<?>>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<FickleField<?>> parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public List<FickleField<?>> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public List<FickleField<?>> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public List<FickleField<?>> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return Collections.emptyList();
    }
}
