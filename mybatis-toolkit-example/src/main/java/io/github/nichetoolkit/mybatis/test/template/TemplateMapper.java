package io.github.nichetoolkit.mybatis.test.template;

import io.github.nichetoolkit.mybatis.*;
import org.apache.ibatis.annotations.Mapper;

/**
 * <code>TemplateMapper</code>
 * <p>The template mapper interface.</p>
 * @see  io.github.nichetoolkit.mybatis.MybatisInfoMapper
 * @see  io.github.nichetoolkit.mybatis.MybatisAlertLinkMapper
 * @see  io.github.nichetoolkit.mybatis.MybatisDeleteLinkMapper
 * @see  io.github.nichetoolkit.mybatis.MybatisRemoveLinkMapper
 * @see  io.github.nichetoolkit.mybatis.MybatisOperateLinkMapper
 * @see  org.apache.ibatis.annotations.Mapper
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Mapper
public interface TemplateMapper extends MybatisInfoMapper<TemplateEntity, TemplateIdentity>,
        MybatisFindLinkMapper<TemplateEntity, TemplateLinkage, TemplateIdentity>,
        MybatisAlertLinkMapper<TemplateEntity, TemplateLinkage, TemplateAlertness, TemplateIdentity>,
        MybatisDeleteLinkMapper<TemplateEntity, TemplateLinkage, TemplateIdentity>,
        MybatisRemoveLinkMapper<TemplateEntity, TemplateLinkage, TemplateIdentity>,
        MybatisOperateLinkMapper<TemplateEntity, TemplateLinkage, TemplateIdentity> {
}
