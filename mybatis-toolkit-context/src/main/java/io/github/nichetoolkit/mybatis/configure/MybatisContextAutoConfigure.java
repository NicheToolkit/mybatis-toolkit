package io.github.nichetoolkit.mybatis.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
public class MybatisContextAutoConfigure {

    public MybatisContextAutoConfigure() {
        log.debug("The auto configuration for [mybatis-context] initiated");
    }

}
