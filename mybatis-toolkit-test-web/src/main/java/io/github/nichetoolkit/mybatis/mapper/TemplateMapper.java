package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.simple.TemplateEntity;
import io.github.nichetoolkit.mybatis.simple.TemplateKey;
import io.github.nichetoolkit.mybatis.stereotype.RestMapper;

/**
 * <p>TemplateMapper</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@RestMapper
public interface TemplateMapper extends MybatisInfoMapper<TemplateEntity, TemplateKey>, MybatisRemoveMapper<TemplateKey> {
}
