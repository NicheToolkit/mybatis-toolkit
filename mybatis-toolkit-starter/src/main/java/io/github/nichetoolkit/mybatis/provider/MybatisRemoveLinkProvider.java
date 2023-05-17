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
public class MybatisRemoveLinkProvider {


    public static <I> String removeByLinkId(ProviderContext providerContext, @Param("linkId") I linkId, @Param("sign") String sign) throws RestException {
        return removeDynamicByLinkId(providerContext, null, linkId, sign);
    }

    public static <I> String removeDynamicByLinkId(ProviderContext providerContext, @Param("tablename") String tablename, @Param("linkId") I linkId, @Param("sign") String sign) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(linkId), "the link id param of 'removeByLinkId' method cannot be empty!", message -> new MybatisParamErrorException("removeByLinkId", "linkId", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(sign), "the sign param of 'removeByLinkId' method cannot be empty!", message -> new MybatisParamErrorException("removeByLinkId", "sign", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.getLogicColumn()), "the logic column of table with 'removeByLinkId' method cannot be empty!", message -> new MybatisTableErrorException("removeByLinkId", "logicColumn", message));
            return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tableName())
                    + " SET " + table.getLogicColumn().columnEqualsSign()
                    + " WHERE " + table.getLinkColumn().columnEqualsLink();
        });
    }

    public static <I> String removeAllByLinkIds(ProviderContext providerContext, @Param("linkIdList") Collection<I> linkIdList, @Param("sign") String sign) throws RestException {
        return removeDynamicAllByLinkIds(providerContext, null, linkIdList, sign);
    }

    public static <I> String removeDynamicAllByLinkIds(ProviderContext providerContext, @Param("tablename") String tablename, @Param("linkIdList") Collection<I> linkIdList, @Param("sign") String sign) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(linkIdList), "the link id list param of 'removeAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("removeAllByLinkIds", "idList", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(sign), "the sign param of 'removeAllByLinkIds' method cannot be empty!", message -> new MybatisParamErrorException("removeAllByLinkIds", "sign", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.getLogicColumn()), "the logic column of table with 'removeAllByLinkIds' method cannot be empty!", message -> new MybatisTableErrorException("removeAllByLinkIds", "logicColumn", message));
                return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tableName())
                        + " SET " + table.getLogicColumn().columnEqualsSign()
                        + " WHERE " + table.getLinkColumn().getColumnName() + " IN " + foreach("linkIdList", "linkId", ", ", "(", ")", () -> "#{linkId}");

            }
        });
    }


}
