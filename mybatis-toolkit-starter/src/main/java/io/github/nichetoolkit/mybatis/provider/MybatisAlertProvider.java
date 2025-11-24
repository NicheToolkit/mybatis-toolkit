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
 * <code>MybatisAlertProvider</code>
 * <p>The mybatis alert provider class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisSqlProvider
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.stereotype.Component
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
     * <code>alertDynamicById</code>
     * <p>The alert dynamic by id method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param id              I <p>The id parameter is <code>I</code> type.</p>
     * @param state          S <p>The state parameter is <code>S</code> type.</p>
     * @param stateName      {@link java.lang.String} <p>The state name parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alert dynamic by id return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I, S> String alertDynamicById(ProviderContext providerContext, String tableName, I id, S state, String stateName) throws RestException {
        OptionalUtils.ofEmpty(id, "The id param of 'alertById' method cannot be empty!", message -> new MybatisParamErrorException("alertById", "id", message));
        OptionalUtils.ofEmpty(state, "The state param of 'alertById' method cannot be empty!", message -> new MybatisParamErrorException("alertById", "state", message));
        return MybatisSqlProvider.providingOfId(providerContext, tableName, id, state, stateName,ALERT_SQL_SUPPLY);
    }

    /**
     * <code>alertDynamicAll</code>
     * <p>The alert dynamic all method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param idList          {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @param state          S <p>The state parameter is <code>S</code> type.</p>
     * @param stateName      {@link java.lang.String} <p>The state name parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alert dynamic all return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I, S> String alertDynamicAll(ProviderContext providerContext, String tableName, Collection<I> idList, S state, String stateName) throws RestException {
        OptionalUtils.ofEmpty(idList, "The id list param of 'alertAll' method cannot be empty!", message -> new MybatisParamErrorException("alertAll", "idList", message));
        OptionalUtils.ofEmpty(state, "The state param of 'alertAll' method cannot be empty!", message -> new MybatisParamErrorException("alertAll", "state", message));
        return MybatisSqlProvider.providingOfAll(providerContext, tableName, idList, state,  stateName,ALERT_SQL_SUPPLY);
    }

    /**
     * <code>alertDynamicAllByWhere</code>
     * <p>The alert dynamic all by where method.</p>
     * @param <I>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param <S>             {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param providerContext {@link org.apache.ibatis.builder.annotation.ProviderContext} <p>The provider context parameter is <code>ProviderContext</code> type.</p>
     * @param tableName       {@link java.lang.String} <p>The table name parameter is <code>String</code> type.</p>
     * @param whereSql        {@link java.lang.String} <p>The where sql parameter is <code>String</code> type.</p>
     * @param state          S <p>The state parameter is <code>S</code> type.</p>
     * @param stateName      {@link java.lang.String} <p>The state name parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alert dynamic all by where return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.apache.ibatis.builder.annotation.ProviderContext
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public static <I, S> String alertDynamicAllByWhere(ProviderContext providerContext, String tableName, String whereSql, S state, String stateName) throws RestException {
        OptionalUtils.ofEmpty(whereSql, "The where sql param of 'alertAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("alertAllByWhere", "whereSql", message));
        OptionalUtils.ofEmpty(state, "The state param of 'alertAllByWhere' method cannot be empty!", message -> new MybatisParamErrorException("alertAllByWhere", "state", message));
        return MybatisSqlProvider.providingOfWhere(providerContext, tableName, whereSql, state, stateName, ALERT_SQL_SUPPLY);
    }

}
