package io.github.nichetoolkit.mybatis.configure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * <code>MybatisRecordProperties</code>
 * <p>The mybatis record properties class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.Setter
 * @see lombok.Getter
 * @see org.springframework.boot.context.properties.ConfigurationProperties
 * @since Jdk1.8
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "nichetoolkit.mybatis.record")
public class MybatisRecordProperties {
    /**
     * <code>enabled</code>
     * <p>The <code>enabled</code> field.</p>
     */
    private boolean enabled = false;
}
