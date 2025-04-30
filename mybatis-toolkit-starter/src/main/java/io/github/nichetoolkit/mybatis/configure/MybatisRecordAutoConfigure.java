package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.MybatisMapperFactory;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * <code>MybatisRecordAutoConfigure</code>
 * <p>The mybatis record auto configure class.</p>
 * @see  lombok.extern.slf4j.Slf4j
 * @see  org.springframework.boot.autoconfigure.AutoConfiguration
 * @see  org.springframework.context.annotation.ComponentScan
 * @see  org.springframework.boot.context.properties.EnableConfigurationProperties
 * @see  org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Slf4j
@AutoConfiguration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
@EnableConfigurationProperties({MybatisRecordProperties.class})
@ConditionalOnProperty(prefix = "nichetoolkit.mybatis.record", name="enabled", havingValue = "true", matchIfMissing = true)
public class MybatisRecordAutoConfigure {

    /**
     * <code>MybatisRecordAutoConfigure</code>
     * <p>Instantiates a new mybatis record auto configure.</p>
     */
    public MybatisRecordAutoConfigure() {
        log.debug("The auto configuration for [mybatis-record] initiated");
    }

    /**
     * <code>recordProvider</code>
     * <p>The record provider method.</p>
     * @param sqlSessionTemplate {@link org.mybatis.spring.SqlSessionTemplate} <p>The sql session template parameter is <code>SqlSessionTemplate</code> type.</p>
     * @see  org.mybatis.spring.SqlSessionTemplate
     * @see  MybatisMapperFactory
     * @see  org.springframework.context.annotation.Bean
     * @see  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
     * @return  {@link MybatisMapperFactory} <p>The record provider return object is <code>MybatisRecordProvider</code> type.</p>
     */
    @Bean
    @ConditionalOnMissingBean(MybatisMapperFactory.class)
    public MybatisMapperFactory<?,?,?> recordProvider(SqlSessionTemplate sqlSessionTemplate) {
        return new MybatisMapperFactory<>(sqlSessionTemplate);
    }

    /**
     * <code>MybatisRecordAutoRegister</code>
     * <p>The mybatis record auto register class.</p>
     * @see  org.springframework.beans.factory.InitializingBean
     * @see  org.springframework.boot.autoconfigure.AutoConfiguration
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    @AutoConfiguration
    public static class MybatisRecordAutoRegister implements InitializingBean {
        /**
         * <code>recordProvider</code>
         * {@link MybatisMapperFactory} <p>The <code>recordProvider</code> field.</p>
         * @see  MybatisMapperFactory
         */
        private final MybatisMapperFactory<?,?,?> recordProvider;

        /**
         * <code>MybatisRecordAutoRegister</code>
         * <p>Instantiates a new mybatis record auto register.</p>
         * @param recordProvider {@link MybatisMapperFactory} <p>The record provider parameter is <code>MybatisRecordProvider</code> type.</p>
         * @see  MybatisMapperFactory
         * @see  org.springframework.beans.factory.annotation.Autowired
         */
        @Autowired
        public MybatisRecordAutoRegister(MybatisMapperFactory<?,?,?> recordProvider) {
            this.recordProvider = recordProvider;
        }

        @Override
        public void afterPropertiesSet() {
            recordProvider.registerAsDefault();
        }
    }

}
