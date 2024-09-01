package io.github.nichetoolkit.mybatis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.mybatis.consts.StyleConstants;
import io.github.nichetoolkit.rest.RestKey;

import java.util.Optional;

/**
 * <code>StyleType</code>
 * <p>The type style type enumeration.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestKey
 * @since Jdk1.8
 */
public enum StyleType implements RestKey<String> {
    /**
     * <code>NORMAL</code>
     * <p>the Normal style type field.</p>
     */
    NORMAL(StyleConstants.NORMAL),
    /**
     * <code>LOWER_UNDERLINE</code>
     * <p>the Lower underline style type field.</p>
     */
    LOWER_UNDERLINE(StyleConstants.LOWER_UNDERLINE),
    /**
     * <code>LOWER</code>
     * <p>the Lower style type field.</p>
     */
    LOWER(StyleConstants.LOWER),
    /**
     * <code>UPPER</code>
     * <p>the Upper style type field.</p>
     */
    UPPER(StyleConstants.UPPER),
    /**
     * <code>UPPER_UNDERLINE</code>
     * <p>the Upper underline style type field.</p>
     */
    UPPER_UNDERLINE(StyleConstants.UPPER_UNDERLINE),
    ;

    private final String key;

    StyleType(String key) {
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
     * @return {@link io.github.nichetoolkit.mybatis.enums.StyleType} <p>the key return object is <code>StyleType</code> type.</p>
     * @see java.lang.String
     * @see com.fasterxml.jackson.annotation.JsonCreator
     */
    @JsonCreator
    public static StyleType parseKey(String key) {
        StyleType typeEnum = RestKey.parseKey(StyleType.class, key);
        return Optional.ofNullable(typeEnum).orElse(StyleType.NORMAL);
    }

}
