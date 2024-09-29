package io.github.nichetoolkit.mybatis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.mybatis.consts.StyleConstants;
import io.github.nichetoolkit.rest.RestKey;

import java.util.Optional;

public enum StyleType implements RestKey<String> {
    NORMAL(StyleConstants.NORMAL),
    LOWER_UNDERLINE(StyleConstants.LOWER_UNDERLINE),
    LOWER(StyleConstants.LOWER),
    UPPER(StyleConstants.UPPER),
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

    @JsonCreator
    public static StyleType parseKey(String key) {
        StyleType typeEnum = RestKey.parseKey(StyleType.class, key);
        return Optional.ofNullable(typeEnum).orElse(StyleType.NORMAL);
    }

}
