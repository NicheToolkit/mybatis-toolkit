package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.MybatisToolkitTestWebApplicationTests;
import io.github.nichetoolkit.mybatis.simple.SimpleModel;
import io.github.nichetoolkit.mybatis.simple.TemplateIdentity;
import io.github.nichetoolkit.mybatis.simple.TemplateModel;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestKey;
import io.github.nichetoolkit.rest.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

class TemplateServiceTest extends MybatisToolkitTestWebApplicationTests {
    @Autowired
    private TemplateService templateService;


    @Test
    public void queryById() throws RestException {
        TemplateIdentity identity = new TemplateIdentity("1", null);
        TemplateModel templateModel = templateService.queryById(identity);
        System.out.println(JsonUtils.parseJson(templateModel));
    }

    @Test
    public void queryAll() throws RestException {
        TemplateIdentity identity1 = new TemplateIdentity("1", null);
        TemplateIdentity identity2 = new TemplateIdentity(null, "3");
        List<TemplateModel> templateModels = templateService.queryAll(Arrays.asList(identity1, identity2));
        System.out.println(JsonUtils.parseJson(templateModels));
    }


}