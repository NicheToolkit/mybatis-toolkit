package io.github.nichetoolkit.mybatis.simple;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.mybatis.enums.TemplateStatus1;
import io.github.nichetoolkit.mybatis.enums.TemplateStatus2;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.DefaultInfoModel;
import io.github.nichetoolkit.rice.RestTablekey;
import io.github.nichetoolkit.rice.enums.OperateType;
import io.github.nichetoolkit.rice.enums.SaveType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.util.Date;

/**
 * <code>TemplateModel</code>
 * <p>The template model class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultInfoModel
 * @see io.github.nichetoolkit.rice.RestTablekey
 * @see com.fasterxml.jackson.annotation.JsonInclude
 * @see com.fasterxml.jackson.annotation.JsonIgnoreProperties
 * @since Jdk1.8
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateModel extends DefaultInfoModel<TemplateModel, TemplateEntity, TemplateIdentity> implements RestTablekey<String> {
    /**
     * <code>linkId1</code>
     * {@link java.lang.String} <p>The <code>linkId1</code> field.</p>
     * @see java.lang.String
     */
    private String linkId1;
    /**
     * <code>linkId2</code>
     * {@link java.lang.String} <p>The <code>linkId2</code> field.</p>
     * @see java.lang.String
     */
    private String linkId2;
    /**
     * <code>time</code>
     * {@link java.util.Date} <p>The <code>time</code> field.</p>
     * @see java.util.Date
     * @see org.springframework.format.annotation.DateTimeFormat
     * @see com.fasterxml.jackson.annotation.JsonFormat
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /**
     * <code>status1</code>
     * {@link io.github.nichetoolkit.mybatis.enums.TemplateStatus1} <p>The <code>status1</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.enums.TemplateStatus1
     */
    private TemplateStatus1 status1;

    /**
     * <code>status2</code>
     * {@link io.github.nichetoolkit.mybatis.enums.TemplateStatus2} <p>The <code>status2</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.enums.TemplateStatus2
     */
    private TemplateStatus2 status2;

    /**
     * <code>TemplateModel</code>
     * <p>Instantiates a new template model.</p>
     */
    public TemplateModel() {
    }

    /**
     * <code>TemplateModel</code>
     * <p>Instantiates a new template model.</p>
     * @param id {@link io.github.nichetoolkit.mybatis.simple.TemplateIdentity} <p>The id parameter is <code>TemplateIdentity</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.TemplateIdentity
     */
    public TemplateModel(TemplateIdentity id) {
        super(id);
    }

    /**
     * <code>TemplateModel</code>
     * <p>Instantiates a new template model.</p>
     * @param builder {@link io.github.nichetoolkit.mybatis.simple.TemplateModel.Builder} <p>The builder parameter is <code>Builder</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.TemplateModel.Builder
     */
    public TemplateModel(Builder builder) {
        super(builder);
        this.time = builder.time;
    }

    /**
     * <code>getLinkId1</code>
     * <p>The get link id 1 getter method.</p>
     * @return {@link java.lang.String} <p>The get link id 1 return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String getLinkId1() {
        return linkId1;
    }

    /**
     * <code>setLinkId1</code>
     * <p>The set link id 1 setter method.</p>
     * @param linkId1 {@link java.lang.String} <p>The link id 1 parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public void setLinkId1(String linkId1) {
        this.linkId1 = linkId1;
    }

    /**
     * <code>getLinkId2</code>
     * <p>The get link id 2 getter method.</p>
     * @return {@link java.lang.String} <p>The get link id 2 return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String getLinkId2() {
        return linkId2;
    }

    /**
     * <code>setLinkId2</code>
     * <p>The set link id 2 setter method.</p>
     * @param linkId2 {@link java.lang.String} <p>The link id 2 parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public void setLinkId2(String linkId2) {
        this.linkId2 = linkId2;
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
     * <code>getStatus1</code>
     * <p>The get status 1 getter method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.enums.TemplateStatus1} <p>The get status 1 return object is <code>TemplateStatus1</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.TemplateStatus1
     */
    public TemplateStatus1 getStatus1() {
        return status1;
    }

    /**
     * <code>setStatus1</code>
     * <p>The set status 1 setter method.</p>
     * @param status1 {@link io.github.nichetoolkit.mybatis.enums.TemplateStatus1} <p>The status 1 parameter is <code>TemplateStatus1</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.TemplateStatus1
     */
    public void setStatus1(TemplateStatus1 status1) {
        this.status1 = status1;
    }

    /**
     * <code>getStatus2</code>
     * <p>The get status 2 getter method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.enums.TemplateStatus2} <p>The get status 2 return object is <code>TemplateStatus2</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.TemplateStatus2
     */
    public TemplateStatus2 getStatus2() {
        return status2;
    }

    /**
     * <code>setStatus2</code>
     * <p>The set status 2 setter method.</p>
     * @param status2 {@link io.github.nichetoolkit.mybatis.enums.TemplateStatus2} <p>The status 2 parameter is <code>TemplateStatus2</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.TemplateStatus2
     */
    public void setStatus2(TemplateStatus2 status2) {
        this.status2 = status2;
    }

    @Override
    public TemplateEntity toEntity() {
        TemplateEntity entity = new TemplateEntity();
        BeanUtils.copyNonnullProperties(this,entity);
        entity.setOperate(this.operate.getKey());
        entity.setLinkage(new TemplateLinkage(this.linkId1,this.linkId2));
        entity.setStatus(new TemplateAlertness(this.status1,this.status2));
        return entity;
    }

    @Override
    public String getTablekey() {
        return "_dynamic";
    }

    /**
     * <code>Builder</code>
     * <p>The builder class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @see io.github.nichetoolkit.rice.DefaultInfoModel.Builder
     * @since Jdk1.8
     */
    public static class Builder extends DefaultInfoModel.Builder<TemplateModel, TemplateEntity, TemplateIdentity> {
        /**
         * <code>time</code>
         * {@link java.util.Date} <p>The <code>time</code> field.</p>
         * @see java.util.Date
         */
        protected Date time;

        /**
         * <code>Builder</code>
         * <p>Instantiates a new builder.</p>
         */
        public Builder() {
        }

        /**
         * <code>time</code>
         * <p>The time method.</p>
         * @param time {@link java.lang.Long} <p>The time parameter is <code>Long</code> type.</p>
         * @return {@link io.github.nichetoolkit.mybatis.simple.TemplateModel.Builder} <p>The time return object is <code>Builder</code> type.</p>
         * @see java.lang.Long
         */
        public Builder time(Long time) {
            this.time = new Date(time);
            return this;
        }

        /**
         * <code>time</code>
         * <p>The time method.</p>
         * @param time {@link java.util.Date} <p>The time parameter is <code>Date</code> type.</p>
         * @return {@link io.github.nichetoolkit.mybatis.simple.TemplateModel.Builder} <p>The time return object is <code>Builder</code> type.</p>
         * @see java.util.Date
         */
        public Builder time(Date time) {
            this.time = time;
            return this;
        }

        @Override
        public Builder id(TemplateIdentity id) {
            this.id = id;
            return this;
        }

        @Override
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        @Override
        public Builder createTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        @Override
        public Builder createTime(@NonNull Long createTime) {
            this.createTime = new Date(createTime);
            return this;
        }

        @Override
        public Builder updateTime(Date updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        @Override
        public Builder updateTime(@NonNull Long updateTime) {
            this.updateTime = new Date(updateTime);
            return this;
        }

        @Override
        public Builder operate(OperateType operate) {
            this.operate = operate;
            return this;
        }

        @Override
        public Builder operate(Integer operate) {
            this.operate = OperateType.parseKey(operate);
            return this;
        }

        @Override
        public Builder save(SaveType save) {
            this.save = save;
            return this;
        }

        @Override
        public Builder save(Integer save) {
            this.save = SaveType.parseKey(save);
            return this;
        }

        @Override
        public TemplateModel build() {
            return new TemplateModel(this);
        }
    }
}
