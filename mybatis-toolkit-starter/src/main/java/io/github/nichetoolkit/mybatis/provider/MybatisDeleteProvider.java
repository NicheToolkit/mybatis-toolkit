package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisParamErrorException;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * <code>PostgresDeleteProvider</code>
 * <p>The postgres delete provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisSqlProvider
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.stereotype.Component
 * @since Jdk1.8
 */
@Slf4j
@Component
public class MybatisDeleteProvider implements MybatisSqlProvider {

    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB);
    }
    /**
     * <code>deleteById</code>
     * <p>The delete by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param id              I <p>The id parameter is <code>I</code> type.</p>
     * @return {@link java.lang.String} <p>The delete by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String deleteById(ProviderContext providerContext, I id) throws RestException {
        return deleteDynamicById(providerContext, null, id);
    }

    /**
     * <code>deleteDynamicById</code>
     * <p>The delete dynamic by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param id              I <p>The id parameter is <code>I</code> type.</p>
     * @return {@link java.lang.String} <p>The delete dynamic by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String deleteDynamicById(ProviderContext providerContext, String tablename, I id) throws RestException {
        OptionalUtils.ofEmpty(id, "The id param of 'deleteById' method cannot be empty!", message -> new MybatisParamErrorException("deleteById", "id", message));
        return MybatisSqlProvider.providingOfId(providerContext, tablename, id, table -> {
        }, DELETE_SQL_SUPPLY);
    }

    /**
     * <code>deleteAll</code>
     * <p>The delete all method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @return {@link java.lang.String} <p>The delete all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.util.Collection
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String deleteAll(ProviderContext providerContext, Collection<I> idList) throws RestException {
        return deleteDynamicAll(providerContext, null, idList);
    }

    /**
     * <code>deleteDynamicAll</code>
     * <p>The delete dynamic all method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @return {@link java.lang.String} <p>The delete dynamic all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I> String deleteDynamicAll(ProviderContext providerContext, String tablename, Collection<I> idList) throws RestException {
        OptionalUtils.ofEmpty(idList, "The id list param of 'deleteByAll' method cannot be empty!", message -> new MybatisParamErrorException("deleteByAll", "idList", message));
        return MybatisSqlProvider.providingOfAll(providerContext, tablename, idList, table -> {
        }, DELETE_SQL_SUPPLY);
    }

    /**
     * <code>deleteAllByWhere</code>
     * <p>The delete all by where method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param whereSql        {@link java.lang.String} <p>The where sql parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The delete all by where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String deleteAllByWhere(ProviderContext providerContext, String whereSql) throws RestException {
        return deleteDynamicAllByWhere(providerContext, null, whereSql);
    }

    /**
     * <code>deleteDynamicAllByWhere</code>
     * <p>The delete dynamic all by where method.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename       {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param whereSql        {@link java.lang.String} <p>The where sql parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The delete dynamic all by where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static String deleteDynamicAllByWhere(ProviderContext providerContext, String tablename, String whereSql) throws RestException {
        OptionalUtils.ofEmpty(whereSql, "The where sql param of 'deleteAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("deleteAllByWhere", "whereSql", message));
        return MybatisSqlProvider.providingOfWhere(providerContext, tablename, whereSql, table -> {
        }, DELETE_SQL_SUPPLY);
    }


}
