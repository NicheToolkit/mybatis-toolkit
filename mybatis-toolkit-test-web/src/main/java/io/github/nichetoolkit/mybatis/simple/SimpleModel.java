package io.github.nichetoolkit.mybatis.simple;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.nichetoolkit.rice.RestTablekey;
import io.github.nichetoolkit.rice.RiceInfoModel;
import io.github.nichetoolkit.rice.enums.OperateType;
import io.github.nichetoolkit.rice.enums.SaveType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.util.Date;

/**
 * <code>SimpleModel</code>
 * <p>The type simple model class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RiceInfoModel
 * @see io.github.nichetoolkit.rice.RestTablekey
 * @since Jdk1.8
 */
public class SimpleModel extends RiceInfoModel<SimpleModel,SimpleEntity> implements RestTablekey<String> {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /**
     * <code>SimpleModel</code>
     * Instantiates a new simple model.
     */
    public SimpleModel() {
    }

    /**
     * <code>SimpleModel</code>
     * Instantiates a new simple model.
     * @param id {@link java.lang.String} <p>the id parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public SimpleModel(String id) {
        super(id);
    }

    /**
     * <code>SimpleModel</code>
     * Instantiates a new simple model.
     * @param builder {@link io.github.nichetoolkit.mybatis.simple.SimpleModel.Builder} <p>the builder parameter is <code>Builder</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.SimpleModel.Builder
     */
    public SimpleModel(Builder builder) {
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
    public SimpleEntity toEntity() {
        SimpleEntity entity = new SimpleEntity();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setDescription(this.description);
        entity.setTime(this.time);
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
     * @see io.github.nichetoolkit.rice.RiceInfoModel.Builder
     * @since Jdk1.8
     */
    public static class Builder extends RiceInfoModel.Builder<SimpleModel,SimpleEntity> {
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
         * @return {@link io.github.nichetoolkit.mybatis.simple.SimpleModel.Builder} <p>the return object is <code>Builder</code> type.</p>
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
         * @return {@link io.github.nichetoolkit.mybatis.simple.SimpleModel.Builder} <p>the return object is <code>Builder</code> type.</p>
         * @see java.util.Date
         */
        public Builder time(Date time) {
            this.time = time;
            return this;
        }

        @Override
        public Builder id(String id) {
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
        public SimpleModel build() {
            return new SimpleModel(this);
        }
    }
}
