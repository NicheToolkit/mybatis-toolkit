package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.record.MybatisRecordProvider;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

@Slf4j
@AutoConfiguration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
@EnableConfigurationProperties({MybatisRecordProperties.class})
@ConditionalOnProperty(prefix = "nichetoolkit.mybatis.record", name="enabled", havingValue = "true", matchIfMissing = true)
public class MybatisRecordAutoConfigure {

    public MybatisRecordAutoConfigure() {
        log.debug("the auto configuration for [mybatis-record] initiated");
    }

    @Bean
    @ConditionalOnMissingBean(MybatisRecordProvider.class)
    public MybatisRecordProvider<?,?,?> recordProvider(SqlSessionTemplate sqlSessionTemplate) {
        return new MybatisRecordProvider<>(sqlSessionTemplate);
    }

    @AutoConfiguration
    public static class MybatisRecordAutoRegister implements InitializingBean {
        private final MybatisRecordProvider<?,?,?> recordProvider;

        @Autowired
        public MybatisRecordAutoRegister(MybatisRecordProvider<?,?,?> recordProvider) {
            this.recordProvider = recordProvider;
        }

        @Override
        public void afterPropertiesSet() {
            recordProvider.registerAsDefault();
        }
    }

}
