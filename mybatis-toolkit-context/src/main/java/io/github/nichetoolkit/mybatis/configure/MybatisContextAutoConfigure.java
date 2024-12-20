package io.github.nichetoolkit.mybatis.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <code>MybatisContextAutoConfigure</code>
 * <p>The mybatis context auto configure class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.context.annotation.ComponentScan
 * @since Jdk1.8
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
public class MybatisContextAutoConfigure {

    /**
     * <code>MybatisContextAutoConfigure</code>
     * <p>Instantiates a new mybatis context auto configure.</p>
     */
    public MybatisContextAutoConfigure() {
        log.debug("The auto configuration for [mybatis-context] initiated");
    }

}
