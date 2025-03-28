package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.RestField;
import io.github.nichetoolkit.rice.mapper.TableMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;


@Mapper
public interface MybatisTableMapper extends MybatisMapper<String>, TableMapper {
    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<String> tableColumns(@Param("tablename") String tablename);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    void createIndex(@Param("tablename") String tablename, @Param("field") RestField<?> field);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    void dropIndex(@Param("tablename") String tablename, @Param("field") RestField<?> field);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    void modifyColumn(@Param("tablename") String tablename, @Param("field") RestField<?> field);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    void addColumn(@Param("tablename") String tablename, @Param("field") RestField<?> field);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    void dropColumn(@Param("tablename") String tablename, @Param("field") RestField<?> field);
}
