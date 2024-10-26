package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.mybatis.enums.SimpleStatus;
import io.github.nichetoolkit.mybatis.stereotype.column.RestAlertKey;
import io.github.nichetoolkit.mybatis.stereotype.column.RestForceInsert;
import io.github.nichetoolkit.mybatis.stereotype.column.RestLinkKey;
import io.github.nichetoolkit.mybatis.stereotype.table.RestEntity;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.RestInfoEntity;
import io.github.nichetoolkit.rice.enums.OperateType;

import java.util.Date;


/**
 * <code>SimpleEntity</code>
 * <p>The simple entity class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestInfoEntity
 * @see io.github.nichetoolkit.mybatis.stereotype.table.RestEntity
 * @since Jdk1.8
 */
@RestEntity(name = "ntr_simple")
public class SimpleEntity extends RestInfoEntity<SimpleEntity, SimpleModel> {
    /**
     * <code>linkId</code>
     * {@link java.lang.String} <p>The <code>linkId</code> field.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.stereotype.column.RestLinkKey
     */
    @RestLinkKey
    private String linkId;
    /**
     * <code>time</code>
     * {@link java.util.Date} <p>The <code>time</code> field.</p>
     * @see java.util.Date
     * @see io.github.nichetoolkit.mybatis.stereotype.column.RestForceInsert
     */
    @RestForceInsert("now()")
    private Date time;
    /**
     * <code>status</code>
     * {@link java.lang.Integer} <p>The <code>status</code> field.</p>
     * @see java.lang.Integer
     * @see io.github.nichetoolkit.mybatis.stereotype.column.RestAlertKey
     */
    @RestAlertKey
    private Integer status;

    /**
     * <code>SimpleEntity</code>
     * <p>Instantiates a new simple entity.</p>
     */
    public SimpleEntity() {
    }

    /**
     * <code>SimpleEntity</code>
     * <p>Instantiates a new simple entity.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public SimpleEntity(String id) {
        super(id);
    }

    /**
     * <code>getLinkId</code>
     * <p>The get link id getter method.</p>
     * @return {@link java.lang.String} <p>The get link id return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String getLinkId() {
        return linkId;
    }

    /**
     * <code>setLinkId</code>
     * <p>The set link id setter method.</p>
     * @param linkId {@link java.lang.String} <p>The link id parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    /**
     * <code>getTime</code>
     * <p>The get time getter method.</p>
     * @return {@link java.util.Date} <p>The get time return object is <code>Date</code> type.</p>
     * @see java.util.Date
     */
    public Date getTime() {
        return time;
    }

    /**
     * <code>setTime</code>
     * <p>The set time setter method.</p>
     * @param time {@link java.util.Date} <p>The time parameter is <code>Date</code> type.</p>
     * @see java.util.Date
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * <code>getStatus</code>
     * <p>The get status getter method.</p>
     * @return {@link java.lang.Integer} <p>The get status return object is <code>Integer</code> type.</p>
     * @see java.lang.Integer
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * <code>setStatus</code>
     * <p>The set status setter method.</p>
     * @param status {@link java.lang.Integer} <p>The status parameter is <code>Integer</code> type.</p>
     * @see java.lang.Integer
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public SimpleModel toModel() {
        SimpleModel simpleModel = new SimpleModel();
        BeanUtils.copyNonnullProperties(this,simpleModel);
        simpleModel.setOperate(OperateType.parseKey(this.operate));
        simpleModel.setStatus(SimpleStatus.parseKey(this.status));
        return simpleModel;
    }

}
