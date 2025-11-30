package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.MybatisExampleApplicationTests;
import io.github.nichetoolkit.mybatis.load.RestLoad;
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

/**
 * <code>LoadServiceTest</code>
 * <p>The load service test class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisExampleApplicationTests
 * @see org.junit.jupiter.api.TestMethodOrder
 * @since Jdk17
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoadServiceTest extends MybatisExampleApplicationTests {

    /**
     * <code>loadService</code>
     * {@link io.github.nichetoolkit.mybatis.service.LoadService} <p>The <code>loadService</code> field.</p>
     * @see org.springframework.beans.factory.annotation.Autowired
     */
    @Autowired
    private LoadService loadService;

    /**
     * <code>loadLink1Service</code>
     * {@link io.github.nichetoolkit.mybatis.service.LoadLink1Service} <p>The <code>loadLink1Service</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.service.LoadLink1Service
     * @see org.springframework.beans.factory.annotation.Autowired
     */
    @Autowired
    private LoadLink1Service loadLink1Service;

    /**
     * <code>loadLink2Service</code>
     * {@link io.github.nichetoolkit.mybatis.service.LoadLink2Service} <p>The <code>loadLink2Service</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.service.LoadLink2Service
     * @see org.springframework.beans.factory.annotation.Autowired
     */
    @Autowired
    private LoadLink2Service loadLink2Service;

    /**
     * <code>testParamId1</code>
     * {@link java.lang.String} <p>The <code>testParamId1</code> field.</p>
     * @see java.lang.String
     */
    private final String testParamId1 = "1666549566271964311";
    /**
     * <code>testParamId2</code>
     * {@link java.lang.String} <p>The <code>testParamId2</code> field.</p>
     * @see java.lang.String
     */
    private final String testParamId2 = "1666549566271964312";

    /**
     * <code>testLoadPk1</code>
     * {@link java.lang.String} <p>The <code>testLoadPk1</code> field.</p>
     * @see java.lang.String
     */
    private final String testLoadPk1 = "1666549566276964310";
    /**
     * <code>testLoadPk1_1</code>
     * {@link java.lang.String} <p>The <code>testLoadPk1_1</code> field.</p>
     * @see java.lang.String
     */
    private final String testLoadPk1_1 = "1666549566276964311";
    /**
     * <code>testLoadPk1_2</code>
     * {@link java.lang.String} <p>The <code>testLoadPk1_2</code> field.</p>
     * @see java.lang.String
     */
    private final String testLoadPk1_2 = "1666549566276964312";

    /**
     * <code>testLoadPk2</code>
     * {@link java.lang.String} <p>The <code>testLoadPk2</code> field.</p>
     * @see java.lang.String
     */
    private final String testLoadPk2 = "166549566276964320";
    /**
     * <code>testLoadPk2_1</code>
     * {@link java.lang.String} <p>The <code>testLoadPk2_1</code> field.</p>
     * @see java.lang.String
     */
    private final String testLoadPk2_1 = "166549566276964321";
    /**
     * <code>testLoadPk2_2</code>
     * {@link java.lang.String} <p>The <code>testLoadPk2_2</code> field.</p>
     * @see java.lang.String
     */
    private final String testLoadPk2_2 = "166549566276964322";

    /**
     * <code>testLinkId1</code>
     * {@link java.lang.String} <p>The <code>testLinkId1</code> field.</p>
     * @see java.lang.String
     */
    private final String testLinkId1 = "1666551617199345610";
    /**
     * <code>testLinkId1_1</code>
     * {@link java.lang.String} <p>The <code>testLinkId1_1</code> field.</p>
     * @see java.lang.String
     */
    private final String testLinkId1_1 = "1666551617199345611";
    /**
     * <code>testLinkId1_2</code>
     * {@link java.lang.String} <p>The <code>testLinkId1_2</code> field.</p>
     * @see java.lang.String
     */
    private final String testLinkId1_2 = "1666551617199345612";

    /**
     * <code>testLinkId2</code>
     * {@link java.lang.String} <p>The <code>testLinkId2</code> field.</p>
     * @see java.lang.String
     */
    private final String testLinkId2 = "1666551617199345620";
    /**
     * <code>testLinkId2_1</code>
     * {@link java.lang.String} <p>The <code>testLinkId2_1</code> field.</p>
     * @see java.lang.String
     */
    private final String testLinkId2_1 = "1666551617199345621";
    /**
     * <code>testLinkId2_2</code>
     * {@link java.lang.String} <p>The <code>testLinkId2_2</code> field.</p>
     * @see java.lang.String
     */
    private final String testLinkId2_2 = "1666551617199345622";

    /**
     * <code>loadTestData</code>
     * <p>The load test data method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(0)
    @Test
    public void loadTestData() throws RestException {
        LoadLink1Model loadLinkModel1_1 = new LoadLink1Model(testLinkId1);
        loadLinkModel1_1.setName("name_" + GeneralUtils.uuid());
        loadLinkModel1_1.setDescription("description_" + GeneralUtils.uuid());
        loadLinkModel1_1.setParamId(testParamId1);

        LoadLink1Model loadLinkModel1_2 = new LoadLink1Model(testLinkId1_1);
        loadLinkModel1_2.setName("name_" + GeneralUtils.uuid());
        loadLinkModel1_2.setDescription("description_" + GeneralUtils.uuid());
        loadLinkModel1_2.setParamId(testParamId1);

        LoadLink1Model loadLinkModel1_3 = new LoadLink1Model(testLinkId1_2);
        loadLinkModel1_3.setName("name_" + GeneralUtils.uuid());
        loadLinkModel1_3.setDescription("description_" + GeneralUtils.uuid());
        loadLinkModel1_3.setParamId(testParamId1);

        List<LoadLink1Model> saveAll1 = loadLink1Service.saveAll(Arrays.asList(loadLinkModel1_1, loadLinkModel1_2, loadLinkModel1_3));
        System.out.println(JsonUtils.parseJson(saveAll1));

        LoadLink2Model loadLinkModel2_1 = new LoadLink2Model(testLinkId2);
        loadLinkModel2_1.setName("name_" + GeneralUtils.uuid());
        loadLinkModel2_1.setDescription("description_" + GeneralUtils.uuid());
        loadLinkModel2_1.setParamId(testParamId1);

        LoadLink2Model loadLinkModel2_2 = new LoadLink2Model(testLinkId2_1);
        loadLinkModel2_2.setName("name_" + GeneralUtils.uuid());
        loadLinkModel2_2.setDescription("description_" + GeneralUtils.uuid());
        loadLinkModel2_2.setParamId(testParamId1);

        LoadLink2Model loadLinkModel2_3 = new LoadLink2Model(testLinkId2_2);
        loadLinkModel2_3.setName("name_" + GeneralUtils.uuid());
        loadLinkModel2_3.setDescription("description_" + GeneralUtils.uuid());
        loadLinkModel2_3.setParamId(testParamId1);

        List<LoadLink2Model> saveAll2 = loadLink2Service.saveAll(Arrays.asList(loadLinkModel2_1, loadLinkModel2_2, loadLinkModel2_3));
        System.out.println(JsonUtils.parseJson(saveAll2));
    }


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
        LoadModel loadModel = new LoadModel(new LoadIdentity(testLoadPk1, testLoadPk2));
        loadModel.setName("name_" + GeneralUtils.uuid());
        loadModel.setDescription("description_" + GeneralUtils.uuid());
        loadModel.setLinkId1(testLinkId1);
        loadModel.setLinkId2(testLinkId2);
        loadModel.setParamId(testParamId1);
        LoadModel save = loadService.save(loadModel);
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
        LoadIdentity identity = new LoadIdentity(testLoadPk1, testLoadPk2);
        LoadModel loadModel = loadService.queryById(identity, RestLoad.ofArray("linkEntity1","linkEntity2s"));
        System.out.println(JsonUtils.parseJson(loadModel));
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
        LoadIdentity identity = new LoadIdentity(testLoadPk1, testLoadPk2);
        LoadIdentity identity1 = new LoadIdentity(testLoadPk1_1, null);
        LoadIdentity identity2 = new LoadIdentity(null, testLoadPk2_2);
        List<LoadModel> loadModels = loadService.queryAll( Arrays.asList(identity, identity1, identity2),RestLoad.of("linkEntity1"));
        System.out.println(JsonUtils.parseJson(loadModels));
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
        LoadFilter loadFilter = new LoadFilter();
        loadFilter.setPageSize(10);
        Date nowDate = new Date();
        loadFilter.setStartTime(DateUtils.addMinutes(nowDate, -5));
        loadFilter.setEndTime(DateUtils.addMinutes(nowDate, 5));
        RestPage<LoadModel> restPage = loadService.queryAllWithFilter(loadFilter);
        System.out.println(JsonUtils.parseJson(restPage));
    }
}