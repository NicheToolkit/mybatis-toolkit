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
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <code>TemplateEntity</code>
 * <p>The template entity class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultInfoEntity
 * @see lombok.Setter
 * @see lombok.Getter
 * @see lombok.experimental.SuperBuilder
 * @see io.github.nichetoolkit.mybatis.table.RestEntity
 * @since Jdk17
 */
@Setter
@Getter
@SuperBuilder
@RestEntity(name = "ntr_template")
public class TemplateEntity extends DefaultInfoEntity<TemplateEntity, TemplateModel, TemplateIdentity> {
    /**
     * <code>linkage</code>
     * {@link io.github.nichetoolkit.mybatis.test.template.TemplateLinkage} <p>The <code>linkage</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.test.template.TemplateLinkage
     * @see io.github.nichetoolkit.mybatis.column.RestLinkKey
     */
    @RestLinkKey
    private TemplateLinkage linkage;
    /**
     * <code>time</code>
     * {@link java.util.Date} <p>The <code>time</code> field.</p>
     * @see java.util.Date
     * @see io.github.nichetoolkit.mybatis.column.RestForceInsert
     */
    @RestForceInsert("now()")
    private Date time;
    /**
     * <code>state</code>
     * {@link io.github.nichetoolkit.mybatis.test.template.TemplateAlertness} <p>The <code>state</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.test.template.TemplateAlertness
     * @see io.github.nichetoolkit.mybatis.column.RestAlertKey
     */
    @RestAlertKey
    private TemplateAlertness state;

    /**
     * <code>TemplateEntity</code>
     * <p>Instantiates a new template entity.</p>
     */
    public TemplateEntity() {
    }

    /**
     * <code>TemplateEntity</code>
     * <p>Instantiates a new template entity.</p>
     * @param id {@link io.github.nichetoolkit.mybatis.test.template.TemplateIdentity} <p>The id parameter is <code>TemplateIdentity</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.test.template.TemplateIdentity
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
