package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisColumn;
import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisUnsupportedErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.helper.OptionalHelper;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;

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
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(name), "the name param of 'findByName' method cannot be empty!", message -> new MybatisParamErrorException("findByName", "name", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findAllByWhere' method cannot be empty!", message -> new MybatisTableErrorException("findByName", "selectColumns", message));
            return "SELECT " + table.selectColumnList()
                    + " FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                    + " WHERE " + Optional.ofNullable(table.fieldColumn("name"))
                    .map(MybatisColumn::columnEqualsProperty).orElse("name = #{name}")
                    + Optional.ofNullable(table.getLogicColumn()).map(logic -> "AND " + logic.columnEqualsSign()).orElse("");
        });

    }

    public static <I> String findByNameAndNotId(ProviderContext providerContext, @Param("name") String name, @Param("id") I id, @Param("sign") String sign) throws RestException {
        return findDynamicByNameAndNotId(providerContext, null, name, id, sign);
    }

    public static <I> String findDynamicByNameAndNotId(ProviderContext providerContext, @Param("tablename") String tablename, @Param("name") String name, @Param("id") I id, @Param("sign") String sign) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(id) && GeneralUtils.isNotEmpty(name), "the id and name params of 'findByNameAndNotId' method cannot be empty!", message -> new MybatisParamErrorException("findByNameAndNotId", "id and name", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findByNameAndNotId' method cannot be empty!", message -> new MybatisTableErrorException("findByNameAndNotId", "selectColumns", message));
            OptionalHelper.trueable(table.isUseUnionKey(), "the union keys of table with 'findByNameAndNotId' method is unsupported!", message -> new MybatisUnsupportedErrorException("findByNameAndNotId", "unionKeys", message));
            return "SELECT " + table.selectColumnList()
                    + " FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                    + " WHERE " + Optional.ofNullable(table.fieldColumn("name"))
                    .map(MybatisColumn::columnEqualsProperty).orElse("name = #{name}")
                    + " AND " + table.getIdentityColumn().columnNotEqualsProperty()
                    + Optional.ofNullable(table.getLogicColumn()).map(logic -> "AND " + logic.columnEqualsSign()).orElse("");
        });
    }


    public static <E> String findByEntity(ProviderContext providerContext, @Param("entity") E entity, @Param("sign") String sign) throws RestException {
        return findDynamicByEntity(providerContext, null, entity, sign);
    }

    public static <E> String findDynamicByEntity(ProviderContext providerContext, @Param("tablename") String tablename, @Param("entity") E entity, @Param("sign") String sign) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(entity), "the entity param of 'findByEntity' method cannot be empty!", message -> new MybatisParamErrorException("findByEntity", "entity", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.uniqueColumns()), "the unique columns of table with 'findByEntity' method cannot be empty!", message -> new MybatisTableErrorException("findByEntity", "uniqueColumns", message));
            OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findByEntity' method cannot be empty!", message -> new MybatisTableErrorException("findByEntity", "selectColumns", message));
            return "SELECT " + table.selectColumnList()
                  + " FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                  + " WHERE (" + table.uniqueColumns().stream()
                  .map(column -> column.columnEqualsProperty("entity."))
                  .collect(Collectors.joining(" OR ")) + ") "
                  + Optional.ofNullable(table.getLogicColumn()).map(logic -> "AND " + logic.columnEqualsSign()).orElse("");
        });

    }

    public static <I, E> String findByEntityAndNotId(ProviderContext providerContext, @Param("entity") E entity, @Param("id") I id, @Param("sign") String sign) throws RestException {
        return findDynamicByEntityAndNotId(providerContext, null, entity, id, sign);
    }

    public static <I, E> String findDynamicByEntityAndNotId(ProviderContext providerContext, @Param("tablename") String tablename, @Param("entity") E entity, @Param("id") I id, @Param("sign") String sign) throws RestException {
        OptionalHelper.falseable(GeneralUtils.isNotEmpty(id) && GeneralUtils.isNotEmpty(entity), "the id and entity params of 'findByEntityAndNotId' method cannot be empty!", message -> new MybatisParamErrorException("findByEntityAndNotId", "id and entity", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.uniqueColumns()), "the unique columns of table with 'findByEntityAndNotId' method cannot be empty!", message -> new MybatisTableErrorException("findByEntityAndNotId", "uniqueColumns", message));
            OptionalHelper.falseable(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findByEntityAndNotId' method cannot be empty!", message -> new MybatisTableErrorException("findByEntityAndNotId", "selectColumns", message));
            OptionalHelper.trueable(table.isUseUnionKey(), "the union keys of table with 'findByEntityAndNotId' method is unsupported!", message -> new MybatisUnsupportedErrorException("findByEntityAndNotId", "unionKeys", message));
            return "SELECT " + table.selectColumnList()
                   + " FROM " + Optional.ofNullable(tablename).orElse(table.tableName())
                   + " WHERE (" + table.uniqueColumns().stream()
                   .map(column -> column.columnEqualsProperty("entity."))
                   .collect(Collectors.joining(" OR ")) + ") "
                   + " AND " + table.getIdentityColumn().columnNotEqualsProperty()
                   + Optional.ofNullable(table.getLogicColumn()).map(logic -> "AND " + logic.columnEqualsSign()).orElse("");
        });
    }

}
