package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.mybatis.stereotype.column.RestUnionKey;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class IdentityModel implements Serializable {
    @RestUnionKey
    protected String templatePk1;

    public IdentityModel() {
    }

    public IdentityModel(String templatePk1) {
        this.templatePk1 = templatePk1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdentityModel that = (IdentityModel) o;
        return Objects.equals(templatePk1, that.templatePk1);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(templatePk1);
    }
}
