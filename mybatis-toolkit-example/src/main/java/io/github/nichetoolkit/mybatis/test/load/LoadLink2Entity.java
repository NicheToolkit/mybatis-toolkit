package io.github.nichetoolkit.mybatis.test.load;

import io.github.nichetoolkit.mybatis.table.RestEntity;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.enums.OperateType;
import lombok.experimental.SuperBuilder;

/**
 * <code>LoadLink2Entity</code>
 * <p>The load link 2 entity class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.test.load.LoadLinkEntity
 * @see io.github.nichetoolkit.mybatis.table.RestEntity
 * @since Jdk1.8
 */
@SuperBuilder
@RestEntity(name = "ntr_load_link2")
public class LoadLink2Entity extends LoadLinkEntity<LoadLink2Entity, LoadLink2Model> {

    /**
     * <code>LoadLink2Entity</code>
     * <p>Instantiates a new load link 2 entity.</p>
     */
    public LoadLink2Entity() {
    }

    /**
     * <code>LoadLink2Entity</code>
     * <p>Instantiates a new load link 2 entity.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public LoadLink2Entity(String id) {
        super(id);
    }

    @Override
    public LoadLink2Model toModel() {
        LoadLink2Model loadLinkModel = new LoadLink2Model();
        BeanUtils.copyNonnullProperties(this, loadLinkModel);
        loadLinkModel.setOperate(OperateType.parseKey(this.operate));
        return loadLinkModel;
    }
}
