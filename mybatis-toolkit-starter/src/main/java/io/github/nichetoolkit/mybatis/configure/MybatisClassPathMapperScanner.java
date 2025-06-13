package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.RestMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class MybatisClassPathMapperScanner extends ClassPathBeanDefinitionScanner {
    public MybatisClassPathMapperScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    protected void registerFilters() {
        addIncludeFilter(new AnnotationTypeFilter(Mapper.class));
        addIncludeFilter(new AnnotationTypeFilter(RestMapper.class));
    }

    @NonNull
    @Override
    protected Set<BeanDefinitionHolder> doScan(@NonNull String... basePackages) {
        BeanDefinitionRegistry registry = super.getRegistry();
        assert registry != null;
        registerBeanDefinitionAnnotation(Arrays.asList(basePackages), registry, Mapper.class);
        return Collections.emptySet();
    }

    private void registerBeanDefinitionAnnotation(List<String> packages, BeanDefinitionRegistry registry, Class<? extends Annotation> annotationClass) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);
        builder.addPropertyValue("processPropertyPlaceHolders", true);
        builder.addPropertyValue("annotationClass", annotationClass);
        builder.addPropertyValue("basePackage", StringUtils.collectionToCommaDelimitedString(packages));
        BeanWrapper beanWrapper = new BeanWrapperImpl(MapperScannerConfigurer.class);
        Stream.of(beanWrapper.getPropertyDescriptors()).filter((x) -> x.getName().equals("lazyInitialization")).findAny().ifPresent((x) -> {
            builder.addPropertyValue("lazyInitialization", "${nichetoolkit.mybatis.table.use-lazy-init:false}");
        });
        registry.registerBeanDefinition(MapperScannerConfigurer.class.getName(), builder.getBeanDefinition());
    }
}