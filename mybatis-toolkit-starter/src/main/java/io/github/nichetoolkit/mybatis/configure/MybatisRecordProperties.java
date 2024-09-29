package io.github.nichetoolkit.mybatis.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "nichetoolkit.mybatis.record")
public class MybatisRecordProperties {
    private boolean enabled = false;
}
