package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.MybatisExampleApplicationTests;
import io.github.nichetoolkit.mybatis.enums.TemplateStatus1;
import io.github.nichetoolkit.mybatis.enums.TemplateStatus2;
import io.github.nichetoolkit.mybatis.simple.*;
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
 * <code>TemplateServiceTest</code>
 * <p>The template service test class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisExampleApplicationTests
 * @see org.junit.jupiter.api.TestMethodOrder
 * @since Jdk1.8
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TemplateServiceTest extends MybatisExampleApplicationTests {
    /**
     * <code>templateService</code>
     * {@link io.github.nichetoolkit.mybatis.service.TemplateService} <p>The <code>templateService</code> field.</p>
     * @see org.springframework.beans.factory.annotation.Autowired
     */
    @Autowired
    private TemplateService templateService;

    /**
     * <code>testTemplatePk1</code>
     * {@link java.lang.String} <p>The <code>testTemplatePk1</code> field.</p>
     * @see java.lang.String
     */
    private final String testTemplatePk1 = "1656549566276964310";
    /**
     * <code>testTemplatePk1_1</code>
     * {@link java.lang.String} <p>The <code>testTemplatePk1_1</code> field.</p>
     * @see java.lang.String
     */
    private final String testTemplatePk1_1 = "1656549566276964311";
    /**
     * <code>testTemplatePk1_2</code>
     * {@link java.lang.String} <p>The <code>testTemplatePk1_2</code> field.</p>
     * @see java.lang.String
     */
    private final String testTemplatePk1_2 = "1656549566276964312";

    /**
     * <code>testTemplatePk2</code>
     * {@link java.lang.String} <p>The <code>testTemplatePk2</code> field.</p>
     * @see java.lang.String
     */
    private final String testTemplatePk2 = "1656549566276964320";
    /**
     * <code>testTemplatePk2_1</code>
     * {@link java.lang.String} <p>The <code>testTemplatePk2_1</code> field.</p>
     * @see java.lang.String
     */
    private final String testTemplatePk2_1 = "1656549566276964321";
    /**
     * <code>testTemplatePk2_2</code>
     * {@link java.lang.String} <p>The <code>testTemplatePk2_2</code> field.</p>
     * @see java.lang.String
     */
    private final String testTemplatePk2_2 = "1656549566276964322";

    /**
     * <code>testLinkId1</code>
     * {@link java.lang.String} <p>The <code>testLinkId1</code> field.</p>
     * @see java.lang.String
     */
    private final String testLinkId1 = "1656551617199345610";
    /**
     * <code>testLinkId1_1</code>
     * {@link java.lang.String} <p>The <code>testLinkId1_1</code> field.</p>
     * @see java.lang.String
     */
    private final String testLinkId1_1 = "1656551617199345611";
    /**
     * <code>testLinkId1_2</code>
     * {@link java.lang.String} <p>The <code>testLinkId1_2</code> field.</p>
     * @see java.lang.String
     */
    private final String testLinkId1_2 = "1656551617199345612";

    /**
     * <code>testLinkId2</code>
     * {@link java.lang.String} <p>The <code>testLinkId2</code> field.</p>
     * @see java.lang.String
     */
    private final String testLinkId2 = "1656551617199345620";
    /**
     * <code>testLinkId2_1</code>
     * {@link java.lang.String} <p>The <code>testLinkId2_1</code> field.</p>
     * @see java.lang.String
     */
    private final String testLinkId2_1 = "1656551617199345621";
    /**
     * <code>testLinkId2_2</code>
     * {@link java.lang.String} <p>The <code>testLinkId2_2</code> field.</p>
     * @see java.lang.String
     */
    private final String testLinkId2_2 = "1656551617199345622";

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
        TemplateModel templateModel = new TemplateModel(new TemplateIdentity(testTemplatePk1, testTemplatePk2));
        templateModel.setName("name_" + GeneralUtils.uuid());
        templateModel.setDescription("description_" + GeneralUtils.uuid());
        templateModel.setLinkId1(testLinkId1);
        templateModel.setLinkId2(testLinkId2);
        templateModel.setStatus1(TemplateStatus1.NONE);
        templateModel.setStatus2(TemplateStatus2.NONE);
        TemplateModel save = templateService.save(templateModel);
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
        TemplateModel templateModel1 = new TemplateModel(new TemplateIdentity(testTemplatePk1_1, testTemplatePk2_1));
        templateModel1.setName("name1_" + GeneralUtils.uuid());
        templateModel1.setDescription("description1_" + GeneralUtils.uuid());
        templateModel1.setLinkId1(testLinkId1_1);
        templateModel1.setLinkId2(testLinkId2_1);
        templateModel1.setStatus1(TemplateStatus1.NONE);
        templateModel1.setStatus2(TemplateStatus2.NONE);

        TemplateModel templateModel2 = new TemplateModel(new TemplateIdentity(testTemplatePk1_2, testTemplatePk2_2));
        templateModel2.setName("name2_" + GeneralUtils.uuid());
        templateModel2.setDescription("description2_" + GeneralUtils.uuid());
        templateModel2.setLinkId1(testLinkId1_2);
        templateModel2.setLinkId2(testLinkId2_2);
        templateModel2.setStatus1(TemplateStatus1.NONE);
        templateModel2.setStatus2(TemplateStatus2.NONE);

        List<TemplateModel> templateModels = templateService.saveAll(Arrays.asList(templateModel1, templateModel2));
        System.out.println(JsonUtils.parseJson(templateModels));
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
        TemplateIdentity identity = new TemplateIdentity(testTemplatePk1, testTemplatePk2);
        TemplateModel templateModel = templateService.queryById(tablekey, identity);
        System.out.println(JsonUtils.parseJson(templateModel));
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
        TemplateIdentity identity = new TemplateIdentity(testTemplatePk1, testTemplatePk2);
        TemplateIdentity identity1 = new TemplateIdentity(testTemplatePk1_1, null);
        TemplateIdentity identity2 = new TemplateIdentity(null, testTemplatePk2_2);
        List<TemplateModel> templateModels = templateService.queryAll(tablekey, Arrays.asList(identity, identity1, identity2));
        System.out.println(JsonUtils.parseJson(templateModels));
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
        TemplateFilter templateFilter = new TemplateFilter();
        templateFilter.setTablekey(tablekey);
        templateFilter.setPageSize(10);
        Date nowDate = new Date();
        templateFilter.setStartTime(DateUtils.addMinutes(nowDate, -5));
        templateFilter.setEndTime(DateUtils.addMinutes(nowDate, 5));
        RestPage<TemplateModel> restPage = templateService.queryAllWithFilter(templateFilter);
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
        TemplateIdentity identity = new TemplateIdentity(testTemplatePk1, testTemplatePk2);
        templateService.removeById(tablekey, identity);
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
        TemplateIdentity identity1 = new TemplateIdentity(null, testTemplatePk2_1);
        TemplateIdentity identity2 = new TemplateIdentity(testTemplatePk1_2, null);
        templateService.removeAll(tablekey, Arrays.asList(identity1, identity2));
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
        TemplateFilter templateFilter = new TemplateFilter();
        templateFilter.setTablekey(tablekey);
        TemplateIdentity identity = new TemplateIdentity(testTemplatePk1, testTemplatePk2);
        TemplateIdentity identity1 = new TemplateIdentity(null, testTemplatePk2_1);
        TemplateIdentity identity2 = new TemplateIdentity(testTemplatePk1_2, null);
        templateFilter.setIds(identity, identity1, identity2);
        templateService.removeAllWithFilter(templateFilter);
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
        TemplateLinkage linkage = new TemplateLinkage(testLinkId1, testLinkId2);
        templateService.removeByLinkId(tablekey, linkage);
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
        TemplateLinkage linkage1 = new TemplateLinkage(testLinkId1_1, null);
        TemplateLinkage linkage2 = new TemplateLinkage(null, testLinkId2_2);
        templateService.removeAllByLinkIds(tablekey, Arrays.asList(linkage1, linkage2));
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
        TemplateIdentity identity = new TemplateIdentity(testTemplatePk1, testTemplatePk2);
        templateService.operateById(tablekey, identity, OperateType.NONE);
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
        TemplateIdentity identity1 = new TemplateIdentity(null, testTemplatePk2_1);
        TemplateIdentity identity2 = new TemplateIdentity(testTemplatePk1_2, null);
        templateService.operateAll(tablekey, Arrays.asList(identity1, identity2), OperateType.NONE);
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
        TemplateFilter templateFilter = new TemplateFilter();
        templateFilter.setTablekey(tablekey);
        TemplateIdentity identity = new TemplateIdentity(testTemplatePk1, testTemplatePk2);
        TemplateIdentity identity1 = new TemplateIdentity(testTemplatePk2_1, null);
        TemplateIdentity identity2 = new TemplateIdentity(null, testTemplatePk2_2);
        templateFilter.setIds(identity, identity1, identity2);
        templateService.operateAllWithFilter(templateFilter);
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
        TemplateLinkage linkage = new TemplateLinkage(testLinkId1, testLinkId2);
        templateService.operateByLinkId(tablekey, linkage, OperateType.NONE);
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
        TemplateLinkage linkage1 = new TemplateLinkage(null, testLinkId1_2);
        TemplateLinkage linkage2 = new TemplateLinkage(testLinkId2_1, null);
        templateService.operateAllByLinkIds(tablekey, Arrays.asList(linkage1, linkage2), OperateType.NONE);
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
        TemplateIdentity identity = new TemplateIdentity(testTemplatePk1, testTemplatePk2);
        TemplateAlertness alertness = new TemplateAlertness(TemplateStatus1.TEST, null);
        templateService.alertById(tablekey, identity, alertness);
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
        TemplateIdentity identity1 = new TemplateIdentity(null, testTemplatePk2_1);
        TemplateIdentity identity2 = new TemplateIdentity(testTemplatePk1_2, null);
        TemplateAlertness alertness = new TemplateAlertness(null, TemplateStatus2.TEST);
        templateService.alertAll(tablekey, Arrays.asList(identity1, identity2), alertness);
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
        TemplateFilter templateFilter = new TemplateFilter();
        templateFilter.setTablekey(tablekey);
        templateFilter.setStatus(new TemplateAlertness(TemplateStatus1.TEST, TemplateStatus2.TEST));
        TemplateIdentity identity = new TemplateIdentity(testTemplatePk1, testTemplatePk2);
        TemplateIdentity identity1 = new TemplateIdentity(testTemplatePk2_1, null);
        TemplateIdentity identity2 = new TemplateIdentity(null, testTemplatePk2_2);
        templateFilter.setIds(identity, identity1, identity2);
        templateService.alertAllWithFilter(templateFilter);
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
        TemplateLinkage linkage = new TemplateLinkage(testLinkId1, testLinkId2);
        TemplateAlertness alertness = new TemplateAlertness(null, TemplateStatus2.TEST);
        templateService.alertByLinkId(tablekey, linkage, alertness);
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
        TemplateLinkage linkage1 = new TemplateLinkage(testLinkId1_1, null);
        TemplateLinkage linkage2 = new TemplateLinkage(null, testLinkId2_2);
        TemplateAlertness alertness = new TemplateAlertness(TemplateStatus1.TEST, null);
        templateService.alertAllByLinkIds(tablekey, Arrays.asList(linkage1, linkage2), alertness);
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
        TemplateIdentity identity = new TemplateIdentity(testTemplatePk1, testTemplatePk2);
        templateService.deleteById(tablekey, identity);
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
        TemplateIdentity identity1 = new TemplateIdentity(null, testTemplatePk2_1);
        TemplateIdentity identity2 = new TemplateIdentity(testTemplatePk1_2, null);
        templateService.deleteAll(tablekey, Arrays.asList(identity1, identity2));
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
        TemplateFilter templateFilter = new TemplateFilter();
        templateFilter.setTablekey(tablekey);
        TemplateIdentity identity = new TemplateIdentity(testTemplatePk1, testTemplatePk2);
        TemplateIdentity identity1 = new TemplateIdentity(null, testTemplatePk2_1);
        TemplateIdentity identity2 = new TemplateIdentity(testTemplatePk1_2, null);
        templateFilter.setIds(identity, identity1, identity2);
        templateService.deleteAllWithFilter(templateFilter);
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
        TemplateLinkage linkage = new TemplateLinkage(testLinkId1, testLinkId2);
        templateService.deleteByLinkId(tablekey, linkage);
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
        TemplateLinkage linkage1 = new TemplateLinkage(null, testLinkId1_2);
        TemplateLinkage linkage2 = new TemplateLinkage(testLinkId2_1, null);
        templateService.deleteAllByLinkIds(tablekey, Arrays.asList(linkage1, linkage2));
    }


}