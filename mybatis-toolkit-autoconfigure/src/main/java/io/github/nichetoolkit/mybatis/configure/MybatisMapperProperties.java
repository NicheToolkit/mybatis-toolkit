package io.github.nichetoolkit.mybatis.configure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * <p>MybatisMapperProperties</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "nichetoolkit.mybatis.mapper")
public class MybatisMapperProperties {
    /** 是否开启自动注入 */
    private boolean enabled = true;

}
