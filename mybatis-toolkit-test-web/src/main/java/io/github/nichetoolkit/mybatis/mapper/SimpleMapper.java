package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.simple.SimpleEntity;
import io.github.nichetoolkit.mybatis.stereotype.RestMapper;

/**
 * <code>SimpleMapper</code>
 * <p>The type simple mapper interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.mapper.MybatisInfoMapper
 * @see io.github.nichetoolkit.mybatis.mapper.MybatisRemoveMapper
 * @see io.github.nichetoolkit.mybatis.stereotype.RestMapper
 * @since Jdk1.8
 */
@RestMapper
public interface SimpleMapper extends MybatisInfoMapper<SimpleEntity,String>, MybatisRemoveMapper<String> {
}
