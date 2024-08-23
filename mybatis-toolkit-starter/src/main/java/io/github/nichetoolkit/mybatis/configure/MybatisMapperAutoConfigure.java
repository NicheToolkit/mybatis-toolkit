package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.advice.MybatisMapperAdvice;
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
@ConditionalOnProperty(prefix = "nichetoolkit.mybatis.mapper", name="enabled", havingValue = "true", matchIfMissing = true)
public class MybatisMapperAutoConfigure {

    public MybatisMapperAutoConfigure() {
        log.debug("================= mybatis-mapper-auto-config initiated ！ ===================");
    }

    @Bean
    @ConditionalOnMissingBean(MybatisMapperAdvice.class)
    public MybatisMapperAdvice<?,?,?> mapperAdvice(SqlSessionTemplate sqlSessionTemplate) {
        return new MybatisMapperAdvice<>(sqlSessionTemplate);
    }

    @Configuration
    @ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
    public static class AutoRegisterConfigure implements InitializingBean {
        private final MybatisMapperAdvice<?,?,?> mapperAdvice;

        @Autowired
        public AutoRegisterConfigure(MybatisMapperAdvice<?,?,?> mapperAdvice) {
            this.mapperAdvice = mapperAdvice;
        }

        @Override
        public void afterPropertiesSet() {
            mapperAdvice.registerAsDefault();
        }
    }

}
