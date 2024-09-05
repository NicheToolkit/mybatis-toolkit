package io.github.nichetoolkit.mybatis.configure;

import io.github.nichetoolkit.mybatis.datasource.ConnectionPoolType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <code>MybatisDatasourceProperties</code>
 * <p>The type mybatis datasource properties class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.Setter
 * @see lombok.Getter
 * @see org.springframework.boot.context.properties.ConfigurationProperties
 * @since Jdk1.8
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "nichetoolkit.mybatis.datasource")
public class MybatisDatasourceProperties {
    /**
     * <code>enabled</code>
     * <p>the <code>enabled</code> field.</p>
     */
    private boolean enabled = true;
    /**
     * <code>type</code>
     * {@link io.github.nichetoolkit.mybatis.datasource.ConnectionPoolType} <p>the <code>type</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.datasource.ConnectionPoolType
     */
    private ConnectionPoolType type = ConnectionPoolType.HIKARI;

}
