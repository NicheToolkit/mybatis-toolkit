package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.*;
import io.github.nichetoolkit.mybatis.test.simple.SimpleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <code>SimpleMapper</code>
 * <p>The simple mapper interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisInfoMapper
 * @see io.github.nichetoolkit.mybatis.MybatisFindLinkMapper
 * @see io.github.nichetoolkit.mybatis.MybatisAlertLinkMapper
 * @see io.github.nichetoolkit.mybatis.MybatisDeleteLinkMapper
 * @see io.github.nichetoolkit.mybatis.MybatisRemoveLinkMapper
 * @see io.github.nichetoolkit.mybatis.MybatisOperateLinkMapper
 * @see org.apache.ibatis.annotations.Mapper
 * @since Jdk17
 */
@Mapper
public interface SimpleMapper extends MybatisInfoMapper<SimpleEntity, String>,
        MybatisFindLinkMapper<SimpleEntity, String, String>,
        MybatisAlertLinkMapper<SimpleEntity, String, Integer, String>,
        MybatisDeleteLinkMapper<SimpleEntity, String, String>,
        MybatisRemoveLinkMapper<SimpleEntity, String, String>,
        MybatisOperateLinkMapper<SimpleEntity, String, String> {
}
