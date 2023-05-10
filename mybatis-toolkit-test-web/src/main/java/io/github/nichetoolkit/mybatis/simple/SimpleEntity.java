package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.rice.OperateEntity;
import io.github.nichetoolkit.rice.RiceInfoEntity;
import io.github.nichetoolkit.rice.stereotype.mybatis.table.RestEntity;
import io.github.nichetoolkit.rice.stereotype.mybatis.table.RestExcludes;

import javax.persistence.Table;
import java.util.Date;


/**
 * <p>SimpleEntity</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@RestEntity(name = "ntr_simple")
@RestExcludes(superClasses = {OperateEntity.class})
public class SimpleEntity extends RiceInfoEntity<SimpleEntity, SimpleModel> {

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
        SimpleModel.Builder builder = new SimpleModel.Builder();
        builder.id(this.id)
                .name(this.name)
                .description(this.description)
                .time(this.time);
        return new SimpleModel(builder);
    }

}
