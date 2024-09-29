package io.github.nichetoolkit.mybatis.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
public class MybatisExampleAutoConfigure {
    public MybatisExampleAutoConfigure() {
        log.debug("the auto configuration for [mybatis-example] initiated");
    }
}
