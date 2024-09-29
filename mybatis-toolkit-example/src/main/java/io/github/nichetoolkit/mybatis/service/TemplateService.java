package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.simple.TemplateFilter;
import io.github.nichetoolkit.mybatis.simple.TemplateIdentity;
import io.github.nichetoolkit.mybatis.simple.TemplateModel;
import io.github.nichetoolkit.rice.service.FilterService;
import io.github.nichetoolkit.rice.service.SingleService;
import io.github.nichetoolkit.rice.service.extend.AlertFieldService;

public interface TemplateService extends FilterService<TemplateModel, TemplateFilter, TemplateIdentity, String>, AlertFieldService<TemplateIdentity>, SingleService<TemplateModel, TemplateIdentity, String> {
}
