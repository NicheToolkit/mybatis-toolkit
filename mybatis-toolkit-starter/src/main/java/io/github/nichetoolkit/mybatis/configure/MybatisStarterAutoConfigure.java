package io.github.nichetoolkit.mybatis.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

@Slf4j
@AutoConfiguration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
@Import({ MybatisStarterAutoConfigure.MybatisMapperAutoRegister.class})
@ImportAutoConfiguration({MybatisRecordAutoConfigure.class})
public class MybatisStarterAutoConfigure {

    public MybatisStarterAutoConfigure() {
        log.debug("the auto configuration for [mybatis-starter] initiated");
    }

    public static class MybatisMapperAutoRegister implements ImportBeanDefinitionRegistrar {

        @Override
        public void registerBeanDefinitions(@NonNull AnnotationMetadata importingClassMetadata, @NonNull BeanDefinitionRegistry registry) {
            MybatisRestMapperScanner scanner = new MybatisRestMapperScanner(registry,false);
            scanner.registerFilters();
            scanner.doScan("io.github.nichetoolkit.mybatis");
        }
    }
}
