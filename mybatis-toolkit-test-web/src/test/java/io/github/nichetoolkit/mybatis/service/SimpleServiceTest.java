package io.github.nichetoolkit.mybatis.service;


import io.github.nichetoolkit.mybatis.MybatisToolkitTestWebApplicationTests;
import io.github.nichetoolkit.mybatis.simple.SimpleFilter;
import io.github.nichetoolkit.mybatis.simple.SimpleModel;
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

/**
 * <code>SimpleServiceTest</code>
 * <p>The type simple service test class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisToolkitTestWebApplicationTests
 * @since Jdk1.8
 */
class SimpleServiceTest extends MybatisToolkitTestWebApplicationTests {

    /**
     * <code>simpleService</code>
     * {@link io.github.nichetoolkit.mybatis.service.SimpleService} <p>the <code>simpleService</code> field.</p>
     * @see org.springframework.beans.factory.annotation.Autowired
     */
    @Autowired
    private SimpleService simpleService;

    /**
     * <code>queryById</code>
     * <p>the by id method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Test
    public void queryById() throws RestException {
        SimpleModel simpleModel = simpleService.queryById("12313123123");
        System.out.println(JsonUtils.parseJson(simpleModel));
    }

    /**
     * <code>queryAll</code>
     * <p>the all method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Test
    public void queryAll() throws RestException {
        List<SimpleModel> simpleModels = simpleService.queryAll(Arrays.asList("12313123123","113a21"));
        System.out.println(JsonUtils.parseJson(simpleModels));
    }

    /**
     * <code>save</code>
     * <p>the method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Test
    public void save() throws RestException {
        SimpleModel simpleModel = new SimpleModel();
        simpleModel.setName("名称_" + GeneralUtils.uuid());
        simpleModel.setDescription("描述_" + GeneralUtils.uuid());
        simpleModel.setTime(new Date());
        SimpleModel save = simpleService.save(simpleModel);
        System.out.println(JsonUtils.parseJson(save));
    }

    /**
     * <code>saveAll</code>
     * <p>the all method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Test
    public void saveAll() throws RestException {
        SimpleModel simpleModel1 = new SimpleModel();
        simpleModel1.setName("名称1_" + GeneralUtils.uuid());
        simpleModel1.setDescription("描述1_" + GeneralUtils.uuid());

        SimpleModel simpleModel2 = new SimpleModel();
        simpleModel2.setName("名称2_" + GeneralUtils.uuid());
        simpleModel2.setDescription("描述2_" + GeneralUtils.uuid());

        List<SimpleModel> simpleModels = simpleService.saveAll(Arrays.asList(simpleModel1, simpleModel2));
        System.out.println(JsonUtils.parseJson(simpleModels));
    }


    /**
     * <code>deleteById</code>
     * <p>the by id method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Test
    public void deleteById() throws RestException {
        simpleService.deleteById("1656549566276964352");
    }

    /**
     * <code>deleteAll</code>
     * <p>the all method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Test
    public void deleteAll() throws RestException {
        simpleService.deleteAll(Arrays.asList("1656551617199345664","1656552404394708992"));
    }

    /**
     * <code>findAllByWhere</code>
     * <p>the all by where method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Test
    public void findAllByWhere() throws RestException {
        SimpleFilter simpleFilter = new SimpleFilter();
        simpleFilter.setPageSize(10);
        simpleFilter.setStartTime(DateUtils.parseTime("2023-05-10 00:00:00"));
        simpleFilter.setEndTime(DateUtils.parseTime("2023-05-13 00:00:00"));
        RestPage<SimpleModel> restPage = simpleService.queryAllWithFilter(simpleFilter);
        System.out.println(JsonUtils.parseJson(restPage));
    }

    /**
     * <code>deleteAllByWhere</code>
     * <p>the all by where method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Test
    public void deleteAllByWhere() throws RestException {
        SimpleFilter simpleFilter = new SimpleFilter();
        simpleFilter.setIds("1656552882444701696","1656553293033508864");
        simpleService.deleteAllWithFilter(simpleFilter);
    }

    /**
     * <code>removeById</code>
     * <p>the by id method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Test
    public void removeById() throws RestException {
        simpleService.deleteById("1658395890546905088");
    }

    /**
     * <code>removeAll</code>
     * <p>the all method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Test
    public void removeAll() throws RestException {
        simpleService.deleteAll(Arrays.asList("1656551617199345664","1656552404394708992"));
    }

    /**
     * <code>removeAllByWhere</code>
     * <p>the all by where method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>the rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Test
    public void removeAllByWhere() throws RestException {
        SimpleFilter simpleFilter = new SimpleFilter();
        simpleFilter.setIds("1656552882444701696","1656553293033508864");
        simpleService.deleteAllWithFilter(simpleFilter);
    }


}