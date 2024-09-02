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

/**
 * <code>MybatisRecordAutoConfigure</code>
 * <p>The type mybatis record auto configure class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.context.annotation.ComponentScan
 * @see org.springframework.boot.context.properties.EnableConfigurationProperties
 * @see org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
 * @since Jdk1.8
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
@EnableConfigurationProperties({MybatisRecordProperties.class})
@ConditionalOnProperty(prefix = "nichetoolkit.mybatis.record", name="enabled", havingValue = "true", matchIfMissing = true)
public class MybatisRecordAutoConfigure {

    /**
     * <code>MybatisRecordAutoConfigure</code>
     * Instantiates a new mybatis record auto configure.
     */
    public MybatisRecordAutoConfigure() {
        log.debug("================= mybatis-record-auto-config initiated ！ ===================");
    }

    /**
     * <code>recordProvider</code>
     * <p>the provider method.</p>
     * @param sqlSessionTemplate {@link org.mybatis.spring.SqlSessionTemplate} <p>the sql session template parameter is <code>SqlSessionTemplate</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.record.MybatisRecordProvider} <p>the provider return object is <code>MybatisRecordProvider</code> type.</p>
     * @see org.mybatis.spring.SqlSessionTemplate
     * @see io.github.nichetoolkit.mybatis.record.MybatisRecordProvider
     * @see org.springframework.context.annotation.Bean
     * @see org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
     */
    @Bean
    @ConditionalOnMissingBean(MybatisRecordProvider.class)
    public MybatisRecordProvider<?,?,?> recordProvider(SqlSessionTemplate sqlSessionTemplate) {
        return new MybatisRecordProvider<>(sqlSessionTemplate);
    }

    /**
     * <code>MybatisRecordAutoRegister</code>
     * <p>The type mybatis record auto register class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @see org.springframework.beans.factory.InitializingBean
     * @see org.springframework.boot.autoconfigure.AutoConfiguration
     * @since Jdk1.8
     */
    @AutoConfiguration
    public static class MybatisRecordAutoRegister implements InitializingBean {
        private final MybatisRecordProvider<?,?,?> recordProvider;

        /**
         * <code>MybatisRecordAutoRegister</code>
         * Instantiates a new mybatis record auto register.
         * @param recordProvider {@link io.github.nichetoolkit.mybatis.record.MybatisRecordProvider} <p>the record provider parameter is <code>MybatisRecordProvider</code> type.</p>
         * @see io.github.nichetoolkit.mybatis.record.MybatisRecordProvider
         * @see org.springframework.beans.factory.annotation.Autowired
         */
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
