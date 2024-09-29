package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.MybatisCacheSupport;
import io.github.nichetoolkit.mybatis.MybatisProviderSupport;
import io.github.nichetoolkit.mybatis.configure.MybatisCacheProperties;
import io.github.nichetoolkit.mybatis.configure.MybatisTableProperties;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultCacheManager extends MybatisCacheSupport {

    public DefaultCacheManager(MybatisCacheProperties cacheProperties) {
        super(cacheProperties);
    }
}
