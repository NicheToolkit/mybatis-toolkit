package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.MybatisSqlSourceHolder;
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
    @ConditionalOnMissingBean(MybatisSqlSourceHolder.class)
    public MybatisSqlSourceHolder mybatisCache(MybatisCacheProperties cacheProperties) {
        return new MybatisSqlSourceHolder(cacheProperties);
    }

}
