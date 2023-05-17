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
 * <p>MybatisAlertFieldProvider</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisAlertFieldProvider {


    public static <I> String alertFieldById(ProviderContext providerContext, @Param("id") I id, @Param("field") String field, @Param("key") Integer key) throws RestException {
        return alertDynamicFieldById(providerContext, null, id, field, key);
    }

    public static <I> String alertDynamicFieldById(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") I id, @Param("field") String field, @Param("key") Integer key) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(id), "the id param of 'alertFieldById' method cannot be empty!", message -> new MybatisParamErrorException("alertFieldById", "id", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(key), "the field param of 'alertFieldById' method cannot be empty!", message -> new MybatisParamErrorException("alertFieldById", "field", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(key), "the key param of 'alertFieldById' method cannot be empty!", message -> new MybatisParamErrorException("alertFieldById", "key", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalHelper.trueable(table.isUseUnionKey(), "the union keys of table with 'alertFieldById' method is unsupported!", message -> new MybatisUnsupportedErrorException("alertFieldById", "unionKeys", message));
            return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tableName())
                    + " SET ${field} = ${key} "
                    + " WHERE " + table.getIdentityColumn().columnEqualsProperty();
        });
    }

    public static <I> String alertFieldAll(ProviderContext providerContext, @Param("idList") Collection<I> idList, @Param("field") String field, @Param("key") Integer key) throws RestException {
        return alertDynamicFieldAll(providerContext, null, idList, field, key);
    }

    public static <I> String alertDynamicFieldAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("field") String field, @Param("key") Integer key) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(idList), "the id list param of 'alertFieldAll' method cannot be empty!", message -> new MybatisParamErrorException("alertFieldAll", "idList", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(field), "the field param of 'alertFieldAll' method cannot be empty!", message -> new MybatisParamErrorException("alertFieldAll", "field", message));
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(key), "the key param of 'alertFieldAll' method cannot be empty!", message -> new MybatisParamErrorException("alertFieldAll", "key", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalHelper.trueable(table.isUseUnionKey(), "the union keys of table with 'alertFieldAll' method is unsupported!", message -> new MybatisUnsupportedErrorException("alertFieldAll", "unionKeys", message));
                return "UPDATE " + Optional.ofNullable(tablename).orElse(table.tableName())
                        + " SET ${field} = ${key} "
                        + " WHERE " + table.getIdentityColumn().getColumnName() + " IN " + foreach("idList", "id", ", ", "(", ")", () -> table.getIdentityColumn().variable());

            }
        });
    }

}
