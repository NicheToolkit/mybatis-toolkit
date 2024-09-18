package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.provider.MybatisAlertFieldProvider;
import io.github.nichetoolkit.rice.mapper.AlertFieldMapper;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Collection;

/**
 * <code>MybatisAlertFieldMapper</code>
 * <p>The type mybatis alert field mapper interface.</p>
 * @param <I> {@link java.lang.Object} <p>the parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.mapper.AlertFieldMapper
 * @since Jdk1.8
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
