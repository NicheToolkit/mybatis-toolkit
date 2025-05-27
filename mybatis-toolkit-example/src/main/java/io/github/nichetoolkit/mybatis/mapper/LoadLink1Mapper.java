package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.*;
import io.github.nichetoolkit.mybatis.test.load.LoadLink1Entity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <code>LoadLink1Mapper</code>
 * <p>The load link 1 mapper interface.</p>
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
public interface LoadLink1Mapper extends MybatisInfoMapper<LoadLink1Entity, String>,
        MybatisFindLinkMapper<LoadLink1Entity, String, String>,
        MybatisDeleteLinkMapper<LoadLink1Entity, String, String>,
        MybatisRemoveLinkMapper<LoadLink1Entity, String, String>,
        MybatisOperateLinkMapper<LoadLink1Entity, String, String> {
}
