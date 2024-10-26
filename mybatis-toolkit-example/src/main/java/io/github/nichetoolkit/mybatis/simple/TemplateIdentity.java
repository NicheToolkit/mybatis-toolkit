package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.mybatis.stereotype.column.RestUnionKey;
import io.github.nichetoolkit.mybatis.stereotype.table.RestIdentity;

import java.io.Serializable;
import java.util.Objects;

/**
 * <code>TemplateIdentity</code>
 * <p>The template identity class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.io.Serializable
 * @see io.github.nichetoolkit.mybatis.stereotype.table.RestIdentity
 * @since Jdk1.8
 */
@RestIdentity
public class TemplateIdentity implements Serializable {
    /**
     * <code>templatePk1</code>
     * {@link java.lang.String} <p>The <code>templatePk1</code> field.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.stereotype.column.RestUnionKey
     */
    @RestUnionKey
    private String templatePk1;
    /**
     * <code>templatePk2</code>
     * {@link java.lang.String} <p>The <code>templatePk2</code> field.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.stereotype.column.RestUnionKey
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
     * @see java.lang.String
     */
    public TemplateIdentity(String templatePk1, String templatePk2) {
        this.templatePk1 = templatePk1;
        this.templatePk2 = templatePk2;
    }

    /**
     * <code>getTemplatePk1</code>
     * <p>The get template pk 1 getter method.</p>
     * @return {@link java.lang.String} <p>The get template pk 1 return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String getTemplatePk1() {
        return templatePk1;
    }

    /**
     * <code>setTemplatePk1</code>
     * <p>The set template pk 1 setter method.</p>
     * @param templatePk1 {@link java.lang.String} <p>The template pk 1 parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public void setTemplatePk1(String templatePk1) {
        this.templatePk1 = templatePk1;
    }

    /**
     * <code>getTemplatePk2</code>
     * <p>The get template pk 2 getter method.</p>
     * @return {@link java.lang.String} <p>The get template pk 2 return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String getTemplatePk2() {
        return templatePk2;
    }

    /**
     * <code>setTemplatePk2</code>
     * <p>The set template pk 2 setter method.</p>
     * @param templatePk2 {@link java.lang.String} <p>The template pk 2 parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public void setTemplatePk2(String templatePk2) {
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
