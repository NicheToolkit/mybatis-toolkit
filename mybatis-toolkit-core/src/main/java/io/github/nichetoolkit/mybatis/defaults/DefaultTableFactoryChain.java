package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.MybatisTableFactory;

import java.util.List;

/**
 * <p>DefaultMybatisTableFactoryChain</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class DefaultTableFactoryChain implements MybatisTableFactory.Chain {
    private final List<MybatisTableFactory> factories;
    private final DefaultTableFactoryChain next;
    private final int index;

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
            return factories.get(index).createTable(clazz, next);
        }
        return null;
    }
}
