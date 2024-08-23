package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.simple.SimpleEntity;
import io.github.nichetoolkit.mybatis.stereotype.RestMapper;

/**
 * <p>SimpleMapper</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@RestMapper
public interface SimpleMapper extends MybatisInfoMapper<SimpleEntity,String>, MybatisRemoveMapper<String> {
}
