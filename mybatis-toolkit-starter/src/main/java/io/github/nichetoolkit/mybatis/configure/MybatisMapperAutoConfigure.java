package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.advice.MybatisMapperAdvice;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

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
    public static class MybatisAdviceAutoRegister implements InitializingBean {
        private final MybatisMapperAdvice<?,?,?> mapperAdvice;

        @Autowired
        public MybatisAdviceAutoRegister(MybatisMapperAdvice<?,?,?> mapperAdvice) {
            this.mapperAdvice = mapperAdvice;
        }

        @Override
        public void afterPropertiesSet() {
            mapperAdvice.registerAsDefault();
        }
    }

    @Configuration
    @ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
    public static class MybatisMapperAutoRegister implements BeanDefinitionRegistryPostProcessor {
        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        }

        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            MybatisRestMapperScanner scanner = new MybatisRestMapperScanner(registry,false);
            scanner.registerFilters();
            scanner.doScan("io.github.nichetoolkit.mybatis");
        }


    }

}
