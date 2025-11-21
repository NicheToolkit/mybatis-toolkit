package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestField;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * <code>MybatisTableProvider</code>
 * <p>The mybatis table provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisSqlProvider
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.stereotype.Component
 * @since Jdk1.8
 */
@Slf4j
@Component
public class MybatisTableProvider implements MybatisSqlProvider {

    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB, DatabaseType.SQLITE);
    }

    /**
     * <code>findColumns</code>
     * <p>The find columns method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The tableName parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The find columns return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String findColumns(ProviderContext providerContext, String tableName) throws RestException {
        return findTableColumns(providerContext, null);
    }

    /**
     * <code>findTableColumns</code>
     * <p>The find table columns method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The tableName parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The find table columns return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String findTableColumns(ProviderContext providerContext, String tableName) throws RestException {
        return MybatisSqlProvider.providingOfTableName(providerContext, tableName);
    }

    /**
     * <code>createIndex</code>
     * <p>The create index method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param field           {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @return {@link java.lang.String} <p>The create index return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see io.github.nichetoolkit.rest.RestField
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String createIndex(ProviderContext providerContext, RestField<?> field) throws RestException {
        return createTableIndex(providerContext, null, field);
    }

    /**
     * <code>createTableIndex</code>
     * <p>The create table index method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The tableName parameter is <code>String</code> type.</p>
     * @param field           {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @return {@link java.lang.String} <p>The create table index return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestField
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String createTableIndex(ProviderContext providerContext, String tableName, RestField<?> field) throws RestException {
        OptionalUtils.ofEmpty(field, "The field param of 'createIndex' method cannot be empty!", message -> new MybatisParamErrorException("createIndex", "field", message));
        OptionalUtils.ofEmpty(field.getKey(), "The field key param of 'createIndex' method cannot be empty!", message -> new MybatisParamErrorException("createIndex", "field key", message));
        return MybatisSqlProvider.providingOfCreateIndex(providerContext, tableName, field);
    }

    /**
     * <code>dropIndex</code>
     * <p>The drop index method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param field           {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @return {@link java.lang.String} <p>The drop index return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see io.github.nichetoolkit.rest.RestField
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String dropIndex(ProviderContext providerContext, RestField<?> field) throws RestException {
        return dropTableIndex(providerContext, null, field);
    }

    /**
     * <code>dropTableIndex</code>
     * <p>The drop table index method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The tableName parameter is <code>String</code> type.</p>
     * @param field           {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @return {@link java.lang.String} <p>The drop table index return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestField
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String dropTableIndex(ProviderContext providerContext, String tableName, RestField<?> field) throws RestException {
        OptionalUtils.ofEmpty(field, "The field param of 'dropIndex' method cannot be empty!", message -> new MybatisParamErrorException("dropIndex", "field", message));
        OptionalUtils.ofEmpty(field.getKey(), "The field key param of 'dropIndex' method cannot be empty!", message -> new MybatisParamErrorException("dropIndex", "field key", message));
        return MybatisSqlProvider.providingOfDropIndex(providerContext, tableName, field);
    }

    /**
     * <code>modifyColumn</code>
     * <p>The modify column method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param field           {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @return {@link java.lang.String} <p>The modify column return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see io.github.nichetoolkit.rest.RestField
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String modifyColumn(ProviderContext providerContext, RestField<?> field) throws RestException {
        return modifyTableColumn(providerContext, null, field);
    }

    /**
     * <code>modifyTableColumn</code>
     * <p>The modify table column method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The tableName parameter is <code>String</code> type.</p>
     * @param field           {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @return {@link java.lang.String} <p>The modify table column return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestField
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String modifyTableColumn(ProviderContext providerContext, String tableName, RestField<?> field) throws RestException {
        OptionalUtils.ofEmpty(field, "The field param of 'modifyColumn' method cannot be empty!", message -> new MybatisParamErrorException("modifyColumn", "field", message));
        OptionalUtils.ofEmpty(field.getKey(), "The field key param of 'modifyColumn' method cannot be empty!", message -> new MybatisParamErrorException("modifyColumn", "field key", message));
        OptionalUtils.ofEmpty(field.getType(), "The field type param of 'modifyColumn' method cannot be empty!", message -> new MybatisParamErrorException("modifyColumn", "field type", message));
        return MybatisSqlProvider.providingOfModifyColumn(providerContext, tableName, field);
    }

    /**
     * <code>addColumn</code>
     * <p>The add column method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param field           {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @return {@link java.lang.String} <p>The add column return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see io.github.nichetoolkit.rest.RestField
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String addColumn(ProviderContext providerContext, RestField<?> field) throws RestException {
        return addTableColumn(providerContext, null, field);
    }

    /**
     * <code>addTableColumn</code>
     * <p>The add table column method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The tableName parameter is <code>String</code> type.</p>
     * @param field           {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @return {@link java.lang.String} <p>The add table column return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestField
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String addTableColumn(ProviderContext providerContext, String tableName, RestField<?> field) throws RestException {
        OptionalUtils.ofEmpty(field, "The field param of 'addColumn' method cannot be empty!", message -> new MybatisParamErrorException("addColumn", "field", message));
        OptionalUtils.ofEmpty(field.getKey(), "The field key param of 'addColumn' method cannot be empty!", message -> new MybatisParamErrorException("addColumn", "field key", message));
        OptionalUtils.ofEmpty(field.getType(), "The field type param of 'addColumn' method cannot be empty!", message -> new MybatisParamErrorException("addColumn", "field type", message));
        return MybatisSqlProvider.providingOfAddColumn(providerContext, tableName, field);
    }

    /**
     * <code>dropColumn</code>
     * <p>The drop column method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param field           {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @return {@link java.lang.String} <p>The drop column return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see io.github.nichetoolkit.rest.RestField
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String dropColumn(ProviderContext providerContext, RestField<?> field) throws RestException {
        return dropTableColumn(providerContext, null, field);
    }

    /**
     * <code>dropTableColumn</code>
     * <p>The drop table column method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The tableName parameter is <code>String</code> type.</p>
     * @param field           {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @return {@link java.lang.String} <p>The drop table column return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestField
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String dropTableColumn(ProviderContext providerContext, String tableName, RestField<?> field) throws RestException {
        OptionalUtils.ofEmpty(field, "The field param of 'dropColumn' method cannot be empty!", message -> new MybatisParamErrorException("dropColumn", "field", message));
        OptionalUtils.ofEmpty(field.getKey(), "The field key param of 'dropColumn' method cannot be empty!", message -> new MybatisParamErrorException("dropColumn", "field key", message));
        return MybatisSqlProvider.providingOfDropColumn(providerContext, tableName, field);
    }

}
