package io.github.nichetoolkit.mybatis.service;


import io.github.nichetoolkit.mybatis.MybatisToolkitTestWebApplicationTests;
import io.github.nichetoolkit.mybatis.simple.SimpleModel;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    @Test
    public void queryAll() throws RestException {
        List<SimpleModel> simpleModels = simpleService.queryAll(Arrays.asList("12313123123","113a21"));
        System.out.println(JsonUtils.parseJson(simpleModels));
    }

    @Test
    public void save() throws RestException {
        SimpleModel simpleModel = new SimpleModel();
        simpleModel.setName("名称_" + GeneralUtils.uuid());
        simpleModel.setDescription("描述_" + GeneralUtils.uuid());
        simpleModel.setTime(new Date());
        SimpleModel save = simpleService.save(simpleModel);
        System.out.println(JsonUtils.parseJson(save));
    }

    @Test
    public void saveAll() throws RestException {
        SimpleModel simpleModel1 = new SimpleModel();
        simpleModel1.setName("名称1_" + GeneralUtils.uuid());
        simpleModel1.setDescription("描述1_" + GeneralUtils.uuid());
        simpleModel1.setTime(new Date());

        SimpleModel simpleModel2 = new SimpleModel();
        simpleModel2.setName("名称2_" + GeneralUtils.uuid());
        simpleModel2.setDescription("描述2_" + GeneralUtils.uuid());
        simpleModel2.setTime(new Date());

        List<SimpleModel> simpleModels = simpleService.saveAll(Arrays.asList(simpleModel1, simpleModel2));
        System.out.println(JsonUtils.parseJson(simpleModels));
    }

}