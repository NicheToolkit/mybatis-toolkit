package io.github.nichetoolkit.mybatis.test.template;

import io.github.nichetoolkit.mybatis.column.RestAlertKey;
import io.github.nichetoolkit.mybatis.column.RestLinkKey;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.DefaultInfoEntity;
import io.github.nichetoolkit.mybatis.column.RestForceInsert;
import io.github.nichetoolkit.mybatis.table.RestEntity;
import io.github.nichetoolkit.rice.enums.OperateType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <code>TemplateEntity</code>
 * <p>The template entity class.</p>
 * @see  io.github.nichetoolkit.rice.DefaultInfoEntity
 * @see  lombok.Setter
 * @see  lombok.Getter
 * @see  RestEntity
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Setter
@Getter
@RestEntity(name = "ntr_template")
public class TemplateEntity extends DefaultInfoEntity<TemplateEntity, TemplateModel, TemplateIdentity> {
    /**
     * <code>linkage</code>
     * {@link TemplateLinkage} <p>The <code>linkage</code> field.</p>
     * @see  TemplateLinkage
     * @see  RestLinkKey
     */
    @RestLinkKey
    private TemplateLinkage linkage;
    /**
     * <code>time</code>
     * {@link java.util.Date} <p>The <code>time</code> field.</p>
     * @see  java.util.Date
     * @see  RestForceInsert
     */
    @RestForceInsert("now()")
    private Date time;
    /**
     * <code>status</code>
     * {@link TemplateAlertness} <p>The <code>status</code> field.</p>
     * @see  TemplateAlertness
     * @see  RestAlertKey
     */
    @RestAlertKey
    private TemplateAlertness status;

    /**
     * <code>TemplateEntity</code>
     * <p>Instantiates a new template entity.</p>
     */
    public TemplateEntity() {
    }

    /**
     * <code>TemplateEntity</code>
     * <p>Instantiates a new template entity.</p>
     * @param id {@link TemplateIdentity} <p>The id parameter is <code>TemplateIdentity</code> type.</p>
     * @see  TemplateIdentity
     */
    public TemplateEntity(TemplateIdentity id) {
        super(id);
    }

    @Override
    public TemplateModel toModel() {
        TemplateModel templateModel = new TemplateModel();
        BeanUtils.copyNonnullProperties(this,templateModel);
        templateModel.setOperate(OperateType.parseKey(this.operate));
        templateModel.setLinkId1(this.linkage.getLinkId1());
        templateModel.setLinkId2(this.linkage.getLinkId2());
        return templateModel;
    }

}
