package io.github.nichetoolkit.mybatis.service;


import io.github.nichetoolkit.mybatis.MybatisExampleApplicationTests;
import io.github.nichetoolkit.mybatis.fickle.FickleArrayList;
import io.github.nichetoolkit.mybatis.simple.FickleEntryFilter;
import io.github.nichetoolkit.mybatis.simple.FickleEntryModel;
import io.github.nichetoolkit.mybatis.simple.FickleSimpleField;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FickleEntryServiceTest extends MybatisExampleApplicationTests {

    @Autowired
    private FickleEntryService fickleEntryService;

    private final String testId = "1656549566276964350";
    private final String testId1 = "1656549566276964351";
    private final String testId2 = "1656549566276964352";

    @Order(1)
    @Test
    public void save() throws RestException {
        FickleEntryModel fickleEntryModel = new FickleEntryModel(testId);
        fickleEntryModel.setName("name_" + GeneralUtils.uuid());
        fickleEntryModel.setDescription("description_" + GeneralUtils.uuid());

        List<FickleSimpleField> fields = new FickleArrayList<>();
        fields.add(new FickleSimpleField("fickle_entry1", 11));
        fields.add(new FickleSimpleField("fickle_entry2", 12L));
        fields.add(new FickleSimpleField("fickle_entry3", 13F));
        fields.add(new FickleSimpleField("fickle_entry4", 14D));
        fields.add(new FickleSimpleField("fickle_entry5", GeneralUtils.uuid()));
        fields.add(new FickleSimpleField("fickle_entry6", "text"));
        fields.add(new FickleSimpleField("fickle_entry7", new Date()));
        fickleEntryModel.setFields(new ArrayList<>(fields));

        FickleEntryModel save = fickleEntryService.save(fickleEntryModel);
        System.out.println(JsonUtils.parseJson(save));
    }

    @Order(2)
    @Test
    public void saveAll() throws RestException {
        FickleEntryModel fickleEntryModel1 = new FickleEntryModel(testId1);
        fickleEntryModel1.setName("name1_" + GeneralUtils.uuid());
        fickleEntryModel1.setDescription("description1_" + GeneralUtils.uuid());

        List<FickleSimpleField> fields1 = new FickleArrayList<>();
        fields1.add(new FickleSimpleField("fickle_entry1", 111));
        fields1.add(new FickleSimpleField("fickle_entry2", 122L));
        fields1.add(new FickleSimpleField("fickle_entry3", 133F));
        fields1.add(new FickleSimpleField("fickle_entry4", 144D));
        fields1.add(new FickleSimpleField("fickle_entry5", GeneralUtils.uuid()));
        fields1.add(new FickleSimpleField("fickle_entry6", "text1"));
        fields1.add(new FickleSimpleField("fickle_entry7", new Date()));
        fickleEntryModel1.setFields(new ArrayList<>(fields1));

        FickleEntryModel fickleEntryModel2 = new FickleEntryModel(testId2);
        fickleEntryModel2.setName("name2_" + GeneralUtils.uuid());
        fickleEntryModel2.setDescription("description2_" + GeneralUtils.uuid());

        List<FickleSimpleField> fields2 = new FickleArrayList<>();
        fields2.add(new FickleSimpleField("fickle_entry1", 1111));
        fields2.add(new FickleSimpleField("fickle_entry3", 1333F));
        fields2.add(new FickleSimpleField("fickle_entry5", GeneralUtils.uuid()));
        fields2.add(new FickleSimpleField("fickle_entry7", new Date()));

        fickleEntryModel2.setFields(new ArrayList<>(fields2));

        List<FickleEntryModel> fickleEntryModels = fickleEntryService.saveAll(Arrays.asList(fickleEntryModel1, fickleEntryModel2));
        System.out.println(JsonUtils.parseJson(fickleEntryModels));
    }

    @Order(3)
    @Test
    public void queryById() throws RestException {
        FickleEntryModel fickleEntryModel = fickleEntryService.queryById(testId);
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
        Date nowDate = new Date();
        fickleEntryFilter.setStartTime(DateUtils.addMinutes(nowDate, -5));
        fickleEntryFilter.setEndTime(DateUtils.addMinutes(nowDate, 5));
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