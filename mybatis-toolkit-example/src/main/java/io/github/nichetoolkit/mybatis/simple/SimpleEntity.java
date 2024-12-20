package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.mybatis.enums.SimpleStatus;
import io.github.nichetoolkit.rice.column.RestAlertKey;
import io.github.nichetoolkit.rice.column.RestForceInsert;
import io.github.nichetoolkit.rice.column.RestLinkKey;
import io.github.nichetoolkit.rice.table.RestEntity;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.RestInfoEntity;
import io.github.nichetoolkit.rice.enums.OperateType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * <code>SimpleEntity</code>
 * <p>The simple entity class.</p>
 * @see  io.github.nichetoolkit.rice.RestInfoEntity
 * @see  lombok.Setter
 * @see  lombok.Getter
 * @see  io.github.nichetoolkit.rice.table.RestEntity
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Setter
@Getter
@RestEntity(name = "ntr_simple")
public class SimpleEntity extends RestInfoEntity<SimpleEntity, SimpleModel> {
    /**
     * <code>linkId</code>
     * {@link java.lang.String} <p>The <code>linkId</code> field.</p>
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rice.column.RestLinkKey
     */
    @RestLinkKey
    private String linkId;
    /**
     * <code>time</code>
     * {@link java.util.Date} <p>The <code>time</code> field.</p>
     * @see  java.util.Date
     * @see  io.github.nichetoolkit.rice.column.RestForceInsert
     */
    @RestForceInsert("now()")
    private Date time;
    /**
     * <code>status</code>
     * {@link java.lang.Integer} <p>The <code>status</code> field.</p>
     * @see  java.lang.Integer
     * @see  io.github.nichetoolkit.rice.column.RestAlertKey
     */
    @RestAlertKey
    private Integer status;

    /**
     * <code>SimpleEntity</code>
     * <p>Instantiates a new simple entity.</p>
     */
    public SimpleEntity() {
    }

    /**
     * <code>SimpleEntity</code>
     * <p>Instantiates a new simple entity.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public SimpleEntity(String id) {
        super(id);
    }

    @Override
    public SimpleModel toModel() {
        SimpleModel simpleModel = new SimpleModel();
        BeanUtils.copyNonnullProperties(this,simpleModel);
        simpleModel.setOperate(OperateType.parseKey(this.operate));
        simpleModel.setStatus(SimpleStatus.parseKey(this.status));
        return simpleModel;
    }

}
