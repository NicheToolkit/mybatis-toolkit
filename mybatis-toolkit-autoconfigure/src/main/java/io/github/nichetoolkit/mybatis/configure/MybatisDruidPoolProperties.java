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
    /**
     * <code>initialSize</code>
     * {@link java.lang.Integer} <p>the <code>initialSize</code> field.</p>
     * @see java.lang.Integer
     */
    private Integer initialSize = 5;
    /**
     * <code>minIdle</code>
     * {@link java.lang.Integer} <p>the <code>minIdle</code> field.</p>
     * @see java.lang.Integer
     */
    private Integer minIdle = 10;
    /**
     * <code>maxActive</code>
     * {@link java.lang.Integer} <p>the <code>maxActive</code> field.</p>
     * @see java.lang.Integer
     */
    private Integer maxActive = 20;
    /**
     * <code>maxWait</code>
     * {@link java.lang.Integer} <p>the <code>maxWait</code> field.</p>
     * @see java.lang.Integer
     */
    private Integer maxWait = 60000;
    /**
     * <code>timeBetweenEvictionRunsMillis</code>
     * {@link java.lang.Integer} <p>the <code>timeBetweenEvictionRunsMillis</code> field.</p>
     * @see java.lang.Integer
     */
    private Integer timeBetweenEvictionRunsMillis = 60000;
    /**
     * <code>minEvictableIdleTimeMillis</code>
     * {@link java.lang.Integer} <p>the <code>minEvictableIdleTimeMillis</code> field.</p>
     * @see java.lang.Integer
     */
    private Integer minEvictableIdleTimeMillis = 300000;
    /**
     * <code>maxEvictableIdleTimeMillis</code>
     * {@link java.lang.Integer} <p>the <code>maxEvictableIdleTimeMillis</code> field.</p>
     * @see java.lang.Integer
     */
    private Integer maxEvictableIdleTimeMillis = 900000;
    /**
     * <code>validationQuery</code>
     * {@link java.lang.String} <p>the <code>validationQuery</code> field.</p>
     * @see java.lang.String
     */
    private String validationQuery = "SELECT 1";
    /**
     * <code>testWhileIdle</code>
     * {@link java.lang.Boolean} <p>the <code>testWhileIdle</code> field.</p>
     * @see java.lang.Boolean
     */
    private Boolean testWhileIdle = true;
    /**
     * <code>testOnBorrow</code>
     * {@link java.lang.Boolean} <p>the <code>testOnBorrow</code> field.</p>
     * @see java.lang.Boolean
     */
    private Boolean testOnBorrow = false;
    /**
     * <code>testOnReturn</code>
     * {@link java.lang.Boolean} <p>the <code>testOnReturn</code> field.</p>
     * @see java.lang.Boolean
     */
    private Boolean testOnReturn = false;

}
