package io.github.nichetoolkit.mybatis.datasource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.rest.RestKey;

import java.util.Optional;

/**
 * <code>DruidRoutingType</code>
 * <p>The type druid routing type enumeration.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestKey
 * @since Jdk1.8
 */
public enum DruidRoutingType implements RestKey<String> {
    /**
     * <code>DEFAULT</code>
     * <p>the Default druid routing type field.</p>
     */
    DEFAULT("default"),
    /**
     * <code>MASTER</code>
     * <p>the Master druid routing type field.</p>
     */
    MASTER("master"),
    /**
     * <code>SLAVE</code>
     * <p>the Slave druid routing type field.</p>
     */
    SLAVE("slave"),
    ;
    /**
     * <code>key</code>
     * {@link java.lang.String} <p>the <code>key</code> field.</p>
     * @see java.lang.String
     */
    private final String key;

    /**
     * <code>DruidRoutingType</code>
     * Instantiates a new druid routing type.
     * @param key {@link java.lang.String} <p>the key parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    DruidRoutingType(String key) {
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
     * @return {@link io.github.nichetoolkit.mybatis.datasource.DruidRoutingType} <p>the key return object is <code>DruidRoutingType</code> type.</p>
     * @see java.lang.String
     * @see com.fasterxml.jackson.annotation.JsonCreator
     */
    @JsonCreator
    public static DruidRoutingType parseKey(String key) {
        DruidRoutingType routingType = RestKey.parseKey(DruidRoutingType.class, key);
        return Optional.ofNullable(routingType).orElse(DruidRoutingType.DEFAULT);
    }

}
