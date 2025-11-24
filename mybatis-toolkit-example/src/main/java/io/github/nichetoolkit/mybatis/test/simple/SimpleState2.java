package io.github.nichetoolkit.mybatis.test.simple;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.rest.RestKey;
import io.github.nichetoolkit.rest.RestState;
import io.github.nichetoolkit.rest.RestValue;

import java.util.Optional;

public enum SimpleState2 implements RestValue<String,String>, RestState<String> {
    NONE("none","无"),
    TEST("test","测试"),
    ;

    private final String key;
    private final String value;

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

    @JsonCreator
    public static SimpleState2 parseKey(String key) {
        SimpleState2 typeEnum = RestKey.parseKey(SimpleState2.class, key);
        return Optional.ofNullable(typeEnum).orElse(SimpleState2.NONE);
    }

    public static SimpleState2 parseValue(String value) {
        SimpleState2 typeEnum = RestValue.parseValue(SimpleState2.class, value);
        return Optional.ofNullable(typeEnum).orElse(SimpleState2.NONE);
    }
}
