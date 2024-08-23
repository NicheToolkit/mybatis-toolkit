package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.MybatisCaches;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.Nullable;

/**
 * <p>MybatisCoreAutoConfigure</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
public class MybatisContextAutoConfigure {

    public MybatisContextAutoConfigure() {
        log.debug("================= mybatis-context-auto-config initiated ！ ===================");
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(MybatisCaches.class)
    public MybatisCaches mybatisCaches(MybatisCacheProperties cacheProperties) {
        return new MybatisCaches(cacheProperties);
    }

}
