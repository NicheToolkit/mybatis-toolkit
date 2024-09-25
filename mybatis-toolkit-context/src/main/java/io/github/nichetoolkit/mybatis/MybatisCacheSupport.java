package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <code>MybatisCacheSupport</code>
 * <p>The type mybatis cache support class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see org.springframework.beans.factory.InitializingBean
 * @see lombok.extern.slf4j.Slf4j
 * @since Jdk1.8
 */
@Slf4j
public abstract class MybatisCacheSupport implements InitializingBean {
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
     * <code>cacheProperties</code>
     * {@link io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties} <p>the <code>cacheProperties</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties
     */
    private final MybatisCacheProperties cacheProperties;

    /**
     * <code>MybatisCacheSupport</code>
     * Instantiates a new mybatis cache support.
     * @param cacheProperties {@link io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties} <p>the cache properties parameter is <code>MybatisCacheProperties</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties
     * @see org.springframework.beans.factory.annotation.Autowired
     */
    @Autowired
    public MybatisCacheSupport(MybatisCacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    /**
     * <code>INSTANCE</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisCacheSupport} <p>the constant <code>INSTANCE</code> field.</p>
     */
    private static MybatisCacheSupport INSTANCE = null;

    /**
     * <code>instance</code>
     * <p>the method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisCacheSupport} <p>the return object is <code>MybatisCacheSupport</code> type.</p>
     */
    public static MybatisCacheSupport instance() {
        return INSTANCE;
    }

    @Override
    public void afterPropertiesSet() {
        INSTANCE = this;
        CACHE_SQL = new ConcurrentHashMap<>(this.cacheProperties.getInitSize());
        USE_ONCE = this.cacheProperties.isUseOnce();
    }


}
