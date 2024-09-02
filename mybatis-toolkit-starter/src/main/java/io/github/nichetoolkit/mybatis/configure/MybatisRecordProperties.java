package io.github.nichetoolkit.mybatis.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * <code>MybatisRecordProperties</code>
 * <p>The type mybatis record properties class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.Data
 * @see org.springframework.boot.context.properties.ConfigurationProperties
 * @since Jdk1.8
 */
@Data
@ConfigurationProperties(prefix = "nichetoolkit.mybatis.record")
public class MybatisRecordProperties {
    private boolean enabled = false;
}
