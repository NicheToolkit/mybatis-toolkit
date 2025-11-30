package io.github.nichetoolkit.mybatis.test.load;

import io.github.nichetoolkit.mybatis.table.RestEntity;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.enums.OperateType;

/**
 * <code>LoadLink1Entity</code>
 * <p>The load link 1 entity class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.test.load.LoadLinkEntity
 * @see io.github.nichetoolkit.mybatis.table.RestEntity
 * @since Jdk17
 */
@RestEntity(name = "ntr_load_link1")
public class LoadLink1Entity extends LoadLinkEntity<LoadLink1Entity, LoadLink1Model> {

    /**
     * <code>LoadLink1Entity</code>
     * <p>Instantiates a new load link 1 entity.</p>
     */
    public LoadLink1Entity() {
    }

    /**
     * <code>LoadLink1Entity</code>
     * <p>Instantiates a new load link 1 entity.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public LoadLink1Entity(String id) {
        super(id);
    }

    @Override
    public LoadLink1Model toModel() {
        LoadLink1Model loadLinkModel = new LoadLink1Model();
        BeanUtils.copyNonnullProperties(this, loadLinkModel);
        loadLinkModel.setOperate(OperateType.parseKey(this.operate));
        return loadLinkModel;
    }
}
