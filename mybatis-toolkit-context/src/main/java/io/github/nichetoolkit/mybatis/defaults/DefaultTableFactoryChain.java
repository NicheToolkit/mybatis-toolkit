package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.MybatisTableFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

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
