package io.github.nichetoolkit.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * <code>MybatisExampleApplication</code>
 * <p>The mybatis example application class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see org.springframework.boot.web.servlet.support.SpringBootServletInitializer
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 * @see org.springframework.context.annotation.ComponentScan
 * @since Jdk1.8
 */
@SpringBootApplication
@ComponentScan(basePackages = "io.github.nichetoolkit")
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
