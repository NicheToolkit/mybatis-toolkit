package io.github.nichetoolkit.mybatis.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <code>MybatisExampleAutoConfigure</code>
 * <p>The type mybatis example auto configure class.</p>
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
     * Instantiates a new mybatis example auto configure.
     */
    public MybatisExampleAutoConfigure() {
        log.debug("the auto configuration for [mybatis-example] initiated");
    }
}
