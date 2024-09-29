package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.stereotype.RestMapper;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.lang.NonNull;

import java.util.Set;

public class MybatisRestMapperScanner extends ClassPathBeanDefinitionScanner {
    public MybatisRestMapperScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    protected void registerFilters() {
        addIncludeFilter(new AnnotationTypeFilter(RestMapper.class));
    }

    @NonNull
    @Override
    protected Set<BeanDefinitionHolder> doScan(@NonNull String... basePackages) {
        return super.doScan(basePackages);
    }

}