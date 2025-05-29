package io.github.nichetoolkit.mybatis.test.fickle;

import io.github.nichetoolkit.mybatis.column.RestFickleEntry;
import io.github.nichetoolkit.mybatis.column.RestUpdate;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.mybatis.table.RestEntity;
import io.github.nichetoolkit.mybatis.table.RestExcludes;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.RestInfoEntity;
import lombok.Getter;
import lombok.Setter;


import java.util.*;


/**
 * <code>FickleEntryEntity</code>
 * <p>The fickle entry entity class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestInfoEntity
 * @see lombok.Setter
 * @see lombok.Getter
 * @see io.github.nichetoolkit.mybatis.table.RestEntity
 * @see io.github.nichetoolkit.mybatis.table.RestExcludes
 * @since Jdk1.8
 */
@Setter
@Getter
@RestEntity(name = "ntr_fickle_entry")
@RestExcludes({"updateTime","createTime"})
public class FickleEntryEntity extends RestInfoEntity<FickleEntryEntity, FickleEntryModel> {

    /**
     * <code>fields</code>
     * {@link java.util.List} <p>The <code>fields</code> field.</p>
     * @see java.util.List
     * @see io.github.nichetoolkit.mybatis.column.RestFickleEntry
     */
    @RestFickleEntry
    private List<RestFickle<?>> fields;

    /**
     * <code>time</code>
     * {@link java.util.Date} <p>The <code>time</code> field.</p>
     * @see java.util.Date
     * @see io.github.nichetoolkit.mybatis.column.RestUpdate
     */
    @RestUpdate(false)
    private Date time;

    /**
     * <code>FickleEntryEntity</code>
     * <p>Instantiates a new fickle entry entity.</p>
     */
    public FickleEntryEntity() {
    }

    /**
     * <code>FickleEntryEntity</code>
     * <p>Instantiates a new fickle entry entity.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
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
