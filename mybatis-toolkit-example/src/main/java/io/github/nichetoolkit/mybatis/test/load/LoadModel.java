package io.github.nichetoolkit.mybatis.test.load;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.DefaultInfoModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

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
@SuperBuilder
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


    @Override
    public LoadEntity toEntity() {
        LoadEntity entity = new LoadEntity();
        BeanUtils.copyNonnullProperties(this,entity);
        entity.setOperate(this.operate.getKey());
        entity.setLinkage(new LoadLinkage(this.linkId1,this.linkId2));
        return entity;
    }

}
