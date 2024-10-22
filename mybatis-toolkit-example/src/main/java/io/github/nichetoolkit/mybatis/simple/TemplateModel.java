package io.github.nichetoolkit.mybatis.simple;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.DefaultInfoModel;
import io.github.nichetoolkit.rice.RestTablekey;
import io.github.nichetoolkit.rice.enums.OperateType;
import io.github.nichetoolkit.rice.enums.SaveType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.util.Date;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateModel extends DefaultInfoModel<TemplateModel, TemplateEntity, TemplateIdentity> implements RestTablekey<String> {
    private String linkId1;
    private String linkId2;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    public TemplateModel() {
    }

    public TemplateModel(TemplateIdentity id) {
        super(id);
    }

    public TemplateModel(Builder builder) {
        super(builder);
        this.time = builder.time;
    }

    public String getLinkId1() {
        return linkId1;
    }

    public void setLinkId1(String linkId1) {
        this.linkId1 = linkId1;
    }

    public String getLinkId2() {
        return linkId2;
    }

    public void setLinkId2(String linkId2) {
        this.linkId2 = linkId2;
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
        BeanUtils.copyNonnullProperties(this,entity);
        entity.setOperate(this.operate.getKey());
        entity.setLinkage(new TemplateLinkage(this.linkId1,this.linkId2));
        return entity;
    }

    @Override
    public String getTablekey() {
        return "_dynamic";
    }

    public static class Builder extends DefaultInfoModel.Builder<TemplateModel, TemplateEntity, TemplateIdentity> {
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
