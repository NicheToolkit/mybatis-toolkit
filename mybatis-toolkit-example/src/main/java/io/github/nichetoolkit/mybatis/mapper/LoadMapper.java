package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.*;
import io.github.nichetoolkit.mybatis.natives.MybatisFindLoadMapper;
import io.github.nichetoolkit.mybatis.test.load.LoadEntity;
import io.github.nichetoolkit.mybatis.test.load.LoadIdentity;
import io.github.nichetoolkit.mybatis.test.load.LoadLinkage;
import org.apache.ibatis.annotations.Mapper;

/**
 * <code>LoadMapper</code>
 * <p>The load mapper interface.</p>
 * @see  MybatisInfoMapper
 * @see  MybatisAlertLinkMapper
 * @see  MybatisDeleteLinkMapper
 * @see  MybatisRemoveLinkMapper
 * @see  MybatisOperateLinkMapper
 * @see  Mapper
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Mapper
public interface LoadMapper extends MybatisInfoMapper<LoadEntity, LoadIdentity>,
        MybatisFindLinkMapper<LoadEntity, LoadLinkage, LoadIdentity>,
        MybatisDeleteLinkMapper<LoadEntity, LoadLinkage, LoadIdentity>,
        MybatisRemoveLinkMapper<LoadEntity, LoadLinkage, LoadIdentity>,
        MybatisOperateLinkMapper<LoadEntity, LoadLinkage, LoadIdentity>,
        MybatisFindLoadMapper<LoadEntity, LoadIdentity> {
}
