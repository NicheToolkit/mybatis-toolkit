package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.mybatis.stereotype.column.RestForceInsert;
import io.github.nichetoolkit.mybatis.stereotype.table.RestEntity;
import io.github.nichetoolkit.rice.RiceInfoEntity;

import java.util.Date;


/**
 * <code>SimpleEntity</code>
 * <p>The type simple entity class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RiceInfoEntity
 * @see io.github.nichetoolkit.mybatis.stereotype.table.RestEntity
 * @since Jdk1.8
 */
@RestEntity(name = "ntr_simple")
public class SimpleEntity extends RiceInfoEntity<SimpleEntity, SimpleModel> {

    @RestForceInsert("now()")
    private Date time;

    /**
     * <code>SimpleEntity</code>
     * Instantiates a new simple entity.
     */
    public SimpleEntity() {
    }

    /**
     * <code>SimpleEntity</code>
     * Instantiates a new simple entity.
     * @param id {@link java.lang.String} <p>the id parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public SimpleEntity(String id) {
        super(id);
    }

    /**
     * <code>getTime</code>
     * <p>the time getter method.</p>
     * @return {@link java.util.Date} <p>the time return object is <code>Date</code> type.</p>
     * @see java.util.Date
     */
    public Date getTime() {
        return time;
    }

    /**
     * <code>setTime</code>
     * <p>the time setter method.</p>
     * @param time {@link java.util.Date} <p>the time parameter is <code>Date</code> type.</p>
     * @see java.util.Date
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
