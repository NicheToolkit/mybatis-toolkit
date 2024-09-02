package io.github.nichetoolkit.mybatis.simple;

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
public class TemplateKey implements Serializable {
    private String templatePk1;

    private String templatePk2;

    /**
     * <code>TemplateKey</code>
     * Instantiates a new template key.
     */
    public TemplateKey() {
    }

    /**
     * <code>TemplateKey</code>
     * Instantiates a new template key.
     * @param templatePk1 {@link java.lang.String} <p>the template pk 1 parameter is <code>String</code> type.</p>
     * @param templatePk2 {@link java.lang.String} <p>the template pk 2 parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public TemplateKey(String templatePk1, String templatePk2) {
        this.templatePk1 = templatePk1;
        this.templatePk2 = templatePk2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateKey that = (TemplateKey) o;
        return Objects.equals(templatePk1, that.templatePk1) &&
                Objects.equals(templatePk2, that.templatePk2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(templatePk1, templatePk2);
    }
}
