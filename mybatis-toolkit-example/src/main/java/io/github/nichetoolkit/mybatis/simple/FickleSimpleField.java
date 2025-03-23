package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.mybatis.MybatisFickle;
import lombok.Getter;

@Getter
public class FickleSimpleField extends MybatisFickle {
    private Object value;

    public FickleSimpleField() {
    }

    public FickleSimpleField(String key, Object value) {
        super(key);
        this.value = value;
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
}
