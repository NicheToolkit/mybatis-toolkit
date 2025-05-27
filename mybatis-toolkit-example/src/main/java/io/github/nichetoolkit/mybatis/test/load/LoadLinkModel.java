package io.github.nichetoolkit.mybatis.test.load;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.rice.RestInfoModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadLinkModel<M extends LoadLinkModel<M, E>, E extends LoadLinkEntity<E, M>> extends RestInfoModel<M, E> {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    private String paramId;

    public LoadLinkModel() {
    }

    public LoadLinkModel(String id) {
        super(id);
    }

}
