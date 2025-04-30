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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FickleEntryMapperTest extends MybatisExampleApplicationTests {
    @Autowired
    private FickleEntryMapper fickleEntryMapper;

    @Test
    @Order(1)
    public void tableColumns() throws RestException {
        List<String> tableColumns = fickleEntryMapper.findColumns();
        System.out.println(JsonUtils.parseJson(tableColumns));
    }

    @Test
    @Order(2)
    public void addColumn() throws RestException {
        RestFickle<?> fickle = RestFickle.ofType("time1", MybatisType.TIMESTAMP,"TIMESTAMP");
        fickleEntryMapper.addColumn(fickle);
    }

    @Test
    @Order(3)
    public void createIndex() throws RestException {
        RestFickle<?> fickle = RestFickle.ofType("time1", MybatisType.TIMESTAMP,"TIMESTAMP");
        fickleEntryMapper.createIndex(fickle);
    }

    @Test
    @Order(4)
    public void dropIndex() throws RestException {
        RestFickle<?> fickle = RestFickle.ofType("time1", MybatisType.TIMESTAMP,"TIMESTAMP");
        fickleEntryMapper.dropIndex(fickle);
    }

    @Test
    @Order(5)
    public void modifyColumn() throws RestException {
        RestFickle<?> fickle = RestFickle.ofType("time1", MybatisType.TIMESTAMP,"TIMESTAMP");
        fickleEntryMapper.modifyColumn(fickle);
    }


    @Test
    @Order(6)
    public void dropColumn() throws RestException {
        RestFickle<?> fickle = RestFickle.ofType("time1", MybatisType.TIMESTAMP,"TIMESTAMP");
        fickleEntryMapper.dropColumn(fickle);
    }
}