package io.github.nichetoolkit.mybatis.configure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <code>MybatisCacheProperties</code>
 * <p>The mybatis cache properties class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.Getter
 * @see lombok.Setter
 * @see org.springframework.stereotype.Component
 * @see org.springframework.boot.context.properties.ConfigurationProperties
 * @since Jdk1.8
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "nichetoolkit.mybatis.cache")
public class MybatisCacheProperties {
    /**
     * <code>initSize</code>
     * {@link java.lang.Integer} <p>The <code>initSize</code> field.</p>
     * @see java.lang.Integer
     */
    private Integer initSize = 1024;
    /**
     * <code>useOnce</code>
     * <p>The <code>useOnce</code> field.</p>
     */
    private boolean useOnce = false;
}
