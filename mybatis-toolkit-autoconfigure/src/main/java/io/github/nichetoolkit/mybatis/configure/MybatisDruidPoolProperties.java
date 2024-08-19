package io.github.nichetoolkit.mybatis.configure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>MybatisDruidPoolProperties</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class MybatisDruidPoolProperties {
    /** 数据库连接池初始值 */
    private Integer initialSize = 5;
    /** 数据库连接池最小空闲值 */
    private Integer minIdle = 10;
    /** 数据库连接池最大值 */
    private Integer maxActive = 20;
    /** 获取连接时最大等待时间，单位毫秒(1分钟) */
    private Integer maxWait = 60000;
    /** 空闲连接检查、废弃连接清理、空闲连接池大小调整的操作时间间隔，单位是毫秒(1分钟)*/
    private Integer timeBetweenEvictionRunsMillis = 60000;
    /** 空闲连接大于minIdle且连接空闲时间大于该值，则关闭该连接，单位毫秒(5分钟，默认30分钟) */
    private Integer minEvictableIdleTimeMillis = 300000;
    /** 空闲连接最大生存时间 单位毫秒(15分钟) */
    private Integer maxEvictableIdleTimeMillis = 900000;
    /** 检测连接是否有效时执行的sql命令 */
    private String validationQuery = "SELECT 1";
    /** 连接空闲时检测，如果连接空闲时间大于timeBetweenEvictionRunsMillis指定的毫秒，执行validationQuery指定的SQL来检测连接是否有效 */
    private Boolean testWhileIdle = true;
    /** 借用连接时执行validationQuery检测连接是否有效 */
    private Boolean testOnBorrow = false;
    /** 归还连接时执行validationQuery检测连接是否有效 */
    private Boolean testOnReturn = false;

    public MybatisDruidPoolProperties() {
    }

}
