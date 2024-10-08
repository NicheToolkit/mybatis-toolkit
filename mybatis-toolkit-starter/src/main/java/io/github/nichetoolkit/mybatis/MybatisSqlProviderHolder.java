package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisProviderLackError;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public class MybatisSqlProviderHolder implements InitializingBean {

    private final MybatisTableProperties tableProperties;
    private final Map<DatabaseType, List<RestSqlProvider>> sqlProviderCaches;
    private final List<RestSqlProvider> sqlProviders;

    @Autowired
    public MybatisSqlProviderHolder(MybatisTableProperties tableProperties, List<RestSqlProvider> sqlProviders) {
        this.tableProperties = tableProperties;
        this.sqlProviderCaches = new ConcurrentHashMap<>(DatabaseType.values().length);
        this.sqlProviders = sqlProviders;
    }

    private static MybatisSqlProviderHolder INSTANCE = null;

    @Override
    public void afterPropertiesSet() {
        log.debug(">>>>> the sql provider holder of  will be loaded.");
        loadOfSqlProviders();
        defaultSqlProviders();
        INSTANCE = this;
    }

    private void loadOfSqlProviders() {
        List<RestSqlProvider> sqlProvidersOfServiceLoader = SpringFactoriesLoader.loadFactories(RestSqlProvider.class, null);
        if (GeneralUtils.isNotEmpty(sqlProvidersOfServiceLoader)) {
            this.sqlProviders.addAll(sqlProvidersOfServiceLoader);
        }
        if (GeneralUtils.isNotEmpty(this.sqlProviders)) {
            this.sqlProviders.stream().distinct().collect(Collectors.groupingBy(RestSqlProvider::databaseType))
                    .forEach((databaseType, sqlProviderList) -> this.sqlProviderCaches.merge(databaseType, sqlProviderList,
                            (oldValue, newValue) -> {
                                oldValue.addAll(newValue);
                                return oldValue;
                            }));
        }
        String messageOfSqlProviders = "the sql providers can not load from service loader";
        OptionalUtils.ofEmptyError(RestOptional.ofEmptyable(this.sqlProviderCaches), messageOfSqlProviders, MybatisProviderLackError::new);
    }

    private void defaultSqlProviders() {
        DatabaseType databaseType = defaultDatabaseType();
        List<RestSqlProvider> sqlProviders = defaultSqlProviders(databaseType);
        String messageOfDatabaseType = "the sql providers can not found, maybe it is unsupported with '" + databaseType.name() + "' type";
        OptionalUtils.ofEmptyError(RestOptional.ofEmptyable(sqlProviders), messageOfDatabaseType, MybatisProviderLackError::new);
    }

    public static MybatisSqlProviderHolder instance() {
        return INSTANCE;
    }

    public static DatabaseType defaultDatabaseType() {
        return INSTANCE.tableProperties.getDatabaseType();
    }

    public static List<RestSqlProvider> defaultSqlProviders(DatabaseType databaseType) {
        return INSTANCE.sqlProviderCaches.get(databaseType);
    }
}
