package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class MybatisCacheSupport implements InitializingBean {
    static final Map<Configuration, Map<String, SqlSource>> CONFIGURATION_CACHE_KEY_MAP = new ConcurrentHashMap<>(4);
    static boolean USE_ONCE;
    static Map<String, MybatisSqlCache> CACHE_SQL;

    private final MybatisCacheProperties cacheProperties;

    @Autowired
    public MybatisCacheSupport(MybatisCacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    private static MybatisCacheSupport INSTANCE = null;

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
