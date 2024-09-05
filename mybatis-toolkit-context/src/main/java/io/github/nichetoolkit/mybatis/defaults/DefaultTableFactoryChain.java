package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.MybatisTableFactory;

import java.util.List;

/**
 * <code>DefaultTableFactoryChain</code>
 * <p>The type default table factory chain class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisTableFactory.Chain
 * @since Jdk1.8
 */
public class DefaultTableFactoryChain implements MybatisTableFactory.Chain {
    /**
     * <code>factories</code>
     * {@link java.util.List} <p>the <code>factories</code> field.</p>
     * @see java.util.List
     */
    private final List<MybatisTableFactory> factories;
    /**
     * <code>next</code>
     * {@link io.github.nichetoolkit.mybatis.defaults.DefaultTableFactoryChain} <p>the <code>next</code> field.</p>
     */
    private final DefaultTableFactoryChain next;
    /**
     * <code>index</code>
     * <p>the <code>index</code> field.</p>
     */
    private final int index;

    /**
     * <code>DefaultTableFactoryChain</code>
     * Instantiates a new default table factory chain.
     * @param factories {@link java.util.List} <p>the factories parameter is <code>List</code> type.</p>
     * @see java.util.List
     */
    public DefaultTableFactoryChain(List<MybatisTableFactory> factories) {
        this(factories, 0);
    }

    /**
     * <code>DefaultTableFactoryChain</code>
     * Instantiates a new default table factory chain.
     * @param factories {@link java.util.List} <p>the factories parameter is <code>List</code> type.</p>
     * @param index     int <p>the index parameter is <code>int</code> type.</p>
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
    public MybatisTable createTable(Class<?> clazz) {
        if (index < factories.size()) {
            MybatisTableFactory mybatisTableFactory = factories.get(index);
            if (mybatisTableFactory.supports(clazz)) {
                return mybatisTableFactory.createTable(clazz, next);
            }
        }
        return null;
    }
}
