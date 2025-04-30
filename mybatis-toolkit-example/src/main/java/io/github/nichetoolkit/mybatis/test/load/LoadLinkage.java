package io.github.nichetoolkit.mybatis.test.load;

import io.github.nichetoolkit.mybatis.column.RestLoadKey;
import io.github.nichetoolkit.mybatis.column.RestLoadParam;
import io.github.nichetoolkit.mybatis.table.RestLinkage;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@RestLinkage
public class LoadLinkage implements Serializable {
    /* load and type may be anyone use */
    @RestLoadKey(key = "linkEntity1", type = LoadLinkEntity1.class)
    private String linkId1;

    @RestLoadKey(key = "linkEntity2", type = LoadLinkEntity2.class)
    private String linkId2;

    public LoadLinkage() {
    }

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
