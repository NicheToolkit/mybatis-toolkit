package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.DefaultMapperFactory;
import io.github.nichetoolkit.mybatis.MybatisMapperFactory;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * <code>MybatisMapperAutoConfigure</code>
 * <p>The mybatis mapper auto configure class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.boot.autoconfigure.AutoConfiguration
 * @see org.springframework.context.annotation.ComponentScan
 * @see org.springframework.boot.context.properties.EnableConfigurationProperties
 * @since Jdk17
 */
@Slf4j
@AutoConfiguration
@ComponentScan("io.github.nichetoolkit.mybatis.provider")
@EnableConfigurationProperties({MybatisRecordProperties.class})
public class MybatisMapperAutoConfigure {

    /**
     * <code>MybatisMapperAutoConfigure</code>
     * <p>Instantiates a new mybatis mapper auto configure.</p>
     */
    public MybatisMapperAutoConfigure() {
        log.debug("The auto configuration for [mybatis-mapper] initiated");
    }

    /**
     * <code>mapperFactory</code>
     * <p>The mapper factory method.</p>
     * @param sqlSessionTemplate {@link org.mybatis.spring.SqlSessionTemplate} <p>The sql session template parameter is <code>SqlSessionTemplate</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisMapperFactory} <p>The mapper factory return object is <code>MybatisMapperFactory</code> type.</p>
     * @see org.mybatis.spring.SqlSessionTemplate
     * @see io.github.nichetoolkit.mybatis.MybatisMapperFactory
     * @see org.springframework.context.annotation.Bean
     * @see org.springframework.boot.autoconfigure.condition.ConditionalOnClass
     * @see org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
     */
    @Bean
    @ConditionalOnClass(SqlSessionTemplate.class)
    @ConditionalOnMissingBean(MybatisMapperFactory.class)
    public MybatisMapperFactory<?,?,?> mapperFactory(SqlSessionTemplate sqlSessionTemplate) {
        DefaultMapperFactory<?,?,?> mapperFactory = new DefaultMapperFactory<>(sqlSessionTemplate);
        mapperFactory.registryMappers();
        return mapperFactory;
    }

    /**
     * <code>MybatisRecordAutoRegister</code>
     * <p>The mybatis record auto register class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @see org.springframework.beans.factory.InitializingBean
     * @see org.springframework.boot.autoconfigure.AutoConfiguration
     * @see org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
     * @since Jdk17
     */
    @AutoConfiguration
    @ConditionalOnProperty(prefix = "nichetoolkit.mybatis.record", name="enabled", havingValue = "true")
    public static class MybatisRecordAutoRegister implements InitializingBean {
        /**
         * <code>mapperFactory</code>
         * {@link io.github.nichetoolkit.mybatis.MybatisMapperFactory} <p>The <code>mapperFactory</code> field.</p>
         * @see io.github.nichetoolkit.mybatis.MybatisMapperFactory
         */
        private final MybatisMapperFactory<?,?,?> mapperFactory;

        /**
         * <code>MybatisRecordAutoRegister</code>
         * <p>Instantiates a new mybatis record auto register.</p>
         * @param mapperFactory {@link io.github.nichetoolkit.mybatis.MybatisMapperFactory} <p>The mapper factory parameter is <code>MybatisMapperFactory</code> type.</p>
         * @see io.github.nichetoolkit.mybatis.MybatisMapperFactory
         */
        public MybatisRecordAutoRegister(MybatisMapperFactory<?,?,?> mapperFactory) {
            this.mapperFactory = mapperFactory;
        }

        @Override
        public void afterPropertiesSet() {
            mapperFactory.registerAsDefault();
        }
    }

}
