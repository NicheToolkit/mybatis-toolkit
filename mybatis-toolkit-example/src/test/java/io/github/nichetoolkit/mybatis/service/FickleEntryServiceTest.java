package io.github.nichetoolkit.mybatis.service;


import io.github.nichetoolkit.mybatis.MybatisExampleApplicationTests;
import io.github.nichetoolkit.mybatis.fickle.FickleArrayList;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.mybatis.mapper.FickleEntryMapper;
import io.github.nichetoolkit.mybatis.simple.FickleEntryFilter;
import io.github.nichetoolkit.mybatis.simple.FickleEntryModel;
import io.github.nichetoolkit.rest.RestException;
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
class FickleEntryServiceTest extends MybatisExampleApplicationTests {

    @Autowired
    private FickleEntryService fickleEntryService;

    @Autowired
    private FickleEntryMapper fickleEntryMapper;

    private final String testId = "1656549566276964350";
    private final String testId1 = "1656549566276964351";
    private final String testId2 = "1656549566276964352";

    @Order(1)
    @Test
    public void save() throws RestException {
        FickleEntryModel fickleEntryModel = new FickleEntryModel(testId);
        fickleEntryModel.setName("name_" + GeneralUtils.uuid());
        fickleEntryModel.setDescription("description_" + GeneralUtils.uuid());

        List<RestFickle<?>> fields = new FickleArrayList<>();
        fields.add(RestFickle.of("fickleEntry1", 11));
        fields.add(RestFickle.of("fickleEntry2", 12L));
        fields.add(RestFickle.of("fickleEntry3", 13F));
        fields.add(RestFickle.of("fickleEntry4", 14D));
        fields.add(RestFickle.of("fickleEntry5", GeneralUtils.uuid()));
        fields.add(RestFickle.of("fickleEntry6", "text"));
        fields.add(RestFickle.of("fickleEntry7", new Date()));
        fickleEntryModel.setFields(fields);

        FickleEntryModel save = fickleEntryService.save(fickleEntryModel);
        System.out.println(JsonUtils.parseJson(save));
    }

    @Order(2)
    @Test
    public void saveAll() throws RestException {
        FickleEntryModel fickleEntryModel1 = new FickleEntryModel(testId1);
        fickleEntryModel1.setName("name1_" + GeneralUtils.uuid());
        fickleEntryModel1.setDescription("description1_" + GeneralUtils.uuid());

        List<RestFickle<?>> fields1 = new FickleArrayList<>();
        fields1.add(RestFickle.of("fickleEntry1", 111));
        fields1.add(RestFickle.of("fickleEntry2", 122L));
        fields1.add(RestFickle.of("fickleEntry3", 133F));
        fields1.add(RestFickle.of("fickleEntry4", 144D));
        fields1.add(RestFickle.of("fickleEntry5", GeneralUtils.uuid()));
        fields1.add(RestFickle.of("fickleEntry6", "text1"));
        fields1.add(RestFickle.of("fickleEntry7", new Date()));
        fickleEntryModel1.setFields(fields1);

        FickleEntryModel fickleEntryModel2 = new FickleEntryModel(testId2);
        fickleEntryModel2.setName("name2_" + GeneralUtils.uuid());
        fickleEntryModel2.setDescription("description2_" + GeneralUtils.uuid());

        List<RestFickle<?>> fields2 = new FickleArrayList<>();
        fields2.add(RestFickle.of("fickleEntry1", 1111));
        fields2.add(RestFickle.of("fickleEntry3", 1333F));
        fields2.add(RestFickle.of("fickleEntry5", GeneralUtils.uuid()));
        fields2.add(RestFickle.of("fickleEntry7", new Date()));

        fickleEntryModel2.setFields(fields2);

        List<FickleEntryModel> fickleEntryModels = fickleEntryService.saveAll(Arrays.asList(fickleEntryModel2,fickleEntryModel1));
        System.out.println(JsonUtils.parseJson(fickleEntryModels));
    }

    @Order(3)
    @Test
    public void queryById() throws RestException {
        FickleEntryModel fickleEntryModel = fickleEntryService.queryById(testId,Arrays.asList(RestFickle.of("fickleEntry1"),RestFickle.of("fickleEntry2")));
        System.out.println(JsonUtils.parseJson(fickleEntryModel));
    }

    @Order(4)
    @Test
    public void queryAll() throws RestException {
        List<FickleEntryModel> fickleEntryModels = fickleEntryService.queryAll(Arrays.asList(testId1, testId2));
        System.out.println(JsonUtils.parseJson(fickleEntryModels));
    }

    @Order(5)
    @Test
    public void queryAllWithFilter() throws RestException {
        FickleEntryFilter fickleEntryFilter = new FickleEntryFilter();
        fickleEntryFilter.setPageSize(10);
        RestPage<FickleEntryModel> restPage = fickleEntryService.queryAllWithFilter(fickleEntryFilter);
        System.out.println(JsonUtils.parseJson(restPage));
    }

    @Order(6)
    @Test
    public void deleteById() throws RestException {
        fickleEntryService.deleteById(testId);
    }

    @Order(7)
    @Test
    public void deleteAll() throws RestException {
        fickleEntryService.deleteAll(Arrays.asList(testId1, testId2));
    }

    @Order(8)
    @Test
    public void deleteAllWithFilter() throws RestException {
        FickleEntryFilter fickleEntryFilter = new FickleEntryFilter();
        fickleEntryFilter.setIds(testId, testId1, testId2);
        fickleEntryService.deleteAllWithFilter(fickleEntryFilter);
    }

}