package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.MybatisAlertnessHandler;
import io.github.nichetoolkit.mybatis.MybatisColumnResolver;
import io.github.nichetoolkit.mybatis.MybatisIdentityHandler;
import io.github.nichetoolkit.rice.DefaultAlertnessHandler;
import io.github.nichetoolkit.rice.DefaultColumnResolver;
import io.github.nichetoolkit.rice.DefaultIdentityHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

/**
 * <code>MybatisStarterAutoConfigure</code>
 * <p>The mybatis starter auto configure class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.boot.autoconfigure.AutoConfiguration
 * @see org.springframework.context.annotation.ComponentScan
 * @see org.springframework.context.annotation.Import
 * @see org.springframework.boot.autoconfigure.ImportAutoConfiguration
 * @since Jdk1.8
 */
@Slf4j
@AutoConfiguration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
@Import({MybatisStarterAutoConfigure.MybatisMapperAutoRegister.class})
@ImportAutoConfiguration({MybatisMapperAutoConfigure.class})
public class MybatisStarterAutoConfigure {

    /**
     * <code>MybatisStarterAutoConfigure</code>
     * <p>Instantiates a new mybatis starter auto configure.</p>
     */
    public MybatisStarterAutoConfigure() {
        log.debug("The auto configuration for [mybatis-starter] initiated");
    }

    /**
     * <code>defaultIdentityHandler</code>
     * <p>The default identity handler method.</p>
     * @return {@link io.github.nichetoolkit.rice.DefaultIdentityHandler} <p>The default identity handler return object is <code>DefaultIdentityHandler</code> type.</p>
     * @see io.github.nichetoolkit.rice.DefaultIdentityHandler
     * @see org.springframework.context.annotation.Bean
     * @see org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
     */
    @Bean
    @ConditionalOnMissingBean(DefaultIdentityHandler.class)
    public DefaultIdentityHandler<?> defaultIdentityHandler() {
        return MybatisIdentityHandler.DEFAULT_HANDLER;
    }


    /**
     * <code>defaultColumnResolver</code>
     * <p>The default column resolver method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisColumnResolver} <p>The default column resolver return object is <code>MybatisColumnResolver</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisColumnResolver
     * @see org.springframework.context.annotation.Bean
     * @see org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
     */
    @Bean
    @ConditionalOnMissingBean(DefaultColumnResolver.class)
    public MybatisColumnResolver defaultColumnResolver() {
        return MybatisColumnResolver.DEFAULT_RESOLVER;
    }

    /**
     * <code>defaultAlertnessHandler</code>
     * <p>The default alertness handler method.</p>
     * @return {@link io.github.nichetoolkit.rice.DefaultAlertnessHandler} <p>The default alertness handler return object is <code>DefaultAlertnessHandler</code> type.</p>
     * @see io.github.nichetoolkit.rice.DefaultAlertnessHandler
     * @see org.springframework.context.annotation.Bean
     * @see org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
     */
    @Bean
    @ConditionalOnMissingBean(DefaultAlertnessHandler.class)
    public DefaultAlertnessHandler<?> defaultAlertnessHandler() {
        return MybatisAlertnessHandler.DEFAULT_HANDLER;
    }

    /**
     * <code>MybatisMapperAutoRegister</code>
     * <p>The mybatis mapper auto register class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @see org.springframework.context.annotation.ImportBeanDefinitionRegistrar
     * @since Jdk1.8
     */
    public static class MybatisMapperAutoRegister implements ImportBeanDefinitionRegistrar {

        @Override
        public void registerBeanDefinitions(@NonNull AnnotationMetadata importingClassMetadata, @NonNull BeanDefinitionRegistry registry) {
            MybatisRestMapperScanner scanner = new MybatisRestMapperScanner(registry, false);
            scanner.registerFilters();
            scanner.doScan("io.github.nichetoolkit.mybatis");
        }
    }

}
