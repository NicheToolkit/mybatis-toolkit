package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.rice.column.RestUnionKey;
import io.github.nichetoolkit.rice.table.RestIdentity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * <code>TemplateIdentity</code>
 * <p>The template identity class.</p>
 * @see  java.io.Serializable
 * @see  io.github.nichetoolkit.rice.table.RestIdentity
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Setter
@Getter
@RestIdentity
public class TemplateIdentity implements Serializable {
    /**
     * <code>templatePk1</code>
     * {@link java.lang.String} <p>The <code>templatePk1</code> field.</p>
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rice.column.RestUnionKey
     */
    @RestUnionKey
    private String templatePk1;
    /**
     * <code>templatePk2</code>
     * {@link java.lang.String} <p>The <code>templatePk2</code> field.</p>
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rice.column.RestUnionKey
     */
    @RestUnionKey
    private String templatePk2;

    /**
     * <code>TemplateIdentity</code>
     * <p>Instantiates a new template identity.</p>
     */
    public TemplateIdentity() {
    }

    /**
     * <code>TemplateIdentity</code>
     * <p>Instantiates a new template identity.</p>
     * @param templatePk1 {@link java.lang.String} <p>The template pk 1 parameter is <code>String</code> type.</p>
     * @param templatePk2 {@link java.lang.String} <p>The template pk 2 parameter is <code>String</code> type.</p>
     * @see  java.lang.String
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
        return Objects.equals(templatePk1, that.templatePk1) && Objects.equals(templatePk2, that.templatePk2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(templatePk1, templatePk2);
    }
}
