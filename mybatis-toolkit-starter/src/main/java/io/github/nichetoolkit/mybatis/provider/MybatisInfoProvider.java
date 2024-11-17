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

import java.util.Arrays;
import java.util.List;

/**
 * <code>PostgresInfoProvider</code>
 * <p>The postgres info provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisSqlProvider
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.stereotype.Component
 * @since Jdk1.8
 */
@Slf4j
@Component
public class MybatisInfoProvider implements MybatisSqlProvider {

    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB,DatabaseType.SQLITE);
    }
    /**
     * <code>findByName</code>
     * <p>The find by name method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param name            {@link java.lang.String} <p>The name parameter is <code>String</code> type.</p>
     * @param logic           {@link java.lang.Object} <p>The logic parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.String} <p>The find by name return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.lang.Object
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String findByName(ProviderContext providerContext, String name, Object logic) throws RestException {
        return findDynamicByName(providerContext, null, name, logic);
    }

    /**
     * <code>findDynamicByName</code>
     * <p>The find dynamic by name method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param name            {@link java.lang.String} <p>The name parameter is <code>String</code> type.</p>
     * @param logic           {@link java.lang.Object} <p>The logic parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.String} <p>The find dynamic by name return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.lang.Object
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String findDynamicByName(ProviderContext providerContext, String tablename, String name, Object logic) throws RestException {
        OptionalUtils.ofEmpty(name, "The name param of 'findByName' method cannot be empty!", log, message -> new MybatisParamErrorException("findByName", "name", message));
        String selectColumns = "The select columns of table with 'findByName' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), selectColumns, log,
                message -> new MybatisTableErrorException("findByName", "selectColumns", message));
        return MybatisSqlProvider.providingOfName(providerContext, tablename, name, tableOptional, SELECT_SQL_SUPPLY);
    }

    /**
     * <code>findByNameAndNotId</code>
     * <p>The find by name and not id method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param name            {@link java.lang.String} <p>The name parameter is <code>String</code> type.</p>
     * @param id              I <p>The id parameter is <code>I</code> type.</p>
     * @param logic           {@link java.lang.Object} <p>The logic parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.String} <p>The find by name and not id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.lang.Object
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String findByNameAndNotId(ProviderContext providerContext, String name, I id, Object logic) throws RestException {
        return findDynamicByNameAndNotId(providerContext, null, name, id, logic);
    }

    /**
     * <code>findDynamicByNameAndNotId</code>
     * <p>The find dynamic by name and not id method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param name            {@link java.lang.String} <p>The name parameter is <code>String</code> type.</p>
     * @param id              I <p>The id parameter is <code>I</code> type.</p>
     * @param logic           {@link java.lang.Object} <p>The logic parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.String} <p>The find dynamic by name and not id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.lang.Object
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String findDynamicByNameAndNotId(ProviderContext providerContext, String tablename, String name, I id, Object logic) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(id) && GeneralUtils.isNotEmpty(name), "The id and name params of 'findByNameAndNotId' method cannot be empty!", log, message -> new MybatisParamErrorException("findByNameAndNotId", "id and name", message));
        String selectColumns = "The select columns of table with 'findByNameAndNotId' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.selectColumns(), selectColumns, log,
                message -> new MybatisTableErrorException("findByNameAndNotId", "selectColumns", message));
        return MybatisSqlProvider.providingOfName(providerContext, tablename, name, id, tableOptional, SELECT_SQL_SUPPLY);
    }

    /**
     * <code>findByEntityUnique</code>
     * <p>The find by entity unique method.</p>
     * @param <E>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param entity          E <p>The entity parameter is <code>E</code> type.</p>
     * @param logic           {@link java.lang.Object} <p>The logic parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.String} <p>The find by entity unique return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.Object
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <E> String findByEntityUnique(ProviderContext providerContext, E entity, Object logic) throws RestException {
        return findDynamicByEntityUnique(providerContext, null, entity, logic);
    }

    /**
     * <code>findDynamicByEntityUnique</code>
     * <p>The find dynamic by entity unique method.</p>
     * @param <E>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param entity          E <p>The entity parameter is <code>E</code> type.</p>
     * @param logic           {@link java.lang.Object} <p>The logic parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.String} <p>The find dynamic by entity unique return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.lang.Object
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <E> String findDynamicByEntityUnique(ProviderContext providerContext, String tablename, E entity, Object logic) throws RestException {
        OptionalUtils.ofEmpty(entity, "The entity param of 'findByEntity' method cannot be empty!", log, message -> new MybatisParamErrorException("findByEntity", "entity", message));
        ConsumerActuator<MybatisTable> tableOptional = tableOptional("findByEntity");
        return MybatisSqlProvider.providingOfEntity(providerContext, tablename, entity, tableOptional, SELECT_SQL_SUPPLY);
    }

    /**
     * <code>findByEntityUniqueAndNotId</code>
     * <p>The find by entity unique and not id method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <E>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param entity          E <p>The entity parameter is <code>E</code> type.</p>
     * @param id              I <p>The id parameter is <code>I</code> type.</p>
     * @param logic           {@link java.lang.Object} <p>The logic parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.String} <p>The find by entity unique and not id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.Object
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I, E> String findByEntityUniqueAndNotId(ProviderContext providerContext, E entity, I id, Object logic) throws RestException {
        return findDynamicByEntityUniqueAndNotId(providerContext, null, entity, id, logic);
    }

    /**
     * <code>findDynamicByEntityUniqueAndNotId</code>
     * <p>The find dynamic by entity unique and not id method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <E>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param entity          E <p>The entity parameter is <code>E</code> type.</p>
     * @param id              I <p>The id parameter is <code>I</code> type.</p>
     * @param logic           {@link java.lang.Object} <p>The logic parameter is <code>Object</code> type.</p>
     * @return {@link java.lang.String} <p>The find dynamic by entity unique and not id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.lang.Object
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I, E> String findDynamicByEntityUniqueAndNotId(ProviderContext providerContext, String tablename, E entity, I id, Object logic) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(id) && GeneralUtils.isNotEmpty(entity), "The id and entity params of 'findByEntityAndNotId' method cannot be empty!", log, message -> new MybatisParamErrorException("findByEntityAndNotId", "id and entity", message));
        ConsumerActuator<MybatisTable> tableOptional = tableOptional("findByEntityAndNotId");
        return MybatisSqlProvider.providingOfEntity(providerContext, tablename, entity, id, tableOptional, SELECT_SQL_SUPPLY);
    }

    /**
     * <code>tableOptional</code>
     * <p>The table optional method.</p>
     * @param methodName {@link java.lang.String} <p>The method name parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The table optional return object is <code>ConsumerActuator</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     */
    private static ConsumerActuator<MybatisTable> tableOptional(String methodName) {
        String uniqueColumns = "The unique columns of table with '" + methodName + "' method cannot be empty!";
        String selectColumns = "The select columns of table with '" + methodName + "' method cannot be empty!";
        return table -> {
            OptionalUtils.ofEmpty(table.uniqueColumns(), uniqueColumns, log, message -> new MybatisTableErrorException(methodName, "uniqueColumns", message));
            OptionalUtils.ofEmpty(table.selectColumns(), selectColumns, log, message -> new MybatisTableErrorException(methodName, "selectColumns", message));
        };
    }

}
