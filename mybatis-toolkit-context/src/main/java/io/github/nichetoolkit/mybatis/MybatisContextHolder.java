package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties;
import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import io.github.nichetoolkit.rice.ServiceIntend;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <code>MybatisContextHolder</code>
 * <p>The mybatis context holder class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.ServiceIntend
 * @see lombok.extern.slf4j.Slf4j
 * @see lombok.Setter
 * @since Jdk1.8
 */
@Slf4j
@Setter
public class MybatisContextHolder implements ServiceIntend<MybatisContextHolder> {
    /**
     * <code>tableProperties</code>
     * {@link io.github.nichetoolkit.mybatis.configure.MybatisTableProperties} <p>The <code>tableProperties</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.configure.MybatisTableProperties
     * @see javax.annotation.Resource
     */
    @Resource
    private MybatisTableProperties tableProperties;
    /**
     * <code>cacheProperties</code>
     * {@link io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties} <p>The <code>cacheProperties</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties
     * @see javax.annotation.Resource
     */
    @Resource
    private MybatisCacheProperties cacheProperties;

    /**
     * <code>IS_USE_ONCE</code>
     * <p>The constant <code>IS_USE_ONCE</code> field.</p>
     */
    private static boolean IS_USE_ONCE;
    /**
     * <code>SQL_CACHES</code>
     * {@link java.util.Map} <p>The <code>SQL_CACHES</code> field.</p>
     * @see java.util.Map
     */
    private static Map<String, MybatisSqlCache> SQL_CACHES;
    /**
     * <code>SQL_SOURCE_CACHES</code>
     * {@link java.util.Map} <p>The <code>SQL_SOURCE_CACHES</code> field.</p>
     * @see java.util.Map
     */
    private static Map<Configuration, Map<String, SqlSource>> SQL_SOURCE_CACHES;
    /**
     * <code>INSTANCE</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisContextHolder} <p>The constant <code>INSTANCE</code> field.</p>
     */
    private static MybatisContextHolder INSTANCE = null;

    /**
     * <code>instance</code>
     * <p>The instance method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisContextHolder} <p>The instance return object is <code>MybatisContextHolder</code> type.</p>
     */
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

    /**
     * <code>isUseOnce</code>
     * <p>The is use once method.</p>
     * @return boolean <p>The is use once return object is <code>boolean</code> type.</p>
     */
    public static boolean isUseOnce() {
        return IS_USE_ONCE;
    }

    /**
     * <code>tableProperties</code>
     * <p>The table properties method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.configure.MybatisTableProperties} <p>The table properties return object is <code>MybatisTableProperties</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.configure.MybatisTableProperties
     */
    public static MybatisTableProperties tableProperties() {
        return INSTANCE.tableProperties;
    }

    /**
     * <code>cacheProperties</code>
     * <p>The cache properties method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties} <p>The cache properties return object is <code>MybatisCacheProperties</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties
     */
    public static MybatisCacheProperties cacheProperties() {
        return INSTANCE.cacheProperties;
    }

    /**
     * <code>putSqlCache</code>
     * <p>The put sql cache method.</p>
     * @param cacheKey {@link java.lang.String} <p>The cache key parameter is <code>String</code> type.</p>
     * @param sqlCache {@link io.github.nichetoolkit.mybatis.MybatisSqlCache} <p>The sql cache parameter is <code>MybatisSqlCache</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisSqlCache} <p>The put sql cache return object is <code>MybatisSqlCache</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisSqlCache
     */
    public static MybatisSqlCache putSqlCache(String cacheKey, MybatisSqlCache sqlCache) {
        return SQL_CACHES.put(cacheKey,sqlCache);
    }

    /**
     * <code>getSqlCache</code>
     * <p>The get sql cache getter method.</p>
     * @param cacheKey {@link java.lang.String} <p>The cache key parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisSqlCache} <p>The get sql cache return object is <code>MybatisSqlCache</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.MybatisSqlCache
     */
    public static MybatisSqlCache getSqlCache(String cacheKey) {
        return SQL_CACHES.get(cacheKey);
    }

    /**
     * <code>containsKey</code>
     * <p>The contains key method.</p>
     * @param cacheKey {@link java.lang.String} <p>The cache key parameter is <code>String</code> type.</p>
     * @return boolean <p>The contains key return object is <code>boolean</code> type.</p>
     * @see java.lang.String
     */
    public static boolean containsKey(String cacheKey) {
        return SQL_CACHES.containsKey(cacheKey);
    }

    /**
     * <code>containsKey</code>
     * <p>The contains key method.</p>
     * @param configuration {@link org.apache.ibatis.session.Configuration} <p>The configuration parameter is <code>Configuration</code> type.</p>
     * @return boolean <p>The contains key return object is <code>boolean</code> type.</p>
     * @see org.apache.ibatis.session.Configuration
     */
    public static boolean containsKey(Configuration configuration) {
        return SQL_SOURCE_CACHES.containsKey(configuration);
    }

    /**
     * <code>containsKey</code>
     * <p>The contains key method.</p>
     * @param configuration {@link org.apache.ibatis.session.Configuration} <p>The configuration parameter is <code>Configuration</code> type.</p>
     * @param cacheKey      {@link java.lang.String} <p>The cache key parameter is <code>String</code> type.</p>
     * @return boolean <p>The contains key return object is <code>boolean</code> type.</p>
     * @see org.apache.ibatis.session.Configuration
     * @see java.lang.String
     */
    public static boolean containsKey(Configuration configuration, String cacheKey) {
        return SQL_SOURCE_CACHES.get(configuration).containsKey(cacheKey);
    }

    /**
     * <code>computeIfAbsent</code>
     * <p>The compute if absent method.</p>
     * @param configuration {@link org.apache.ibatis.session.Configuration} <p>The configuration parameter is <code>Configuration</code> type.</p>
     * @return {@link java.util.Map} <p>The compute if absent return object is <code>Map</code> type.</p>
     * @see org.apache.ibatis.session.Configuration
     * @see java.util.Map
     */
    public static Map<String, SqlSource> computeIfAbsent(Configuration configuration) {
        return SQL_SOURCE_CACHES.computeIfAbsent(configuration, k -> new HashMap<>());
    }

    /**
     * <code>getSqlSource</code>
     * <p>The get sql source getter method.</p>
     * @param configuration {@link org.apache.ibatis.session.Configuration} <p>The configuration parameter is <code>Configuration</code> type.</p>
     * @return {@link java.util.Map} <p>The get sql source return object is <code>Map</code> type.</p>
     * @see org.apache.ibatis.session.Configuration
     * @see java.util.Map
     */
    public static Map<String, SqlSource> getSqlSource(Configuration configuration) {
        return SQL_SOURCE_CACHES.get(configuration);
    }

    /**
     * <code>getSqlSource</code>
     * <p>The get sql source getter method.</p>
     * @param configuration {@link org.apache.ibatis.session.Configuration} <p>The configuration parameter is <code>Configuration</code> type.</p>
     * @param cacheKey      {@link java.lang.String} <p>The cache key parameter is <code>String</code> type.</p>
     * @return {@link org.apache.ibatis.mapping.SqlSource} <p>The get sql source return object is <code>SqlSource</code> type.</p>
     * @see org.apache.ibatis.session.Configuration
     * @see java.lang.String
     * @see org.apache.ibatis.mapping.SqlSource
     */
    public static SqlSource getSqlSource(Configuration configuration, String cacheKey) {
        return SQL_SOURCE_CACHES.get(configuration).get(cacheKey);
    }
}
