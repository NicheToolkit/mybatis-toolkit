package io.github.nichetoolkit.mybatis.test.load;


import io.github.nichetoolkit.rest.util.BeanUtils;

/**
 * <code>LoadLink1Model</code>
 * <p>The load link 1 model class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.test.load.LoadLinkModel
 * @since Jdk17
 */
public class LoadLink1Model extends LoadLinkModel<LoadLink1Model, LoadLink1Entity> {

    /**
     * <code>LoadLink1Model</code>
     * <p>Instantiates a new load link 1 model.</p>
     */
    public LoadLink1Model() {
    }

    /**
     * <code>LoadLink1Model</code>
     * <p>Instantiates a new load link 1 model.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public LoadLink1Model(String id) {
        super(id);
    }

    @Override
    public LoadLink1Entity toEntity() {
        LoadLink1Entity entity = new LoadLink1Entity();
        BeanUtils.copyNonnullProperties(this, entity);
        entity.setOperate(this.operate.getKey());
        return entity;
    }

}
