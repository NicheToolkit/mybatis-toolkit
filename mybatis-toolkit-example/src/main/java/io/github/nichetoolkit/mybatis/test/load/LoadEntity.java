package io.github.nichetoolkit.mybatis.test.load;

import io.github.nichetoolkit.mybatis.column.RestForceInsert;
import io.github.nichetoolkit.mybatis.column.RestLinkKey;
import io.github.nichetoolkit.mybatis.column.RestLoadEntity;
import io.github.nichetoolkit.mybatis.column.RestLoadParam;
import io.github.nichetoolkit.mybatis.table.RestEntity;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.DefaultInfoEntity;
import io.github.nichetoolkit.rice.enums.OperateType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@RestEntity(name = "ntr_load")
public class LoadEntity extends DefaultInfoEntity<LoadEntity, LoadModel, LoadIdentity> {
    @RestLinkKey
    private LoadLinkage linkage;

    @RestLoadParam(param = "paramId", types = LoadLink2Entity.class)
    private String paramId;

    @RestForceInsert("now()")
    private Date time;

    @RestLoadEntity
    private LoadLink1Entity linkEntity1;

    @RestLoadEntity
    private List<LoadLink2Entity> linkEntity2s;

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
        if (GeneralUtils.isNotEmpty(this.linkEntity1)) {
            loadModel.setLink1(this.linkEntity1.toModel());
        }
        if (GeneralUtils.isNotEmpty(this.linkEntity2s)) {
            List<LoadLink2Model> loadLink2Models = this.linkEntity2s.stream().map(LoadLink2Entity::toModel).collect(Collectors.toList());
            loadModel.setLink2s(new ArrayList<>(loadLink2Models));
        }
        return loadModel;
    }

}
