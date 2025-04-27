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
    List<String> findTableColumns(@Param("tablename") String tablename);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    void createTableIndex(@Param("tablename") String tablename, @Param("field") RestField<?> field);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    void dropTableIndex(@Param("tablename") String tablename, @Param("field") RestField<?> field);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    void modifyTableColumn(@Param("tablename") String tablename, @Param("field") RestField<?> field);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    void addTableColumn(@Param("tablename") String tablename, @Param("field") RestField<?> field);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    void dropTableColumn(@Param("tablename") String tablename, @Param("field") RestField<?> field);
}
