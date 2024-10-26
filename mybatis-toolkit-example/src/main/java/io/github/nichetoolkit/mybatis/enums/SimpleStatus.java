package io.github.nichetoolkit.mybatis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.rest.RestValue;

import java.util.Optional;

public enum SimpleStatus implements RestValue<Integer,String> {
    NONE(1,"无"),
    TEST(2,"测试"),
    ;

    private final Integer key;
    private final String value;

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

    @JsonCreator
    public static SimpleStatus parseKey(Integer key) {
        SimpleStatus typeEnum = RestValue.parseKey(SimpleStatus.class, key);
        return Optional.ofNullable(typeEnum).orElse(SimpleStatus.NONE);
    }

    public static SimpleStatus parseValue(String value) {
        SimpleStatus typeEnum = RestValue.parseValue(SimpleStatus.class, value);
        return Optional.ofNullable(typeEnum).orElse(SimpleStatus.NONE);
    }
}
