package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.MybatisTableFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultCachingTableFactory implements MybatisTableFactory {
    private final Map<Class<?>, MybatisTable> CLASS_TABLE_MAP = new ConcurrentHashMap<>();

    @Override
    public boolean supports(@NonNull Class<?> entityType) {
        return true;
    }

    @Override
    public MybatisTable createTable(@NonNull Class<?> entityType, @Nullable Class<?> identityType, @Nullable Class<?> linkageType, @Nullable Class<?> alertnessType, Chain chain) {
        if (CLASS_TABLE_MAP.get(entityType) == null) {
            synchronized (entityType) {
                if (CLASS_TABLE_MAP.get(entityType) == null) {
                    MybatisTable mybatisTable = chain.createTable(entityType, identityType, linkageType, alertnessType);
                    if (mybatisTable != null) {
                        CLASS_TABLE_MAP.put(entityType, mybatisTable);
                    } else {
                        return null;
                    }
                }
            }
        }
        return CLASS_TABLE_MAP.get(entityType);
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
