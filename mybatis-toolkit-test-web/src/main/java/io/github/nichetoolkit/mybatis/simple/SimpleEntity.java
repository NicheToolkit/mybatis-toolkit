package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.rice.RiceInfoEntity;
import io.github.nichetoolkit.rice.stereotype.mybatis.RestIdentity;
import io.github.nichetoolkit.rice.stereotype.mybatis.column.*;
import io.github.nichetoolkit.rice.stereotype.mybatis.table.RestEntity;
import io.github.nichetoolkit.rice.stereotype.mybatis.table.RestUnionKeys;

import java.util.Date;


/**
 * <p>SimpleEntity</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@RestEntity(name = "ntr_simple")
public class SimpleEntity extends RiceInfoEntity<SimpleEntity, SimpleModel> {

    @RestForceInsert("now()")
    private Date time;

    @RestName(name = "id")
    @RestIdentity
    private String uuid;

    @RestLinkKey
    private String uk1;

    @RestLinkKey
    private String uk2;

    @RestName(name = "id")
    @RestPrimaryKey
    private String pk;

    @RestExclude
    private String ignored;

    public SimpleEntity() {
    }

    public SimpleEntity(String id) {
        super(id);
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getUk1() {
        return uk1;
    }

    public void setUk1(String uk1) {
        this.uk1 = uk1;
    }

    public String getUk2() {
        return uk2;
    }

    public void setUk2(String uk2) {
        this.uk2 = uk2;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIgnored() {
        return ignored;
    }

    public void setIgnored(String ignored) {
        this.ignored = ignored;
    }

    @Override
    public SimpleModel toModel() {
        SimpleModel.Builder builder = new SimpleModel.Builder();
        builder.id(this.id)
                .name(this.name)
                .description(this.description)
                .time(this.time);
        return new SimpleModel(builder);
    }

}
