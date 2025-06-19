package io.github.nichetoolkit.mybatis.test.load;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.rice.RestInfoModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <code>LoadLinkModel</code>
 * <p>The load link model class.</p>
 * @param <M> {@link io.github.nichetoolkit.mybatis.test.load.LoadLinkModel} <p>The generic parameter is <code>LoadLinkModel</code> type.</p>
 * @param <E> {@link io.github.nichetoolkit.mybatis.test.load.LoadLinkEntity} <p>The generic parameter is <code>LoadLinkEntity</code> type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.test.load.LoadLinkEntity
 * @see io.github.nichetoolkit.rice.RestInfoModel
 * @see lombok.Setter
 * @see lombok.Getter
 * @see com.fasterxml.jackson.annotation.JsonInclude
 * @see com.fasterxml.jackson.annotation.JsonIgnoreProperties
 * @since Jdk1.8
 */
@Setter
@Getter
@SuperBuilder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadLinkModel<M extends LoadLinkModel<M, E>, E extends LoadLinkEntity<E, M>> extends RestInfoModel<M, E> {
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
     * <code>LoadLinkModel</code>
     * <p>Instantiates a new load link model.</p>
     */
    public LoadLinkModel() {
    }

    /**
     * <code>LoadLinkModel</code>
     * <p>Instantiates a new load link model.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public LoadLinkModel(String id) {
        super(id);
    }

}
