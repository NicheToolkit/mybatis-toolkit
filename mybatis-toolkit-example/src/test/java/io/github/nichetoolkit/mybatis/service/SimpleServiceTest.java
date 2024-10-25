package io.github.nichetoolkit.mybatis.service;


import io.github.nichetoolkit.mybatis.MybatisExampleApplicationTests;
import io.github.nichetoolkit.mybatis.simple.SimpleFilter;
import io.github.nichetoolkit.mybatis.simple.SimpleModel;
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
class SimpleServiceTest extends MybatisExampleApplicationTests {

    @Autowired
    private SimpleService simpleService;

    private final String testId = "1656549566276964350";
    private final String testId1 = "1656549566276964351";
    private final String testId2 = "1656549566276964352";

    private final String testLinkId = "1656551617199345660";
    private final String testLinkId1 = "1656551617199345661";
    private final String testLinkId2 = "1656551617199345662";

    private final String tablekey = "_dynamic";

    @Order(1)
    @Test
    public void save() throws RestException {
        SimpleModel simpleModel = new SimpleModel(testId);
        simpleModel.setName("name_" + GeneralUtils.uuid());
        simpleModel.setDescription("description_" + GeneralUtils.uuid());
        simpleModel.setLinkId(testLinkId);
        SimpleModel save = simpleService.save(simpleModel);
        System.out.println(JsonUtils.parseJson(save));
    }

    @Order(2)
    @Test
    public void saveAll() throws RestException {
        SimpleModel simpleModel1 = new SimpleModel(testId1);
        simpleModel1.setName("name1_" + GeneralUtils.uuid());
        simpleModel1.setDescription("description1_" + GeneralUtils.uuid());
        simpleModel1.setLinkId(testLinkId1);

        SimpleModel simpleModel2 = new SimpleModel(testId2);
        simpleModel2.setName("name2_" + GeneralUtils.uuid());
        simpleModel2.setDescription("description2_" + GeneralUtils.uuid());
        simpleModel2.setLinkId(testLinkId2);

        List<SimpleModel> simpleModels = simpleService.saveAll(Arrays.asList(simpleModel1, simpleModel2));
        System.out.println(JsonUtils.parseJson(simpleModels));
    }

    @Order(3)
    @Test
    public void queryById() throws RestException {
        SimpleModel simpleModel = simpleService.queryById(tablekey, testId);
        System.out.println(JsonUtils.parseJson(simpleModel));
    }

    @Order(4)
    @Test
    public void queryAll() throws RestException {
        List<SimpleModel> simpleModels = simpleService.queryAll(tablekey, Arrays.asList(testId1, testId2));
        System.out.println(JsonUtils.parseJson(simpleModels));
    }

    @Order(5)
    @Test
    public void queryAllWithFilter() throws RestException {
        SimpleFilter simpleFilter = new SimpleFilter();
        simpleFilter.setTablekey(tablekey);
        simpleFilter.setPageSize(10);
        Date nowDate = new Date();
        simpleFilter.setStartTime(DateUtils.addMinutes(nowDate, -5));
        simpleFilter.setEndTime(DateUtils.addMinutes(nowDate, 5));
        RestPage<SimpleModel> restPage = simpleService.queryAllWithFilter(simpleFilter);
        System.out.println(JsonUtils.parseJson(restPage));
    }

    @Order(6)
    @Test
    public void removeById() throws RestException {
        simpleService.removeById(tablekey, testId);
    }

    @Order(7)
    @Test
    public void removeAll() throws RestException {
        simpleService.removeAll(tablekey, Arrays.asList(testId1, testId2));
    }

    @Order(8)
    @Test
    public void removeAllByWhere() throws RestException {
        SimpleFilter simpleFilter = new SimpleFilter();
        simpleFilter.setTablekey(tablekey);
        simpleFilter.setIds(testId, testId1, testId2);
        simpleService.removeAllWithFilter(simpleFilter);
    }

    @Order(9)
    @Test
    public void removeByLinkId() throws RestException {
        simpleService.removeByLinkId(tablekey, testLinkId);
    }

    @Order(10)
    @Test
    public void removeAllByLinkIds() throws RestException {
        simpleService.removeAllByLinkIds(tablekey, Arrays.asList(testLinkId1, testLinkId2));
    }

    @Order(11)
    @Test
    public void operateById() throws RestException {
        simpleService.operateById(tablekey, testId, OperateType.NONE);
    }

    @Order(12)
    @Test
    public void operateAll() throws RestException {
        simpleService.operateAll(tablekey, Arrays.asList(testId1, testId2), OperateType.NONE);
    }

    @Order(13)
    @Test
    public void operateAllByWhere() throws RestException {
        SimpleFilter simpleFilter = new SimpleFilter();
        simpleFilter.setTablekey(tablekey);
        simpleFilter.setOperate(OperateType.NONE);
        simpleFilter.setIds(testId, testId1, testId1);
        simpleService.operateAllWithFilter(simpleFilter);
    }

    @Order(14)
    @Test
    public void operateByLinkId() throws RestException {
        simpleService.operateByLinkId(tablekey, testLinkId, OperateType.NONE);
    }

    @Order(15)
    @Test
    public void operateAllByLinkIds() throws RestException {
        simpleService.operateAllByLinkIds(tablekey, Arrays.asList(testLinkId1, testLinkId2), OperateType.NONE);
    }

    @Order(16)
    @Test
    public void deleteById() throws RestException {
        simpleService.deleteById(tablekey, testId);
    }

    @Order(17)
    @Test
    public void deleteAll() throws RestException {
        simpleService.deleteAll(tablekey, Arrays.asList(testId1, testId2));
    }

    @Order(18)
    @Test
    public void deleteAllByWhere() throws RestException {
        SimpleFilter simpleFilter = new SimpleFilter();
        simpleFilter.setTablekey(tablekey);
        simpleFilter.setIds(testId, testId1, testId2);
        simpleService.deleteAllWithFilter(simpleFilter);
    }

    @Order(19)
    @Test
    public void deleteByLinkId() throws RestException {
        simpleService.deleteByLinkId(tablekey, testLinkId);
    }

    @Order(20)
    @Test
    public void deleteAllByLinkIds() throws RestException {
        simpleService.deleteAllByLinkIds(tablekey, Arrays.asList(testLinkId1, testLinkId2));
    }

}