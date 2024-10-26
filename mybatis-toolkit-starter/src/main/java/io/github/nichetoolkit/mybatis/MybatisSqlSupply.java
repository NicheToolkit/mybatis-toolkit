package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.builder.SqlBuilder;
import io.github.nichetoolkit.rest.RestException;
import org.springframework.lang.Nullable;

public interface MybatisSqlSupply extends MybatisOrder {

    String supply(@Nullable String tablename, MybatisTable table, MybatisSqlScript sqlScript, SqlBuilder sqlBuilder, Object... parameters) throws RestException;

    interface AlertSqlSupply extends ParameterSqlSupply {

        @Override
        default String supply(String tablename, MybatisTable mybatisTable, SqlBuilder sqlBuilder, Object... parameters) throws RestException {
            return supply(tablename, mybatisTable, sqlBuilder, parameters[0]);
        }

        String supply(@Nullable String tablename, MybatisTable mybatisTable, SqlBuilder sqlBuilder, Object statusParameter) throws RestException;
    }

    interface ParameterSqlSupply extends MybatisSqlSupply {

        @Override
        default String supply(String tablename, MybatisTable mybatisTable, MybatisSqlScript sqlScript, SqlBuilder sqlBuilder, Object... parameters) throws RestException {
            return supply(tablename, mybatisTable, sqlBuilder, parameters);
        }

        String supply(@Nullable String tablename, MybatisTable mybatisTable, SqlBuilder sqlBuilder, Object... parameters) throws RestException;
    }

    interface SupplySqlSupply extends MybatisSqlSupply {

        @Override
        default String supply(String tablename, MybatisTable mybatisTable, MybatisSqlScript sqlScript, SqlBuilder sqlBuilder, Object... parameters) throws RestException {
            return supply(tablename, mybatisTable, sqlScript, this, sqlBuilder);
        }

        String supply(@Nullable String tablename, MybatisTable mybatisTable, MybatisSqlScript sqlScript, MybatisSqlSupply sqlSupply, SqlBuilder sqlBuilder) throws RestException;
    }

    interface SimpleSqlSupply extends MybatisSqlSupply {

        @Override
        default String supply(String tablename, MybatisTable mybatisTable, MybatisSqlScript sqlScript, SqlBuilder sqlBuilder, Object... parameters) throws RestException {
            return supply(tablename, mybatisTable, sqlBuilder);
        }

        String supply(@Nullable String tablename, MybatisTable mybatisTable, SqlBuilder sqlBuilder) throws RestException;
    }

}
