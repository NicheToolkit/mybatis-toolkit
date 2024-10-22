package io.github.nichetoolkit.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class MybatisExampleApplicationTests {

    @Order(0)
    @Test
    void contextLoads() {
        log.info("The example test will be initiated");
    }

}
