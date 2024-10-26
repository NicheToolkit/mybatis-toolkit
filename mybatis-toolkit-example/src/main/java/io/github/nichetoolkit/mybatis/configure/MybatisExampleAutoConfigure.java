package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.simple.TemplateIdentity;
import io.github.nichetoolkit.rest.identity.IdentityUtils;
import io.github.nichetoolkit.rice.resolver.RestIdentityResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <code>MybatisExampleAutoConfigure</code>
 * <p>The mybatis example auto configure class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.context.annotation.ComponentScan
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
     * <code>templateIdentityResolver</code>
     * <p>The template identity resolver method.</p>
     * @return {@link io.github.nichetoolkit.rice.resolver.RestIdentityResolver} <p>The template identity resolver return object is <code>RestIdentityResolver</code> type.</p>
     * @see io.github.nichetoolkit.rice.resolver.RestIdentityResolver
     * @see org.springframework.context.annotation.Bean
     */
    @Bean
    public RestIdentityResolver<TemplateIdentity> templateIdentityResolver() {
        return () -> new TemplateIdentity(IdentityUtils.valueOfString(),IdentityUtils.valueOfString());
    }
}
