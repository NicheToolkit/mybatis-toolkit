package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.provider.MybatisSuperProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

/**
 * <code>MybatisStarterAutoConfigure</code>
 * <p>The type mybatis starter auto configure class.</p>
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
@Import({ MybatisStarterAutoConfigure.MybatisMapperAutoRegister.class})
@ImportAutoConfiguration({MybatisRecordAutoConfigure.class})
public class MybatisStarterAutoConfigure {

    /**
     * <code>MybatisStarterAutoConfigure</code>
     * Instantiates a new mybatis starter auto configure.
     */
    public MybatisStarterAutoConfigure() {
        log.debug("================= mybatis-starter-auto-config initiated ！ ===================");
    }

    /**
     * <code>mybatisProvider</code>
     * <p>the provider method.</p>
     * @param tableProperties {@link io.github.nichetoolkit.mybatis.configure.MybatisTableProperties} <p>the table properties parameter is <code>MybatisTableProperties</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.provider.MybatisSuperProvider} <p>the provider return object is <code>MybatisSuperProvider</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.configure.MybatisTableProperties
     * @see io.github.nichetoolkit.mybatis.provider.MybatisSuperProvider
     * @see org.springframework.context.annotation.Bean
     * @see org.springframework.context.annotation.Primary
     * @see org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(MybatisSuperProvider.class)
    public MybatisSuperProvider mybatisProvider(MybatisTableProperties tableProperties) {
        return new MybatisSuperProvider(tableProperties);
    }

    /**
     * <code>MybatisMapperAutoRegister</code>
     * <p>The type mybatis mapper auto register class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @see org.springframework.context.annotation.ImportBeanDefinitionRegistrar
     * @since Jdk1.8
     */
    public static class MybatisMapperAutoRegister implements ImportBeanDefinitionRegistrar {

        @Override
        public void registerBeanDefinitions(@NonNull AnnotationMetadata importingClassMetadata, @NonNull BeanDefinitionRegistry registry) {
            MybatisRestMapperScanner scanner = new MybatisRestMapperScanner(registry,false);
            scanner.registerFilters();
            scanner.doScan("io.github.nichetoolkit.mybatis");
        }
    }
}
