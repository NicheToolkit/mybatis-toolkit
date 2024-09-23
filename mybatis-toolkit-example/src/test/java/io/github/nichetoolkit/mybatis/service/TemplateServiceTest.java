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
    /**
     * <code>simpleService</code>
     * {@link io.github.nichetoolkit.mybatis.service.SimpleService} <p>the <code>simpleService</code> field.</p>
     * @see org.springframework.beans.factory.annotation.Autowired
     */
    @Autowired
    private TemplateService templateService;

    /**
     * <code>queryById</code>
     * <p>the by id method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Test
    public void queryById() throws RestException {
        TemplateIdentity identity1 = new TemplateIdentity("1",null);
        TemplateIdentity identity2 = new TemplateIdentity(null,"3");
        List<TemplateModel> templateModels = templateService.queryAll(Arrays.asList(identity1, identity2));
        System.out.println(JsonUtils.parseJson(templateModels));
    }
}