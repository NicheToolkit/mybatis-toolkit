package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.mybatis.stereotype.column.RestLinkKey;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.DefaultInfoEntity;
import io.github.nichetoolkit.mybatis.stereotype.column.RestForceInsert;
import io.github.nichetoolkit.mybatis.stereotype.table.RestEntity;
import io.github.nichetoolkit.rice.enums.OperateType;

import java.util.Date;

@RestEntity(name = "ntr_template")
public class TemplateEntity extends DefaultInfoEntity<TemplateEntity, TemplateModel, TemplateIdentity> {
    @RestLinkKey
    private TemplateLinkage linkage;
    @RestForceInsert("now()")
    private Date time;

    public TemplateEntity() {
    }

    public TemplateEntity(TemplateIdentity id) {
        super(id);
    }

    public TemplateLinkage getLinkage() {
        return linkage;
    }

    public void setLinkage(TemplateLinkage linkage) {
        this.linkage = linkage;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public TemplateModel toModel() {
        TemplateModel templateModel = new TemplateModel();
        BeanUtils.copyNonnullProperties(this,templateModel);
        templateModel.setOperate(OperateType.parseKey(this.operate));
        templateModel.setLinkId1(this.linkage.getLinkId1());
        templateModel.setLinkId2(this.linkage.getLinkId2());
        return templateModel;
    }

}
