package io.github.nichetoolkit.mybatis.provider.natives;

import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisUnsupportedErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Collection;
import java.util.Optional;

public class MybatisFindLoadProvider {

    public static <I> String findByIdLoad(ProviderContext providerContext, @Param("id") I id, @Param("loadParams") Boolean... loadParams) throws RestException {
        return findDynamicByIdLoad(providerContext, null, id, loadParams);
    }

    @SuppressWarnings("Duplicates")
    public static <I> String findDynamicByIdLoad(ProviderContext providerContext, @Param("tablename") String tablename, @Param("id") I id, @Param("loadParams") Boolean... loadParams) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(id), "the id param of 'findById' method cannot be empty!", message -> new MybatisParamErrorException("findById", "id", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findById' method cannot be empty!", message -> new MybatisTableErrorException("findById", "selectColumns", message));
            OptionalUtils.trueable(table.isUseUnionKey(), "the union keys of table with 'findById' method is unsupported!", message -> new MybatisUnsupportedErrorException("findById", "unionKeys", message));
            return "SELECT " + table.selectAliasColumnList()
                    + " FROM " + table.tablenameAsAlias(tablename)
                    + " WHERE " + table.identityColumnEqualsProperty();
        });

    }

    public static <I> String findAllLoad(ProviderContext providerContext, @Param("idList") Collection<I> idList, @Param("loadParams") Boolean... loadParams) throws RestException {
        return findDynamicAllLoad(providerContext, null, idList,loadParams);
    }

    @SuppressWarnings("Duplicates")
    public static <I> String findDynamicAllLoad(ProviderContext providerContext, @Param("tablename") String tablename, @Param("idList") Collection<I> idList, @Param("loadParams") Boolean... loadParams) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(idList), "the id list param of 'findByAll' method cannot be empty!", message -> new MybatisParamErrorException("findByAll", "idList", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findByAll' method cannot be empty!", message -> new MybatisTableErrorException("findByAll", "selectColumns", message));
                OptionalUtils.trueable(table.isUseUnionKey(), "the union keys of table with 'findByAll' method is unsupported!", message -> new MybatisUnsupportedErrorException("findByAll", "unionKeys", message));
                return "SELECT " + table.selectAliasColumnList()
                        + " FROM " + table.tablename(tablename)
                        + " WHERE " + table.getIdentityColumn().columnName() + " IN " + foreach("idList", "id", ", ", "(", ")", () -> table.getIdentityColumn().variable());

            }
        });
    }

}
