package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.*;
import io.github.nichetoolkit.mybatis.test.load.LoadLinkEntity2;
import org.apache.ibatis.annotations.Mapper;

/**
 * <code>LoadLinkMapper2</code>
 * <p>The load link mapper 2 interface.</p>
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
public interface LoadLinkMapper2 extends MybatisInfoMapper<LoadLinkEntity2, String>,
        MybatisFindLinkMapper<LoadLinkEntity2, String, String>,
        MybatisDeleteLinkMapper<LoadLinkEntity2, String, String>,
        MybatisRemoveLinkMapper<LoadLinkEntity2, String, String>,
        MybatisOperateLinkMapper<LoadLinkEntity2, String, String> {
}
