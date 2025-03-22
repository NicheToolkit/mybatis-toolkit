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
import java.util.Collection;
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
     * <code>indexColumn</code>
     * <p>The index column method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param field {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.RestField
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The index column return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static String indexColumn(ProviderContext providerContext, String tablename, RestField<?> field) throws RestException {
        OptionalUtils.ofEmpty(tablename, "The tablename param of 'indexColumn' method cannot be empty!", message -> new MybatisParamErrorException("indexColumn", "tablename", message));
        return MybatisSqlProvider.providingOfIndexColumn(providerContext, tablename, field);
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
        return MybatisSqlProvider.providingOfAddColumn(providerContext, tablename, field);
    }

    /**
     * <code>refreshColumn</code>
     * <p>The refresh column method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param field {@link io.github.nichetoolkit.rest.RestField} <p>The field parameter is <code>RestField</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.RestField
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The refresh column return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static String refreshColumn(ProviderContext providerContext, String tablename, RestField<?> field) throws RestException {
        OptionalUtils.ofEmpty(tablename, "The tablename param of 'refreshColumn' method cannot be empty!", message -> new MybatisParamErrorException("refreshColumn", "tablename", message));
        return MybatisSqlProvider.providingOfRefreshColumn(providerContext, tablename, field);
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
        return MybatisSqlProvider.providingOfDropColumn(providerContext, tablename, field);
    }

}
