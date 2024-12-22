package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.*;
import io.github.nichetoolkit.mybatis.simple.CommonEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <code>CommonMapper</code>
 * <p>The common mapper interface.</p>
 * @see  io.github.nichetoolkit.mybatis.MybatisInfoMapper
 * @see  org.apache.ibatis.annotations.Mapper
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Mapper
public interface CommonMapper extends MybatisInfoMapper<CommonEntity, String> {
}
