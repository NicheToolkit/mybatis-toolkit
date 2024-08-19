package io.github.nichetoolkit.mybatis.datasource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.rest.RestKey;

import java.util.Optional;

/**
 * <p>DruidDatasourceType</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public enum DruidDatasourceType implements RestKey<String> {
    /** 主库 */
    DEFAULT("default"),
    /** 主库 */
    MASTER("master"),
    /** 从库 */
    SLAVE("slave"),
    ;
    private final String key;

    DruidDatasourceType(String key) {
        this.key = key;
    }

    @JsonValue
    @Override
    public String getKey() {
        return this.key;
    }

    @JsonCreator
    public static DruidDatasourceType parseKey(String key) {
        DruidDatasourceType datasourceType = RestKey.parseKey(DruidDatasourceType.class, key);
        return Optional.ofNullable(datasourceType).orElse(DruidDatasourceType.DEFAULT);
    }

}
