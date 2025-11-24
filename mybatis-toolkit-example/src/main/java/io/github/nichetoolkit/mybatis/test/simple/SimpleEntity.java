package io.github.nichetoolkit.mybatis.test.simple;

import io.github.nichetoolkit.mybatis.column.RestAlertKey;
import io.github.nichetoolkit.mybatis.column.RestForceInsert;
import io.github.nichetoolkit.mybatis.column.RestLinkKey;
import io.github.nichetoolkit.mybatis.table.RestEntity;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.RestInfoEntity;
import io.github.nichetoolkit.rice.enums.OperateType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;


@Setter
@Getter
@SuperBuilder
@RestEntity(name = "ntr_simple")
public class SimpleEntity extends RestInfoEntity<SimpleEntity, SimpleModel> {
    @RestLinkKey("linkId")
    private String linkId;
    @RestForceInsert("now()")
    private Date time;
    @RestAlertKey("simpleState1")
    private Integer state1;
    @RestAlertKey("simpleState2")
    private String state2;

    public SimpleEntity() {
    }

    public SimpleEntity(String id) {
        super(id);
    }

    @Override
    public SimpleModel toModel() {
        SimpleModel simpleModel = new SimpleModel();
        BeanUtils.copyNonnullProperties(this,simpleModel);
        simpleModel.setOperate(OperateType.parseKey(this.operate));
        simpleModel.setState1(SimpleState1.parseKey(this.state1));
        simpleModel.setState2(SimpleState2.parseKey(this.state2));
        return simpleModel;
    }

}
