package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisColumn;
import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.error.natives.ParamErrorException;
import io.github.nichetoolkit.rest.helper.OptionalHelper;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>MybatisInfoProvider</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisInfoProvider {


    public static String findByName(ProviderContext providerContext, @Param("name") String name, @Param("sign") String sign) throws RestException {
        return findDynamicByName(providerContext, null, name, sign);
    }

    public static String findDynamicByName(ProviderContext providerContext, @Param("tablename") String tablename, @Param("name") String name, @Param("sign") String sign) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(name), "name cannot be empty!", ParamErrorException::new);
        return MybatisSqlScript.caching(providerContext, table -> "SELECT " + table.selectColumnList()
                + " FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                + " WHERE " + Optional.ofNullable(table.fieldColumn("name"))
                .map(MybatisColumn::columnEqualsProperty).orElse("name = #{name}")
                + Optional.ofNullable(table.getLogic()).map(logic -> "AND " + logic.columnEqualsSign()).orElse("")
        );

    }

    public static String findByNameAndNotId(ProviderContext providerContext, @Param("name") String name, @Param("id") Object id, @Param("sign") String sign) throws RestException {
        return findDynamicByNameAndNotId(providerContext, null, name, id, sign);
    }

    public static String findDynamicByNameAndNotId(ProviderContext providerContext, @Param("tablename") String tablename, @Param("name") String name, @Param("id") Object id, @Param("sign") String sign) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(id) && GeneralUtils.isNotEmpty(name), "id and name cannot be empty!", ParamErrorException::new);
        return MybatisSqlScript.caching(providerContext, table -> "SELECT " + table.selectColumnList()
                + " FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                + " WHERE " + Optional.ofNullable(table.fieldColumn("name"))
                .map(MybatisColumn::columnEqualsProperty).orElse("name = #{name}")
                + " AND " + table.getIdentity().columnNotEqualsProperty()
                + Optional.ofNullable(table.getLogic()).map(logic -> "AND " + logic.columnEqualsSign()).orElse("")
        );
    }


    public static String findByEntity(ProviderContext providerContext, @Param("entity") Object entity, @Param("sign") String sign) throws RestException {
        return findDynamicByEntity(providerContext, null, entity, sign);
    }

    public static String findDynamicByEntity(ProviderContext providerContext, @Param("tablename") String tablename, @Param("entity") Object entity, @Param("sign") String sign) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(entity), "entity cannot be empty!", ParamErrorException::new);
        return MybatisSqlScript.caching(providerContext, table -> "SELECT " + table.selectColumnList()
                + " FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                + " WHERE (" + table.uniqueColumns().stream()
                .map(column-> column.columnEqualsProperty("entity."))
                .collect(Collectors.joining(" OR ")) + ") "
                + Optional.ofNullable(table.getLogic()).map(logic -> "AND " + logic.columnEqualsSign()).orElse(""));

    }

    public static String findByEntityAndNotId(ProviderContext providerContext, @Param("entity") Object entity, @Param("id") Object id, @Param("sign") String sign) throws RestException {
        return findDynamicByEntityAndNotId(providerContext, null, entity, id, sign);
    }

    public static String findDynamicByEntityAndNotId(ProviderContext providerContext, @Param("tablename") String tablename,  @Param("entity") Object entity, @Param("id") Object id, @Param("sign") String sign) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(id) && GeneralUtils.isNotEmpty(entity), "id and entity cannot be empty!", ParamErrorException::new);
        return MybatisSqlScript.caching(providerContext, table -> "SELECT " + table.selectColumnList()
                + " FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                + " WHERE (" + table.uniqueColumns().stream()
                .map(column-> column.columnEqualsProperty("entity."))
                .collect(Collectors.joining(" OR ")) + ") "
                + " AND " + table.getIdentity().columnNotEqualsProperty()
                + Optional.ofNullable(table.getLogic()).map(logic -> "AND " + logic.columnEqualsSign()).orElse(""));
    }

}
