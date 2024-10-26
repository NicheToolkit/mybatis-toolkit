package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.mybatis.enums.SimpleStatus;
import io.github.nichetoolkit.mybatis.stereotype.column.RestAlertKey;
import io.github.nichetoolkit.mybatis.stereotype.column.RestForceInsert;
import io.github.nichetoolkit.mybatis.stereotype.column.RestLinkKey;
import io.github.nichetoolkit.mybatis.stereotype.table.RestEntity;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.RestInfoEntity;
import io.github.nichetoolkit.rice.enums.OperateType;

import java.util.Date;


@RestEntity(name = "ntr_simple")
public class SimpleEntity extends RestInfoEntity<SimpleEntity, SimpleModel> {
    @RestLinkKey
    private String linkId;
    @RestForceInsert("now()")
    private Date time;
    @RestAlertKey
    private Integer status;

    public SimpleEntity() {
    }

    public SimpleEntity(String id) {
        super(id);
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public SimpleModel toModel() {
        SimpleModel simpleModel = new SimpleModel();
        BeanUtils.copyNonnullProperties(this,simpleModel);
        simpleModel.setOperate(OperateType.parseKey(this.operate));
        simpleModel.setStatus(SimpleStatus.parseKey(this.status));
        return simpleModel;
    }

}
