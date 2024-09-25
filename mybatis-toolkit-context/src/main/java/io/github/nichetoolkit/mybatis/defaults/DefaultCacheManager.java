package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.MybatisCacheSupport;
import io.github.nichetoolkit.mybatis.MybatisProviderSupport;
import io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties;
import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>DefaultProviderManager</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class DefaultCacheManager extends MybatisCacheSupport {

    /**
     * <code>MybatisCaches</code>
     * Instantiates a new mybatis caches.
     * @param cacheProperties {@link MybatisCacheProperties} <p>the cache properties parameter is <code>MybatisCacheProperties</code> type.</p>
     * @see MybatisCacheProperties
     * @see Autowired
     */
    public DefaultCacheManager(MybatisCacheProperties cacheProperties) {
        super(cacheProperties);
    }
}
