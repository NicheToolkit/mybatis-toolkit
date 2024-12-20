package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.rice.enums.DatabaseType;
import io.github.nichetoolkit.rice.enums.ExcludedType;
import io.github.nichetoolkit.mybatis.error.MybatisProviderLackError;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import io.github.nichetoolkit.rice.ServiceIntend;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.SpringFactoriesLoader;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <code>MybatisSqlProviderHolder</code>
 * <p>The mybatis sql provider holder class.</p>
 * @see  io.github.nichetoolkit.rice.ServiceIntend
 * @see  lombok.extern.slf4j.Slf4j
 * @see  lombok.Setter
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Slf4j
@Setter
public class MybatisSqlProviderHolder implements ServiceIntend<MybatisSqlProviderHolder> {
    /**
     * <code>SQL_PROVIDER_CACHES</code>
     * {@link java.util.Map} <p>The constant <code>SQL_PROVIDER_CACHES</code> field.</p>
     * @see  java.util.Map
     */
    private static final Map<DatabaseType, List<MybatisSqlProvider>> SQL_PROVIDER_CACHES = new ConcurrentHashMap<>(DatabaseType.values().length);
    /**
     * <code>tableProperties</code>
     * {@link io.github.nichetoolkit.mybatis.configure.MybatisTableProperties} <p>The <code>tableProperties</code> field.</p>
     * @see  io.github.nichetoolkit.mybatis.configure.MybatisTableProperties
     * @see  javax.annotation.Resource
     */
    @Resource
    private MybatisTableProperties tableProperties;
    /**
     * <code>sqlProviders</code>
     * {@link java.util.List} <p>The <code>sqlProviders</code> field.</p>
     * @see  java.util.List
     * @see  javax.annotation.Resource
     */
    @Resource
    private List<MybatisSqlProvider> sqlProviders;

    /**
     * <code>INSTANCE</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisSqlProviderHolder} <p>The constant <code>INSTANCE</code> field.</p>
     */
    private static MybatisSqlProviderHolder INSTANCE = null;

    @Override
    public void afterPropertiesSet() {
        INSTANCE = this;
    }

    /**
     * <code>instance</code>
     * <p>The instance method.</p>
     * @return  {@link io.github.nichetoolkit.mybatis.MybatisSqlProviderHolder} <p>The instance return object is <code>MybatisSqlProviderHolder</code> type.</p>
     */
    public static MybatisSqlProviderHolder instance() {
        return INSTANCE;
    }

    @Override
    public void afterAutowirePropertiesSet() {
        loadOfSqlProviders();
        defaultSqlProviders();
    }

    /**
     * <code>loadOfSqlProviders</code>
     * <p>The load of sql providers method.</p>
     */
    private void loadOfSqlProviders() {
        List<MybatisSqlProvider> sqlProvidersOfServiceLoader = SpringFactoriesLoader.loadFactories(MybatisSqlProvider.class, null);
        if (GeneralUtils.isNotEmpty(sqlProvidersOfServiceLoader)) {
            this.sqlProviders.addAll(sqlProvidersOfServiceLoader);
        }
        if (GeneralUtils.isNotEmpty(this.sqlProviders)) {
            this.sqlProviders.stream().distinct().forEach(sqlProvider -> {
                List<DatabaseType> databaseTypes = sqlProvider.databaseTypes();
                if (GeneralUtils.isNotEmpty(databaseTypes)) {
                    databaseTypes.forEach(databaseType -> {
                        if (SQL_PROVIDER_CACHES.containsKey(databaseType)) {
                            SQL_PROVIDER_CACHES.get(databaseType).add(sqlProvider);
                        } else {
                            List<MybatisSqlProvider> sqlProviderList =new ArrayList<>();
                            sqlProviderList.add(sqlProvider);
                            SQL_PROVIDER_CACHES.put(databaseType, sqlProviderList);
                        }
                    });
                }
            });
        }
        String messageOfSqlProviders = "the sql providers can not be loaded from spring factories loader or spring beans";
        OptionalUtils.ofEmptyError(RestOptional.ofEmptyable(SQL_PROVIDER_CACHES), messageOfSqlProviders, log, MybatisProviderLackError::new);
    }

    /**
     * <code>defaultSqlProviders</code>
     * <p>The default sql providers method.</p>
     */
    private void defaultSqlProviders() {
        DatabaseType databaseType = defaultDatabaseType();
        List<MybatisSqlProvider> sqlProviders = defaultSqlProviders(databaseType);
        String messageOfDatabaseType = "the sql providers can not found, maybe it is unsupported with '" + databaseType.name() + "' type";
        OptionalUtils.ofEmptyError(RestOptional.ofEmptyable(sqlProviders), messageOfDatabaseType, log, MybatisProviderLackError::new);
    }

    /**
     * <code>defaultDatabaseType</code>
     * <p>The default database type method.</p>
     * @return  {@link io.github.nichetoolkit.rice.enums.DatabaseType} <p>The default database type return object is <code>DatabaseType</code> type.</p>
     * @see  io.github.nichetoolkit.rice.enums.DatabaseType
     */
    public static DatabaseType defaultDatabaseType() {
        return INSTANCE.tableProperties.getDatabaseType();
    }

    /**
     * <code>defaultExcludedType</code>
     * <p>The default excluded type method.</p>
     * @return  {@link io.github.nichetoolkit.rice.enums.ExcludedType} <p>The default excluded type return object is <code>ExcludedType</code> type.</p>
     * @see  io.github.nichetoolkit.rice.enums.ExcludedType
     */
    public static ExcludedType defaultExcludedType() {
        return INSTANCE.tableProperties.getExcludedType();
    }

    /**
     * <code>mysqlIgnoreInsert</code>
     * <p>The mysql ignore insert method.</p>
     * @return boolean <p>The mysql ignore insert return object is <code>boolean</code> type.</p>
     */
    public static boolean mysqlIgnoreInsert() {
        return INSTANCE.tableProperties.getMysqlIgnoreInsert();
    }

    /**
     * <code>defaultSqlProviders</code>
     * <p>The default sql providers method.</p>
     * @param databaseType {@link io.github.nichetoolkit.rice.enums.DatabaseType} <p>The database type parameter is <code>DatabaseType</code> type.</p>
     * @see  io.github.nichetoolkit.rice.enums.DatabaseType
     * @see  java.util.List
     * @return  {@link java.util.List} <p>The default sql providers return object is <code>List</code> type.</p>
     */
    public static List<MybatisSqlProvider> defaultSqlProviders(DatabaseType databaseType) {
        return SQL_PROVIDER_CACHES.get(databaseType);
    }
}
