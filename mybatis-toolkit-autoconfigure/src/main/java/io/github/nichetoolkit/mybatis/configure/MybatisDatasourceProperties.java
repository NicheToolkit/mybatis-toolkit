package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.datasource.DatasourceType;
import io.github.nichetoolkit.mybatis.driver.DatabaseType;
import io.github.nichetoolkit.rest.http.config.ProxyConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * <p>MybatisDatasourceProperties</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Component
@ConfigurationProperties(prefix = "nichetoolkit.mybatis.datasource")
public class MybatisDatasourceProperties {
    /** 是否开启自动注入 */
    private boolean enabled = true;
    /** 数据源类型 */
    private DatasourceType type = DatasourceType.DEFAULT;
    /** 动态数据源Druid连接池配置 */
    @NestedConfigurationProperty
    private MybatisDruidPoolProperties druid = new MybatisDruidPoolProperties();

    public MybatisDatasourceProperties() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public DatasourceType getType() {
        return type;
    }

    public void setType(DatasourceType type) {
        this.type = type;
    }

    public MybatisDruidPoolProperties getDruid() {
        return druid;
    }

    public void setDruid(MybatisDruidPoolProperties druid) {
        this.druid = druid;
    }
}
