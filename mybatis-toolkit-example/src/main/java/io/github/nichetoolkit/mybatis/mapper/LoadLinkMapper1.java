package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.*;
import io.github.nichetoolkit.mybatis.test.load.LoadLinkEntity1;
import org.apache.ibatis.annotations.Mapper;

/**
 * <code>LoadLinkMapper1</code>
 * <p>The load link mapper 1 interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisInfoMapper
 * @see io.github.nichetoolkit.mybatis.MybatisFindLinkMapper
 * @see io.github.nichetoolkit.mybatis.MybatisDeleteLinkMapper
 * @see io.github.nichetoolkit.mybatis.MybatisRemoveLinkMapper
 * @see io.github.nichetoolkit.mybatis.MybatisOperateLinkMapper
 * @see org.apache.ibatis.annotations.Mapper
 * @since Jdk1.8
 */
@Mapper
public interface LoadLinkMapper1 extends MybatisInfoMapper<LoadLinkEntity1, String>,
        MybatisFindLinkMapper<LoadLinkEntity1, String, String>,
        MybatisDeleteLinkMapper<LoadLinkEntity1, String, String>,
        MybatisRemoveLinkMapper<LoadLinkEntity1, String, String>,
        MybatisOperateLinkMapper<LoadLinkEntity1, String, String> {
}
