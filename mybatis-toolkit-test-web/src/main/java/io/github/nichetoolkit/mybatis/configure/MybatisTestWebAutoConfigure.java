package io.github.nichetoolkit.mybatis.configure;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>MybatisTestWebAutoConfigure</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Slf4j
@Configuration
@MapperScan(basePackages = {"io.github.nichetoolkit.mybatis.mapper"})
@ComponentScan(basePackages = {"io.github.nichetoolkit.mybatis"})
public class MybatisTestWebAutoConfigure {
    public MybatisTestWebAutoConfigure() {
        log.debug("================= mybatis-toolkit-test-web initiated ！ ===================");
    }
}
