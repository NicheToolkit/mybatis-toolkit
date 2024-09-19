//package io.github.nichetoolkit.mybatis.provider.natives;
//
//import io.github.nichetoolkit.mybatis.MybatisSqlScript;
//import io.github.nichetoolkit.mybatis.MybatisTable;
//import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
//import io.github.nichetoolkit.mybatis.error.MybatisUnsupportedErrorException;
//import io.github.nichetoolkit.rest.RestException;
//import io.github.nichetoolkit.rest.util.GeneralUtils;
//import io.github.nichetoolkit.rest.util.OptionalUtils;
//import io.github.nichetoolkit.rice.IdEntity;
//import io.github.nichetoolkit.rice.filter.IdFilter;
//import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.builder.annotation.ProviderContext;
//
//import java.util.Collection;
//import java.util.Optional;
//
//public class MybatisDeleteFilterProvider {
//
//    public static <E extends IdEntity<I>, F extends IdFilter<I, K>, I, K> String deleteAllByFilterWhere(ProviderContext providerContext, @Param("whereSql") String whereSql, @Param("filter") F filter) throws RestException {
//        return deleteDynamicAllByFilterWhere(providerContext, null, whereSql);
//    }
//
//    public static <E extends IdEntity<I>, F extends IdFilter<I, K>, I, K> String deleteDynamicAllByFilterWhere(ProviderContext providerContext, @Param("tablename") String tablename, @Param("whereSql") String whereSql) throws RestException {
//        OptionalUtils.falseable(GeneralUtils.isNotEmpty(whereSql), "the where sql param of 'deleteAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("deleteAllByWhere", "whereSql", message));
//        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
//            @Override
//            public String sql(MybatisTable table) throws RestException {
//                return "DELETE FROM " + table.tablename(tablename)
//                        + " WHERE 1=1 "
//                        + ifTest("whereSql!=null", () -> "${whereSql}");
//            }
//        });
//    }
//
//
//}
