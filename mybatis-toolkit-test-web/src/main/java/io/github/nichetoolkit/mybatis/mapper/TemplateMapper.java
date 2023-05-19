package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.simple.TemplateEntity;
import io.github.nichetoolkit.mybatis.simple.TemplateKey;
import org.springframework.stereotype.Component;

/**
 * <p>TemplateMapper</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Component
public interface TemplateMapper extends MybatisInfoMapper<TemplateEntity, TemplateKey>, MybatisRemoveMapper<TemplateKey> {
}
