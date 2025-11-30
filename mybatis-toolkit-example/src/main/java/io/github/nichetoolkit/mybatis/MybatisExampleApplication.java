package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.scan.EnableMybatisConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * <code>MybatisExampleApplication</code>
 * <p>The mybatis example application class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see org.springframework.boot.web.servlet.support.SpringBootServletInitializer
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 * @see io.github.nichetoolkit.mybatis.scan.EnableMybatisConfiguration
 * @since Jdk17
 */
@SpringBootApplication
@EnableMybatisConfiguration
public class MybatisExampleApplication extends SpringBootServletInitializer {

    /**
     * <code>main</code>
     * <p>The entry point of application.</p>
     * @param args {@link java.lang.String} <p>The input arguments.</p>
     * @see java.lang.String
     */
    public static void main(String[] args) {
        SpringApplication.run(MybatisExampleApplication.class, args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MybatisExampleApplication.class);
    }
}
