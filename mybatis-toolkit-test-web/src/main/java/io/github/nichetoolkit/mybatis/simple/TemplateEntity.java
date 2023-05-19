package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.rice.RestInfoEntity;
import io.github.nichetoolkit.rice.RiceInfoEntity;
import io.github.nichetoolkit.rice.stereotype.mybatis.column.RestForceInsert;
import io.github.nichetoolkit.rice.stereotype.mybatis.table.RestEntity;

import java.util.Date;


/**
 * <p>TemplateEntity</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@RestEntity(name = "ntr_simple")
public class TemplateEntity extends RestInfoEntity<TemplateEntity, TemplateModel,TemplateKey> {

    @RestForceInsert("now()")
    private Date time;

    public TemplateEntity() {
    }

    public TemplateEntity(TemplateKey id) {
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
        TemplateModel.Builder builder = new TemplateModel.Builder();
        builder.id(this.id)
                .name(this.name)
                .description(this.description)
                .time(this.time);
        return new TemplateModel(builder);
    }

}
