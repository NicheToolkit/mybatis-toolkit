package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <code>MybatisCaches</code>
 * <p>The type mybatis caches class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @since Jdk1.8
 */
@Slf4j
public class MybatisCaches {

    /**
     * <code>CONFIGURATION_CACHE_KEY_MAP</code>
     * {@link java.util.Map} <p>the <code>CONFIGURATION_CACHE_KEY_MAP</code> field.</p>
     * @see java.util.Map
     */
    static final Map<Configuration, Map<String, SqlSource>> CONFIGURATION_CACHE_KEY_MAP = new ConcurrentHashMap<>(4);
    /**
     * <code>USE_ONCE</code>
     * <p>the <code>USE_ONCE</code> field.</p>
     */
    static boolean USE_ONCE;

    /**
     * <code>CACHE_SQL</code>
     * {@link java.util.Map} <p>the <code>CACHE_SQL</code> field.</p>
     * @see java.util.Map
     */
    static Map<String, MybatisSqlCache> CACHE_SQL;

    /**
     * <code>MybatisCaches</code>
     * Instantiates a new mybatis caches.
     * @param cacheProperties {@link io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties} <p>the cache properties parameter is <code>MybatisCacheProperties</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties
     * @see org.springframework.beans.factory.annotation.Autowired
     */
    @Autowired
    public MybatisCaches(MybatisCacheProperties cacheProperties) {
        CACHE_SQL = new ConcurrentHashMap<>(cacheProperties.getInitSize());
        USE_ONCE = cacheProperties.isUseOnce();
    }

}
