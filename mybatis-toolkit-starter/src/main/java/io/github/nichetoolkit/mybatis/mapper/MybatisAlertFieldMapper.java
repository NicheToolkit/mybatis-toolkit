package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisCaching;
import io.github.nichetoolkit.mybatis.provider.MybatisAlertFieldProvider;
import io.github.nichetoolkit.rice.mapper.AlertFieldMapper;
import io.github.nichetoolkit.rice.mapper.AlertMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Collection;

/**
 * <p>MybatisAlertFieldMapper</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface MybatisAlertFieldMapper<I> extends AlertFieldMapper<I> {

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisAlertFieldProvider.class, method = "alertFieldById")
    Integer alertFieldById(@Param("id") I id, @Param("field") String field, @Param("key") Integer key);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisAlertFieldProvider.class, method = "alertDynamicFieldById")
    Integer alertDynamicFieldById(@Param("tablename") String tablename, @Param("id") I id, @Param("field") String field, @Param("key") Integer key);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisAlertFieldProvider.class, method = "alertFieldAll")
    Integer alertFieldAll(@Param("idList") Collection<I> idList, @Param("field") String field, @Param("key") Integer key);

    @Override
    @Lang(MybatisCaching.class)
    @SelectProvider(type = MybatisAlertFieldProvider.class, method = "alertDynamicFieldAll")
    Integer alertDynamicFieldAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("field") String field, @Param("key") Integer key);

}
