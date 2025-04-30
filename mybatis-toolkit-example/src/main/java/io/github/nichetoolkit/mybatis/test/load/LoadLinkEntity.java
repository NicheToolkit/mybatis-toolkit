package io.github.nichetoolkit.mybatis.test.load;

import io.github.nichetoolkit.mybatis.column.RestForceInsert;
import io.github.nichetoolkit.mybatis.column.RestLinkKey;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.RestInfoEntity;
import io.github.nichetoolkit.rice.enums.OperateType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class LoadLinkEntity extends RestInfoEntity<LoadLinkEntity, LoadLinkModel> {
    @RestLinkKey
    private String paramId;
    @RestForceInsert("now()")
    private Date time;

    public LoadLinkEntity() {
    }

    public LoadLinkEntity(String id) {
        super(id);
    }

    @Override
    public LoadLinkModel toModel() {
        LoadLinkModel loadLinkModel = new LoadLinkModel();
        BeanUtils.copyNonnullProperties(this, loadLinkModel);
        loadLinkModel.setOperate(OperateType.parseKey(this.operate));
        return loadLinkModel;
    }

}
