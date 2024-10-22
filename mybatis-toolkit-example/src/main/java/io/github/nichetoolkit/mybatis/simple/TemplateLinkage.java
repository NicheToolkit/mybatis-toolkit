package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.mybatis.stereotype.table.RestLinkage;

import java.io.Serializable;
import java.util.Objects;

@RestLinkage
public class TemplateLinkage implements Serializable {

    private String linkId1;

    private String linkId2;

    public TemplateLinkage() {
    }

    public TemplateLinkage(String linkId1, String linkId2) {
        this.linkId1 = linkId1;
        this.linkId2 = linkId2;
    }

    public String getLinkId1() {
        return linkId1;
    }

    public void setLinkId1(String linkId1) {
        this.linkId1 = linkId1;
    }

    public String getLinkId2() {
        return linkId2;
    }

    public void setLinkId2(String linkId2) {
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
