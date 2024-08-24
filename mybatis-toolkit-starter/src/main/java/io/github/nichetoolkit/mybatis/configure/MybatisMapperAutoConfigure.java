package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.advice.MybatisMapperAdvice;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotationMetadata;

/**
 * <p>MybatisAutoConfigure</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
@EnableConfigurationProperties({MybatisMapperProperties.class})
@Import({ MybatisMapperAutoConfigure.MybatisMapperAutoRegister.class})
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

    public static class MybatisMapperAutoRegister implements ImportBeanDefinitionRegistrar {

        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            MybatisRestMapperScanner scanner = new MybatisRestMapperScanner(registry,false);
            scanner.registerFilters();
            scanner.doScan("io.github.nichetoolkit.mybatis");
        }
    }

}
