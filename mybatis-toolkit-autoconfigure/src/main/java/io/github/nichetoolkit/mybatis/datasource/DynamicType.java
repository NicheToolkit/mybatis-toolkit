package io.github.nichetoolkit.mybatis.datasource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.rest.RestKey;

import java.util.Optional;

/**
 * <p>DynamicType</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public enum DynamicType implements RestKey<String> {
    /** 主库 */
    MASTER("master"),
    /** 从库 */
    SLAVE("slave"),
    ;
    private final String key;

    DynamicType(String key) {
        this.key = key;
    }

    @JsonValue
    @Override
    public String getKey() {
        return this.key;
    }

    @JsonCreator
    public static DynamicType parseKey(String key) {
        DynamicType datasourceType = RestKey.parseKey(DynamicType.class, key);
        return Optional.ofNullable(datasourceType).orElse(DynamicType.MASTER);
    }

}
