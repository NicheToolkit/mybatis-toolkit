package io.github.nichetoolkit.mybatis.datasource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.rest.RestKey;

import java.util.Optional;

/**
 * <code>ConnectionPoolType</code>
 * <p>The type connection pool type enumeration.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestKey
 * @since Jdk1.8
 */
public enum ConnectionPoolType implements RestKey<String> {
    /**
     * <code>HIKARI</code>
     * <p>the Hikari connection pool type field.</p>
     */
    HIKARI("hikari"),
    /**
     * <code>DRUID</code>
     * <p>the Druid connection pool type field.</p>
     */
    DRUID("druid"),
    /**
     * <code>CUSTOM</code>
     * <p>the Custom connection pool type field.</p>
     */
    CUSTOM("custom"),
    ;
    /**
     * <code>key</code>
     * {@link java.lang.String} <p>the <code>key</code> field.</p>
     * @see java.lang.String
     */
    private final String key;

    /**
     * <code>ConnectionPoolType</code>
     * Instantiates a new connection pool type.
     * @param key {@link java.lang.String} <p>the key parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    ConnectionPoolType(String key) {
        this.key = key;
    }

    @JsonValue
    @Override
    public String getKey() {
        return this.key;
    }

    /**
     * <code>parseKey</code>
     * <p>the key method.</p>
     * @param key {@link java.lang.String} <p>the key parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.datasource.ConnectionPoolType} <p>the key return object is <code>ConnectionPoolType</code> type.</p>
     * @see java.lang.String
     * @see com.fasterxml.jackson.annotation.JsonCreator
     */
    @JsonCreator
    public static ConnectionPoolType parseKey(String key) {
        ConnectionPoolType datasourceType = RestKey.parseKey(ConnectionPoolType.class, key);
        return Optional.ofNullable(datasourceType).orElse(ConnectionPoolType.HIKARI);
    }

}
