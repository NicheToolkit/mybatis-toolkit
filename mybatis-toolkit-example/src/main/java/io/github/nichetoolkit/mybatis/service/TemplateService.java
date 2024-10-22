package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.simple.TemplateFilter;
import io.github.nichetoolkit.mybatis.simple.TemplateIdentity;
import io.github.nichetoolkit.mybatis.simple.TemplateModel;
import io.github.nichetoolkit.rice.service.FilterService;
import io.github.nichetoolkit.rice.service.OperateService;
import io.github.nichetoolkit.rice.service.RemoveLinkService;
import io.github.nichetoolkit.rice.service.SingleService;

public interface TemplateService extends FilterService<TemplateModel, TemplateFilter, TemplateIdentity, String>, SingleService<TemplateModel, TemplateIdentity, String>, RemoveLinkService<TemplateIdentity, String>, OperateService<TemplateIdentity, String> {
}
