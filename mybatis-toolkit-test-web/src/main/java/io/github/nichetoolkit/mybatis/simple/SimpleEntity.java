package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.mybatis.stereotype.column.RestForceInsert;
import io.github.nichetoolkit.mybatis.stereotype.table.RestEntity;
import io.github.nichetoolkit.rice.RiceInfoEntity;

import java.util.Date;



@RestEntity(name = "ntr_simple")
public class SimpleEntity extends RiceInfoEntity<SimpleEntity, SimpleModel> {

    /**
     * time.
     * The Time.
     */
    @RestForceInsert("now()")
    private Date time;

    /**
     * Instantiates a new Simple entity.
     */
    public SimpleEntity() {
    }

    /**
     * Instantiates a new Simple entity.
     * @param id the id
     */
    public SimpleEntity(String id) {
        super(id);
    }

    /**
     * Gets time.
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * Sets time.
     * @param time the time
     */
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
