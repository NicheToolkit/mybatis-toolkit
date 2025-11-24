package io.github.nichetoolkit.mybatis.test.simple;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.rest.RestKey;
import io.github.nichetoolkit.rest.RestState;
import io.github.nichetoolkit.rest.RestValue;

import java.util.Optional;

public enum SimpleState1 implements RestValue<Integer,String>, RestState<Integer> {
    NONE(1,"无"),
    TEST(2,"测试"),
    ;

    private final Integer key;
    private final String value;

    SimpleState1(Integer key, String value) {
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
    public static SimpleState1 parseKey(Integer key) {
        SimpleState1 typeEnum = RestKey.parseKey(SimpleState1.class, key);
        return Optional.ofNullable(typeEnum).orElse(SimpleState1.NONE);
    }

    public static SimpleState1 parseValue(String value) {
        SimpleState1 typeEnum = RestValue.parseValue(SimpleState1.class, value);
        return Optional.ofNullable(typeEnum).orElse(SimpleState1.NONE);
    }
}
