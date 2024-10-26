package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.stereotype.RestMapper;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.lang.NonNull;

import java.util.Set;

/**
 * <code>MybatisRestMapperScanner</code>
 * <p>The mybatis rest mapper scanner class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see org.springframework.context.annotation.ClassPathBeanDefinitionScanner
 * @since Jdk1.8
 */
public class MybatisRestMapperScanner extends ClassPathBeanDefinitionScanner {
    /**
     * <code>MybatisRestMapperScanner</code>
     * <p>Instantiates a new mybatis rest mapper scanner.</p>
     * @param registry          {@link org.springframework.beans.factory.support.BeanDefinitionRegistry} <p>The registry parameter is <code>BeanDefinitionRegistry</code> type.</p>
     * @param useDefaultFilters boolean <p>The use default filters parameter is <code>boolean</code> type.</p>
     * @see org.springframework.beans.factory.support.BeanDefinitionRegistry
     */
    public MybatisRestMapperScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    /**
     * <code>registerFilters</code>
     * <p>The register filters method.</p>
     */
    protected void registerFilters() {
        addIncludeFilter(new AnnotationTypeFilter(RestMapper.class));
    }

    @NonNull
    @Override
    protected Set<BeanDefinitionHolder> doScan(@NonNull String... basePackages) {
        return super.doScan(basePackages);
    }

}