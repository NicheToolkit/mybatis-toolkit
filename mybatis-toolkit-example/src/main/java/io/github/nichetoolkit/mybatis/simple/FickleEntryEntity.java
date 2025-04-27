package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.mybatis.column.RestFickleEntry;
import io.github.nichetoolkit.mybatis.column.RestUpdate;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.mybatis.table.RestEntity;
import io.github.nichetoolkit.mybatis.table.RestExcludes;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.RestInfoEntity;
import org.springframework.lang.NonNull;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Setter
@Getter
@RestEntity(name = "ntr_fickle_entry")
@RestExcludes({"updateTime","createTime"})
public class FickleEntryEntity extends RestInfoEntity<FickleEntryEntity, FickleEntryModel> {
    @RestUpdate(false)
    private Date time;

    @NonNull
    @RestFickleEntry
    private RestFickle<?>[] fields = new RestFickle<?>[0];

    public FickleEntryEntity() {
    }

    public FickleEntryEntity(String id) {
        super(id);
    }

    @Override
    public FickleEntryModel toModel() {
        FickleEntryModel model = new FickleEntryModel();
        BeanUtils.copyNonnullProperties(this,model);
        return model;
    }

}
