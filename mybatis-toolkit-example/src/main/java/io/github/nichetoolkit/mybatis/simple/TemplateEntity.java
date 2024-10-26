package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.mybatis.stereotype.column.RestAlertKey;
import io.github.nichetoolkit.mybatis.stereotype.column.RestLinkKey;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.DefaultInfoEntity;
import io.github.nichetoolkit.mybatis.stereotype.column.RestForceInsert;
import io.github.nichetoolkit.mybatis.stereotype.table.RestEntity;
import io.github.nichetoolkit.rice.enums.OperateType;

import java.util.Date;

/**
 * <code>TemplateEntity</code>
 * <p>The template entity class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultInfoEntity
 * @see io.github.nichetoolkit.mybatis.stereotype.table.RestEntity
 * @since Jdk1.8
 */
@RestEntity(name = "ntr_template")
public class TemplateEntity extends DefaultInfoEntity<TemplateEntity, TemplateModel, TemplateIdentity> {
    /**
     * <code>linkage</code>
     * {@link io.github.nichetoolkit.mybatis.simple.TemplateLinkage} <p>The <code>linkage</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.simple.TemplateLinkage
     * @see io.github.nichetoolkit.mybatis.stereotype.column.RestLinkKey
     */
    @RestLinkKey
    private TemplateLinkage linkage;
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
     * {@link io.github.nichetoolkit.mybatis.simple.TemplateAlertness} <p>The <code>status</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.simple.TemplateAlertness
     * @see io.github.nichetoolkit.mybatis.stereotype.column.RestAlertKey
     */
    @RestAlertKey
    private TemplateAlertness status;

    /**
     * <code>TemplateEntity</code>
     * <p>Instantiates a new template entity.</p>
     */
    public TemplateEntity() {
    }

    /**
     * <code>TemplateEntity</code>
     * <p>Instantiates a new template entity.</p>
     * @param id {@link io.github.nichetoolkit.mybatis.simple.TemplateIdentity} <p>The id parameter is <code>TemplateIdentity</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.TemplateIdentity
     */
    public TemplateEntity(TemplateIdentity id) {
        super(id);
    }

    /**
     * <code>getLinkage</code>
     * <p>The get linkage getter method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.simple.TemplateLinkage} <p>The get linkage return object is <code>TemplateLinkage</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.TemplateLinkage
     */
    public TemplateLinkage getLinkage() {
        return linkage;
    }

    /**
     * <code>setLinkage</code>
     * <p>The set linkage setter method.</p>
     * @param linkage {@link io.github.nichetoolkit.mybatis.simple.TemplateLinkage} <p>The linkage parameter is <code>TemplateLinkage</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.TemplateLinkage
     */
    public void setLinkage(TemplateLinkage linkage) {
        this.linkage = linkage;
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
     * @return {@link io.github.nichetoolkit.mybatis.simple.TemplateAlertness} <p>The get status return object is <code>TemplateAlertness</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.TemplateAlertness
     */
    public TemplateAlertness getStatus() {
        return status;
    }

    /**
     * <code>setStatus</code>
     * <p>The set status setter method.</p>
     * @param status {@link io.github.nichetoolkit.mybatis.simple.TemplateAlertness} <p>The status parameter is <code>TemplateAlertness</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.TemplateAlertness
     */
    public void setStatus(TemplateAlertness status) {
        this.status = status;
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
