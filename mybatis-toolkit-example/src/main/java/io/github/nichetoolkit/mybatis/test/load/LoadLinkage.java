package io.github.nichetoolkit.mybatis.test.load;

import io.github.nichetoolkit.mybatis.column.RestLoadKey;
import io.github.nichetoolkit.mybatis.table.RestLinkage;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * <code>LoadLinkage</code>
 * <p>The load linkage class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.io.Serializable
 * @see lombok.Setter
 * @see lombok.Getter
 * @see io.github.nichetoolkit.mybatis.table.RestLinkage
 * @since Jdk1.8
 */
@Setter
@Getter
@RestLinkage
public class LoadLinkage implements Serializable {
    /**
     * <code>linkId1</code>
     * {@link java.lang.String} <p>The <code>linkId1</code> field.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.column.RestLoadKey
     */
    /* load and type may be anyone use */
    @RestLoadKey(key = "linkEntity1", type = LoadLink1Entity.class)
    private String linkId1;

    /**
     * <code>linkId2</code>
     * {@link java.lang.String} <p>The <code>linkId2</code> field.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.column.RestLoadKey
     */
    @RestLoadKey(key = "linkEntity2", type = LoadLink2Entity.class)
    private String linkId2;

    /**
     * <code>LoadLinkage</code>
     * <p>Instantiates a new load linkage.</p>
     */
    public LoadLinkage() {
    }

    /**
     * <code>LoadLinkage</code>
     * <p>Instantiates a new load linkage.</p>
     * @param linkId1 {@link java.lang.String} <p>The link id 1 parameter is <code>String</code> type.</p>
     * @param linkId2 {@link java.lang.String} <p>The link id 2 parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public LoadLinkage(String linkId1, String linkId2) {
        this.linkId1 = linkId1;
        this.linkId2 = linkId2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoadLinkage that = (LoadLinkage) o;
        return Objects.equals(linkId1, that.linkId1) && Objects.equals(linkId2, that.linkId2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(linkId1, linkId2);
    }
}
