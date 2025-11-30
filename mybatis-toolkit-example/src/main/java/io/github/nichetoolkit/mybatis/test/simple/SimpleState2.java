package io.github.nichetoolkit.mybatis.test.simple;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.rest.RestKey;
import io.github.nichetoolkit.rest.RestState;
import io.github.nichetoolkit.rest.RestValue;

import java.util.Optional;

/**
 * <code>SimpleState2</code>
 * <p>The simple state 2 enumeration.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestValue
 * @see io.github.nichetoolkit.rest.RestState
 * @since Jdk17
 */
public enum SimpleState2 implements RestValue<String,String>, RestState<String> {
    /**
     * <code>NONE</code>
     * <p>The none simple state 2 field.</p>
     */
    NONE("none","无"),
    /**
     * <code>TEST</code>
     * <p>The test simple state 2 field.</p>
     */
    TEST("test","测试"),
    ;

    /**
     * <code>key</code>
     * {@link java.lang.String} <p>The <code>key</code> field.</p>
     * @see java.lang.String
     */
    private final String key;
    /**
     * <code>value</code>
     * {@link java.lang.String} <p>The <code>value</code> field.</p>
     * @see java.lang.String
     */
    private final String value;

    /**
     * <code>SimpleState2</code>
     * <p>Instantiates a new simple state 2.</p>
     * @param key   {@link java.lang.String} <p>The key parameter is <code>String</code> type.</p>
     * @param value {@link java.lang.String} <p>The value parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    SimpleState2(String key, String value) {
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
     * <p>The parse key method.</p>
     * @param key {@link java.lang.String} <p>The key parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.test.simple.SimpleState2} <p>The parse key return object is <code>SimpleState2</code> type.</p>
     * @see java.lang.String
     * @see com.fasterxml.jackson.annotation.JsonCreator
     */
    @JsonCreator
    public static SimpleState2 parseKey(String key) {
        SimpleState2 typeEnum = RestKey.parseKey(SimpleState2.class, key);
        return Optional.ofNullable(typeEnum).orElse(SimpleState2.NONE);
    }

    /**
     * <code>parseValue</code>
     * <p>The parse value method.</p>
     * @param value {@link java.lang.String} <p>The value parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.test.simple.SimpleState2} <p>The parse value return object is <code>SimpleState2</code> type.</p>
     * @see java.lang.String
     */
    public static SimpleState2 parseValue(String value) {
        SimpleState2 typeEnum = RestValue.parseValue(SimpleState2.class, value);
        return Optional.ofNullable(typeEnum).orElse(SimpleState2.NONE);
    }
}
