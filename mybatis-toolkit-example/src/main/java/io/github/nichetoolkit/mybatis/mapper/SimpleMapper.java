package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.simple.SimpleEntity;
import io.github.nichetoolkit.mybatis.stereotype.RestMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SimpleMapper extends MybatisInfoMapper<SimpleEntity, String>, MybatisRemoveMapper<String> {
}
