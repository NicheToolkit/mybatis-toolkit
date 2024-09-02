package io.github.nichetoolkit.mybatis.defaults;


import io.github.nichetoolkit.mybatis.MybatisColumn;
import io.github.nichetoolkit.mybatis.MybatisColumnFactory;
import io.github.nichetoolkit.mybatis.MybatisField;
import io.github.nichetoolkit.mybatis.MybatisTable;

import java.util.List;
import java.util.Optional;

/**
 * <code>DefaultColumnFactoryChain</code>
 * <p>The type default column factory chain class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain
 * @since Jdk1.8
 */
public class DefaultColumnFactoryChain implements MybatisColumnFactory.Chain {
    private final List<MybatisColumnFactory> factories;
    private final DefaultColumnFactoryChain next;
    private final int index;

    /**
     * <code>DefaultColumnFactoryChain</code>
     * Instantiates a new default column factory chain.
     * @param factories {@link java.util.List} <p>the factories parameter is <code>List</code> type.</p>
     * @see java.util.List
     */
    public DefaultColumnFactoryChain(List<MybatisColumnFactory> factories) {
        this(factories, 0);
    }

    private DefaultColumnFactoryChain(List<MybatisColumnFactory> factories, int index) {
        this.factories = factories;
        this.index = index;
        if (this.index < this.factories.size()) {
            this.next = new DefaultColumnFactoryChain(factories, this.index + 1);
        } else {
            this.next = null;
        }
    }

    @Override
    public Optional<List<MybatisColumn>> createColumn(MybatisTable mybatisTable, MybatisField mybatisField) {
        if (index < factories.size()) {
            MybatisColumnFactory mybatisColumnFactory = factories.get(index);
            if (mybatisColumnFactory.supports(mybatisTable,mybatisField)) {
                return mybatisColumnFactory.createColumn(mybatisTable, mybatisField, next);
            }
        }
        return Optional.empty();
    }
}
