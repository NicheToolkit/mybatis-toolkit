package io.github.nichetoolkit.mybatis.test.load;

import io.github.nichetoolkit.mybatis.column.RestForceInsert;
import io.github.nichetoolkit.mybatis.column.RestLinkKey;
import io.github.nichetoolkit.rice.RestInfoEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class LoadLinkEntity<E extends LoadLinkEntity<E, M>, M extends LoadLinkModel<M, E>> extends RestInfoEntity<E, M> {
    @RestLinkKey
    private String paramId;
    @RestForceInsert("now()")
    private Date time;

    public LoadLinkEntity() {
    }

    public LoadLinkEntity(String id) {
        super(id);
    }

}
