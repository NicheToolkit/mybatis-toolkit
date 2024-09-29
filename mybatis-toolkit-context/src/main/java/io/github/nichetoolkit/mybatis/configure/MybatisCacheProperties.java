package io.github.nichetoolkit.mybatis.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "nichetoolkit.mybatis.cache")
public class MybatisCacheProperties {
    private Integer initSize = 1024;
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
