package io.github.nichetoolkit.mybatis.configure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Setter
@Getter
@ConfigurationProperties(prefix = "nichetoolkit.mybatis.record")
public class MybatisRecordProperties {
    private boolean enabled = false;
}
