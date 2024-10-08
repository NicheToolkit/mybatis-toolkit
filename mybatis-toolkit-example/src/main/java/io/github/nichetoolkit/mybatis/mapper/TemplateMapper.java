package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.simple.TemplateEntity;
import io.github.nichetoolkit.mybatis.simple.TemplateIdentity;
import io.github.nichetoolkit.mybatis.stereotype.RestMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TemplateMapper extends MybatisInfoMapper<TemplateEntity, TemplateIdentity> {
}
