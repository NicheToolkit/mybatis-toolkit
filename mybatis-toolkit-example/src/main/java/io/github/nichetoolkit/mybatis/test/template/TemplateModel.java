package io.github.nichetoolkit.mybatis.test.template;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.DefaultInfoModel;
import io.github.nichetoolkit.rice.RestTableKey;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <code>TemplateModel</code>
 * <p>The template model class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultInfoModel
 * @see io.github.nichetoolkit.rice.RestTableKey
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
public class TemplateModel extends DefaultInfoModel<TemplateModel, TemplateEntity, TemplateIdentity> implements RestTableKey<String> {
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
     * <code>status1</code>
     * {@link TemplateState1} <p>The <code>status1</code> field.</p>
     * @see TemplateState1
     */
    private TemplateState1 status1;

    /**
     * <code>status2</code>
     * {@link TemplateState2} <p>The <code>status2</code> field.</p>
     * @see TemplateState2
     */
    private TemplateState2 status2;

    /**
     * <code>TemplateModel</code>
     * <p>Instantiates a new template model.</p>
     */
    public TemplateModel() {
    }

    /**
     * <code>TemplateModel</code>
     * <p>Instantiates a new template model.</p>
     * @param id {@link io.github.nichetoolkit.mybatis.test.template.TemplateIdentity} <p>The id parameter is <code>TemplateIdentity</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.test.template.TemplateIdentity
     */
    public TemplateModel(TemplateIdentity id) {
        super(id);
    }


    @Override
    public TemplateEntity toEntity() {
        TemplateEntity entity = new TemplateEntity();
        BeanUtils.copyNonnullProperties(this,entity);
        entity.setOperate(this.operate.getKey());
        entity.setLinkage(new TemplateLinkage(this.linkId1,this.linkId2));
        entity.setStatus(new TemplateAlertness(this.status1,this.status2));
        return entity;
    }

    @Override
    public String getTableKey() {
        return "_dynamic";
    }

}
