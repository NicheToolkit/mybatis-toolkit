package io.github.nichetoolkit.mybatis.datasource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.rest.RestKey;

import java.util.Optional;

/**
 * <p>ConnectionPoolType</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public enum ConnectionPoolType implements RestKey<String> {
    /** 默认Hikari 数据源 */
    HIKARI("hikari"),
    /** 阿里Druid 数据源 */
    DRUID("druid"),
    /** 自定义数据源 JDBC Sharding 实现等*/
    CUSTOM("custom"),
    ;
    private final String key;
    
    ConnectionPoolType(String key) {
        this.key = key;
    }

    @JsonValue
    @Override
    public String getKey() {
        return this.key;
    }
    
    @JsonCreator
    public static ConnectionPoolType parseKey(String key) {
        ConnectionPoolType datasourceType = RestKey.parseKey(ConnectionPoolType.class, key);
        return Optional.ofNullable(datasourceType).orElse(ConnectionPoolType.HIKARI);
    }

}
