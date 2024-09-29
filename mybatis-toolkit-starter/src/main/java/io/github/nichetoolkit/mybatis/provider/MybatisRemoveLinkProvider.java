package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisProviderResolver;
import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Collection;

public class MybatisRemoveLinkProvider implements MybatisProviderResolver {


    public static <I> String removeByLinkId(ProviderContext providerContext, @Param("linkId") I linkId, @Param("logicSign") String logicSign) throws RestException {
        return removeDynamicByLinkId(providerContext, null, linkId, logicSign);
    }

    public static <I> String removeDynamicByLinkId(ProviderContext providerContext, @Param("tablename") String tablename, @Param("linkId") I linkId, @Param("logicSign") String logicSign) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(linkId), "the link id param of 'removeByLinkId' method cannot be empty!", message -> new MybatisParamErrorException("removeByLinkId", "linkId", message));
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(logicSign), "the logicSign param of 'removeByLinkId' method cannot be empty!", message -> new MybatisParamErrorException("removeByLinkId", "logicSign", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.getLogicColumn()), "the logic column of table with 'removeByLinkId' method cannot be empty!", message -> new MybatisTableErrorException("removeByLinkId", "logicColumn", message));
            return "UPDATE " + table.tablename(tablename)
                    + " SET " + table.getLogicColumn().columnEqualsLogic()
                    + " WHERE " + table.getLinkColumn().columnEqualsLink();
        });
    }

    public static <I> String removeAllByLinkIds(ProviderContext providerContext, @Param("linkIdList") Collection<I> linkIdList, @Param("logicSign") String logicSign) throws RestException {
        return removeDynamicAllByLinkIds(providerContext, null, linkIdList, logicSign);
    }

    public static <I> String removeDynamicAllByLinkIds(ProviderContext providerContext, @Param("tablename") String tablename, @Param("linkIdList") Collection<I> linkIdList, @Param("logicSign") String logicSign) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(linkIdList), "the link id list param of 'removeAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("removeAllByLinkIds", "idList", message));
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(logicSign), "the logicSign param of 'removeAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("removeAllByLinkIds", "logicSign", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.getLogicColumn()), "the logic column of table with 'removeAllByLinkIds' method cannot be empty!", message -> new MybatisTableErrorException("removeAllByLinkIds", "logicColumn", message));
                return "UPDATE " + table.tablename(tablename)
                        + " SET " + table.getLogicColumn().columnEqualsLogic()
                        + " WHERE " + table.getLinkColumn().columnName() + " IN " + foreach("linkIdList", "linkId", ", ", "(", ")", () -> "#{linkId}");

            }
        });
    }


}
