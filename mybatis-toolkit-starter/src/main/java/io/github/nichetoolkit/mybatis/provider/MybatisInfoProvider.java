package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisColumn;
import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Optional;
import java.util.stream.Collectors;

public class MybatisInfoProvider implements MybatisProviderResolver {

    public static String findByName(ProviderContext providerContext, @Param("name") String name, @Param("logicValue") String logicValue) throws RestException {
        return findDynamicByName(providerContext, null, name, logicValue);
    }

    public static String findDynamicByName(ProviderContext providerContext, @Param("tablename") String tablename, @Param("name") String name, @Param("logicValue") String logicValue) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(name), "the name param of 'findByName' method cannot be empty!", message -> new MybatisParamErrorException("findByName", "name", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findAllByWhere' method cannot be empty!", message -> new MybatisTableErrorException("findByName", "selectColumns", message));
            return "SELECT " + table.selectColumnSql()
                    + " FROM " + table.tablename(tablename)
                    + " WHERE " + Optional.ofNullable(table.fieldColumn("name"))
                    .map(MybatisColumn::columnEqualsProperty).orElse("name = #{name}")
                    + Optional.ofNullable(table.getLogicColumn()).map(logic -> "AND " + logic.columnEqualsLogic()).orElse("");
        });

    }

    public static <I> String findByNameAndNotId(ProviderContext providerContext, @Param("name") String name, @Param("id") I id, @Param("logicValue") String logicValue) throws RestException {
        return findDynamicByNameAndNotId(providerContext, null, name, id, logicValue);
    }

    public static <I> String findDynamicByNameAndNotId(ProviderContext providerContext, @Param("tablename") String tablename, @Param("name") String name, @Param("id") I id, @Param("logicValue") String logicValue) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(id) && GeneralUtils.isNotEmpty(name), "the id and name params of 'findByNameAndNotId' method cannot be empty!", message -> new MybatisParamErrorException("findByNameAndNotId", "id and name", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findByNameAndNotId' method cannot be empty!", message -> new MybatisTableErrorException("findByNameAndNotId", "selectColumns", message));
            return "SELECT " + table.selectColumnSql()
                    + " FROM " + table.tablename(tablename)
                    + " WHERE " + Optional.ofNullable(table.fieldColumn("name"))
                    .map(MybatisColumn::columnEqualsProperty).orElse("name = #{name}")
//                    + " AND " + table.getIdentityColumn().columnNotEqualsProperty()
                    + Optional.ofNullable(table.getLogicColumn()).map(logic -> "AND " + logic.columnEqualsLogic()).orElse("");
        });
    }


    public static <E> String findByEntity(ProviderContext providerContext, @Param("entity") E entity, @Param("logicValue") String logicValue) throws RestException {
        return findDynamicByEntity(providerContext, null, entity, logicValue);
    }

    public static <E> String findDynamicByEntity(ProviderContext providerContext, @Param("tablename") String tablename, @Param("entity") E entity, @Param("logicValue") String logicValue) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(entity), "the entity param of 'findByEntity' method cannot be empty!", message -> new MybatisParamErrorException("findByEntity", "entity", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.uniqueColumns()), "the unique columns of table with 'findByEntity' method cannot be empty!", message -> new MybatisTableErrorException("findByEntity", "uniqueColumns", message));
            OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findByEntity' method cannot be empty!", message -> new MybatisTableErrorException("findByEntity", "selectColumns", message));
            return "SELECT " + table.selectColumnSql()
                  + " FROM " + table.tablename(tablename)
                  + " WHERE (" + table.uniqueColumns().stream()
                  .map(column -> column.columnEqualsProperty("entity."))
                  .collect(Collectors.joining(" OR ")) + ") "
                  + Optional.ofNullable(table.getLogicColumn()).map(logic -> "AND " + logic.columnEqualsLogic()).orElse("");
        });

    }

    public static <I, E> String findByEntityAndNotId(ProviderContext providerContext, @Param("entity") E entity, @Param("id") I id, @Param("logicValue") String logicValue) throws RestException {
        return findDynamicByEntityAndNotId(providerContext, null, entity, id, logicValue);
    }

    public static <I, E> String findDynamicByEntityAndNotId(ProviderContext providerContext, @Param("tablename") String tablename, @Param("entity") E entity, @Param("id") I id, @Param("logicValue") String logicValue) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(id) && GeneralUtils.isNotEmpty(entity), "the id and entity params of 'findByEntityAndNotId' method cannot be empty!", message -> new MybatisParamErrorException("findByEntityAndNotId", "id and entity", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.uniqueColumns()), "the unique columns of table with 'findByEntityAndNotId' method cannot be empty!", message -> new MybatisTableErrorException("findByEntityAndNotId", "uniqueColumns", message));
            OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(table.selectColumns()), "the select columns of table with 'findByEntityAndNotId' method cannot be empty!", message -> new MybatisTableErrorException("findByEntityAndNotId", "selectColumns", message));
            return "SELECT " + table.selectColumnSql()
                   + " FROM " + table.tablename(tablename)
                   + " WHERE (" + table.uniqueColumns().stream()
                   .map(column -> column.columnEqualsProperty("entity."))
                   .collect(Collectors.joining(" OR ")) + ") "
//                   + " AND " + table.getIdentityColumn().columnNotEqualsProperty()
                   + Optional.ofNullable(table.getLogicColumn()).map(logic -> "AND " + logic.columnEqualsLogic()).orElse("");
        });
    }

}
