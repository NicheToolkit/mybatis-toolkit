package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.MybatisExampleApplicationTests;
import io.github.nichetoolkit.mybatis.load.RestLoad;
import io.github.nichetoolkit.mybatis.test.load.*;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.DateUtils;
import io.github.nichetoolkit.rest.util.FileUtils;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.JsonUtils;
import io.github.nichetoolkit.rice.RestPage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileServiceTest extends MybatisExampleApplicationTests {

    private String test = "a";

    @Order(1)
    @Test
    public void fileCreateTest() throws RestException {
        Path tempFile = FileUtils.createTempFile("test", ".png");
        log.info("{}", tempFile.toString());
    }

    @Order(2)
    @Test
    public void Test() throws RestException {

        log.info("{}", test = "b");
        log.info("{}", test1());
    }

    private String test1() {
        return test = "c";
    }
}