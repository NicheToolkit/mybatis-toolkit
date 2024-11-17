package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.mybatis.error.MybatisTableErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.actuator.ConsumerActuator;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <code>PostgresSaveProvider</code>
 * <p>The postgres save provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisSqlProvider
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.stereotype.Component
 * @since Jdk1.8
 */
@Slf4j
@Component
public class MybatisSaveProvider implements MybatisSqlProvider {

    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB);
    }
    /**
     * <code>save</code>
     * <p>The save method.</p>
     * @param <E>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param entity          E <p>The entity parameter is <code>E</code> type.</p>
     * @return {@link java.lang.String} <p>The save return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <E> String save(ProviderContext providerContext, E entity) throws RestException {
        return saveDynamic(providerContext, null, entity);
    }

    /**
     * <code>saveDynamic</code>
     * <p>The save dynamic method.</p>
     * @param <E>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param entity          E <p>The entity parameter is <code>E</code> type.</p>
     * @return {@link java.lang.String} <p>The save dynamic return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <E> String saveDynamic(ProviderContext providerContext, String tablename, E entity) throws RestException {
        OptionalUtils.ofEmpty(entity, "The entity param of 'save' method cannot be empty!", message -> new MybatisParamErrorException("save", "entity", message));
        String insertColumns = "The insert columns of table with 'save' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.insertColumns(), insertColumns, log,
                message -> new MybatisTableErrorException("save", "insertColumns", message));
        return MybatisSqlProvider.providingOfSave(providerContext, tablename, entity, tableOptional, SAVE_SQL_SUPPLY);
    }

    /**
     * <code>saveAll</code>
     * <p>The save all method.</p>
     * @param <E>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param entityList      {@link java.util.Collection} <p>The entity list parameter is <code>Collection</code> type.</p>
     * @return {@link java.lang.String} <p>The save all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.util.Collection
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <E> String saveAll(ProviderContext providerContext, Collection<E> entityList) throws RestException {
        return saveDynamicAll(providerContext, null, entityList);
    }

    /**
     * <code>saveDynamicAll</code>
     * <p>The save dynamic all method.</p>
     * @param <E>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param entityList      {@link java.util.Collection} <p>The entity list parameter is <code>Collection</code> type.</p>
     * @return {@link java.lang.String} <p>The save dynamic all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <E> String saveDynamicAll(ProviderContext providerContext, String tablename, Collection<E> entityList) throws RestException {
        OptionalUtils.ofEmpty(entityList, "The entity list param of 'saveAll' method cannot be empty!", message -> new MybatisParamErrorException("saveAll", "entityList", message));
        String insertColumns = "The insert columns of table with 'save' method cannot be empty!";
        ConsumerActuator<MybatisTable> tableOptional = table -> OptionalUtils.ofEmpty(table.insertColumns(), insertColumns, log,
                message -> new MybatisTableErrorException("saveAll", "insertColumns", message));
        return MybatisSqlProvider.providingOfAllSave(providerContext, tablename, entityList, tableOptional, SAVE_SQL_SUPPLY);
    }
}
