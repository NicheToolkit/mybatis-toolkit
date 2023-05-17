package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisUnsupportedErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.helper.OptionalHelper;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Collection;
import java.util.Optional;

/**
 * <p>MybatisRemoveLinkProvider</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisOperateLinkProvider {


    public static <I> String operateByLinkId(ProviderContext providerContext, @Param("linkId") I linkId, @Param("operate") Integer operate) throws RestException {
        return operateDynamicByLinkId(providerContext, null, linkId, operate);
    }

    public static <I> String operateDynamicByLinkId(ProviderContext providerContext, @Param("tablename") String tablename, @Param("linkId") I linkId, @Param("operate") Integer operate) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(linkId), "the link id param of 'operateByLinkId' method cannot be empty!", message -> new MybatisTableErrorException("operateByLinkId", "linkId", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(operate), "the operate param of 'operateByLinkId' method cannot be empty!", message -> new MybatisParamErrorException("operateByLinkId", "operate", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.getOperateColumn()), "the operate column of table with 'operateByLinkId' method cannot be empty!", message -> new MybatisTableErrorException("operateByLinkId", "operateColumn", message));
            return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tableName())
                    + " SET " + table.getOperateColumn().columnEqualsOperate()
                    + " WHERE " + table.getLinkColumn().columnEqualsLink();
        });
    }

    public static <I> String operateAllByLinkIds(ProviderContext providerContext, @Param("linkIdList") Collection<I> linkIdList, @Param("operate") Integer operate) throws RestException {
        return operateDynamicAllByLinkIds(providerContext, null, linkIdList, operate);
    }

    public static <I> String operateDynamicAllByLinkIds(ProviderContext providerContext, @Param("tablename") String tablename, @Param("linkIdList") Collection<I> linkIdList, @Param("operate") Integer operate) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(linkIdList), "the link id list param of 'operateAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("operateAllByLinkIds", "idList", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(operate), "the operate param of 'operateAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("operateAllByLinkIds", "operate", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.getOperateColumn()), "the operate column of table with 'operateAllByLinkIds' method cannot be empty!", message -> new MybatisTableErrorException("operateAllByLinkIds", "operateColumn", message));
                return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tableName())
                        + " SET " + table.getOperateColumn().columnEqualsOperate()
                        + " WHERE " + table.getLinkColumn().getColumnName() + " IN " + foreach("linkIdList", "linkId", ", ", "(", ")", () -> "#{linkId}");

            }
        });
    }


}
