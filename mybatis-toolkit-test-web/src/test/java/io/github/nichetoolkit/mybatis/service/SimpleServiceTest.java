package io.github.nichetoolkit.mybatis.service;


import io.github.nichetoolkit.mybatis.MybatisToolkitTestWebApplicationTests;
import io.github.nichetoolkit.mybatis.simple.SimpleModel;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @auther Cyan
 * @create 2023/5/10
 */
class SimpleServiceTest extends MybatisToolkitTestWebApplicationTests {

    @Autowired
    private SimpleService simpleService;

    @Test
    public void queryById() throws RestException {
        SimpleModel simpleModel = simpleService.queryById("12313123123");
        System.out.println(JsonUtils.parseJson(simpleModel));
    }

}