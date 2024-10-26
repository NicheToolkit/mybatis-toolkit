package io.github.nichetoolkit.mybatis.service;


import io.github.nichetoolkit.mybatis.MybatisExampleApplicationTests;
import io.github.nichetoolkit.mybatis.enums.SimpleStatus;
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

/**
 * <code>SimpleServiceTest</code>
 * <p>The simple service test class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisExampleApplicationTests
 * @see org.junit.jupiter.api.TestMethodOrder
 * @since Jdk1.8
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SimpleServiceTest extends MybatisExampleApplicationTests {

    /**
     * <code>simpleService</code>
     * {@link io.github.nichetoolkit.mybatis.service.SimpleService} <p>The <code>simpleService</code> field.</p>
     * @see org.springframework.beans.factory.annotation.Autowired
     */
    @Autowired
    private SimpleService simpleService;

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
     * <code>testLinkId</code>
     * {@link java.lang.String} <p>The <code>testLinkId</code> field.</p>
     * @see java.lang.String
     */
    private final String testLinkId = "1656551617199345660";
    /**
     * <code>testLinkId1</code>
     * {@link java.lang.String} <p>The <code>testLinkId1</code> field.</p>
     * @see java.lang.String
     */
    private final String testLinkId1 = "1656551617199345661";
    /**
     * <code>testLinkId2</code>
     * {@link java.lang.String} <p>The <code>testLinkId2</code> field.</p>
     * @see java.lang.String
     */
    private final String testLinkId2 = "1656551617199345662";

    /**
     * <code>tablekey</code>
     * {@link java.lang.String} <p>The <code>tablekey</code> field.</p>
     * @see java.lang.String
     */
    private final String tablekey = "_dynamic";

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
        SimpleModel simpleModel = new SimpleModel(testId);
        simpleModel.setName("name_" + GeneralUtils.uuid());
        simpleModel.setDescription("description_" + GeneralUtils.uuid());
        simpleModel.setLinkId(testLinkId);
        simpleModel.setStatus(SimpleStatus.NONE);
        SimpleModel save = simpleService.save(simpleModel);
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
        SimpleModel simpleModel1 = new SimpleModel(testId1);
        simpleModel1.setName("name1_" + GeneralUtils.uuid());
        simpleModel1.setDescription("description1_" + GeneralUtils.uuid());
        simpleModel1.setLinkId(testLinkId1);
        simpleModel1.setStatus(SimpleStatus.NONE);

        SimpleModel simpleModel2 = new SimpleModel(testId2);
        simpleModel2.setName("name2_" + GeneralUtils.uuid());
        simpleModel2.setDescription("description2_" + GeneralUtils.uuid());
        simpleModel2.setLinkId(testLinkId2);
        simpleModel2.setStatus(SimpleStatus.NONE);

        List<SimpleModel> simpleModels = simpleService.saveAll(Arrays.asList(simpleModel1, simpleModel2));
        System.out.println(JsonUtils.parseJson(simpleModels));
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
        SimpleModel simpleModel = simpleService.queryById(tablekey, testId);
        System.out.println(JsonUtils.parseJson(simpleModel));
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
        List<SimpleModel> simpleModels = simpleService.queryAll(tablekey, Arrays.asList(testId1, testId2));
        System.out.println(JsonUtils.parseJson(simpleModels));
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
        SimpleFilter simpleFilter = new SimpleFilter();
        simpleFilter.setTablekey(tablekey);
        simpleFilter.setPageSize(10);
        Date nowDate = new Date();
        simpleFilter.setStartTime(DateUtils.addMinutes(nowDate, -5));
        simpleFilter.setEndTime(DateUtils.addMinutes(nowDate, 5));
        RestPage<SimpleModel> restPage = simpleService.queryAllWithFilter(simpleFilter);
        System.out.println(JsonUtils.parseJson(restPage));
    }

    /**
     * <code>removeById</code>
     * <p>The remove by id method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(6)
    @Test
    public void removeById() throws RestException {
        simpleService.removeById(tablekey, testId);
    }

    /**
     * <code>removeAll</code>
     * <p>The remove all method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(7)
    @Test
    public void removeAll() throws RestException {
        simpleService.removeAll(tablekey, Arrays.asList(testId1, testId2));
    }

    /**
     * <code>removeAllWithFilter</code>
     * <p>The remove all with filter method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(8)
    @Test
    public void removeAllWithFilter() throws RestException {
        SimpleFilter simpleFilter = new SimpleFilter();
        simpleFilter.setTablekey(tablekey);
        simpleFilter.setIds(testId, testId1, testId2);
        simpleService.removeAllWithFilter(simpleFilter);
    }

    /**
     * <code>removeByLinkId</code>
     * <p>The remove by link id method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(9)
    @Test
    public void removeByLinkId() throws RestException {
        simpleService.removeByLinkId(tablekey, testLinkId);
    }

    /**
     * <code>removeAllByLinkIds</code>
     * <p>The remove all by link ids method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(10)
    @Test
    public void removeAllByLinkIds() throws RestException {
        simpleService.removeAllByLinkIds(tablekey, Arrays.asList(testLinkId1, testLinkId2));
    }

    /**
     * <code>operateById</code>
     * <p>The operate by id method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(11)
    @Test
    public void operateById() throws RestException {
        simpleService.operateById(tablekey, testId, OperateType.NONE);
    }

    /**
     * <code>operateAll</code>
     * <p>The operate all method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(12)
    @Test
    public void operateAll() throws RestException {
        simpleService.operateAll(tablekey, Arrays.asList(testId1, testId2), OperateType.NONE);
    }

    /**
     * <code>operateAllWithFilter</code>
     * <p>The operate all with filter method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(13)
    @Test
    public void operateAllWithFilter() throws RestException {
        SimpleFilter simpleFilter = new SimpleFilter();
        simpleFilter.setTablekey(tablekey);
        simpleFilter.setOperate(OperateType.NONE);
        simpleFilter.setIds(testId, testId1, testId1);
        simpleService.operateAllWithFilter(simpleFilter);
    }

    /**
     * <code>operateByLinkId</code>
     * <p>The operate by link id method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(14)
    @Test
    public void operateByLinkId() throws RestException {
        simpleService.operateByLinkId(tablekey, testLinkId, OperateType.NONE);
    }

    /**
     * <code>operateAllByLinkIds</code>
     * <p>The operate all by link ids method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(15)
    @Test
    public void operateAllByLinkIds() throws RestException {
        simpleService.operateAllByLinkIds(tablekey, Arrays.asList(testLinkId1, testLinkId2), OperateType.NONE);
    }

    /**
     * <code>alertById</code>
     * <p>The alert by id method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(16)
    @Test
    public void alertById() throws RestException {
        simpleService.alertById(tablekey, testId, SimpleStatus.TEST);
    }

    /**
     * <code>alertAll</code>
     * <p>The alert all method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(17)
    @Test
    public void alertAll() throws RestException {
        simpleService.alertAll(tablekey, Arrays.asList(testId1, testId2), SimpleStatus.TEST);
    }

    /**
     * <code>alertAllWithFilter</code>
     * <p>The alert all with filter method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(18)
    @Test
    public void alertAllWithFilter() throws RestException {
        SimpleFilter simpleFilter = new SimpleFilter();
        simpleFilter.setTablekey(tablekey);
        simpleFilter.setStatus(SimpleStatus.TEST);
        simpleFilter.setIds(testId, testId1, testId1);
        simpleService.alertAllWithFilter(simpleFilter);
    }

    /**
     * <code>alertByLinkId</code>
     * <p>The alert by link id method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(19)
    @Test
    public void alertByLinkId() throws RestException {
        simpleService.alertByLinkId(tablekey, testLinkId, SimpleStatus.TEST);
    }

    /**
     * <code>alertAllByLinkIds</code>
     * <p>The alert all by link ids method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(20)
    @Test
    public void alertAllByLinkIds() throws RestException {
        simpleService.alertAllByLinkIds(tablekey, Arrays.asList(testLinkId1, testLinkId2), SimpleStatus.TEST);
    }

    /**
     * <code>deleteById</code>
     * <p>The delete by id method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(21)
    @Test
    public void deleteById() throws RestException {
        simpleService.deleteById(tablekey, testId);
    }

    /**
     * <code>deleteAll</code>
     * <p>The delete all method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(22)
    @Test
    public void deleteAll() throws RestException {
        simpleService.deleteAll(tablekey, Arrays.asList(testId1, testId2));
    }

    /**
     * <code>deleteAllWithFilter</code>
     * <p>The delete all with filter method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(23)
    @Test
    public void deleteAllWithFilter() throws RestException {
        SimpleFilter simpleFilter = new SimpleFilter();
        simpleFilter.setTablekey(tablekey);
        simpleFilter.setIds(testId, testId1, testId2);
        simpleService.deleteAllWithFilter(simpleFilter);
    }

    /**
     * <code>deleteByLinkId</code>
     * <p>The delete by link id method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(24)
    @Test
    public void deleteByLinkId() throws RestException {
        simpleService.deleteByLinkId(tablekey, testLinkId);
    }

    /**
     * <code>deleteAllByLinkIds</code>
     * <p>The delete all by link ids method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Order
     * @see org.junit.jupiter.api.Test
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Order(25)
    @Test
    public void deleteAllByLinkIds() throws RestException {
        simpleService.deleteAllByLinkIds(tablekey, Arrays.asList(testLinkId1, testLinkId2));
    }

}