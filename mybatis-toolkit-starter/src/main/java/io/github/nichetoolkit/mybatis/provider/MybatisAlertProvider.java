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
 * <p>MybatisAlertProvider</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisAlertProvider {


    public static <I> String alertById(ProviderContext providerContext, @Param("id") I id, @Param("key") Integer key) throws RestException {
        return alertDynamicById(providerContext, null, id, key);
    }

    public static <I> String alertDynamicById(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") I id, @Param("key") Integer key) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(id), "the id param of 'alertById' method cannot be empty!", message -> new MybatisParamErrorException("alertById", "id", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(key), "the key param of 'alertById' method cannot be empty!", message -> new MybatisParamErrorException("alertById", "key", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.getAlertColumn()), "the alert column of table with 'alertById' method cannot be empty!", message -> new MybatisTableErrorException("alertById", "alertColumn", message));
            OptionalHelper.trueable(table.isUseUnionKey(), "the union keys of table with 'alertById' method is unsupported!", message -> new MybatisUnsupportedErrorException("alertById", "unionKeys", message));
            return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tableName())
                    + " SET " + table.getAlertColumn().columnEqualsKey()
                    + " WHERE " + table.getIdentityColumn().columnEqualsProperty();
        });
    }

    public static <I> String alertAll(ProviderContext providerContext, @Param("idList") Collection<I> idList, @Param("key") Integer key) throws RestException {
        return alertDynamicAll(providerContext, null, idList, key);
    }

    public static <I> String alertDynamicAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("key") Integer key) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(idList), "the id list param of 'alertAll' method cannot be empty!", message -> new MybatisParamErrorException("alertAll", "idList", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(key), "the key param of 'alertAll' method cannot be empty!", message -> new MybatisParamErrorException("alertAll", "key", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.getAlertColumn()), "the alert column of table with 'alertAll' method cannot be empty!", message -> new MybatisTableErrorException("alertAll", "alertColumn", message));
                OptionalHelper.trueable(table.isUseUnionKey(), "the union keys of table with 'alertAll' method is unsupported!", message -> new MybatisUnsupportedErrorException("alertAll", "unionKeys", message));
                return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tableName())
                        + " SET " + table.getAlertColumn().columnEqualsKey()
                        + " WHERE " + table.getIdentityColumn().getColumnName() + " IN " + foreach("idList", "id", ", ", "(", ")", () -> table.getIdentityColumn().variable());

            }
        });
    }

}
