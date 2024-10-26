package io.github.nichetoolkit.mybatis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.rest.RestValue;

import java.util.Optional;

public enum TemplateStatus2 implements RestValue<Integer,String> {
    NONE(1,"无"),
    TEST(2,"测试"),
    ;

    private final Integer key;
    private final String value;

    TemplateStatus2(Integer key, String value) {
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
    public static TemplateStatus2 parseKey(Integer key) {
        TemplateStatus2 typeEnum = RestValue.parseKey(TemplateStatus2.class, key);
        return Optional.ofNullable(typeEnum).orElse(TemplateStatus2.NONE);
    }

    public static TemplateStatus2 parseValue(String value) {
        TemplateStatus2 typeEnum = RestValue.parseValue(TemplateStatus2.class, value);
        return Optional.ofNullable(typeEnum).orElse(TemplateStatus2.NONE);
    }
}
