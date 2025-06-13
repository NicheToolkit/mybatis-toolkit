package io.github.nichetoolkit.mybatis.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MybatisMapperAutoRegister implements BeanFactoryAware, ImportBeanDefinitionRegistrar {
    private BeanFactory beanFactory;

    public MybatisMapperAutoRegister() {
    }

    @Override
    public void registerBeanDefinitions(@NonNull AnnotationMetadata importingClassMetadata, @NonNull BeanDefinitionRegistry registry) {
        List<String> packages = new ArrayList<>();
        if (AutoConfigurationPackages.has(this.beanFactory)) {
            packages = AutoConfigurationPackages.get(this.beanFactory);
        } else {
            packages.add("io.github.nichetoolkit.mybatis");
        }
        MybatisClassPathMapperScanner scanner = new MybatisClassPathMapperScanner(registry, false);
        scanner.registerFilters();
        scanner.doScan(packages.toArray(new String[0]));
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}

