package io.github.nichetoolkit.mybatis.test.load;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.mybatis.column.RestLoadEntity;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.DefaultInfoModel;
import io.github.nichetoolkit.rice.enums.OperateType;
import io.github.nichetoolkit.rice.enums.SaveType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.List;

/**
 * <code>LoadModel</code>
 * <p>The load model class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultInfoModel
 * @see lombok.Setter
 * @see lombok.Getter
 * @see com.fasterxml.jackson.annotation.JsonInclude
 * @see com.fasterxml.jackson.annotation.JsonIgnoreProperties
 * @since Jdk1.8
 */
@Setter
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadModel extends DefaultInfoModel<LoadModel, LoadEntity, LoadIdentity> {
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
     * <code>paramId</code>
     * {@link java.lang.String} <p>The <code>paramId</code> field.</p>
     * @see java.lang.String
     */
    private String paramId;

    /**
     * <code>link1</code>
     * {@link io.github.nichetoolkit.mybatis.test.load.LoadLinkModel} <p>The <code>link1</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.test.load.LoadLinkModel
     */
    private LoadLinkModel<?,?> link1;

    /**
     * <code>link2s</code>
     * {@link java.util.List} <p>The <code>link2s</code> field.</p>
     * @see java.util.List
     */
    private List<LoadLinkModel<?,?>> link2s;

    /**
     * <code>LoadModel</code>
     * <p>Instantiates a new load model.</p>
     */
    public LoadModel() {
    }

    /**
     * <code>LoadModel</code>
     * <p>Instantiates a new load model.</p>
     * @param id {@link io.github.nichetoolkit.mybatis.test.load.LoadIdentity} <p>The id parameter is <code>LoadIdentity</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.test.load.LoadIdentity
     */
    public LoadModel(LoadIdentity id) {
        super(id);
    }

    /**
     * <code>LoadModel</code>
     * <p>Instantiates a new load model.</p>
     * @param builder {@link io.github.nichetoolkit.mybatis.test.load.LoadModel.Builder} <p>The builder parameter is <code>Builder</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.test.load.LoadModel.Builder
     */
    public LoadModel(Builder builder) {
        super(builder);
        this.time = builder.time;
    }

    @Override
    public LoadEntity toEntity() {
        LoadEntity entity = new LoadEntity();
        BeanUtils.copyNonnullProperties(this,entity);
        entity.setOperate(this.operate.getKey());
        entity.setLinkage(new LoadLinkage(this.linkId1,this.linkId2));
        return entity;
    }

    /**
     * <code>Builder</code>
     * <p>The builder class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @see io.github.nichetoolkit.rice.DefaultInfoModel.Builder
     * @since Jdk1.8
     */
    public static class Builder extends DefaultInfoModel.Builder<LoadModel, LoadEntity, LoadIdentity> {
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
         * @return {@link io.github.nichetoolkit.mybatis.test.load.LoadModel.Builder} <p>The time return object is <code>Builder</code> type.</p>
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
         * @return {@link io.github.nichetoolkit.mybatis.test.load.LoadModel.Builder} <p>The time return object is <code>Builder</code> type.</p>
         * @see java.util.Date
         */
        public Builder time(Date time) {
            this.time = time;
            return this;
        }

        @Override
        public Builder id(LoadIdentity id) {
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
        public LoadModel build() {
            return new LoadModel(this);
        }
    }
}
