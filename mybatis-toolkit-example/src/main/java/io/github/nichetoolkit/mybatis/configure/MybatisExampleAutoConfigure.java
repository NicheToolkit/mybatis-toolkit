package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.simple.TemplateIdentity;
import io.github.nichetoolkit.rest.identity.IdentityUtils;
import io.github.nichetoolkit.rice.resolver.RestIdentityResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
public class MybatisExampleAutoConfigure {
    public MybatisExampleAutoConfigure() {
        log.debug("The auto configuration for [mybatis-example] initiated");
    }

    @Bean
    public RestIdentityResolver<TemplateIdentity> templateIdentityResolver() {
        return () -> new TemplateIdentity(IdentityUtils.valueOfString(),IdentityUtils.valueOfString());
    }
}
