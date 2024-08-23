package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>MybatisCaching</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Slf4j
public class MybatisCaches {

    /**
     * 多数据源，多配置的情况下（甚至单元测试时），同一个方法会在不同的 Configuration 中出现，如果不做处理就会出现不一致
     */
    static final Map<Configuration, Map<String, SqlSource>> CONFIGURATION_CACHE_KEY_MAP = new ConcurrentHashMap<>(4);
    /**
     * 是否只使用一次，默认 false，设置为 true 后，当使用过一次后，就会取消引用，可以被后续的GC清理
     * 当使用SqlSessionFactory配置多数据源时，不能设置为 true，设置true被GC清理后，新的数据源就无法正常使用
     * 当从DataSource层面做多数据源时，只有一个SqlSessionFactory时，可以设置为true
     */
    static boolean USE_ONCE;

    /**
     * 缓存方法对应的 MybatisSqlCache，预设1024约等于30个实体，每个实体25个方法
     * 当存在一个数据源时，当前缓存是可以最终清空的，但是多个数据源时，就必须保留，因为不清楚什么时候可以清理
     */
    static Map<String, MybatisSqlCache> CACHE_SQL;

    @Autowired
    public MybatisCaches(MybatisCacheProperties cacheProperties) {
        CACHE_SQL = new ConcurrentHashMap<>(cacheProperties.getInitSize());
        USE_ONCE = cacheProperties.isUseOnce();
    }

}
