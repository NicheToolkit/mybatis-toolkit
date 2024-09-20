package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.mybatis.stereotype.table.RestIdentity;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * <code>TemplateKey</code>
 * <p>The type template key class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.io.Serializable
 * @see lombok.Data
 * @since Jdk1.8
 */
@Data
@RestIdentity
public class TemplateIdentity implements Serializable {
    /**
     * <code>templatePk1</code>
     * {@link java.lang.String} <p>the <code>templatePk1</code> field.</p>
     * @see java.lang.String
     */
    private String templatePk1;

    /**
     * <code>templatePk2</code>
     * {@link java.lang.String} <p>the <code>templatePk2</code> field.</p>
     * @see java.lang.String
     */
    private String templatePk2;

    /**
     * <code>TemplateKey</code>
     * Instantiates a new template key.
     */
    public TemplateIdentity() {
    }

    /**
     * <code>TemplateKey</code>
     * Instantiates a new template key.
     * @param templatePk1 {@link java.lang.String} <p>the template pk 1 parameter is <code>String</code> type.</p>
     * @param templatePk2 {@link java.lang.String} <p>the template pk 2 parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public TemplateIdentity(String templatePk1, String templatePk2) {
        this.templatePk1 = templatePk1;
        this.templatePk2 = templatePk2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateIdentity that = (TemplateIdentity) o;
        return Objects.equals(templatePk1, that.templatePk1) &&
                Objects.equals(templatePk2, that.templatePk2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(templatePk1, templatePk2);
    }
}
