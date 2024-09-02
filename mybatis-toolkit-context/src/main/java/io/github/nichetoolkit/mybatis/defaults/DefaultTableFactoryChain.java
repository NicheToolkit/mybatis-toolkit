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
    private final List<MybatisTableFactory> factories;
    private final DefaultTableFactoryChain next;
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
