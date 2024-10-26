package io.github.nichetoolkit.mybatis.defaults;


import io.github.nichetoolkit.mybatis.MybatisColumn;
import io.github.nichetoolkit.mybatis.MybatisColumnFactory;
import io.github.nichetoolkit.mybatis.MybatisField;
import io.github.nichetoolkit.mybatis.MybatisTable;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * <code>DefaultColumnFactoryChain</code>
 * <p>The default column factory chain class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisColumnFactory.Chain
 * @since Jdk1.8
 */
public class DefaultColumnFactoryChain implements MybatisColumnFactory.Chain {
    /**
     * <code>factories</code>
     * {@link java.util.List} <p>The <code>factories</code> field.</p>
     * @see java.util.List
     */
    private final List<MybatisColumnFactory> factories;
    /**
     * <code>next</code>
     * {@link io.github.nichetoolkit.mybatis.defaults.DefaultColumnFactoryChain} <p>The <code>next</code> field.</p>
     */
    private final DefaultColumnFactoryChain next;
    /**
     * <code>index</code>
     * <p>The <code>index</code> field.</p>
     */
    private final int index;

    /**
     * <code>DefaultColumnFactoryChain</code>
     * <p>Instantiates a new default column factory chain.</p>
     * @param factories {@link java.util.List} <p>The factories parameter is <code>List</code> type.</p>
     * @see java.util.List
     */
    public DefaultColumnFactoryChain(List<MybatisColumnFactory> factories) {
        this(factories, 0);
    }

    /**
     * <code>DefaultColumnFactoryChain</code>
     * <p>Instantiates a new default column factory chain.</p>
     * @param factories {@link java.util.List} <p>The factories parameter is <code>List</code> type.</p>
     * @param index     int <p>The index parameter is <code>int</code> type.</p>
     * @see java.util.List
     */
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
    public Optional<List<MybatisColumn>> createColumn(@NonNull MybatisTable mybatisTable,@NonNull MybatisField mybatisField) {
        if (index < factories.size()) {
            MybatisColumnFactory mybatisColumnFactory = factories.get(index);
            if (mybatisColumnFactory.supports(mybatisTable,mybatisField)) {
                return mybatisColumnFactory.createColumn(mybatisTable, mybatisField, next);
            }
        }
        return Optional.empty();
    }
}
