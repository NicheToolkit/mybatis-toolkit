package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisInfoMapper;
import io.github.nichetoolkit.mybatis.MybatisRemoveMapper;
import io.github.nichetoolkit.mybatis.simple.SimpleEntity;
import io.github.nichetoolkit.mybatis.stereotype.RestMapper;

/**
 * <code>SimpleMapper</code>
 * <p>The type simple mapper interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see MybatisInfoMapper
 * @see MybatisRemoveMapper
 * @see io.github.nichetoolkit.mybatis.stereotype.RestMapper
 * @since Jdk1.8
 */
@RestMapper(entityType = SimpleEntity.class)
public interface SimpleMapper extends MybatisInfoMapper<SimpleEntity,String>, MybatisRemoveMapper<String> {
}
