package io.github.nichetoolkit.mybatis.test.common;

import io.github.nichetoolkit.mybatis.table.*;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.RestInfoEntity;
import io.github.nichetoolkit.mybatis.column.RestUpdate;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * <code>CommonEntity</code>
 * <p>The common entity class.</p>
 * @see  io.github.nichetoolkit.rice.RestInfoEntity
 * @see  lombok.Setter
 * @see  lombok.Getter
 * @see  io.github.nichetoolkit.mybatis.table.RestEntity
 * @see  io.github.nichetoolkit.mybatis.table.RestUpdateIgnores
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Setter
@Getter
@RestEntity(name = "ntr_common")
@RestExcludes({"updateTime","createTime"})
@RestUpdateIgnores({"name","description","updateTime"})
public class CommonEntity extends RestInfoEntity<CommonEntity, CommonModel> {
    /**
     * <code>time</code>
     * {@link java.util.Date} <p>The <code>time</code> field.</p>
     * @see  java.util.Date
     * @see  io.github.nichetoolkit.mybatis.column.RestUpdate
     */
    @RestUpdate(false)
    private Date time;

    /**
     * <code>CommonEntity</code>
     * <p>Instantiates a new common entity.</p>
     */
    public CommonEntity() {
    }

    /**
     * <code>CommonEntity</code>
     * <p>Instantiates a new common entity.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public CommonEntity(String id) {
        super(id);
    }

    @Override
    public CommonModel toModel() {
        CommonModel commonModel = new CommonModel();
        BeanUtils.copyNonnullProperties(this,commonModel);
        return commonModel;
    }

}
