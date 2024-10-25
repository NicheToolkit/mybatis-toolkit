package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.MybatisExampleApplicationTests;
import io.github.nichetoolkit.mybatis.simple.*;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.DateUtils;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.JsonUtils;
import io.github.nichetoolkit.rice.RestPage;
import io.github.nichetoolkit.rice.enums.OperateType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TemplateServiceTest extends MybatisExampleApplicationTests {
    @Autowired
    private TemplateService templateService;

    private final String testTemplatePk1 = "1656549566276964310";
    private final String testTemplatePk1_1 = "1656549566276964311";
    private final String testTemplatePk1_2 = "1656549566276964312";

    private final String testTemplatePk2 = "1656549566276964320";
    private final String testTemplatePk2_1 = "1656549566276964321";
    private final String testTemplatePk2_2 = "1656549566276964322";

    private final String testLinkId1 = "1656551617199345610";
    private final String testLinkId1_1 = "1656551617199345611";
    private final String testLinkId1_2 = "1656551617199345612";

    private final String testLinkId2 = "1656551617199345620";
    private final String testLinkId2_1 = "1656551617199345621";
    private final String testLinkId2_2 = "1656551617199345622";

    private final String tablekey = "_dynamic";

    @Order(1)
    @Test
    public void save() throws RestException {
        TemplateModel templateModel = new TemplateModel(new TemplateIdentity(testTemplatePk1, testTemplatePk2));
        templateModel.setName("name_" + GeneralUtils.uuid());
        templateModel.setDescription("description_" + GeneralUtils.uuid());
        templateModel.setLinkId1(testLinkId1);
        templateModel.setLinkId2(testLinkId2);
        TemplateModel save = templateService.save(templateModel);
        System.out.println(JsonUtils.parseJson(save));
    }

    @Order(2)
    @Test
    public void saveAll() throws RestException {
        TemplateModel templateModel1 = new TemplateModel(new TemplateIdentity(testTemplatePk1_1, testTemplatePk2_1));
        templateModel1.setName("name1_" + GeneralUtils.uuid());
        templateModel1.setDescription("description1_" + GeneralUtils.uuid());
        templateModel1.setLinkId1(testLinkId1_1);
        templateModel1.setLinkId2(testLinkId2_1);

        TemplateModel templateModel2 = new TemplateModel(new TemplateIdentity(testTemplatePk1_2, testTemplatePk2_2));
        templateModel2.setName("name2_" + GeneralUtils.uuid());
        templateModel2.setDescription("description2_" + GeneralUtils.uuid());
        templateModel2.setLinkId1(testLinkId1_2);
        templateModel2.setLinkId2(testLinkId2_2);

        List<TemplateModel> templateModels = templateService.saveAll(Arrays.asList(templateModel1, templateModel2));
        System.out.println(JsonUtils.parseJson(templateModels));
    }

    @Order(3)
    @Test
    public void queryById() throws RestException {
        TemplateIdentity identity = new TemplateIdentity(testTemplatePk1, testTemplatePk2);
        TemplateModel templateModel = templateService.queryById(tablekey, identity);
        System.out.println(JsonUtils.parseJson(templateModel));
    }

    @Order(4)
    @Test
    public void queryAll() throws RestException {
        TemplateIdentity identity = new TemplateIdentity(testTemplatePk1, testTemplatePk2);
        TemplateIdentity identity1 = new TemplateIdentity(testTemplatePk1_1, null);
        TemplateIdentity identity2 = new TemplateIdentity(null, testTemplatePk2_2);
        List<TemplateModel> templateModels = templateService.queryAll(tablekey, Arrays.asList(identity, identity1, identity2));
        System.out.println(JsonUtils.parseJson(templateModels));
    }

    @Order(5)
    @Test
    public void queryAllWithFilter() throws RestException {
        TemplateFilter templateFilter = new TemplateFilter();
        templateFilter.setTablekey(tablekey);
        templateFilter.setPageSize(10);
        Date nowDate = new Date();
        templateFilter.setStartTime(DateUtils.addMinutes(nowDate, -5));
        templateFilter.setEndTime(DateUtils.addMinutes(nowDate, 5));
        RestPage<TemplateModel> restPage = templateService.queryAllWithFilter(templateFilter);
        System.out.println(JsonUtils.parseJson(restPage));
    }

    @Order(6)
    @Test
    public void removeById() throws RestException {
        TemplateIdentity identity = new TemplateIdentity(testTemplatePk1, testTemplatePk2);
        templateService.removeById(tablekey, identity);
    }

    @Order(7)
    @Test
    public void removeAll() throws RestException {
        TemplateIdentity identity1 = new TemplateIdentity(null, testTemplatePk2_1);
        TemplateIdentity identity2 = new TemplateIdentity(testTemplatePk1_2, null);
        templateService.removeAll(tablekey, Arrays.asList(identity1, identity2));
    }

    @Order(8)
    @Test
    public void removeAllByWhere() throws RestException {
        TemplateFilter templateFilter = new TemplateFilter();
        templateFilter.setTablekey(tablekey);
        TemplateIdentity identity = new TemplateIdentity(testTemplatePk1, testTemplatePk2);
        TemplateIdentity identity1 = new TemplateIdentity(null, testTemplatePk2_1);
        TemplateIdentity identity2 = new TemplateIdentity(testTemplatePk1_2, null);
        templateFilter.setIds(identity, identity1, identity2);
        templateService.removeAllWithFilter(templateFilter);
    }

    @Order(9)
    @Test
    public void removeByLinkId() throws RestException {
        TemplateLinkage linkage = new TemplateLinkage(testLinkId1, testLinkId2);
        templateService.removeByLinkId(tablekey, linkage);
    }

    @Order(10)
    @Test
    public void removeAllByLinkIds() throws RestException {
        TemplateLinkage linkage1 = new TemplateLinkage(testLinkId1_1, null);
        TemplateLinkage linkage2 = new TemplateLinkage(null, testLinkId2_2);
        templateService.removeAllByLinkIds(tablekey, Arrays.asList(linkage1, linkage2));
    }

    @Order(11)
    @Test
    public void operateById() throws RestException {
        TemplateIdentity identity = new TemplateIdentity(testTemplatePk1, testTemplatePk2);
        templateService.operateById(identity, OperateType.NONE);
    }

    @Order(12)
    @Test
    public void operateAll() throws RestException {
        TemplateIdentity identity1 = new TemplateIdentity(null, testTemplatePk2_1);
        TemplateIdentity identity2 = new TemplateIdentity(testTemplatePk1_2, null);
        templateService.operateAll(Arrays.asList(identity1, identity2), OperateType.NONE);
    }

    @Order(13)
    @Test
    public void operateAllByWhere() throws RestException {
        TemplateFilter templateFilter = new TemplateFilter();
        templateFilter.setTablekey(tablekey);
        TemplateIdentity identity = new TemplateIdentity(testTemplatePk1, testTemplatePk2);
        TemplateIdentity identity1 = new TemplateIdentity(testTemplatePk2_1, null);
        TemplateIdentity identity2 = new TemplateIdentity(null, testTemplatePk2_2);
        templateFilter.setIds(identity, identity1, identity2);
        templateService.operateAllWithFilter(templateFilter);
    }

    @Order(14)
    @Test
    public void operateByLinkId() throws RestException {
        TemplateLinkage linkage = new TemplateLinkage(testLinkId1, testLinkId2);
        templateService.operateByLinkId(tablekey, linkage, OperateType.NONE);
    }

    @Order(15)
    @Test
    public void operateAllByLinkIds() throws RestException {
        TemplateLinkage linkage1 = new TemplateLinkage(testLinkId1_1, null);
        TemplateLinkage linkage2 = new TemplateLinkage(null, testLinkId2_2);
        templateService.operateAllByLinkIds(tablekey, Arrays.asList(linkage1, linkage2), OperateType.NONE);
    }

    @Order(16)
    @Test
    public void deleteById() throws RestException {
        TemplateIdentity identity = new TemplateIdentity(testTemplatePk1, testTemplatePk2);
        templateService.deleteById(tablekey, identity);
    }

    @Order(17)
    @Test
    public void deleteAll() throws RestException {
        TemplateIdentity identity1 = new TemplateIdentity(null, testTemplatePk2_1);
        TemplateIdentity identity2 = new TemplateIdentity(testTemplatePk1_2, null);
        templateService.deleteAll(tablekey, Arrays.asList(identity1, identity2));
    }

    @Order(18)
    @Test
    public void deleteAllByWhere() throws RestException {
        TemplateFilter templateFilter = new TemplateFilter();
        templateFilter.setTablekey(tablekey);
        TemplateIdentity identity = new TemplateIdentity(testTemplatePk1, testTemplatePk2);
        TemplateIdentity identity1 = new TemplateIdentity(null, testTemplatePk2_1);
        TemplateIdentity identity2 = new TemplateIdentity(testTemplatePk1_2, null);
        templateFilter.setIds(identity, identity1, identity2);
        templateService.deleteAllWithFilter(templateFilter);
    }

    @Order(19)
    @Test
    public void deleteByLinkId() throws RestException {
        TemplateLinkage linkage = new TemplateLinkage(testLinkId1, testLinkId2);
        templateService.deleteByLinkId(tablekey, linkage);
    }

    @Order(20)
    @Test
    public void deleteAllByLinkIds() throws RestException {
        TemplateLinkage linkage1 = new TemplateLinkage(testLinkId1_1, null);
        TemplateLinkage linkage2 = new TemplateLinkage(null, testLinkId2_2);
        templateService.deleteAllByLinkIds(tablekey, Arrays.asList(linkage1, linkage2));
    }


}