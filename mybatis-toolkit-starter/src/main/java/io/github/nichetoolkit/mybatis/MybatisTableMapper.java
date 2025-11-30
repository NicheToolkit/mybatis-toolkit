package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.RestField;
import io.github.nichetoolkit.rice.mapper.TableMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;


/**
 * <code>MybatisTableMapper</code>
 * <p>The mybatis table mapper interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisMapper
 * @see io.github.nichetoolkit.rice.mapper.TableMapper
 * @see org.apache.ibatis.annotations.Mapper
 * @since Jdk17
 */
@Mapper
public interface MybatisTableMapper extends MybatisMapper<String>, TableMapper {
    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    List<String> findTableColumns(@Param("tableName") String tableName);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    void createTableIndex(@Param("tableName") String tableName, @Param("field") RestField<?> field);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    void dropTableIndex(@Param("tableName") String tableName, @Param("field") RestField<?> field);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    void modifyTableColumn(@Param("tableName") String tableName, @Param("field") RestField<?> field);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    void addTableColumn(@Param("tableName") String tableName, @Param("field") RestField<?> field);

    @Override
    @Lang(MybatisSqlSourceCaching.class)
    @SelectProvider(MybatisSqlProviderResolver.class)
    void dropTableColumn(@Param("tableName") String tableName, @Param("field") RestField<?> field);
}
