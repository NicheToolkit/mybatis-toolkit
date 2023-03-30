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
public class DefaultMybatisColumnFactoryChain implements MybatisColumnFactory.Chain {
    private final List<MybatisColumnFactory> factories;
    private final DefaultMybatisColumnFactoryChain next;
    private final int index;

    public DefaultMybatisColumnFactoryChain(List<MybatisColumnFactory> factories) {
        this(factories, 0);
    }

    private DefaultMybatisColumnFactoryChain(List<MybatisColumnFactory> factories, int index) {
        this.factories = factories;
        this.index = index;
        if (this.index < this.factories.size()) {
            this.next = new DefaultMybatisColumnFactoryChain(factories, this.index + 1);
        } else {
            this.next = null;
        }
    }

    @Override
    public Optional<List<MybatisColumn>> createMybatisColumn(MybatisTable mybatisTable, MybatisField mybatisField) {
        if (index < factories.size()) {
            return factories.get(index).createMybatisColumn(mybatisTable, mybatisField, next);
        }
        return Optional.empty();
    }
}
