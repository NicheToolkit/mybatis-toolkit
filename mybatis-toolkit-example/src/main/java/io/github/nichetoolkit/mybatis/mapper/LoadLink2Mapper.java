package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.*;
import io.github.nichetoolkit.mybatis.test.load.LoadLink2Entity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <code>LoadLink2Mapper</code>
 * <p>The load link 2 mapper interface.</p>
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
public interface LoadLink2Mapper extends MybatisInfoMapper<LoadLink2Entity, String>,
        MybatisFindLinkMapper<LoadLink2Entity, String, String>,
        MybatisDeleteLinkMapper<LoadLink2Entity, String, String>,
        MybatisRemoveLinkMapper<LoadLink2Entity, String, String>,
        MybatisOperateLinkMapper<LoadLink2Entity, String, String> {
}
