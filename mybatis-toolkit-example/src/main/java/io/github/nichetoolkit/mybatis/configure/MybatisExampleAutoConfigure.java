package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.simple.TemplateIdentity;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.identity.IdentityUtils;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.RestIdResolver;
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
    public RestIdResolver<TemplateIdentity> templateIdentityResolver() {
        return new RestIdResolver<TemplateIdentity>() {
            @Override
            public <M extends RestId<TemplateIdentity>> TemplateIdentity resolve(M model) throws RestException {
                return new TemplateIdentity(IdentityUtils.valueOfString(),IdentityUtils.valueOfString());
            }
        };
    }
}
