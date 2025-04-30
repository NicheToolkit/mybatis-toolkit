package io.github.nichetoolkit.mybatis.test.simple;

import io.github.nichetoolkit.mybatis.*;
import org.apache.ibatis.annotations.Mapper;

/**
 * <code>SimpleMapper</code>
 * <p>The simple mapper interface.</p>
 * @see  io.github.nichetoolkit.mybatis.MybatisInfoMapper
 * @see  io.github.nichetoolkit.mybatis.MybatisAlertLinkMapper
 * @see  io.github.nichetoolkit.mybatis.MybatisDeleteLinkMapper
 * @see  io.github.nichetoolkit.mybatis.MybatisRemoveLinkMapper
 * @see  io.github.nichetoolkit.mybatis.MybatisOperateLinkMapper
 * @see  org.apache.ibatis.annotations.Mapper
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Mapper
public interface SimpleMapper extends MybatisInfoMapper<SimpleEntity, String>,
        MybatisFindLinkMapper<SimpleEntity, String, String>,
        MybatisAlertLinkMapper<SimpleEntity, String, Integer, String>,
        MybatisDeleteLinkMapper<SimpleEntity, String, String>,
        MybatisRemoveLinkMapper<SimpleEntity, String, String>,
        MybatisOperateLinkMapper<SimpleEntity, String, String> {
}
