package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.simple.TemplateFilter;
import io.github.nichetoolkit.mybatis.simple.TemplateIdentity;
import io.github.nichetoolkit.mybatis.simple.TemplateModel;
import io.github.nichetoolkit.rice.service.*;

/**
 * <code>TemplateService</code>
 * <p>The template service interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.service.FilterService
 * @see io.github.nichetoolkit.rice.service.SingleService
 * @see io.github.nichetoolkit.rice.service.AlertLinkService
 * @see io.github.nichetoolkit.rice.service.DeleteLinkService
 * @see io.github.nichetoolkit.rice.service.RemoveLinkService
 * @see io.github.nichetoolkit.rice.service.OperateLinkService
 * @since Jdk1.8
 */
public interface TemplateService extends FilterService<TemplateModel, TemplateFilter, TemplateIdentity, String>, SingleService<TemplateModel, TemplateIdentity, String>,
        AlertLinkService<TemplateIdentity, String>, DeleteLinkService<TemplateIdentity, String>, RemoveLinkService<TemplateIdentity, String>, OperateLinkService<TemplateIdentity, String> {
}
