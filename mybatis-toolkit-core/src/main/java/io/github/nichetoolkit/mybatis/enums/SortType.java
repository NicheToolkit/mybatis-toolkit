package io.github.nichetoolkit.mybatis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.rest.RestValue;

import java.util.Optional;

/**
 * <code>SortType</code>
 * <p>The type sort type enumeration.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestValue
 * @since Jdk1.8
 */
public enum SortType implements RestValue<String,String> {
    /**
     * <code>NONE</code>
     * <p>the None sort type field.</p>
     */
    NONE("",""),
    /**
     * <code>ASC</code>
     * <p>the Asc sort type field.</p>
     */
    ASC("ASC","升序"),
    /**
     * <code>DESC</code>
     * <p>the Desc sort type field.</p>
     */
    DESC("DESC","降序")
    ;

    /**
     * <code>key</code>
     * {@link java.lang.String} <p>the <code>key</code> field.</p>
     * @see java.lang.String
     */
    private final String key;
    /**
     * <code>value</code>
     * {@link java.lang.String} <p>the <code>value</code> field.</p>
     * @see java.lang.String
     */
    private final String value;

    /**
     * <code>SortType</code>
     * Instantiates a new sort type.
     * @param key   {@link java.lang.String} <p>the key parameter is <code>String</code> type.</p>
     * @param value {@link java.lang.String} <p>the value parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    SortType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @JsonValue
    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * <code>parseKey</code>
     * <p>the key method.</p>
     * @param key {@link java.lang.String} <p>the key parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.enums.SortType} <p>the key return object is <code>SortType</code> type.</p>
     * @see java.lang.String
     * @see com.fasterxml.jackson.annotation.JsonCreator
     */
    @JsonCreator
    public static SortType parseKey(String key) {
        SortType typeEnum = RestValue.parseKey(SortType.class, key);
        return Optional.ofNullable(typeEnum).orElse(SortType.DESC);
    }

    /**
     * <code>parseValue</code>
     * <p>the value method.</p>
     * @param value {@link java.lang.String} <p>the value parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.enums.SortType} <p>the value return object is <code>SortType</code> type.</p>
     * @see java.lang.String
     */
    public static SortType parseValue(String value) {
        SortType typeEnum = RestValue.parseValue(SortType.class, value);
        return Optional.ofNullable(typeEnum).orElse(SortType.DESC);
    }


}
