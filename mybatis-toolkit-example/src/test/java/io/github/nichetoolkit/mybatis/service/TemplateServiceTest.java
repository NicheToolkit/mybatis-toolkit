package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.MybatisToolkitTestWebApplicationTests;
import io.github.nichetoolkit.mybatis.builder.SqlUtils;
import io.github.nichetoolkit.mybatis.enums.StyleType;
import io.github.nichetoolkit.mybatis.simple.TemplateFilter;
import io.github.nichetoolkit.mybatis.simple.TemplateIdentity;
import io.github.nichetoolkit.mybatis.simple.TemplateModel;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.DateUtils;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.JsonUtils;
import io.github.nichetoolkit.rice.RestPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
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
        TemplateIdentity identity1 = new TemplateIdentity("1656551617199345664", null);
        TemplateIdentity identity2 = new TemplateIdentity(null, "1656552404394708992");
        TemplateIdentity identity3 = new TemplateIdentity("1656551617199345665", "1656552404394708993");
        templateFilter.setId(identity1);
        templateFilter.setIds(identity2,identity3);
        RestPage<TemplateModel> restPage = templateService.queryAllWithFilter(templateFilter);
        System.out.println(JsonUtils.parseJson(restPage));
    }

    @Test
    public void save() throws RestException {
        TemplateModel templateModel = new TemplateModel();
        templateModel.setName("name_" + GeneralUtils.uuid());
        templateModel.setDescription("description_" + GeneralUtils.uuid());
        templateModel.setTime(new Date());
        TemplateModel save = templateService.save(templateModel);
        System.out.println(JsonUtils.parseJson(save));
    }

    @Test
    public void saveAll() throws RestException {
        TemplateModel templateModel1 = new TemplateModel();
        templateModel1.setName("name1_" + GeneralUtils.uuid());
        templateModel1.setDescription("description1_" + GeneralUtils.uuid());

        TemplateModel templateModel2 = new TemplateModel();
        templateModel2.setName("name2_" + GeneralUtils.uuid());
        templateModel2.setDescription("description2_" + GeneralUtils.uuid());

        List<TemplateModel> templateModels = templateService.saveAll(Arrays.asList(templateModel1, templateModel2));
        System.out.println(JsonUtils.parseJson(templateModels));
    }


    @Test
    public void whereSqlOfIds() throws RestException {
        TemplateIdentity identity1 = new TemplateIdentity("1656551617199345664", null);
        TemplateIdentity identity2 = new TemplateIdentity(null, "1656552404394708992");
        TemplateIdentity identity3 = new TemplateIdentity("1656551617199345665", "1656552404394708993");
        String whereSqlOfIds = SqlUtils.whereSqlOfIds(Arrays.asList(identity1, identity2, identity3), TemplateIdentity.class, StyleType.LOWER_UNDERLINE);
        System.out.println(whereSqlOfIds);
    }


}