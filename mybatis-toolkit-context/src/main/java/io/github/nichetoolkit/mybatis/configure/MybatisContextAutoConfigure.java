package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.MybatisCacheSupport;
import io.github.nichetoolkit.mybatis.MybatisProviderSupport;
import io.github.nichetoolkit.mybatis.defaults.DefaultCacheManager;
import io.github.nichetoolkit.mybatis.defaults.DefaultProviderManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
public class MybatisContextAutoConfigure {

    public MybatisContextAutoConfigure() {
        log.debug("the auto configuration for [mybatis-context] initiated");
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(MybatisCacheSupport.class)
    public MybatisCacheSupport mybatisCache(MybatisCacheProperties cacheProperties) {
        return new DefaultCacheManager(cacheProperties);
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(MybatisProviderSupport.class)
    public MybatisProviderSupport mybatisProvider(MybatisTableProperties tableProperties) {
        return new DefaultProviderManager(tableProperties);
    }

}
