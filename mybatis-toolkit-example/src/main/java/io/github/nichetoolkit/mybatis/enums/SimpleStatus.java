package io.github.nichetoolkit.mybatis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.rest.RestValue;

import java.util.Optional;

/**
 * <code>SimpleStatus</code>
 * <p>The simple status enumeration.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestValue
 * @since Jdk1.8
 */
public enum SimpleStatus implements RestValue<Integer,String> {
    /**
     * <code>NONE</code>
     * <p>The none simple status field.</p>
     */
    NONE(1,"无"),
    /**
     * <code>TEST</code>
     * <p>The test simple status field.</p>
     */
    TEST(2,"测试"),
    ;

    /**
     * <code>key</code>
     * {@link java.lang.Integer} <p>The <code>key</code> field.</p>
     * @see java.lang.Integer
     */
    private final Integer key;
    /**
     * <code>value</code>
     * {@link java.lang.String} <p>The <code>value</code> field.</p>
     * @see java.lang.String
     */
    private final String value;

    /**
     * <code>SimpleStatus</code>
     * <p>Instantiates a new simple status.</p>
     * @param key   {@link java.lang.Integer} <p>The key parameter is <code>Integer</code> type.</p>
     * @param value {@link java.lang.String} <p>The value parameter is <code>String</code> type.</p>
     * @see java.lang.Integer
     * @see java.lang.String
     */
    SimpleStatus(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    @JsonValue
    @Override
    public Integer getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * <code>parseKey</code>
     * <p>The parse key method.</p>
     * @param key {@link java.lang.Integer} <p>The key parameter is <code>Integer</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.enums.SimpleStatus} <p>The parse key return object is <code>SimpleStatus</code> type.</p>
     * @see java.lang.Integer
     * @see com.fasterxml.jackson.annotation.JsonCreator
     */
    @JsonCreator
    public static SimpleStatus parseKey(Integer key) {
        SimpleStatus typeEnum = RestValue.parseKey(SimpleStatus.class, key);
        return Optional.ofNullable(typeEnum).orElse(SimpleStatus.NONE);
    }

    /**
     * <code>parseValue</code>
     * <p>The parse value method.</p>
     * @param value {@link java.lang.String} <p>The value parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.enums.SimpleStatus} <p>The parse value return object is <code>SimpleStatus</code> type.</p>
     * @see java.lang.String
     */
    public static SimpleStatus parseValue(String value) {
        SimpleStatus typeEnum = RestValue.parseValue(SimpleStatus.class, value);
        return Optional.ofNullable(typeEnum).orElse(SimpleStatus.NONE);
    }
}
