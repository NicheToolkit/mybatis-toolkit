package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.test.template.TemplateIdentity;
import io.github.nichetoolkit.rest.identity.IdentityUtils;
import io.github.nichetoolkit.rice.resolver.RestIdResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <code>MybatisExampleAutoConfigure</code>
 * <p>The mybatis example auto configure class.</p>
 * @see  lombok.extern.slf4j.Slf4j
 * @see  org.springframework.context.annotation.Configuration
 * @see  org.springframework.context.annotation.ComponentScan
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
public class MybatisExampleAutoConfigure {
    /**
     * <code>MybatisExampleAutoConfigure</code>
     * <p>Instantiates a new mybatis example auto configure.</p>
     */
    public MybatisExampleAutoConfigure() {
        log.debug("The auto configuration for [mybatis-example] initiated");
    }

    /**
     * <code>templateIdResolver</code>
     * <p>The template id resolver method.</p>
     * @return  {@link io.github.nichetoolkit.rice.resolver.RestIdResolver} <p>The template id resolver return object is <code>RestIdResolver</code> type.</p>
     * @see  io.github.nichetoolkit.rice.resolver.RestIdResolver
     * @see  org.springframework.context.annotation.Bean
     */
    @Bean
    public RestIdResolver<TemplateIdentity> templateIdResolver() {
        return () -> new TemplateIdentity(IdentityUtils.valueOfString(),IdentityUtils.valueOfString());
    }
}
