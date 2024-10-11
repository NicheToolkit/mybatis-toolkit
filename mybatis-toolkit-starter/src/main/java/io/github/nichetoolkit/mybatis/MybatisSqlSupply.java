package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.RestException;
import org.springframework.lang.Nullable;

public interface MybatisSqlSupply extends MybatisOrder {

    String supply(@Nullable String tablename, MybatisTable table, String whereSql, MybatisSqlScript sqlScript) throws RestException;

    interface SimpleSqlProvider extends MybatisSqlSupply {

        @Override
        default String supply(@Nullable String tablename, MybatisTable mybatisTable, String whereSql, MybatisSqlScript sqlScript) throws RestException {
            return supply(tablename, mybatisTable, whereSql, sqlScript, this);
        }

        String supply(@Nullable String tablename, MybatisTable mybatisTable, String whereSql, MybatisSqlScript sqlScript, MybatisSqlSupply sqlProvider) throws RestException;

    }

}