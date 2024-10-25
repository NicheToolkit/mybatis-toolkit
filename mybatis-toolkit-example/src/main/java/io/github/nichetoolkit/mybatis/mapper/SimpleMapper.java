package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.simple.SimpleEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SimpleMapper extends MybatisInfoMapper<SimpleEntity, String>,
        MybatisDeleteLinkMapper<SimpleEntity, String, String>,
        MybatisRemoveLinkMapper<SimpleEntity, String, String>,
        MybatisOperateLinkMapper<SimpleEntity, String, String> {
}
