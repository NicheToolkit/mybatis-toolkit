package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.mybatis.enums.DatabaseType;
import io.github.nichetoolkit.mybatis.error.MybatisProviderLackError;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.OptionalUtils;
import io.github.nichetoolkit.rice.ServiceIntend;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.SpringFactoriesLoader;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Setter
public class MybatisSqlProviderHolder implements ServiceIntend<MybatisSqlProviderHolder> {
    private static final Map<DatabaseType, List<MybatisSqlProvider>> SQL_PROVIDER_CACHES = new ConcurrentHashMap<>(DatabaseType.values().length);
    @Resource
    private MybatisTableProperties tableProperties;
    @Resource
    private List<MybatisSqlProvider> sqlProviders;

    private static MybatisSqlProviderHolder INSTANCE = null;

    @Override
    public void afterPropertiesSet() {
        INSTANCE = this;
    }

    public static MybatisSqlProviderHolder instance() {
        return INSTANCE;
    }

    @Override
    public void afterAutowirePropertiesSet() {
        loadOfSqlProviders();
        defaultSqlProviders();
    }

    private void loadOfSqlProviders() {
        List<MybatisSqlProvider> sqlProvidersOfServiceLoader = SpringFactoriesLoader.loadFactories(MybatisSqlProvider.class, null);
        if (GeneralUtils.isNotEmpty(sqlProvidersOfServiceLoader)) {
            this.sqlProviders.addAll(sqlProvidersOfServiceLoader);
        }
        if (GeneralUtils.isNotEmpty(this.sqlProviders)) {
            this.sqlProviders.stream().distinct().collect(Collectors.groupingBy(MybatisSqlProvider::databaseType))
                    .forEach((databaseType, sqlProviderList) -> {
                        if (SQL_PROVIDER_CACHES.containsKey(databaseType)) {
                            SQL_PROVIDER_CACHES.get(databaseType).addAll(sqlProviderList);
                        } else {
                            SQL_PROVIDER_CACHES.put(databaseType, sqlProviderList);
                        }
                    });
        }
        String messageOfSqlProviders = "the sql providers can not be loaded from spring factories loader or spring beans";
        OptionalUtils.ofEmptyError(RestOptional.ofEmptyable(SQL_PROVIDER_CACHES), messageOfSqlProviders, log, MybatisProviderLackError::new);
    }

    private void defaultSqlProviders() {
        DatabaseType databaseType = defaultDatabaseType();
        List<MybatisSqlProvider> sqlProviders = defaultSqlProviders(databaseType);
        String messageOfDatabaseType = "the sql providers can not found, maybe it is unsupported with '" + databaseType.name() + "' type";
        OptionalUtils.ofEmptyError(RestOptional.ofEmptyable(sqlProviders), messageOfDatabaseType, log,MybatisProviderLackError::new);
    }

    public static DatabaseType defaultDatabaseType() {
        return INSTANCE.tableProperties.getDatabaseType();
    }

    public static List<MybatisSqlProvider> defaultSqlProviders(DatabaseType databaseType) {
        return SQL_PROVIDER_CACHES.get(databaseType);
    }
}
