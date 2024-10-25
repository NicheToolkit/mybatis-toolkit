package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.actuator.ConsumerActuator;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component
public class PostgresSaveProvider implements MybatisSqlProvider {

    @Override
    public DatabaseType databaseType() {
        return DatabaseType.POSTGRESQL;
    }

    public static <E> String save(ProviderContext providerContext, E entity) throws RestException {
        return saveDynamic(providerContext, null, entity);
    }

    public static <E> String saveDynamic(ProviderContext providerContext, String tablename, E entity) throws RestException {
        OptionalUtils.ofEmpty(entity, "The entity param of 'save' method cannot be empty!", message -> new MybatisParamErrorException("save", "entity", message));
        String insertColumns = "The insert columns of table with 'save' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.insertColumns(), insertColumns, log,
                message -> new MybatisTableErrorException("save", "insertColumns", message));
        return MybatisSqlProvider.providingOfSave(providerContext, tablename, entity, tableOptional, MybatisSqlProvider.SAVE_SQL_SUPPLY);
    }

    public static <E> String saveAll(ProviderContext providerContext, Collection<E> entityList) throws RestException {
        return saveDynamicAll(providerContext, null, entityList);
    }

    public static <E> String saveDynamicAll(ProviderContext providerContext, String tablename, Collection<E> entityList) throws RestException {
        OptionalUtils.ofEmpty(entityList, "The entity list param of 'saveAll' method cannot be empty!", message -> new MybatisParamErrorException("saveAll", "entityList", message));
        String insertColumns = "The insert columns of table with 'save' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.insertColumns(), insertColumns, log,
                message -> new MybatisTableErrorException("saveAll", "insertColumns", message));
        return MybatisSqlProvider.providingOfAllSave(providerContext, tablename, entityList, tableOptional, MybatisSqlProvider.SAVE_SQL_SUPPLY);
    }
}
