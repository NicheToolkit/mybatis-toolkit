package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.provider.MybatisSuperProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * <p>MybatisStarterAutoConfigure</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
public class MybatisStarterAutoConfigure {

    public MybatisStarterAutoConfigure() {
        log.debug("================= mybatis-starter-auto-config initiated ！ ===================");
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(MybatisSuperProvider.class)
    public MybatisSuperProvider mybatisProvider(MybatisTableProperties tableProperties) {
        return new MybatisSuperProvider(tableProperties);
    }
}
