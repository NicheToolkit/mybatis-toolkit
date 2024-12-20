package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.rice.table.RestLinkage;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * <code>TemplateLinkage</code>
 * <p>The template linkage class.</p>
 * @see  java.io.Serializable
 * @see  io.github.nichetoolkit.rice.table.RestLinkage
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Setter
@Getter
@RestLinkage
public class TemplateLinkage implements Serializable {

    /**
     * <code>linkId1</code>
     * {@link java.lang.String} <p>The <code>linkId1</code> field.</p>
     * @see  java.lang.String
     */
    private String linkId1;

    /**
     * <code>linkId2</code>
     * {@link java.lang.String} <p>The <code>linkId2</code> field.</p>
     * @see  java.lang.String
     */
    private String linkId2;

    /**
     * <code>TemplateLinkage</code>
     * <p>Instantiates a new template linkage.</p>
     */
    public TemplateLinkage() {
    }

    /**
     * <code>TemplateLinkage</code>
     * <p>Instantiates a new template linkage.</p>
     * @param linkId1 {@link java.lang.String} <p>The link id 1 parameter is <code>String</code> type.</p>
     * @param linkId2 {@link java.lang.String} <p>The link id 2 parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public TemplateLinkage(String linkId1, String linkId2) {
        this.linkId1 = linkId1;
        this.linkId2 = linkId2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateLinkage that = (TemplateLinkage) o;
        return Objects.equals(linkId1, that.linkId1) && Objects.equals(linkId2, that.linkId2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(linkId1, linkId2);
    }
}
