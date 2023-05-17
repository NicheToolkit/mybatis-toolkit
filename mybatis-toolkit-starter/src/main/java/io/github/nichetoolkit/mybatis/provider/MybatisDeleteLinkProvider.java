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
public class MybatisDeleteLinkProvider {


    public static <I> String deleteByLinkId(ProviderContext providerContext, @Param("linkId") I linkId) throws RestException {
        return deleteDynamicByLinkId(providerContext, null, linkId);
    }

    public static <I> String deleteDynamicByLinkId(ProviderContext providerContext, @Param("tablename") String tablename, @Param("linkId") I linkId) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(linkId), "the link id param of 'deleteByLinkId' method cannot be empty!", message -> new MybatisTableErrorException("deleteByLinkId", "linkId", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.getOperateColumn()), "the delete column of table with 'deleteByLinkId' method cannot be empty!", message -> new MybatisTableErrorException("deleteByLinkId", "deleteColumn", message));
            return "DELETE FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                    + " WHERE " + table.getLinkColumn().columnEqualsLink();
        });
    }

    public static <I> String deleteAllByLinkIds(ProviderContext providerContext, @Param("linkIdList") Collection<I> linkIdList) throws RestException {
        return deleteDynamicAllByLinkIds(providerContext, null, linkIdList);
    }

    public static <I> String deleteDynamicAllByLinkIds(ProviderContext providerContext, @Param("tablename") String tablename, @Param("linkIdList") Collection<I> linkIdList) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(linkIdList), "the link id list param of 'deleteAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("deleteAllByLinkIds", "idList", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.getOperateColumn()), "the delete column of table with 'deleteAllByLinkIds' method cannot be empty!", message -> new MybatisTableErrorException("deleteAllByLinkIds", "deleteColumn", message));
                return "DELETE FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                        + " WHERE " + table.getLinkColumn().getColumnName() + " IN " + foreach("linkIdList", "linkId", ", ", "(", ")", () -> "#{linkId}");

            }
        });
    }


}
