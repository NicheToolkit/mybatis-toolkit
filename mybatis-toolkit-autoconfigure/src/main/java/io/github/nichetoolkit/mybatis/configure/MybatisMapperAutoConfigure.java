package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.MybatisMapperProvider;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>MybatisAutoConfigure</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
@EnableConfigurationProperties({MybatisMapperProperties.class})
@ConditionalOnProperty(prefix = "nichetoolkit.mybatis.mapper.enabled", havingValue = "true", matchIfMissing = true)
public class MybatisMapperAutoConfigure {

    public MybatisMapperAutoConfigure() {
        log.debug("================= mybatis-mapper-auto-config initiated ！ ===================");
    }

    @Bean
    @ConditionalOnMissingBean(MybatisMapperProvider.class)
    public MybatisMapperProvider<?,?,?> mapperProvider(SqlSessionTemplate sqlSessionTemplate) {
        return new MybatisMapperProvider<>(sqlSessionTemplate);
    }

    @Configuration
    @ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
    public static class AutoRegisterConfigure implements InitializingBean {
        private final MybatisMapperProvider<?,?,?> mapperProvider;

        @Autowired
        public AutoRegisterConfigure(MybatisMapperProvider<?,?,?> mapperProvider) {
            this.mapperProvider = mapperProvider;
        }

        @Override
        public void afterPropertiesSet() {
            mapperProvider.registerAsDefault();
        }
    }

}
