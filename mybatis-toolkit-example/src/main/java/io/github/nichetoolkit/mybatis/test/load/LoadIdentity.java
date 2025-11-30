package io.github.nichetoolkit.mybatis.test.load;

import io.github.nichetoolkit.mybatis.column.RestUnionKey;
import io.github.nichetoolkit.mybatis.table.RestIdentity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;

/**
 * <code>LoadIdentity</code>
 * <p>The load identity class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.io.Serializable
 * @see lombok.Setter
 * @see lombok.Getter
 * @see lombok.experimental.SuperBuilder
 * @see io.github.nichetoolkit.mybatis.table.RestIdentity
 * @since Jdk17
 */
@Setter
@Getter
@SuperBuilder
@RestIdentity
public class LoadIdentity implements Serializable {
    /**
     * <code>loadPk1</code>
     * {@link java.lang.String} <p>The <code>loadPk1</code> field.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.column.RestUnionKey
     */
    @RestUnionKey
    private String loadPk1;
    /**
     * <code>loadPk2</code>
     * {@link java.lang.String} <p>The <code>loadPk2</code> field.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.column.RestUnionKey
     */
    @RestUnionKey
    private String loadPk2;

    /**
     * <code>LoadIdentity</code>
     * <p>Instantiates a new load identity.</p>
     */
    public LoadIdentity() {
    }

    /**
     * <code>LoadIdentity</code>
     * <p>Instantiates a new load identity.</p>
     * @param loadPk1 {@link java.lang.String} <p>The load pk 1 parameter is <code>String</code> type.</p>
     * @param loadPk2 {@link java.lang.String} <p>The load pk 2 parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
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
