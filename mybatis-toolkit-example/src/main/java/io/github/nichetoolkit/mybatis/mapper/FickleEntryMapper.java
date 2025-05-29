package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.MybatisInfoMapper;
import io.github.nichetoolkit.mybatis.natives.MybatisFindFickleMapper;
import io.github.nichetoolkit.mybatis.test.fickle.FickleEntryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <code>FickleEntryMapper</code>
 * <p>The fickle entry mapper interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisInfoMapper
 * @see io.github.nichetoolkit.mybatis.natives.MybatisFindFickleMapper
 * @see org.apache.ibatis.annotations.Mapper
 * @since Jdk1.8
 */
@Mapper
public interface FickleEntryMapper extends MybatisInfoMapper<FickleEntryEntity, String>, MybatisFindFickleMapper<FickleEntryEntity,String> {
}
