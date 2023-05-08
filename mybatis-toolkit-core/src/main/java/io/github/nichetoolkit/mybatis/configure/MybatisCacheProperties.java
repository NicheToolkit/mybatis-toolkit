package io.github.nichetoolkit.mybatis.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>MybatisCacheProperties</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Component
@ConfigurationProperties(prefix = "nichetoolkit.mybatis.cache")
public class MybatisCacheProperties {
    /** 默认缓存大小 */
    private Integer initSize = 1024;
    /** 默认缓存使用一次 */
    private boolean useOnce = false;

    public MybatisCacheProperties() {
    }

    public Integer getInitSize() {
        return initSize;
    }

    public void setInitSize(Integer initSize) {
        this.initSize = initSize;
    }

    public boolean isUseOnce() {
        return useOnce;
    }

    public void setUseOnce(boolean useOnce) {
        this.useOnce = useOnce;
    }
}
