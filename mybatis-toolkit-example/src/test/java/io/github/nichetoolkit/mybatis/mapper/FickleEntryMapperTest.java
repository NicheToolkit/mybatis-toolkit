package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisExampleApplicationTests;
import io.github.nichetoolkit.mybatis.enums.MybatisType;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.JsonUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <code>FickleEntryMapperTest</code>
 * <p>The fickle entry mapper test class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisExampleApplicationTests
 * @see org.junit.jupiter.api.TestMethodOrder
 * @since Jdk17
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FickleEntryMapperTest extends MybatisExampleApplicationTests {
    /**
     * <code>fickleEntryMapper</code>
     * {@link io.github.nichetoolkit.mybatis.mapper.FickleEntryMapper} <p>The <code>fickleEntryMapper</code> field.</p>
     * @see org.springframework.beans.factory.annotation.Autowired
     */
    @Autowired
    private FickleEntryMapper fickleEntryMapper;

    /**
     * <code>tableColumns</code>
     * <p>The table columns method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Test
     * @see org.junit.jupiter.api.Order
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Test
    @Order(1)
    public void tableColumns() throws RestException {
        List<String> tableColumns = fickleEntryMapper.findColumns();
        System.out.println(JsonUtils.parseJson(tableColumns));
    }

    /**
     * <code>addColumn</code>
     * <p>The add column method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Test
     * @see org.junit.jupiter.api.Order
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Test
    @Order(2)
    public void addColumn() throws RestException {
        RestFickle<?> fickle = RestFickle.ofType("time1", MybatisType.TIMESTAMP,"TIMESTAMP");
        fickleEntryMapper.addColumn(fickle);
    }

    /**
     * <code>createIndex</code>
     * <p>The create index method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Test
     * @see org.junit.jupiter.api.Order
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Test
    @Order(3)
    public void createIndex() throws RestException {
        RestFickle<?> fickle = RestFickle.ofType("time1", MybatisType.TIMESTAMP,"TIMESTAMP");
        fickleEntryMapper.createIndex(fickle);
    }

    /**
     * <code>dropIndex</code>
     * <p>The drop index method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Test
     * @see org.junit.jupiter.api.Order
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Test
    @Order(4)
    public void dropIndex() throws RestException {
        RestFickle<?> fickle = RestFickle.ofType("time1", MybatisType.TIMESTAMP,"TIMESTAMP");
        fickleEntryMapper.dropIndex(fickle);
    }

    /**
     * <code>modifyColumn</code>
     * <p>The modify column method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Test
     * @see org.junit.jupiter.api.Order
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Test
    @Order(5)
    public void modifyColumn() throws RestException {
        RestFickle<?> fickle = RestFickle.ofType("time1", MybatisType.TIMESTAMP,"TIMESTAMP");
        fickleEntryMapper.modifyColumn(fickle);
    }


    /**
     * <code>dropColumn</code>
     * <p>The drop column method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.junit.jupiter.api.Test
     * @see org.junit.jupiter.api.Order
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Test
    @Order(6)
    public void dropColumn() throws RestException {
        RestFickle<?> fickle = RestFickle.ofType("time1", MybatisType.TIMESTAMP,"TIMESTAMP");
        fickleEntryMapper.dropColumn(fickle);
    }
}