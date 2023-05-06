package io.github.nichetoolkit.mybatis.mapper;

import io.github.nichetoolkit.mybatis.simple.SimpleEntity;
import io.github.nichetoolkit.rice.RiceInfoMapper;
import org.springframework.stereotype.Component;

/**
 * <p>SimpleMapper</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Component
public interface SimpleMapper extends RiceInfoMapper<SimpleEntity>, MybatisMapper<SimpleEntity,String> {
}
