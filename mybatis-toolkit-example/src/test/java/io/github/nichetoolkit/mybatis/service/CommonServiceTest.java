package io.github.nichetoolkit.mybatis.service;


import io.github.nichetoolkit.mybatis.MybatisExampleApplicationTests;
import io.github.nichetoolkit.mybatis.test.common.CommonFilter;
import io.github.nichetoolkit.mybatis.test.common.CommonModel;
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

/**
 * <code>CommonServiceTest</code>
 * <p>The common service test class.</p>
 * @see  io.github.nichetoolkit.mybatis.MybatisExampleApplicationTests
 * @see  org.junit.jupiter.api.TestMethodOrder
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommonServiceTest extends MybatisExampleApplicationTests {

    /**
     * <code>commonService</code>
     * {@link CommonService} <p>The <code>commonService</code> field.</p>
     * @see  org.springframework.beans.factory.annotation.Autowired
     */
    @Autowired
    private CommonService commonService;

    /**
     * <code>testId</code>
     * {@link java.lang.String} <p>The <code>testId</code> field.</p>
     * @see  java.lang.String
     */
    private final String testId = "1656549566276964350";
    /**
     * <code>testId1</code>
     * {@link java.lang.String} <p>The <code>testId1</code> field.</p>
     * @see  java.lang.String
     */
    private final String testId1 = "1656549566276964351";
    /**
     * <code>testId2</code>
     * {@link java.lang.String} <p>The <code>testId2</code> field.</p>
     * @see  java.lang.String
     */
    private final String testId2 = "1656549566276964352";

    /**
     * <code>save</code>
     * <p>The save method.</p>
     * @see  org.junit.jupiter.api.Order
     * @see  org.junit.jupiter.api.Test
     * @see  io.github.nichetoolkit.rest.RestException
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    @Order(1)
    @Test
    public void save() throws RestException {
        CommonModel commonModel = new CommonModel(testId);
        commonModel.setName("name_" + GeneralUtils.uuid());
        commonModel.setDescription("description_" + GeneralUtils.uuid());
        CommonModel save = commonService.save(commonModel);
        System.out.println(JsonUtils.parseJson(save));
    }

    /**
     * <code>saveAll</code>
     * <p>The save all method.</p>
     * @see  org.junit.jupiter.api.Order
     * @see  org.junit.jupiter.api.Test
     * @see  io.github.nichetoolkit.rest.RestException
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    @Order(2)
    @Test
    public void saveAll() throws RestException {
        CommonModel commonModel1 = new CommonModel(testId1);
        commonModel1.setName("name1_" + GeneralUtils.uuid());
        commonModel1.setDescription("description1_" + GeneralUtils.uuid());

        CommonModel commonModel2 = new CommonModel(testId2);
        commonModel2.setName("name2_" + GeneralUtils.uuid());
        commonModel2.setDescription("description2_" + GeneralUtils.uuid());

        List<CommonModel> commonModels = commonService.saveAll(Arrays.asList(commonModel1, commonModel2));
        System.out.println(JsonUtils.parseJson(commonModels));
    }

    /**
     * <code>queryById</code>
     * <p>The query by id method.</p>
     * @see  org.junit.jupiter.api.Order
     * @see  org.junit.jupiter.api.Test
     * @see  io.github.nichetoolkit.rest.RestException
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    @Order(3)
    @Test
    public void queryById() throws RestException {
        CommonModel commonModel = commonService.queryById(testId);
        System.out.println(JsonUtils.parseJson(commonModel));
    }

    /**
     * <code>queryAll</code>
     * <p>The query all method.</p>
     * @see  org.junit.jupiter.api.Order
     * @see  org.junit.jupiter.api.Test
     * @see  io.github.nichetoolkit.rest.RestException
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    @Order(4)
    @Test
    public void queryAll() throws RestException {
        List<CommonModel> commonModels = commonService.queryAll(Arrays.asList(testId1, testId2));
        System.out.println(JsonUtils.parseJson(commonModels));
    }

    /**
     * <code>queryAllWithFilter</code>
     * <p>The query all with filter method.</p>
     * @see  org.junit.jupiter.api.Order
     * @see  org.junit.jupiter.api.Test
     * @see  io.github.nichetoolkit.rest.RestException
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    @Order(5)
    @Test
    public void queryAllWithFilter() throws RestException {
        CommonFilter commonFilter = new CommonFilter();
        commonFilter.setPageSize(10);
        Date nowDate = new Date();
        commonFilter.setStartTime(DateUtils.addMinutes(nowDate, -5));
        commonFilter.setEndTime(DateUtils.addMinutes(nowDate, 5));
        RestPage<CommonModel> restPage = commonService.queryAllWithFilter(commonFilter);
        System.out.println(JsonUtils.parseJson(restPage));
    }

    /**
     * <code>deleteById</code>
     * <p>The delete by id method.</p>
     * @see  org.junit.jupiter.api.Order
     * @see  org.junit.jupiter.api.Test
     * @see  io.github.nichetoolkit.rest.RestException
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    @Order(6)
    @Test
    public void deleteById() throws RestException {
        commonService.deleteById(testId);
    }

    /**
     * <code>deleteAll</code>
     * <p>The delete all method.</p>
     * @see  org.junit.jupiter.api.Order
     * @see  org.junit.jupiter.api.Test
     * @see  io.github.nichetoolkit.rest.RestException
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    @Order(7)
    @Test
    public void deleteAll() throws RestException {
        commonService.deleteAll(Arrays.asList(testId1, testId2));
    }

    /**
     * <code>deleteAllWithFilter</code>
     * <p>The delete all with filter method.</p>
     * @see  org.junit.jupiter.api.Order
     * @see  org.junit.jupiter.api.Test
     * @see  io.github.nichetoolkit.rest.RestException
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    @Order(8)
    @Test
    public void deleteAllWithFilter() throws RestException {
        CommonFilter commonFilter = new CommonFilter();
        commonFilter.setIds(testId, testId1, testId2);
        commonService.deleteAllWithFilter(commonFilter);
    }

}