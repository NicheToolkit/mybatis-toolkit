package io.github.nichetoolkit.mybatis.configure;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <code>MybatisTestWebAutoConfigure</code>
 * <p>The type mybatis test web auto configure class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.context.annotation.ComponentScan
 * @since Jdk1.8
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
public class MybatisTestWebAutoConfigure {
    /**
     * <code>MybatisTestWebAutoConfigure</code>
     * Instantiates a new mybatis test web auto configure.
     */
    public MybatisTestWebAutoConfigure() {
        log.debug("================= mybatis-toolkit-test-web initiated ！ ===================");
    }
}
