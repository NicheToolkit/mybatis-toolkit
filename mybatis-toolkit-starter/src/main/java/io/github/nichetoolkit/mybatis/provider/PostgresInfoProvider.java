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

@Slf4j
@Component
public class PostgresInfoProvider implements MybatisSqlProvider {

    @Override
    public DatabaseType databaseType() {
        return DatabaseType.POSTGRESQL;
    }

    public static String findByName(ProviderContext providerContext, String name, Object logic) throws RestException {
        return findDynamicByName(providerContext, null, name, logic);
    }

    public static String findDynamicByName(ProviderContext providerContext, String tablename, String name, Object logic) throws RestException {
        OptionalUtils.ofEmpty(name, "The name param of 'findByName' method cannot be empty!", log, message -> new MybatisParamErrorException("findByName", "name", message));
        String selectColumns = "The select columns of table with 'findByName' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), selectColumns, log,
                message -> new MybatisTableErrorException("findByName", "selectColumns", message));
        return MybatisSqlProvider.providingOfName(providerContext, tablename, name, tableOptional, SELECT_SQL_SUPPLY);
    }

    public static <I> String findByNameAndNotId(ProviderContext providerContext, String name, I id, Object logic) throws RestException {
        return findDynamicByNameAndNotId(providerContext, null, name, id, logic);
    }

    public static <I> String findDynamicByNameAndNotId(ProviderContext providerContext, String tablename, String name, I id, Object logic) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(id) && GeneralUtils.isNotEmpty(name), "The id and name params of 'findByNameAndNotId' method cannot be empty!", log, message -> new MybatisParamErrorException("findByNameAndNotId", "id and name", message));
        String selectColumns = "The select columns of table with 'findByNameAndNotId' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), selectColumns, log,
                message -> new MybatisTableErrorException("findByNameAndNotId", "selectColumns", message));
        return MybatisSqlProvider.providingOfName(providerContext, tablename, name, id, tableOptional, SELECT_SQL_SUPPLY);
    }

    public static <E> String findByEntityUnique(ProviderContext providerContext, E entity, Object logic) throws RestException {
        return findDynamicByEntityUnique(providerContext, null, entity, logic);
    }

    public static <E> String findDynamicByEntityUnique(ProviderContext providerContext, String tablename, E entity, Object logic) throws RestException {
        OptionalUtils.ofEmpty(entity, "The entity param of 'findByEntity' method cannot be empty!", log, message -> new MybatisParamErrorException("findByEntity", "entity", message));
        ConsumerActuator<MybatisTable> tableOptional = tableOptional("findByEntity");
        return MybatisSqlProvider.providingOfEntity(providerContext, tablename, entity, tableOptional, SELECT_SQL_SUPPLY);
    }

    public static <I, E> String findByEntityUniqueAndNotId(ProviderContext providerContext, E entity, I id, Object logic) throws RestException {
        return findDynamicByEntityUniqueAndNotId(providerContext, null, entity, id, logic);
    }

    public static <I, E> String findDynamicByEntityUniqueAndNotId(ProviderContext providerContext, String tablename, E entity, I id, Object logic) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(id) && GeneralUtils.isNotEmpty(entity), "The id and entity params of 'findByEntityAndNotId' method cannot be empty!", log, message -> new MybatisParamErrorException("findByEntityAndNotId", "id and entity", message));
        ConsumerActuator<MybatisTable> tableOptional = tableOptional("findByEntityAndNotId");
        return MybatisSqlProvider.providingOfEntity(providerContext, tablename, entity, id, tableOptional, SELECT_SQL_SUPPLY);
    }

    private static ConsumerActuator<MybatisTable> tableOptional(String methodName) {
        String uniqueColumns = "The unique columns of table with '" + methodName + "' method cannot be empty!";
        String selectColumns = "The select columns of table with '" + methodName + "' method cannot be empty!";
        return table -> {
            OptionalUtils.ofEmpty(table.uniqueColumns(), uniqueColumns, log, message -> new MybatisTableErrorException(methodName, "uniqueColumns", message));
            OptionalUtils.ofEmpty(table.selectColumns(), selectColumns, log, message -> new MybatisTableErrorException(methodName, "selectColumns", message));
        };
    }

}
