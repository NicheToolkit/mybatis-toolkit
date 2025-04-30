package io.github.nichetoolkit.mybatis.test.load;

import io.github.nichetoolkit.mybatis.column.RestUnionKey;
import io.github.nichetoolkit.mybatis.table.RestIdentity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@RestIdentity
public class LoadIdentity implements Serializable {
    @RestUnionKey
    private String loadPk1;
    @RestUnionKey
    private String loadPk2;

    public LoadIdentity() {
    }

    public LoadIdentity(String loadPk1, String loadPk2) {
        this.loadPk1 = loadPk1;
        this.loadPk2 = loadPk2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoadIdentity that = (LoadIdentity) o;
        return Objects.equals(loadPk1, that.loadPk1) && Objects.equals(loadPk2, that.loadPk2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loadPk1, loadPk2);
    }
}
