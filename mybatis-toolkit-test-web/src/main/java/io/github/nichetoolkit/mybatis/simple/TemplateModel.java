package io.github.nichetoolkit.mybatis.simple;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.nichetoolkit.rice.RestInfoModel;
import io.github.nichetoolkit.rice.RestTablekey;
import io.github.nichetoolkit.rice.RiceInfoModel;
import io.github.nichetoolkit.rice.enums.OperateType;
import io.github.nichetoolkit.rice.enums.SaveType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.util.Date;

/**
 * <p>TemplateModel</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class TemplateModel extends RestInfoModel<TemplateModel, TemplateEntity, TemplateKey> implements RestTablekey<String> {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    public TemplateModel() {
    }

    public TemplateModel(TemplateKey id) {
        super(id);
    }

    public TemplateModel(Builder builder) {
        super(builder);
        this.time = builder.time;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public TemplateEntity toEntity() {
        TemplateEntity entity = new TemplateEntity();
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

    public static class Builder extends RestInfoModel.Builder<TemplateModel, TemplateEntity, TemplateKey> {
        protected Date time;

        public Builder() {
        }

        public Builder time(Long time) {
            this.time = new Date(time);
            return this;
        }

        public Builder time(Date time) {
            this.time = time;
            return this;
        }

        @Override
        public Builder id(TemplateKey id) {
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
