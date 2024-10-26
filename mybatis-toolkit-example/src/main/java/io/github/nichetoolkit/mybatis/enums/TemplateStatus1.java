package io.github.nichetoolkit.mybatis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.rest.RestValue;

import java.util.Optional;

/**
 * <code>TemplateStatus1</code>
 * <p>The template status 1 enumeration.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestValue
 * @since Jdk1.8
 */
public enum TemplateStatus1 implements RestValue<Integer,String> {
    /**
     * <code>NONE</code>
     * <p>The none template status 1 field.</p>
     */
    NONE(1,"无"),
    /**
     * <code>TEST</code>
     * <p>The test template status 1 field.</p>
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
     * <code>TemplateStatus1</code>
     * <p>Instantiates a new template status 1.</p>
     * @param key   {@link java.lang.Integer} <p>The key parameter is <code>Integer</code> type.</p>
     * @param value {@link java.lang.String} <p>The value parameter is <code>String</code> type.</p>
     * @see java.lang.Integer
     * @see java.lang.String
     */
    TemplateStatus1(Integer key, String value) {
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
     * @return {@link io.github.nichetoolkit.mybatis.enums.TemplateStatus1} <p>The parse key return object is <code>TemplateStatus1</code> type.</p>
     * @see java.lang.Integer
     * @see com.fasterxml.jackson.annotation.JsonCreator
     */
    @JsonCreator
    public static TemplateStatus1 parseKey(Integer key) {
        TemplateStatus1 typeEnum = RestValue.parseKey(TemplateStatus1.class, key);
        return Optional.ofNullable(typeEnum).orElse(TemplateStatus1.NONE);
    }

    /**
     * <code>parseValue</code>
     * <p>The parse value method.</p>
     * @param value {@link java.lang.String} <p>The value parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.enums.TemplateStatus1} <p>The parse value return object is <code>TemplateStatus1</code> type.</p>
     * @see java.lang.String
     */
    public static TemplateStatus1 parseValue(String value) {
        TemplateStatus1 typeEnum = RestValue.parseValue(TemplateStatus1.class, value);
        return Optional.ofNullable(typeEnum).orElse(TemplateStatus1.NONE);
    }
}
