package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisInfoMapper;
import io.github.nichetoolkit.mybatis.natives.MybatisFindFickleMapper;
import io.github.nichetoolkit.mybatis.simple.FickleEntryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <code>FickleEntryMapper</code>
 * <p>The fickle entry mapper interface.</p>
 * @see  io.github.nichetoolkit.mybatis.MybatisInfoMapper
 * @see  org.apache.ibatis.annotations.Mapper
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Mapper
public interface FickleEntryMapper extends MybatisInfoMapper<FickleEntryEntity, String>, MybatisFindFickleMapper<FickleEntryEntity,String> {
}
