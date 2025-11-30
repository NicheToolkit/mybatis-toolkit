package io.github.nichetoolkit.mybatis.test.template;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.rest.RestKey;
import io.github.nichetoolkit.rest.RestValue;

import java.util.Optional;

/**
 * <code>TemplateState1</code>
 * <p>The template state 1 enumeration.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestValue
 * @since Jdk17
 */
public enum TemplateState1 implements RestValue<Integer,String> {
    /**
     * <code>NONE</code>
     * <p>The none template state 1 field.</p>
     */
    NONE(1,"无"),
    /**
     * <code>TEST</code>
     * <p>The test template state 1 field.</p>
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
     * <code>TemplateState1</code>
     * <p>Instantiates a new template state 1.</p>
     * @param key   {@link java.lang.Integer} <p>The key parameter is <code>Integer</code> type.</p>
     * @param value {@link java.lang.String} <p>The value parameter is <code>String</code> type.</p>
     * @see java.lang.Integer
     * @see java.lang.String
     */
    TemplateState1(Integer key, String value) {
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
     * @return {@link io.github.nichetoolkit.mybatis.test.template.TemplateState1} <p>The parse key return object is <code>TemplateState1</code> type.</p>
     * @see java.lang.Integer
     * @see com.fasterxml.jackson.annotation.JsonCreator
     */
    @JsonCreator
    public static TemplateState1 parseKey(Integer key) {
        TemplateState1 typeEnum = RestKey.parseKey(TemplateState1.class, key);
        return Optional.ofNullable(typeEnum).orElse(TemplateState1.NONE);
    }

    /**
     * <code>parseValue</code>
     * <p>The parse value method.</p>
     * @param value {@link java.lang.String} <p>The value parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.test.template.TemplateState1} <p>The parse value return object is <code>TemplateState1</code> type.</p>
     * @see java.lang.String
     */
    public static TemplateState1 parseValue(String value) {
        TemplateState1 typeEnum = RestValue.parseValue(TemplateState1.class, value);
        return Optional.ofNullable(typeEnum).orElse(TemplateState1.NONE);
    }
}
