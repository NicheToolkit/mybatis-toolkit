package io.github.nichetoolkit.mybatis.test.load;

import io.github.nichetoolkit.mybatis.column.RestForceInsert;
import io.github.nichetoolkit.mybatis.column.RestLinkKey;
import io.github.nichetoolkit.mybatis.column.RestLoadEntity;
import io.github.nichetoolkit.mybatis.column.RestLoadParam;
import io.github.nichetoolkit.mybatis.table.RestEntity;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.DefaultInfoEntity;
import io.github.nichetoolkit.rice.enums.OperateType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <code>LoadEntity</code>
 * <p>The load entity class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultInfoEntity
 * @see lombok.Setter
 * @see lombok.Getter
 * @see io.github.nichetoolkit.mybatis.table.RestEntity
 * @since Jdk1.8
 */
@Setter
@Getter
@RestEntity(name = "ntr_load")
public class LoadEntity extends DefaultInfoEntity<LoadEntity, LoadModel, LoadIdentity> {
    /**
     * <code>linkage</code>
     * {@link io.github.nichetoolkit.mybatis.test.load.LoadLinkage} <p>The <code>linkage</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.test.load.LoadLinkage
     * @see io.github.nichetoolkit.mybatis.column.RestLinkKey
     */
    @RestLinkKey
    private LoadLinkage linkage;

    /**
     * <code>paramId</code>
     * {@link java.lang.String} <p>The <code>paramId</code> field.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.column.RestLoadParam
     */
    @RestLoadParam(param = "paramId", types = LoadLink2Entity.class)
    private String paramId;

    /**
     * <code>time</code>
     * {@link java.util.Date} <p>The <code>time</code> field.</p>
     * @see java.util.Date
     * @see io.github.nichetoolkit.mybatis.column.RestForceInsert
     */
    @RestForceInsert("now()")
    private Date time;

    /**
     * <code>linkEntity1</code>
     * {@link io.github.nichetoolkit.mybatis.test.load.LoadLink1Entity} <p>The <code>linkEntity1</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.test.load.LoadLink1Entity
     * @see io.github.nichetoolkit.mybatis.column.RestLoadEntity
     */
    @RestLoadEntity
    private LoadLink1Entity linkEntity1;

    /**
     * <code>linkEntity2s</code>
     * {@link java.util.List} <p>The <code>linkEntity2s</code> field.</p>
     * @see java.util.List
     * @see io.github.nichetoolkit.mybatis.column.RestLoadEntity
     */
    @RestLoadEntity
    private List<LoadLink2Entity> linkEntity2s;

    /**
     * <code>LoadEntity</code>
     * <p>Instantiates a new load entity.</p>
     */
    public LoadEntity() {
    }

    /**
     * <code>LoadEntity</code>
     * <p>Instantiates a new load entity.</p>
     * @param id {@link io.github.nichetoolkit.mybatis.test.load.LoadIdentity} <p>The id parameter is <code>LoadIdentity</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.test.load.LoadIdentity
     */
    public LoadEntity(LoadIdentity id) {
        super(id);
    }

    @Override
    public LoadModel toModel() {
        LoadModel loadModel = new LoadModel();
        BeanUtils.copyNonnullProperties(this, loadModel);
        loadModel.setOperate(OperateType.parseKey(this.operate));
        loadModel.setLinkId1(this.linkage.getLinkId1());
        loadModel.setLinkId2(this.linkage.getLinkId2());
        if (GeneralUtils.isNotEmpty(this.linkEntity1)) {
            loadModel.setLink1(this.linkEntity1.toModel());
        }
        if (GeneralUtils.isNotEmpty(this.linkEntity2s)) {
            List<LoadLink2Model> loadLink2Models = this.linkEntity2s.stream().map(LoadLink2Entity::toModel).collect(Collectors.toList());
            loadModel.setLink2s(new ArrayList<>(loadLink2Models));
        }
        return loadModel;
    }

}
