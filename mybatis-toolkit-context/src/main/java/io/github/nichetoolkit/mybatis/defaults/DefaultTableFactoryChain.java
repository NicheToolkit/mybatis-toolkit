package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.MybatisTableFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * <code>DefaultTableFactoryChain</code>
 * <p>The default table factory chain class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisTableFactory.Chain
 * @since Jdk1.8
 */
public class DefaultTableFactoryChain implements MybatisTableFactory.Chain {
    /**
     * <code>factories</code>
     * {@link java.util.List} <p>The <code>factories</code> field.</p>
     * @see java.util.List
     */
    private final List<MybatisTableFactory> factories;
    /**
     * <code>next</code>
     * {@link io.github.nichetoolkit.mybatis.defaults.DefaultTableFactoryChain} <p>The <code>next</code> field.</p>
     */
    private final DefaultTableFactoryChain next;
    /**
     * <code>index</code>
     * <p>The <code>index</code> field.</p>
     */
    private final int index;

    /**
     * <code>DefaultTableFactoryChain</code>
     * <p>Instantiates a new default table factory chain.</p>
     * @param factories {@link java.util.List} <p>The factories parameter is <code>List</code> type.</p>
     * @see java.util.List
     */
    public DefaultTableFactoryChain(List<MybatisTableFactory> factories) {
        this(factories, 0);
    }

    /**
     * <code>DefaultTableFactoryChain</code>
     * <p>Instantiates a new default table factory chain.</p>
     * @param factories {@link java.util.List} <p>The factories parameter is <code>List</code> type.</p>
     * @param index     int <p>The index parameter is <code>int</code> type.</p>
     * @see java.util.List
     */
    private DefaultTableFactoryChain(List<MybatisTableFactory> factories, int index) {
        this.factories = factories;
        this.index = index;
        if (this.index < this.factories.size()) {
            this.next = new DefaultTableFactoryChain(factories, this.index + 1);
        } else {
            this.next = null;
        }
    }

    @Override
    public MybatisTable createTable(@NonNull Class<?> entityType, @Nullable Class<?> identityType, @Nullable Class<?> linkageType, @Nullable Class<?> alertnessType) {
        if (index < factories.size()) {
            MybatisTableFactory mybatisTableFactory = factories.get(index);
            if (mybatisTableFactory.supports(entityType)) {
                return mybatisTableFactory.createTable(entityType, identityType, linkageType, alertnessType, next);
            }
        }
        return null;
    }
}
