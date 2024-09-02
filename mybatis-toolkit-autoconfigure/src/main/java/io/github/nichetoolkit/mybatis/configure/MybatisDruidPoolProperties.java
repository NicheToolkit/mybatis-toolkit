package io.github.nichetoolkit.mybatis.configure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <code>MybatisDruidPoolProperties</code>
 * <p>The type mybatis druid pool properties class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.Setter
 * @see lombok.Getter
 * @see org.springframework.boot.context.properties.ConfigurationProperties
 * @since Jdk1.8
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class MybatisDruidPoolProperties {
    private Integer initialSize = 5;
    private Integer minIdle = 10;
    private Integer maxActive = 20;
    private Integer maxWait = 60000;
    private Integer timeBetweenEvictionRunsMillis = 60000;
    private Integer minEvictableIdleTimeMillis = 300000;
    private Integer maxEvictableIdleTimeMillis = 900000;
    private String validationQuery = "SELECT 1";
    private Boolean testWhileIdle = true;
    private Boolean testOnBorrow = false;
    private Boolean testOnReturn = false;

}
