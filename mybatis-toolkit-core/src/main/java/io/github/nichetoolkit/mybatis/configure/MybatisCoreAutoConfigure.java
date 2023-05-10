package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.MybatisCaches;
import io.github.nichetoolkit.mybatis.defaults.DefaultColumnFactory;
import io.github.nichetoolkit.mybatis.defaults.DefaultTableFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * <p>MybatisCoreAutoConfigure</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
public class MybatisCoreAutoConfigure {

    public MybatisCoreAutoConfigure() {
        log.debug("================= mybatis-core-auto-config initiated ！ ===================");
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(MybatisCaches.class)
    public MybatisCaches mybatisCaches(MybatisCacheProperties cacheProperties) {
        return new MybatisCaches(cacheProperties);
    }

}
