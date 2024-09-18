package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlScript;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class MybatisSaveProvider extends MybatisMapperProvider {
    
    public MybatisSaveProvider(MybatisTableProperties tableProperties) {
        super(tableProperties);
    }

    public static <E> String save(ProviderContext providerContext, @Param("entity") E entity) throws RestException {
        return saveDynamic(providerContext, null, entity);
    }

    public static <E> String saveDynamic(ProviderContext providerContext, @Param("tablename") String tablename, @Param("entity") E entity) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(entity), "the entity param of 'save' method cannot be empty!", message -> new MybatisParamErrorException("save", "entity", message));
        return MybatisSqlScript.caching(providerContext, table -> {
            OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.insertColumns()), "the insert columns of table with 'save' method cannot be empty!", message -> new MybatisTableErrorException("save", "insertColumns", message));
            OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.identityColumns()), "the identity columns of table with 'save' method cannot be empty!", message -> new MybatisTableErrorException("save", "identityColumns", message));
            return "INSERT INTO " + Optional.ofNullable(tablename).orElse(table.tablename())
                    + " (" + table.insertColumnList() + ")"
                    + " VALUES (" + table.insertColumns().stream()
                    .map(column -> column.variable("entity.")).collect(Collectors.joining(", "))
                    + ")" + saveUpsetSql(tablename,table);
        });
    }

    public static <E> String saveAll(ProviderContext providerContext, @Param("entityList") Collection<E> entityList) throws RestException {
        return saveDynamicAll(providerContext, null, entityList);
    }

    public static <E> String saveDynamicAll(ProviderContext providerContext, @Param("tablename") String tablename, @Param("entityList") Collection<E> entityList) throws RestException {
        OptionalUtils.falseable(GeneralUtils.isNotEmpty(entityList), "the entity list param of 'saveAll' method cannot be empty!", message -> new MybatisParamErrorException("saveAll", "entityList", message));
        return MybatisSqlScript.caching(providerContext, new MybatisSqlScript() {
            @Override
            public String sql(MybatisTable table) throws RestException {
                OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.insertColumns()), "the insert columns of table with 'saveAll' method cannot be empty!", message -> new MybatisTableErrorException("saveAll", "insertColumns", message));
                OptionalUtils.falseable(GeneralUtils.isNotEmpty(table.identityColumns()), "the identity columns of table with 'saveAll' method cannot be empty!", message -> new MybatisTableErrorException("saveAll", "identityColumns", message));
                return "INSERT INTO " + Optional.ofNullable(tablename).orElse(table.tablename())
                        + " (" + table.insertColumnList() + ") VALUES "
                        + foreach("entityList", "entity", ", ", () ->
                        " (" + table.insertColumns().stream().map(column -> column.variable("entity.")).collect(Collectors.joining(", ")) + " )")
                        + saveUpsetSql(tablename,table);
            }
        });
    }
}