package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.DefaultInfoEntity;
import io.github.nichetoolkit.mybatis.stereotype.column.RestForceInsert;
import io.github.nichetoolkit.mybatis.stereotype.table.RestEntity;

import java.util.Date;


@RestEntity(name = "ntr_template")
public class TemplateEntity extends DefaultInfoEntity<TemplateEntity, TemplateModel, TemplateIdentity> {

    @RestForceInsert("now()")
    private Date time;

    public TemplateEntity() {
    }

    public TemplateEntity(TemplateIdentity id) {
        super(id);
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
        return templateModel;
    }

}
