//package io.github.nichetoolkit.mybatis.natives;
//
//import io.github.nichetoolkit.mybatis.MybatisCaching;
//import io.github.nichetoolkit.mybatis.provider.natives.MybatisDeleteFilterProvider;
//import io.github.nichetoolkit.rice.IdEntity;
//import io.github.nichetoolkit.rice.filter.IdFilter;
//import io.github.nichetoolkit.rice.mapper.natives.DeleteFilterMapper;
//import org.apache.ibatis.annotations.DeleteProvider;
//import org.apache.ibatis.annotations.Lang;
//import org.apache.ibatis.annotations.Param;
//
//public interface MybatisDeleteFilterMapper<E extends IdEntity<I>, F extends IdFilter<I, K>, I, K> extends DeleteFilterMapper<E,F,I,K> {
//
//    @Override
//    @Lang(MybatisCaching.class)
//    @DeleteProvider(type = MybatisDeleteFilterProvider.class, method = "deleteAllByFilterWhere")
//    Integer deleteAllByFilterWhere(@Param("whereSql") String whereSql, @Param("filter") F filter);
//
//    @Override
//    @Lang(MybatisCaching.class)
//    @DeleteProvider(type = MybatisDeleteFilterProvider.class, method = "deleteDynamicAllByWhere")
//    Integer deleteDynamicAllByFilterWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql, @Param("filter") F filter);
//
//}
