package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.MybatisToolkitTestWebApplicationTests;
import io.github.nichetoolkit.mybatis.builder.SqlUtils;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.mybatis.simple.TemplateFilter;
import io.github.nichetoolkit.mybatis.simple.TemplateIdentity;
import io.github.nichetoolkit.mybatis.simple.TemplateModel;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.stream.RestCollectors;
import io.github.nichetoolkit.rest.stream.RestStream;
import io.github.nichetoolkit.rest.util.DateUtils;
import io.github.nichetoolkit.rest.util.JsonUtils;
import io.github.nichetoolkit.rice.RestPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

class TemplateServiceTest extends MybatisToolkitTestWebApplicationTests {
    @Autowired
    private TemplateService templateService;


    @Test
    public void queryById() throws RestException {
        TemplateIdentity identity = new TemplateIdentity("1656551617199345664","1656552404394708992");
        TemplateModel templateModel = templateService.queryById(identity);
        System.out.println(JsonUtils.parseJson(templateModel));
    }

    @Test
    public void queryAll() throws RestException {
        TemplateIdentity identity1 = new TemplateIdentity("1656551617199345664", null);
        TemplateIdentity identity2 = new TemplateIdentity(null, "1656552404394708992");
        TemplateIdentity identity3 = new TemplateIdentity("1656551617199345665", "1656552404394708993");
        List<TemplateModel> templateModels = templateService.queryAll(Arrays.asList(identity1, identity2,identity3));
        System.out.println(JsonUtils.parseJson(templateModels));
    }

    @Test
    public void queryAllWithFilter() throws RestException {
        TemplateFilter templateFilter = new TemplateFilter();
        templateFilter.setPageSize(10);
        templateFilter.setStartTime(DateUtils.parseTime("2023-05-10 00:00:00"));
        templateFilter.setEndTime(DateUtils.parseTime("2023-05-13 00:00:00"));
        RestPage<TemplateModel> restPage = templateService.queryAllWithFilter(templateFilter);
        System.out.println(JsonUtils.parseJson(restPage));
    }

    @Test
    public void logicalOr() throws RestException {
        Boolean logicalOr = RestStream.stream(Arrays.asList(true, false)).collect(RestCollectors.logicalOr());
        System.out.println(logicalOr);

    }

    @Test
    public void templateIdentity() throws RestException {
        TemplateIdentity identity1 = new TemplateIdentity("1656551617199345664", null);
        TemplateIdentity identity2 = new TemplateIdentity(null, "1656552404394708992");
        TemplateIdentity identity3 = new TemplateIdentity("1656551617199345665", "1656552404394708993");
        String whereSqlOfIds = SqlUtils.whereSqlOfIds(Arrays.asList(identity1, identity2, identity3), TemplateIdentity.class, StyleType.LOWER_UNDERLINE);
        System.out.println(whereSqlOfIds);
    }


}