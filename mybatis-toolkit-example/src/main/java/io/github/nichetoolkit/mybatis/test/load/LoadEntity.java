package io.github.nichetoolkit.mybatis.test.load;

import io.github.nichetoolkit.mybatis.column.RestForceInsert;
import io.github.nichetoolkit.mybatis.column.RestLinkKey;
import io.github.nichetoolkit.mybatis.column.RestLoadEntity;
import io.github.nichetoolkit.mybatis.column.RestLoadParam;
import io.github.nichetoolkit.mybatis.table.RestEntity;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.DefaultInfoEntity;
import io.github.nichetoolkit.rice.enums.OperateType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@RestEntity(name = "ntr_load")
public class LoadEntity extends DefaultInfoEntity<LoadEntity, LoadModel, LoadIdentity> {
    @RestLinkKey
    private LoadLinkage linkage;

    @RestLoadParam(
            load = "paramId",
            type = {LoadLinkEntity1.class, LoadLinkEntity2.class}
    )
    private String paramId;

    @RestForceInsert("now()")
    private Date time;

    /* load and index may be anyone use */
    @RestLoadEntity(load = "linkEntity1")
    private LoadLinkEntity1 linkEntity1;

    /* load and index may be anyone use */
    @RestLoadEntity(load = "linkEntity2", index = 1)
    private LoadLinkEntity2 linkEntity2;

    public LoadEntity() {
    }

    public LoadEntity(LoadIdentity id) {
        super(id);
    }

    @Override
    public LoadModel toModel() {
        LoadModel loadModel = new LoadModel();
        BeanUtils.copyNonnullProperties(this, loadModel);
        loadModel.setOperate(OperateType.parseKey(this.operate));
        loadModel.setLinkId1(this.linkage.getLinkId1());
        loadModel.setLinkId2(this.linkage.getLinkId2());
        return loadModel;
    }

}
