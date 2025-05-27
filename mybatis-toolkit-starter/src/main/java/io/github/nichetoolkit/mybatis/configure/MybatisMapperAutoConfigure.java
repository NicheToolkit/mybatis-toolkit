package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.DefaultMapperFactory;
import io.github.nichetoolkit.mybatis.MybatisMapperFactory;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@AutoConfiguration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
@EnableConfigurationProperties({MybatisRecordProperties.class})
public class MybatisMapperAutoConfigure {

    public MybatisMapperAutoConfigure() {
        log.debug("The auto configuration for [mybatis-mapper] initiated");
    }

    @Bean
    @Autowired
    @ConditionalOnBean(SqlSessionTemplate.class)
    @ConditionalOnMissingBean(MybatisMapperFactory.class)
    public MybatisMapperFactory<?,?,?> mapperFactory(SqlSessionTemplate sqlSessionTemplate) {
        return new DefaultMapperFactory<>(sqlSessionTemplate);
    }

    @AutoConfiguration
    @ConditionalOnProperty(prefix = "nichetoolkit.mybatis.record", name="enabled", havingValue = "true", matchIfMissing = true)
    public static class MybatisRecordAutoRegister implements InitializingBean {
        private final MybatisMapperFactory<?,?,?> mapperFactory;

        @Autowired
        public MybatisRecordAutoRegister(MybatisMapperFactory<?,?,?> mapperFactory) {
            this.mapperFactory = mapperFactory;
        }

        @Override
        public void afterPropertiesSet() {
            mapperFactory.registerAsDefault();
        }
    }

}
