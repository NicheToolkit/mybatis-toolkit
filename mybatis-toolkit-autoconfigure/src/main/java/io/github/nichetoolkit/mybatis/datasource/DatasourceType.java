package io.github.nichetoolkit.mybatis.datasource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.rest.RestKey;

import java.util.Optional;

/**
 * <p>DatasourceType</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public enum DatasourceType implements RestKey<String> {
    /** 默认单一数据源 */
    DEFAULT("default"),
    /** 动态数据源 */
    DYNAMIC("dynamic"),
    /** 自定义数据源 JDBC Sharding 实现等*/
    CUSTOM("custom"),
    ;
    private final String key;
    
    DatasourceType(String key) {
        this.key = key;
    }

    @JsonValue
    @Override
    public String getKey() {
        return this.key;
    }
    
    @JsonCreator
    public static DatasourceType parseKey(String key) {
        DatasourceType datasourceType = RestKey.parseKey(DatasourceType.class, key);
        return Optional.ofNullable(datasourceType).orElse(DatasourceType.DEFAULT);
    }

}
