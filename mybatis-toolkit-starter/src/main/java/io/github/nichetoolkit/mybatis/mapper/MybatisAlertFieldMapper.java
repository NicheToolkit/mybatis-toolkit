//package io.github.nichetoolkit.mybatis.mapper;
//
//import io.github.nichetoolkit.mybatis.MybatisSqlSourceCaching;
//import io.github.nichetoolkit.mybatis.provider.MybatisAlertFieldProvider;
//import io.github.nichetoolkit.rice.mapper.AlertFieldMapper;
//import org.apache.ibatis.annotations.Lang;
//import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.annotations.UpdateProvider;
//
//import java.util.Collection;
//
//public interface MybatisAlertFieldMapper<I> extends AlertFieldMapper<I> {
//
//    @Override
//    @Lang(MybatisSqlSourceCaching.class)
//    @UpdateProvider(type = MybatisAlertFieldProvider.class, method = "alertFieldById")
//    Integer alertFieldById(@Param("id") I id, @Param("field") String field, @Param("key") Integer key);
//
//    @Override
//    @Lang(MybatisSqlSourceCaching.class)
//    @UpdateProvider(type = MybatisAlertFieldProvider.class, method = "alertDynamicFieldById")
//    Integer alertDynamicFieldById(@Param("tablename") String tablename, @Param("id") I id, @Param("field") String field, @Param("key") Integer key);
//
//    @Override
//    @Lang(MybatisSqlSourceCaching.class)
//    @UpdateProvider(type = MybatisAlertFieldProvider.class, method = "alertFieldAll")
//    Integer alertFieldAll(@Param("idList") Collection<I> idList, @Param("field") String field, @Param("key") Integer key);
//
//    @Override
//    @Lang(MybatisSqlSourceCaching.class)
//    @UpdateProvider(type = MybatisAlertFieldProvider.class, method = "alertDynamicFieldAll")
//    Integer alertDynamicFieldAll(@Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("field") String field, @Param("key") Integer key);
//
//}
