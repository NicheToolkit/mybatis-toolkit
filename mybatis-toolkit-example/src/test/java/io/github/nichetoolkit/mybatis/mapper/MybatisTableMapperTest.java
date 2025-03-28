package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.*;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.JsonUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MybatisTableMapperTest extends MybatisExampleApplicationTests {
    @Autowired
    private MybatisTableMapper tableMapper;

    private final String tablename = "ntr_fickle_entry";

    @Test
    @Order(1)
    public void tableColumns() throws RestException {
        List<String> tableColumns = tableMapper.tableColumns(tablename);
        System.out.println(JsonUtils.parseJson(tableColumns));
    }

    @Test
    @Order(2)
    public void addColumn() throws RestException {
        MybatisFickle fickle = new MybatisFickle("time1");
        fickle.setType(RestFieldType.build(MybatisType.TIMESTAMP,"TIMESTAMP"));
        tableMapper.addColumn(tablename,fickle);
    }

    @Test
    @Order(3)
    public void createIndex() throws RestException {
        MybatisFickle fickle = new MybatisFickle("time1");
        fickle.setType(RestFieldType.build(MybatisType.TIMESTAMP,"TIMESTAMP"));
        tableMapper.createIndex(tablename,fickle);
    }

    @Test
    @Order(4)
    public void dropIndex() throws RestException {
        MybatisFickle fickle = new MybatisFickle("time1");
        fickle.setType(RestFieldType.build(MybatisType.TIMESTAMP,"TIMESTAMP"));
        tableMapper.dropIndex(tablename,fickle);
    }

    @Test
    @Order(5)
    public void modifyColumn() throws RestException {
        MybatisFickle fickle = new MybatisFickle("time1");
        fickle.setType(RestFieldType.build(MybatisType.TIMESTAMP,"TIMESTAMP"));
        tableMapper.modifyColumn(tablename,fickle);
    }


    @Test
    @Order(6)
    public void dropColumn() throws RestException {
        MybatisFickle fickle = new MybatisFickle("time1");
        fickle.setType(RestFieldType.build(MybatisType.TIMESTAMP,"TIMESTAMP"));
        tableMapper.dropColumn(tablename,fickle);
    }

}