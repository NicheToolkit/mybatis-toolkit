package io.github.nichetoolkit.mybatis.simple;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * <p>The type template model class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultInfoModel
 * @see io.github.nichetoolkit.rice.RestTablekey
 * @since Jdk1.8
 */
public class TemplateModel extends DefaultInfoModel<TemplateModel, TemplateEntity, TemplateIdentity> implements RestTablekey<String> {
    /**
     * <code>time</code>
     * {@link java.util.Date} <p>the <code>time</code> field.</p>
     * @see java.util.Date
     * @see org.springframework.format.annotation.DateTimeFormat
     * @see com.fasterxml.jackson.annotation.JsonFormat
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /**
     * <code>TemplateModel</code>
     * Instantiates a new template model.
     */
    public TemplateModel() {
    }

    /**
     * <code>TemplateModel</code>
     * Instantiates a new template model.
     * @param id {@link TemplateIdentity} <p>the id parameter is <code>TemplateKey</code> type.</p>
     * @see TemplateIdentity
     */
    public TemplateModel(TemplateIdentity id) {
        super(id);
    }

    /**
     * <code>TemplateModel</code>
     * Instantiates a new template model.
     * @param builder {@link io.github.nichetoolkit.mybatis.simple.TemplateModel.Builder} <p>the builder parameter is <code>Builder</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.TemplateModel.Builder
     */
    public TemplateModel(Builder builder) {
        super(builder);
        this.time = builder.time;
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
    public TemplateEntity toEntity() {
        TemplateEntity entity = new TemplateEntity();
        BeanUtils.copyNonnullProperties(this,entity);
        return entity;
    }

    @Override
    public String getTablekey() {
        return "_dynamic";
    }

    /**
     * <code>Builder</code>
     * <p>The type builder class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @see io.github.nichetoolkit.rice.DefaultInfoModel.Builder
     * @since Jdk1.8
     */
    public static class Builder extends DefaultInfoModel.Builder<TemplateModel, TemplateEntity, TemplateIdentity> {
        /**
         * <code>time</code>
         * {@link java.util.Date} <p>the <code>time</code> field.</p>
         * @see java.util.Date
         */
        protected Date time;

        /**
         * <code>Builder</code>
         * Instantiates a new builder.
         */
        public Builder() {
        }

        /**
         * <code>time</code>
         * <p>the method.</p>
         * @param time {@link java.lang.Long} <p>the time parameter is <code>Long</code> type.</p>
         * @return {@link io.github.nichetoolkit.mybatis.simple.TemplateModel.Builder} <p>the return object is <code>Builder</code> type.</p>
         * @see java.lang.Long
         */
        public Builder time(Long time) {
            this.time = new Date(time);
            return this;
        }

        /**
         * <code>time</code>
         * <p>the method.</p>
         * @param time {@link java.util.Date} <p>the time parameter is <code>Date</code> type.</p>
         * @return {@link io.github.nichetoolkit.mybatis.simple.TemplateModel.Builder} <p>the return object is <code>Builder</code> type.</p>
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
