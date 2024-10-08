//package io.github.nichetoolkit.mybatis.provider;
//
//import io.github.nichetoolkit.mybatis.MybatisSqlScript;
//import io.github.nichetoolkit.mybatis.MybatisTable;
//import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
//import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
//import io.github.nichetoolkit.rest.RestException;
//import io.github.nichetoolkit.rest.util.GeneralUtils;
//import io.github.nichetoolkit.rest.util.OptionalUtils;
//import org.apache.ibatis.builder.annotation.ProviderContext;
//
//import java.util.Collection;
//
//public class MybatisDeleteLinkProvider {
//
//
//    public static <I> String deleteByLinkId(ProviderContext providerContext, I linkId) throws RestException {
//        return deleteDynamicByLinkId(providerContext, null, linkId);
//    }
//
//    public static <I> String deleteDynamicByLinkId(ProviderContext providerContext, String tablename, I linkId) throws RestException {
//        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(linkId), "the link id param of 'deleteByLinkId' method cannot be empty!", message -> new MybatisTableErrorException("deleteByLinkId", "linkId", message));
//        return MybatisSqlScript.caching(providerContext, table -> {
//            OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.getOperateColumn()), "the delete column of table with 'deleteByLinkId' method cannot be empty!", message -> new MybatisTableErrorException("deleteByLinkId", "deleteColumn", message));
//            return "DELETE FROM " + table.tablename(tablename)
//                    + " WHERE " + table.getLinkColumn().columnEqualsLink();
//        });
//    }
//
//    public static <I> String deleteAllByLinkIds(ProviderContext providerContext, Collection<I> linkIdList) throws RestException {
//        return deleteDynamicAllByLinkIds(providerContext, null, linkIdList);
//    }
//
//    public static <I> String deleteDynamicAllByLinkIds(ProviderContext providerContext, String tablename, Collection<I> linkIdList) throws RestException {
//        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(linkIdList), "the link id list param of 'deleteAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("deleteAllByLinkIds", "idList", message));
//        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
//            @Override
//            public String sql(MybatisTable table) throws RestException {
//                OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.getOperateColumn()), "the delete column of table with 'deleteAllByLinkIds' method cannot be empty!", message -> new MybatisTableErrorException("deleteAllByLinkIds", "deleteColumn", message));
//                return "DELETE FROM " + table.tablename(tablename)
//                        + " WHERE " + table.getLinkColumn().columnName() + " IN " + foreach("linkIdList", "linkId", ", ", "(", ")", () -> "#{linkId}");
//
//            }
//        });
//    }
//
//
//}
