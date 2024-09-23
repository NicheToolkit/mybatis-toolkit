package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.simple.TemplateEntity;
import io.github.nichetoolkit.mybatis.simple.TemplateIdentity;
import io.github.nichetoolkit.mybatis.stereotype.RestMapper;

/**
 * <code>TemplateMapper</code>
 * <p>The type template mapper interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see MybatisInfoMapper
 * @see MybatisRemoveMapper
 * @see io.github.nichetoolkit.mybatis.stereotype.RestMapper
 * @since Jdk1.8
 */
@RestMapper
public interface TemplateMapper extends MybatisInfoMapper<TemplateEntity, TemplateIdentity>, MybatisRemoveMapper<TemplateIdentity> {
}
