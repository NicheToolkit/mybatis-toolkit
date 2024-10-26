package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.*;
import io.github.nichetoolkit.mybatis.simple.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TemplateMapper extends MybatisInfoMapper<TemplateEntity, TemplateIdentity>,
        MybatisAlertLinkMapper<TemplateEntity, TemplateLinkage, TemplateAlertness, TemplateIdentity>,
        MybatisDeleteLinkMapper<TemplateEntity, TemplateLinkage, TemplateIdentity>,
        MybatisRemoveLinkMapper<TemplateEntity, TemplateLinkage, TemplateIdentity>,
        MybatisOperateLinkMapper<TemplateEntity, TemplateLinkage, TemplateIdentity> {
}
