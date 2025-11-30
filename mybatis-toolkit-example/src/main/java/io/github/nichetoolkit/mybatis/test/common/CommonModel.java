package io.github.nichetoolkit.mybatis.test.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.RestInfoModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <code>CommonModel</code>
 * <p>The common model class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestInfoModel
 * @see lombok.Setter
 * @see lombok.Getter
 * @see lombok.experimental.SuperBuilder
 * @see com.fasterxml.jackson.annotation.JsonInclude
 * @see com.fasterxml.jackson.annotation.JsonIgnoreProperties
 * @since Jdk17
 */
@Setter
@Getter
@SuperBuilder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonModel extends RestInfoModel<CommonModel, CommonEntity> {
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
     * <code>CommonModel</code>
     * <p>Instantiates a new common model.</p>
     */
    public CommonModel() {
    }

    /**
     * <code>CommonModel</code>
     * <p>Instantiates a new common model.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public CommonModel(String id) {
        super(id);
    }

    @Override
    public CommonEntity toEntity() {
        CommonEntity entity = new CommonEntity();
        BeanUtils.copyNonnullProperties(this, entity);
        return entity;
    }

}
