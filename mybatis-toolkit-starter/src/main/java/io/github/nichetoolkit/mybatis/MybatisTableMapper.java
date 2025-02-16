package io.github.nichetoolkit.mybatis;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;


@Mapper
public interface MybatisTableMapper extends MybatisMapper<String> {
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<String> tableColumns(@Param("tablename") String tablename);

}
