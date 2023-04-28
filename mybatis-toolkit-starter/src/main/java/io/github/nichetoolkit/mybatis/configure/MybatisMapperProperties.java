package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.enums.StyleType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>MybatisMapperProperties</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Component
@ConfigurationProperties(prefix = "nichetoolkit.mybatis.mapper")
public class MybatisMapperProperties {
    private Boolean enabled = true;
    /** 默认样式 */
    private StyleType styleType = StyleType.LOWER_UNDERLINE;

    public MybatisMapperProperties() {
    }

    public StyleType getStyleType() {
        return styleType;
    }

    public void setStyleType(StyleType styleType) {
        this.styleType = styleType;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
