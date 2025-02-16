package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.mybatis.fickle.FickleField;
import io.github.nichetoolkit.mybatis.fickle.FickleType;
import io.github.nichetoolkit.mybatis.fickle.defaults.FickleObjectType;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.Objects;

@Getter
public class FickleSimpleField implements FickleField<Object> {
    private String key;
    private Object value;

    public FickleSimpleField() {
    }

    public FickleSimpleField(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    @NonNull
    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(@NonNull String key) {
        this.key = key;
    }

    @Override
    public String getAlias() {
        return "";
    }

    @Override
    public String getName() {
        return this.key;
    }

    @NonNull
    @Override
    public FickleType getType() {
        return new FickleObjectType();
    }

    @Override
    public String getComment() {
        return "";
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public Object setValue(Object value) {
        this.value = value;
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FickleSimpleField that = (FickleSimpleField) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }
}
