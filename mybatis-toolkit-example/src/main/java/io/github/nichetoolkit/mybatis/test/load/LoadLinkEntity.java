package io.github.nichetoolkit.mybatis.test.load;

import io.github.nichetoolkit.mybatis.column.RestForceInsert;
import io.github.nichetoolkit.mybatis.column.RestLinkKey;
import io.github.nichetoolkit.rice.RestInfoEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <code>LoadLinkEntity</code>
 * <p>The load link entity class.</p>
 * @param <E> {@link io.github.nichetoolkit.mybatis.test.load.LoadLinkEntity} <p>The generic parameter is <code>LoadLinkEntity</code> type.</p>
 * @param <M> {@link io.github.nichetoolkit.mybatis.test.load.LoadLinkModel} <p>The generic parameter is <code>LoadLinkModel</code> type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.test.load.LoadLinkModel
 * @see io.github.nichetoolkit.rice.RestInfoEntity
 * @see lombok.Setter
 * @see lombok.Getter
 * @since Jdk1.8
 */
@Setter
@Getter
@SuperBuilder
public class LoadLinkEntity<E extends LoadLinkEntity<E, M>, M extends LoadLinkModel<M, E>> extends RestInfoEntity<E, M> {
    /**
     * <code>paramId</code>
     * {@link java.lang.String} <p>The <code>paramId</code> field.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.mybatis.column.RestLinkKey
     */
    @RestLinkKey
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
     * <code>LoadLinkEntity</code>
     * <p>Instantiates a new load link entity.</p>
     */
    public LoadLinkEntity() {
    }

    /**
     * <code>LoadLinkEntity</code>
     * <p>Instantiates a new load link entity.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public LoadLinkEntity(String id) {
        super(id);
    }

}
