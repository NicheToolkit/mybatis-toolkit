package io.github.nichetoolkit.mybatis.provider;

import io.github.nichetoolkit.mybatis.MybatisSqlProvider;
import io.github.nichetoolkit.rice.enums.DatabaseType;
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
 * <code>MybatisAlertProvider</code>
 * <p>The mybatis alert provider class.</p>
 * @see  io.github.nichetoolkit.mybatis.MybatisSqlProvider
 * @see  lombok.extern.slf4j.Slf4j
 * @see  org.springframework.stereotype.Component
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Slf4j
@Component
public class MybatisAlertProvider implements MybatisSqlProvider {

    @Override
    public List<DatabaseType> databaseTypes() {
        return Arrays.asList(DatabaseType.POSTGRESQL, DatabaseType.MYSQL, DatabaseType.GAUSSDB,DatabaseType.SQLITE);
    }

    /**
     * <code>alertById</code>
     * <p>The alert by id method.</p>
     * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param id I <p>The id parameter is <code>I</code> type.</p>
     * @param status S <p>The status parameter is <code>S</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The alert by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static <I, S> String alertById(ProviderContext providerContext, I id, S status) throws RestException {
        return alertDynamicById(providerContext, null, id, status);
    }

    /**
     * <code>alertDynamicById</code>
     * <p>The alert dynamic by id method.</p>
     * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param id I <p>The id parameter is <code>I</code> type.</p>
     * @param status S <p>The status parameter is <code>S</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The alert dynamic by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static <I, S> String alertDynamicById(ProviderContext providerContext, String tablename, I id, S status) throws RestException {
        OptionalUtils.ofEmpty(id, "The id param of 'alertById' method cannot be empty!", message -> new MybatisParamErrorException("alertById", "id", message));
        OptionalUtils.ofEmpty(status, "The status param of 'alertById' method cannot be empty!", message -> new MybatisParamErrorException("alertById", "status", message));
        return MybatisSqlProvider.providingOfId(providerContext, tablename, id, status, ALERT_SQL_SUPPLY);
    }

    /**
     * <code>alertAll</code>
     * <p>The alert all method.</p>
     * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param idList {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @param status S <p>The status parameter is <code>S</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.util.Collection
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The alert all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static <I, S> String alertAll(ProviderContext providerContext, Collection<I> idList, S status) throws RestException {
        return alertDynamicAll(providerContext, null, idList, status);
    }

    /**
     * <code>alertDynamicAll</code>
     * <p>The alert dynamic all method.</p>
     * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param idList {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @param status S <p>The status parameter is <code>S</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  java.util.Collection
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The alert dynamic all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static <I, S> String alertDynamicAll(ProviderContext providerContext, String tablename, Collection<I> idList, S status) throws RestException {
        OptionalUtils.ofEmpty(idList, "The id list param of 'alertAll' method cannot be empty!", message -> new MybatisParamErrorException("alertAll", "idList", message));
        OptionalUtils.ofEmpty(status, "The status param of 'alertAll' method cannot be empty!", message -> new MybatisParamErrorException("alertAll", "status", message));
        return MybatisSqlProvider.providingOfAll(providerContext, tablename, idList, status, ALERT_SQL_SUPPLY);
    }

    /**
     * <code>alertAllByWhere</code>
     * <p>The alert all by where method.</p>
     * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param whereSql {@link java.lang.String} <p>The where sql parameter is <code>String</code> type.</p>
     * @param status S <p>The status parameter is <code>S</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The alert all by where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static <I, S> String alertAllByWhere(ProviderContext providerContext, String whereSql, S status) throws RestException {
        return alertDynamicAllByWhere(providerContext, null, whereSql, status);
    }

    /**
     * <code>alertDynamicAllByWhere</code>
     * <p>The alert dynamic all by where method.</p>
     * @param <I>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>  {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @param whereSql {@link java.lang.String} <p>The where sql parameter is <code>String</code> type.</p>
     * @param status S <p>The status parameter is <code>S</code> type.</p>
     * @see  org.apache.ibatis.builder.annotation.ProviderContext
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link java.lang.String} <p>The alert dynamic all by where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public static <I, S> String alertDynamicAllByWhere(ProviderContext providerContext, String tablename, String whereSql, S status) throws RestException {
        OptionalUtils.ofEmpty(whereSql, "The where sql param of 'alertAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("alertAllByWhere", "whereSql", message));
        OptionalUtils.ofEmpty(status, "The status param of 'alertAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("alertAllByWhere", "status", message));
        return MybatisSqlProvider.providingOfWhere(providerContext, tablename, whereSql, status, ALERT_SQL_SUPPLY);
    }

}
