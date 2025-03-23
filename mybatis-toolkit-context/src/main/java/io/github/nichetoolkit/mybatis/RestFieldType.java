package io.github.nichetoolkit.mybatis;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.mybatis.fickle.FickleType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.lang.NonNull;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class RestFieldType implements FickleType {

    public MybatisType type;

    public String value;

    public RestFieldType(MybatisType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String getAlias() {
        return this.type.getAlias();
    }

    @Override
    public JdbcType getJdbcType() {
        return this.type.getJdbcType();
    }

    @Override
    public Class<?> getType() {
        return this.type.getType();
    }

    @Override
    public String setValue(String value) {
        this.value = value;
        return this.value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String name() {
        return this.type.name();
    }

    @NonNull
    @Override
    public Integer getKey() {
        return this.type.getKey();
    }

    public static RestFieldType build(MybatisType type, String value) {
        return new RestFieldType(type, value);
    }
}
