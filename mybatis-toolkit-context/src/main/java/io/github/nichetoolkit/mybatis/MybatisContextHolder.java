package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties;
import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.rest.RestIntend;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Data
public class MybatisContextHolder implements RestIntend<MybatisContextHolder> {
    @Resource
    private MybatisTableProperties tableProperties;
    @Resource
    private MybatisCacheProperties cacheProperties;
    
    private static boolean IS_USE_ONCE;
    private static Map<String, MybatisSqlCache> SQL_CACHES;
    private static Map<Configuration, Map<String, SqlSource>> SQL_SOURCE_CACHES;
    private static MybatisContextHolder INSTANCE = null;
    public static MybatisContextHolder instance() {
        return INSTANCE;
    }

    @Override
    public void afterPropertiesSet() {
        INSTANCE = this;
    }

    @Override
    public void afterAutowirePropertiesSet() {
        SQL_SOURCE_CACHES = new ConcurrentHashMap<>(4);
        SQL_CACHES = new ConcurrentHashMap<>(this.cacheProperties.getInitSize());
        IS_USE_ONCE = this.cacheProperties.isUseOnce();
    }

    public static boolean isUseOnce() {
        return INSTANCE.cacheProperties.isUseOnce();
    }

    public static MybatisTableProperties tableProperties() {
        return INSTANCE.tableProperties;
    }

    public static MybatisCacheProperties cacheProperties() {
        return INSTANCE.cacheProperties;
    }

    public static MybatisSqlCache putSqlCache(String cacheKey, MybatisSqlCache sqlCache) {
        return SQL_CACHES.put(cacheKey,sqlCache);
    }

    public static MybatisSqlCache getSqlCache(String cacheKey) {
        return SQL_CACHES.get(cacheKey);
    }

    public static boolean containsKey(String cacheKey) {
        return SQL_CACHES.containsKey(cacheKey);
    }

    public static boolean containsKey(Configuration configuration) {
        return SQL_SOURCE_CACHES.containsKey(configuration);
    }

    public static boolean containsKey(Configuration configuration, String cacheKey) {
        return SQL_SOURCE_CACHES.get(configuration).containsKey(cacheKey);
    }

    public static Map<String, SqlSource> computeIfAbsent(Configuration configuration) {
        return SQL_SOURCE_CACHES.computeIfAbsent(configuration, k -> new HashMap<>());
    }

    public static Map<String, SqlSource> getSqlSource(Configuration configuration) {
        return SQL_SOURCE_CACHES.get(configuration);
    }

    public static SqlSource getSqlSource(Configuration configuration, String cacheKey) {
        return SQL_SOURCE_CACHES.get(configuration).get(cacheKey);
    }
}
