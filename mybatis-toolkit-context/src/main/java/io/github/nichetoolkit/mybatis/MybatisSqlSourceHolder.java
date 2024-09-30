package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MybatisSqlSourceHolder implements InitializingBean {

    private final boolean isUseOnce;
    private final Map<String, MybatisSqlCache> sqlCaches;
    private final Map<Configuration, Map<String, SqlSource>> sqlSourceCaches;


    @Autowired
    public MybatisSqlSourceHolder(MybatisCacheProperties cacheProperties) {
        this.sqlSourceCaches = new ConcurrentHashMap<>(4);
        this.sqlCaches = new ConcurrentHashMap<>(cacheProperties.getInitSize());
        this.isUseOnce = cacheProperties.isUseOnce();
    }

    private static MybatisSqlSourceHolder INSTANCE = null;

    public static MybatisSqlSourceHolder instance() {
        return INSTANCE;
    }

    @Override
    public void afterPropertiesSet() {
        INSTANCE = this;
    }

    public static boolean isUseOnce() {
        return INSTANCE.isUseOnce;
    }

    public static MybatisSqlCache putSqlCache(String cacheKey,MybatisSqlCache sqlCache) {
        return INSTANCE.sqlCaches.put(cacheKey,sqlCache);
    }

    public static MybatisSqlCache getSqlCache(String cacheKey) {
        return INSTANCE.sqlCaches.get(cacheKey);
    }

    public static boolean containsKey(String cacheKey) {
        return INSTANCE.sqlCaches.containsKey(cacheKey);
    }

    public static boolean containsKey(Configuration configuration) {
        return INSTANCE.sqlSourceCaches.containsKey(configuration);
    }

    public static boolean containsKey(Configuration configuration, String cacheKey) {
        return INSTANCE.sqlSourceCaches.get(configuration).containsKey(cacheKey);
    }

    public static Map<String, SqlSource> computeIfAbsent(Configuration configuration) {
        return INSTANCE.sqlSourceCaches.computeIfAbsent(configuration, k -> new HashMap<>());
    }

    public static Map<String, SqlSource> getSqlSource(Configuration configuration) {
        return INSTANCE.sqlSourceCaches.get(configuration);
    }

    public static SqlSource getSqlSource(Configuration configuration, String cacheKey) {
        return INSTANCE.sqlSourceCaches.get(configuration).get(cacheKey);
    }
}
