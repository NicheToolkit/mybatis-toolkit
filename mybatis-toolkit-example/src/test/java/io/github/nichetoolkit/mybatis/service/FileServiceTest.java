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

/**
 * <code>FileServiceTest</code>
 * <p>The file service test class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisExampleApplicationTests
 * @see lombok.extern.slf4j.Slf4j
 * @see org.junit.jupiter.api.TestMethodOrder
 * @since Jdk17
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileServiceTest extends MybatisExampleApplicationTests {

    /**
     * <code>test</code>
     * {@link java.lang.String} <p>The <code>test</code> field.</p>
     * @see java.lang.String
     */
    private String test = "a";

    /**
     * <code>fileCreateTest</code>
     * <p>The file create test method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(1)
    @Test
    public void fileCreateTest() throws RestException {
        Path tempFile = FileUtils.createTempFile("test", ".png");
        log.info("{}", tempFile.toString());
    }

    /**
     * <code>Test</code>
     * <p>The test method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(2)
    @Test
    public void Test() throws RestException {

        log.info("{}", test = "b");
        log.info("{}", test1());
    }

    /**
     * <code>test1</code>
     * <p>The test 1 method.</p>
     * @return {@link java.lang.String} <p>The test 1 return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    private String test1() {
        return test = "c";
    }
}