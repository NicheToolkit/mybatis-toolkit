package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.simple.TemplateFilter;
import io.github.nichetoolkit.mybatis.simple.TemplateKey;
import io.github.nichetoolkit.mybatis.simple.TemplateModel;
import io.github.nichetoolkit.rice.service.FilterService;
import io.github.nichetoolkit.rice.service.extend.SingleService;

/**
 * <p>TemplateService</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface TemplateService extends FilterService<String, TemplateKey, TemplateModel, TemplateFilter>, SingleService<String, TemplateKey, TemplateModel> {
}
