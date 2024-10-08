//package io.github.nichetoolkit.mybatis.provider;
//
//import io.github.nichetoolkit.mybatis.RestSqlProvider;
//import io.github.nichetoolkit.mybatis.MybatisSqlScript;
//import io.github.nichetoolkit.mybatis.MybatisTable;
//import io.github.nichetoolkit.mybatis.enums.DatabaseType;
//import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
//import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
//import io.github.nichetoolkit.rest.RestException;
//import io.github.nichetoolkit.rest.util.GeneralUtils;
//import io.github.nichetoolkit.rest.util.OptionalUtils;
//import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.builder.annotation.ProviderContext;
//
//import java.util.Collection;
//
//public class MybatisOperateProvider {
//
//
//    public static <I> String operateById(ProviderContext providerContext, @Param("id") I id, @Param("operate") Integer operate) throws RestException {
//        return operateDynamicById(providerContext, null, id, operate);
//    }
//
//    public static <I> String operateDynamicById(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") I id, @Param("operate") Integer operate) throws RestException {
//        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(id), "the id param of 'operateById' method cannot be empty!", message -> new MybatisTableErrorException("operateById", "id", message));
//        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(operate), "the operate param of 'operateById' method cannot be empty!", message -> new MybatisParamErrorException("operateById", "operate", message));
//        return MybatisSqlScript.caching(providerContext, table -> {
//            OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.getOperateColumn()), "the operate column of table with 'operateById' method cannot be empty!", message -> new MybatisTableErrorException("operateById", "operateColumn", message));
//            return "UPDATE " + table.tablename(tablename)
//                    + " SET " + table.getOperateColumn().columnEqualsOperate()
//                    + " WHERE " + table.sqlOfIdentityColumns();
//        });
//    }
//
//    public static <I> String operateAll(ProviderContext providerContext, @Param("idList") Collection<I> idList, @Param("operate") Integer operate) throws RestException {
//        return operateDynamicAll(providerContext, null, idList, operate);
//    }
//
//    public static <I> String operateDynamicAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("operate") Integer operate) throws RestException {
//        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(idList), "the id list param of 'operateAll' method cannot be empty!", message -> new MybatisParamErrorException("operateAll", "idList", message));
//        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(operate), "the operate param of 'operateAll' method cannot be empty!", message -> new MybatisParamErrorException("operateAll", "operate", message));
//
//        return RestSqlProvider.providing(providerContext,tablename,idList, new RestSqlProvider() {
//
//            @Override
//            public DatabaseType databaseType() throws RestException {
//                return DatabaseType.POSTGRESQL;
//            }
//
//            @Override
//            public <IDENTITY> String provide(String tablename, MybatisTable table, String whereSql, MybatisSqlScript sqlScript) throws RestException {
//                OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.getOperateColumn()), "the operate column of table with 'operateAll' method cannot be empty!", message -> new MybatisTableErrorException("operateAll", "operateColumn", message));
//                return "UPDATE " + table.tablename(tablename)
//                        + " SET " + table.getOperateColumn().columnEqualsOperate()
//                        + " WHERE " + whereSql;
//
//            }
//        });
//    }
//
//    public static String operateAllByWhere(ProviderContext providerContext, @Param("whereSql") String whereSql, @Param("operate") Integer operate) throws RestException {
//        return operateDynamicAllByWhere(providerContext, null, whereSql, operate);
//    }
//
//    public static String operateDynamicAllByWhere(ProviderContext providerContext, @Param("tablename") String tablename, @Param("whereSql") String whereSql, @Param("operate") Integer operate) throws RestException {
//        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(whereSql), "the where sql param of 'operateAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("operateAllByWhere", "whereSql", message));
//        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(operate), "the operate param of 'operateAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("operateAllByWhere", "operate", message));
//        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
//            @Override
//            public String sql(MybatisTable table) throws RestException {
//                OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.getOperateColumn()), "the operate column of table with 'operateAllByWhere' method cannot be empty!", message -> new MybatisTableErrorException("operateAllByWhere", "operateColumn", message));
//                return "UPDATE " + table.tablename(tablename)
//                        + " SET " + table.getOperateColumn().columnEqualsOperate()
//                        + " WHERE 1=1 "
//                        + whereSql;
//            }
//        });
//    }
//
//
//}
