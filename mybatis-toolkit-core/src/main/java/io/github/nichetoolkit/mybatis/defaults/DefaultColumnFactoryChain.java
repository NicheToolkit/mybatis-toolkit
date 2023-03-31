package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.MybatisColumn;
import io.github.nichetoolkit.mybatis.MybatisColumnFactory;
import io.github.nichetoolkit.mybatis.MybatisField;
import io.github.nichetoolkit.mybatis.MybatisTable;

import java.util.List;
import java.util.Optional;

/**
 * <p>DefaultMybatisColumnFactoryChain</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class DefaultColumnFactoryChain implements MybatisColumnFactory.Chain {
    private final List<MybatisColumnFactory> factories;
    private final DefaultColumnFactoryChain next;
    private final int index;

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
            return factories.get(index).createColumn(mybatisTable, mybatisField, next);
        }
        return Optional.empty();
    }
}
