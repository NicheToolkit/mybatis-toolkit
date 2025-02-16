package io.github.nichetoolkit.mybatis.simple;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.mybatis.column.RestFickleEntry;
import io.github.nichetoolkit.mybatis.fickle.FickleArrayList;
import io.github.nichetoolkit.mybatis.fickle.FickleField;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.RestInfoModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * <code>FickleEntryModel</code>
 * <p>The fickle entry model class.</p>
 * @see  io.github.nichetoolkit.rice.RestInfoModel
 * @see  lombok.Setter
 * @see  lombok.Getter
 * @see  com.fasterxml.jackson.annotation.JsonInclude
 * @see  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Setter
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FickleEntryModel extends RestInfoModel<FickleEntryModel, FickleEntryEntity> {
    /**
     * <code>time</code>
     * {@link java.util.Date} <p>The <code>time</code> field.</p>
     * @see  java.util.Date
     * @see  org.springframework.format.annotation.DateTimeFormat
     * @see  com.fasterxml.jackson.annotation.JsonFormat
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /**
     * <code>fields</code>
     * {@link java.util.List} <p>The <code>fields</code> field.</p>
     * @see  java.util.List
     */
    private List<FickleField<?>> fields;

    /**
     * <code>FickleEntryModel</code>
     * <p>Instantiates a new fickle entry model.</p>
     */
    public FickleEntryModel() {
    }

    /**
     * <code>FickleEntryModel</code>
     * <p>Instantiates a new fickle entry model.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     */
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
