package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.mybatis.stereotype.column.RestUnionKey;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * <code>IdentityModel</code>
 * <p>The type identity model class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.io.Serializable
 * @see lombok.Data
 * @since Jdk1.8
 */
@Data
public class IdentityModel implements Serializable {
    /**
     * <code>templatePk1</code>
     * {@link java.lang.String} <p>the <code>templatePk1</code> field.</p>
     * @see java.lang.String
     */
    @RestUnionKey
    protected String templatePk1;

    /**
     * <code>IdentityModel</code>
     * Instantiates a new identity model.
     */
    public IdentityModel() {
    }

    /**
     * <code>IdentityModel</code>
     * Instantiates a new identity model.
     * @param templatePk1 {@link java.lang.String} <p>the template pk 1 parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
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
