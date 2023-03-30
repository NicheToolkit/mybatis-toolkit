package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.MybatisTableFactory;
import io.github.nichetoolkit.rest.RestException;

import java.util.List;

/**
 * <p>DefaultMybatisTableFactoryChain</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class DefaultMybatisTableFactoryChain implements MybatisTableFactory.Chain {
    private final List<MybatisTableFactory> factories;
    private final DefaultMybatisTableFactoryChain next;
    private final int index;

    public DefaultMybatisTableFactoryChain(List<MybatisTableFactory> factories) {
        this(factories, 0);
    }

    private DefaultMybatisTableFactoryChain(List<MybatisTableFactory> factories, int index) {
        this.factories = factories;
        this.index = index;
        if (this.index < this.factories.size()) {
            this.next = new DefaultMybatisTableFactoryChain(factories, this.index + 1);
        } else {
            this.next = null;
        }
    }

    @Override
    public MybatisTable createMybatisTable(Class<?> clazz) throws RestException {
        if (index < factories.size()) {
            return factories.get(index).createMybatisTable(clazz, next);
        }
        return null;
    }
}
