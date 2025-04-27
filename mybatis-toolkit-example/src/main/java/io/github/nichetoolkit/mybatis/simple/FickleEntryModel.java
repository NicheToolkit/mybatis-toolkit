package io.github.nichetoolkit.mybatis.simple;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.RestInfoModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Setter
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FickleEntryModel extends RestInfoModel<FickleEntryModel, FickleEntryEntity> {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    private List<RestFickle<?>> fields;

    public FickleEntryModel() {
    }

    public FickleEntryModel(String id) {
        super(id);
    }

    @Override
    public FickleEntryEntity toEntity() {
        FickleEntryEntity entity = new FickleEntryEntity();
        BeanUtils.copyNonnullProperties(this, entity);
        if (GeneralUtils.isNotEmpty(fields)) {
            entity.setFields(fields.stream().collect(Collectors.toMap(RestFickle::getKey, Function.identity())));
        }
        return entity;
    }

}
