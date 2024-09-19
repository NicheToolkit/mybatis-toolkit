package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.rice.DefaultInfoEntity;
import io.github.nichetoolkit.rice.RestInfoEntity;
import io.github.nichetoolkit.mybatis.stereotype.column.RestForceInsert;
import io.github.nichetoolkit.mybatis.stereotype.table.RestEntity;

import java.util.Date;


/**
 * <code>TemplateEntity</code>
 * <p>The type template entity class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultInfoEntity
 * @see io.github.nichetoolkit.mybatis.stereotype.table.RestEntity
 * @since Jdk1.8
 */
@RestEntity(name = "ntr_template")
public class TemplateEntity extends DefaultInfoEntity<TemplateEntity, TemplateModel, TemplateKey> {

    /**
     * <code>time</code>
     * {@link java.util.Date} <p>the <code>time</code> field.</p>
     * @see java.util.Date
     * @see io.github.nichetoolkit.mybatis.stereotype.column.RestForceInsert
     */
    @RestForceInsert("now()")
    private Date time;

    /**
     * <code>TemplateEntity</code>
     * Instantiates a new template entity.
     */
    public TemplateEntity() {
    }

    /**
     * <code>TemplateEntity</code>
     * Instantiates a new template entity.
     * @param id {@link io.github.nichetoolkit.mybatis.simple.TemplateKey} <p>the id parameter is <code>TemplateKey</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.TemplateKey
     */
    public TemplateEntity(TemplateKey id) {
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
    public TemplateModel toModel() {
        TemplateModel.Builder builder = new TemplateModel.Builder();
        builder.id(this.id)
                .name(this.name)
                .description(this.description)
                .time(this.time);
        return new TemplateModel(builder);
    }

}
