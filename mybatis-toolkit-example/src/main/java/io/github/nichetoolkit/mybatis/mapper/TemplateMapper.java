package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisInfoMapper;
import io.github.nichetoolkit.mybatis.MybatisRemoveMapper;
import io.github.nichetoolkit.mybatis.simple.SimpleEntity;
import io.github.nichetoolkit.mybatis.simple.TemplateEntity;
import io.github.nichetoolkit.mybatis.simple.TemplateKey;
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
@RestMapper(entityType = TemplateEntity.class, identityKeyType = TemplateKey.class)
public interface TemplateMapper extends MybatisInfoMapper<TemplateEntity, TemplateKey>, MybatisRemoveMapper<TemplateKey> {
}
