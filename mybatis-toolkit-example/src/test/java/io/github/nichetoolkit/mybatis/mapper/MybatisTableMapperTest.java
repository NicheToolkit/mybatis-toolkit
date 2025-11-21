package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.*;
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
 * <code>MybatisTableMapperTest</code>
 * <p>The mybatis table mapper test class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisExampleApplicationTests
 * @see org.junit.jupiter.api.TestMethodOrder
 * @since Jdk1.8
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MybatisTableMapperTest extends MybatisExampleApplicationTests {
    /**
     * <code>tableMapper</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisTableMapper} <p>The <code>tableMapper</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTableMapper
     * @see org.springframework.beans.factory.annotation.Autowired
     */
    @Autowired
    private MybatisTableMapper tableMapper;

    /**
     * <code>tableName</code>
     * {@link java.lang.String} <p>The <code>tableName</code> field.</p>
     * @see java.lang.String
     */
    private final String tableName = "ntr_fickle_entry";

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
        List<String> tableColumns = tableMapper.findTableColumns(tableName);
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
        tableMapper.addTableColumn(tableName,fickle);
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
        tableMapper.createTableIndex(tableName,fickle);
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
        tableMapper.dropTableIndex(tableName,fickle);
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
        tableMapper.modifyTableColumn(tableName,fickle);
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
        tableMapper.dropTableColumn(tableName,fickle);
    }

}