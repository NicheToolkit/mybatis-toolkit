package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.mybatis.stereotype.column.RestForceInsert;
import io.github.nichetoolkit.mybatis.stereotype.table.RestEntity;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.RestInfoEntity;

import java.util.Date;


@RestEntity(name = "ntr_simple")
public class SimpleEntity extends RestInfoEntity<SimpleEntity, SimpleModel> {

    @RestForceInsert("now()")
    private Date time;

    public SimpleEntity() {
    }

    public SimpleEntity(String id) {
        super(id);
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public SimpleModel toModel() {
        SimpleModel simpleModel = new SimpleModel();
        BeanUtils.copyNonnullProperties(this,simpleModel);
        return simpleModel;
    }

}
