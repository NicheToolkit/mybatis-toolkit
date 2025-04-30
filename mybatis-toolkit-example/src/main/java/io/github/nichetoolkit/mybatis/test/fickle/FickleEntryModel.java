package io.github.nichetoolkit.mybatis.test.fickle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.RestInfoModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FickleEntryModel extends RestInfoModel<FickleEntryModel, FickleEntryEntity> {

    private List<RestFickle<?>> fields;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    public FickleEntryModel() {
    }

    public FickleEntryModel(String id) {
        super(id);
    }

    @Override
    public FickleEntryEntity toEntity() {
        FickleEntryEntity entity = new FickleEntryEntity();
        BeanUtils.copyNonnullProperties(this, entity);
        return entity;
    }

}
