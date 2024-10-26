package io.github.nichetoolkit.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <code>MybatisExampleApplicationTests</code>
 * <p>The mybatis example application tests class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.boot.test.context.SpringBootTest
 * @since Jdk1.8
 */
@Slf4j
@SpringBootTest
public class MybatisExampleApplicationTests {

    /**
     * <code>contextLoads</code>
     * <p>The context loads method.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     */
    @Order(0)
    @Test
    void contextLoads() {
        log.info("The example test will be initiated");
    }

}
