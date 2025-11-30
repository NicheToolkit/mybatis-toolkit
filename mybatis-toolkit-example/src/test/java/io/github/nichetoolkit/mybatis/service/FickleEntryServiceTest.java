package io.github.nichetoolkit.mybatis.service;


import io.github.nichetoolkit.mybatis.MybatisExampleApplicationTests;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.mybatis.mapper.FickleEntryMapper;
import io.github.nichetoolkit.mybatis.test.fickle.FickleEntryFilter;
import io.github.nichetoolkit.mybatis.test.fickle.FickleEntryModel;
import io.github.nichetoolkit.rest.RestException;
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

/**
 * <code>FickleEntryServiceTest</code>
 * <p>The fickle entry service test class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisExampleApplicationTests
 * @see org.junit.jupiter.api.TestMethodOrder
 * @since Jdk17
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FickleEntryServiceTest extends MybatisExampleApplicationTests {

    /**
     * <code>fickleEntryService</code>
     * {@link io.github.nichetoolkit.mybatis.service.FickleEntryService} <p>The <code>fickleEntryService</code> field.</p>
     * @see org.springframework.beans.factory.annotation.Autowired
     */
    @Autowired
    private FickleEntryService fickleEntryService;

    /**
     * <code>fickleEntryMapper</code>
     * {@link io.github.nichetoolkit.mybatis.mapper.FickleEntryMapper} <p>The <code>fickleEntryMapper</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.mapper.FickleEntryMapper
     * @see org.springframework.beans.factory.annotation.Autowired
     */
    @Autowired
    private FickleEntryMapper fickleEntryMapper;

    /**
     * <code>testId</code>
     * {@link java.lang.String} <p>The <code>testId</code> field.</p>
     * @see java.lang.String
     */
    private final String testId = "1656549566276964350";
    /**
     * <code>testId1</code>
     * {@link java.lang.String} <p>The <code>testId1</code> field.</p>
     * @see java.lang.String
     */
    private final String testId1 = "1656549566276964351";
    /**
     * <code>testId2</code>
     * {@link java.lang.String} <p>The <code>testId2</code> field.</p>
     * @see java.lang.String
     */
    private final String testId2 = "1656549566276964352";

    /**
     * <code>save</code>
     * <p>The save method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(1)
    @Test
    public void save() throws RestException {
        FickleEntryModel fickleEntryModel = new FickleEntryModel(testId);
        fickleEntryModel.setName("name_" + GeneralUtils.uuid());
        fickleEntryModel.setDescription("description_" + GeneralUtils.uuid());

        List<RestFickle<?>> fields = new ArrayList<>();
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

    /**
     * <code>saveAll</code>
     * <p>The save all method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(2)
    @Test
    public void saveAll() throws RestException {
        FickleEntryModel fickleEntryModel1 = new FickleEntryModel(testId1);
        fickleEntryModel1.setName("name1_" + GeneralUtils.uuid());
        fickleEntryModel1.setDescription("description1_" + GeneralUtils.uuid());

        List<RestFickle<?>> fields1 = new ArrayList<>();
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

        List<RestFickle<?>> fields2 = new ArrayList<>();
        fields2.add(RestFickle.of("fickleEntry1", 1111));
        fields2.add(RestFickle.of("fickleEntry3", 1333F));
        fields2.add(RestFickle.of("fickleEntry5", GeneralUtils.uuid()));
        fields2.add(RestFickle.of("fickleEntry7", new Date()));

        fickleEntryModel2.setFields(fields2);

        List<FickleEntryModel> fickleEntryModels = fickleEntryService.saveAll(Arrays.asList(fickleEntryModel2, fickleEntryModel1));
        System.out.println(JsonUtils.parseJson(fickleEntryModels));
    }

    /**
     * <code>queryById</code>
     * <p>The query by id method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(3)
    @Test
    public void queryById() throws RestException {
        FickleEntryModel fickleEntryModel = fickleEntryService.queryById(testId, Arrays.asList(RestFickle.of("fickleEntry1"), RestFickle.of("fickleEntry2")));
        System.out.println(JsonUtils.parseJson(fickleEntryModel));
    }

    /**
     * <code>queryAll</code>
     * <p>The query all method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(4)
    @Test
    public void queryAll() throws RestException {
        List<FickleEntryModel> fickleEntryModels = fickleEntryService.queryAll(Arrays.asList(testId1, testId2),Arrays.asList(RestFickle.of("fickleEntry1"), RestFickle.of("fickleEntry2")));
        System.out.println(JsonUtils.parseJson(fickleEntryModels));
    }

    /**
     * <code>queryAllWithFilter</code>
     * <p>The query all with filter method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(5)
    @Test
    public void queryAllWithFilter() throws RestException {
        FickleEntryFilter fickleEntryFilter = new FickleEntryFilter();
        fickleEntryFilter.setPageSize(10);
        RestPage<FickleEntryModel> restPage = fickleEntryService.queryAllWithFilter(fickleEntryFilter);
        System.out.println(JsonUtils.parseJson(restPage));
    }

    /**
     * <code>deleteById</code>
     * <p>The delete by id method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(6)
    @Test
    public void deleteById() throws RestException {
        fickleEntryService.deleteById(testId);
    }

    /**
     * <code>deleteAll</code>
     * <p>The delete all method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(7)
    @Test
    public void deleteAll() throws RestException {
        fickleEntryService.deleteAll(Arrays.asList(testId1, testId2));
    }

    /**
     * <code>deleteAllWithFilter</code>
     * <p>The delete all with filter method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(8)
    @Test
    public void deleteAllWithFilter() throws RestException {
        FickleEntryFilter fickleEntryFilter = new FickleEntryFilter();
        fickleEntryFilter.setIds(testId, testId1, testId2);
        fickleEntryService.deleteAllWithFilter(fickleEntryFilter);
    }

}