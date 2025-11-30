package io.github.nichetoolkit.mybatis.test.load;


import io.github.nichetoolkit.rest.util.BeanUtils;

/**
 * <code>LoadLink2Model</code>
 * <p>The load link 2 model class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.test.load.LoadLinkModel
 * @since Jdk17
 */
public class LoadLink2Model extends LoadLinkModel<LoadLink2Model, LoadLink2Entity> {


    /**
     * <code>LoadLink2Model</code>
     * <p>Instantiates a new load link 2 model.</p>
     */
    public LoadLink2Model() {
    }

    /**
     * <code>LoadLink2Model</code>
     * <p>Instantiates a new load link 2 model.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public LoadLink2Model(String id) {
        super(id);
    }

    @Override
    public LoadLink2Entity toEntity() {
        LoadLink2Entity entity = new LoadLink2Entity();
        BeanUtils.copyNonnullProperties(this,entity);
        entity.setOperate(this.operate.getKey());
        return entity;
    }
}
