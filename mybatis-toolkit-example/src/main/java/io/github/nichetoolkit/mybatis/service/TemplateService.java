package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.simple.TemplateFilter;
import io.github.nichetoolkit.mybatis.simple.TemplateIdentity;
import io.github.nichetoolkit.mybatis.simple.TemplateModel;
import io.github.nichetoolkit.rice.service.*;

public interface TemplateService extends FilterService<TemplateModel, TemplateFilter, TemplateIdentity, String>, SingleService<TemplateModel, TemplateIdentity, String>,
        AlertLinkService<TemplateIdentity, String>, DeleteLinkService<TemplateIdentity, String>, RemoveLinkService<TemplateIdentity, String>, OperateLinkService<TemplateIdentity, String> {
}
