package io.github.nichetoolkit.mybatis.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
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
@ImportAutoConfiguration({MybatisRecordAutoConfigure.class})
public class MybatisStarterAutoConfigure {

    /**
     * <code>MybatisStarterAutoConfigure</code>
     * <p>Instantiates a new mybatis starter auto configure.</p>
     */
    public MybatisStarterAutoConfigure() {
        log.debug("The auto configuration for [mybatis-starter] initiated");
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
