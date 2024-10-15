package io.github.nichetoolkit.mybatis.configure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "nichetoolkit.mybatis.cache")
public class MybatisCacheProperties {
    private Integer initSize = 1024;
    private boolean useOnce = false;
}
