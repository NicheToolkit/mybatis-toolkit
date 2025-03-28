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
 * @see  io.github.nichetoolkit.mybatis.MybatisSqlProvider
 * @see  lombok.extern.slf4j.Slf4j
 * @see  org.springframework.stereotype.Component
 * @author Cyan (snow22314@outlook.com)
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
     * <code>tableColumns</code>
     * <p>The table columns method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The table columns return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static String tableColumns(ProviderContext providerContext, String tablename) throws RestException {
        OptionalUtils.ofEmpty(tablename, "The tablename param of 'tableColumns' method cannot be empty!", message -> new MybatisParamErrorException("tableColumns", "tablename", message));
        return MybatisSqlProvider.providingOfTablename(providerContext, tablename);
    }

    /**
     * <code>createIndex</code>
     * <p>The create index method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param field {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.RestField
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The create index return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static String createIndex(ProviderContext providerContext, String tablename, RestField<?> field) throws RestException {
        OptionalUtils.ofEmpty(tablename, "The tablename param of 'createIndex' method cannot be empty!", message -> new MybatisParamErrorException("createIndex", "tablename", message));
        OptionalUtils.ofEmpty(field, "The field param of 'createIndex' method cannot be empty!", message -> new MybatisParamErrorException("createIndex", "field", message));
        OptionalUtils.ofEmpty(field.getKey(), "The field key param of 'createIndex' method cannot be empty!", message -> new MybatisParamErrorException("createIndex", "field key", message));
        return MybatisSqlProvider.providingOfCreateIndex(providerContext, tablename, field);
    }

    /**
     * <code>dropIndex</code>
     * <p>The drop index method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param field {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.RestField
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The drop index return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static String dropIndex(ProviderContext providerContext, String tablename, RestField<?> field) throws RestException {
        OptionalUtils.ofEmpty(tablename, "The tablename param of 'dropIndex' method cannot be empty!", message -> new MybatisParamErrorException("dropIndex", "tablename", message));
        OptionalUtils.ofEmpty(field, "The field param of 'dropIndex' method cannot be empty!", message -> new MybatisParamErrorException("dropIndex", "field", message));
        OptionalUtils.ofEmpty(field.getKey(), "The field key param of 'dropIndex' method cannot be empty!", message -> new MybatisParamErrorException("dropIndex", "field key", message));
        return MybatisSqlProvider.providingOfDropIndex(providerContext, tablename, field);
    }

    /**
     * <code>modifyColumn</code>
     * <p>The modify column method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param field {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.RestField
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The modify column return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static String modifyColumn(ProviderContext providerContext, String tablename, RestField<?> field) throws RestException {
        OptionalUtils.ofEmpty(tablename, "The tablename param of 'modifyColumn' method cannot be empty!", message -> new MybatisParamErrorException("modifyColumn", "tablename", message));
        OptionalUtils.ofEmpty(field, "The field param of 'modifyColumn' method cannot be empty!", message -> new MybatisParamErrorException("modifyColumn", "field", message));
        OptionalUtils.ofEmpty(field.getKey(), "The field key param of 'modifyColumn' method cannot be empty!", message -> new MybatisParamErrorException("modifyColumn", "field key", message));
        OptionalUtils.ofEmpty(field.getType(), "The field type param of 'modifyColumn' method cannot be empty!", message -> new MybatisParamErrorException("modifyColumn", "field type", message));
        return MybatisSqlProvider.providingOfModifyColumn(providerContext, tablename, field);
    }

    /**
     * <code>addColumn</code>
     * <p>The add column method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param field {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.RestField
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The add column return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static String addColumn(ProviderContext providerContext, String tablename, RestField<?> field) throws RestException {
        OptionalUtils.ofEmpty(tablename, "The tablename param of 'addColumn' method cannot be empty!", message -> new MybatisParamErrorException("addColumn", "tablename", message));
        OptionalUtils.ofEmpty(field, "The field param of 'addColumn' method cannot be empty!", message -> new MybatisParamErrorException("addColumn", "field", message));
        OptionalUtils.ofEmpty(field.getKey(), "The field key param of 'addColumn' method cannot be empty!", message -> new MybatisParamErrorException("addColumn", "field key", message));
        OptionalUtils.ofEmpty(field.getType(), "The field type param of 'addColumn' method cannot be empty!", message -> new MybatisParamErrorException("addColumn", "field type", message));
        return MybatisSqlProvider.providingOfAddColumn(providerContext, tablename, field);
    }

    /**
     * <code>dropColumn</code>
     * <p>The drop column method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param field {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.RestField
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The drop column return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static String dropColumn(ProviderContext providerContext, String tablename, RestField<?> field) throws RestException {
        OptionalUtils.ofEmpty(tablename, "The tablename param of 'dropColumn' method cannot be empty!", message -> new MybatisParamErrorException("dropColumn", "tablename", message));
        OptionalUtils.ofEmpty(field, "The field param of 'dropColumn' method cannot be empty!", message -> new MybatisParamErrorException("dropColumn", "field", message));
        OptionalUtils.ofEmpty(field.getKey(), "The field key param of 'dropColumn' method cannot be empty!", message -> new MybatisParamErrorException("dropColumn", "field key", message));
        return MybatisSqlProvider.providingOfDropColumn(providerContext, tablename, field);
    }

}
