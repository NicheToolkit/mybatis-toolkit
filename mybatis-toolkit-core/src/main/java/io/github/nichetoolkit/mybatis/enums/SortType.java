package io.github.nichetoolkit.mybatis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.rest.RestValue;

import java.util.Optional;

public enum SortType implements RestValue<String,String> {
    NONE("",""),
    ASC("ASC","升序"),
    DESC("DESC","降序")
    ;

    private final String key;
    private final String value;

    SortType(String key, String value) {
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
    public static SortType parseKey(String key) {
        SortType typeEnum = RestValue.parseKey(SortType.class, key);
        return Optional.ofNullable(typeEnum).orElse(SortType.DESC);
    }

    public static SortType parseValue(String value) {
        SortType typeEnum = RestValue.parseValue(SortType.class, value);
        return Optional.ofNullable(typeEnum).orElse(SortType.DESC);
    }


}
