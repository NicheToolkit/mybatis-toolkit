package io.github.nichetoolkit.mybatis.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <code>MybatisCoreAutoConfigure</code>
 * <p>The mybatis core auto configure class.</p>
 * @see  lombok.extern.slf4j.Slf4j
 * @see  org.springframework.context.annotation.Configuration
 * @see  org.springframework.context.annotation.ComponentScan
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
public class MybatisCoreAutoConfigure {
    /**
     * <code>MybatisCoreAutoConfigure</code>
     * <p>Instantiates a new mybatis core auto configure.</p>
     */
    public MybatisCoreAutoConfigure() {
        log.debug("The auto configuration for [mybatis-core] initiated");
    }

}
