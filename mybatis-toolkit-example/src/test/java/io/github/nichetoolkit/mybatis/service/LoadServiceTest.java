package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.MybatisExampleApplicationTests;
import io.github.nichetoolkit.mybatis.test.load.LoadService;
import io.github.nichetoolkit.mybatis.test.load.LoadIdentity;
import io.github.nichetoolkit.mybatis.test.load.LoadModel;
import io.github.nichetoolkit.mybatis.test.load.*;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.DateUtils;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.JsonUtils;
import io.github.nichetoolkit.rice.RestPage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoadServiceTest extends MybatisExampleApplicationTests {

    @Autowired
    private LoadService loadService;

    private final String testParamId1 = "1666549566271964311";
    private final String testParamId2 = "1666549566271964312";

    private final String testLoadPk1 = "1666549566276964310";
    private final String testLoadPk1_1 = "1666549566276964311";
    private final String testLoadPk1_2 = "1666549566276964312";

    private final String testLoadPk2 = "166549566276964320";
    private final String testLoadPk2_1 = "166549566276964321";
    private final String testLoadPk2_2 = "166549566276964322";

    private final String testLinkId1 = "1666551617199345610";
    private final String testLinkId1_1 = "1666551617199345611";
    private final String testLinkId1_2 = "1666551617199345612";

    private final String testLinkId2 = "1666551617199345620";
    private final String testLinkId2_1 = "1666551617199345621";
    private final String testLinkId2_2 = "1666551617199345622";
    
    @Order(1)
    @Test
    public void save() throws RestException {
        LoadModel loadModel = new LoadModel(new LoadIdentity(testLoadPk1, testLoadPk2));
        loadModel.setName("name_" + GeneralUtils.uuid());
        loadModel.setDescription("description_" + GeneralUtils.uuid());
        loadModel.setLinkId1(testLinkId1);
        loadModel.setLinkId2(testLinkId2);
        loadModel.setParamId(testParamId1);
        LoadModel save = loadService.save(loadModel);
        System.out.println(JsonUtils.parseJson(save));
    }

    @Order(2)
    @Test
    public void saveAll() throws RestException {
        LoadModel loadModel1 = new LoadModel(new LoadIdentity(testLoadPk1_1, testLoadPk2_1));
        loadModel1.setName("name1_" + GeneralUtils.uuid());
        loadModel1.setDescription("description1_" + GeneralUtils.uuid());
        loadModel1.setLinkId1(testLinkId1_1);
        loadModel1.setLinkId2(testLinkId2_1);
        loadModel1.setParamId(testParamId1);

        LoadModel loadModel2 = new LoadModel(new LoadIdentity(testLoadPk1_2, testLoadPk2_2));
        loadModel2.setName("name2_" + GeneralUtils.uuid());
        loadModel2.setDescription("description2_" + GeneralUtils.uuid());
        loadModel2.setLinkId1(testLinkId1_2);
        loadModel2.setLinkId2(testLinkId2_2);
        loadModel2.setParamId(testParamId2);

        List<LoadModel> loadModels = loadService.saveAll(Arrays.asList(loadModel1, loadModel2));
        System.out.println(JsonUtils.parseJson(loadModels));
    }

    @Order(3)
    @Test
    public void queryById() throws RestException {
        LoadIdentity identity = new LoadIdentity(testLoadPk1, testLoadPk2);
        LoadModel loadModel = loadService.queryById(identity);
        System.out.println(JsonUtils.parseJson(loadModel));
    }

    @Order(4)
    @Test
    public void queryAll() throws RestException {
        LoadIdentity identity = new LoadIdentity(testLoadPk1, testLoadPk2);
        LoadIdentity identity1 = new LoadIdentity(testLoadPk1_1, null);
        LoadIdentity identity2 = new LoadIdentity(null, testLoadPk2_2);
        List<LoadModel> loadModels = loadService.queryAll( Arrays.asList(identity, identity1, identity2));
        System.out.println(JsonUtils.parseJson(loadModels));
    }

    @Order(5)
    @Test
    public void queryAllWithFilter() throws RestException {
        LoadFilter loadFilter = new LoadFilter();
        loadFilter.setPageSize(10);
        Date nowDate = new Date();
        loadFilter.setStartTime(DateUtils.addMinutes(nowDate, -5));
        loadFilter.setEndTime(DateUtils.addMinutes(nowDate, 5));
        RestPage<LoadModel> restPage = loadService.queryAllWithFilter(loadFilter);
        System.out.println(JsonUtils.parseJson(restPage));
    }
}