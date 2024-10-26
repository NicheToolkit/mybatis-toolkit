package io.github.nichetoolkit.mybatis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.rest.RestValue;

import java.util.Optional;

public enum TemplateStatus1 implements RestValue<Integer,String> {
    NONE(1,"无"),
    TEST(2,"测试"),
    ;

    private final Integer key;
    private final String value;

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

    @JsonCreator
    public static TemplateStatus1 parseKey(Integer key) {
        TemplateStatus1 typeEnum = RestValue.parseKey(TemplateStatus1.class, key);
        return Optional.ofNullable(typeEnum).orElse(TemplateStatus1.NONE);
    }

    public static TemplateStatus1 parseValue(String value) {
        TemplateStatus1 typeEnum = RestValue.parseValue(TemplateStatus1.class, value);
        return Optional.ofNullable(typeEnum).orElse(TemplateStatus1.NONE);
    }
}
