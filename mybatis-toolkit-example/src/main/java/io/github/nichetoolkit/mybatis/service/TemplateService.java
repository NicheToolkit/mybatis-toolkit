package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.simple.TemplateFilter;
import io.github.nichetoolkit.mybatis.simple.TemplateIdentity;
import io.github.nichetoolkit.mybatis.simple.TemplateModel;
import io.github.nichetoolkit.rice.service.FilterService;
import io.github.nichetoolkit.rice.service.SingleService;
import io.github.nichetoolkit.rice.service.extend.AlertFieldService;

/**
 * <code>TemplateService</code>
 * <p>The type template service interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.service.FilterService
 * @see io.github.nichetoolkit.rice.service.SingleService
 * @since Jdk1.8
 */
public interface TemplateService extends FilterService<TemplateModel, TemplateFilter, TemplateIdentity, String>, AlertFieldService<TemplateIdentity>, SingleService<TemplateModel, TemplateIdentity, String> {
}
