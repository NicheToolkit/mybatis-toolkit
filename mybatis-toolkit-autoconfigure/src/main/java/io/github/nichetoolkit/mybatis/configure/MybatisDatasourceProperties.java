package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.datasource.ConnectionPoolType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>MybatisDatasourceProperties</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "nichetoolkit.mybatis.datasource")
public class MybatisDatasourceProperties {
    /** 是否开启自动注入 */
    private boolean enabled = true;
    /** 数据源类型 */
    private ConnectionPoolType type = ConnectionPoolType.HIKARI;

    public MybatisDatasourceProperties() {
    }

}
