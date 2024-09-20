package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.MybatisTableFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <code>DefaultCachingTableFactory</code>
 * <p>The type default caching table factory class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisTableFactory
 * @since Jdk1.8
 */
public class DefaultCachingTableFactory implements MybatisTableFactory {
    /**
     * <code>CLASS_TABLE_MAP</code>
     * {@link java.util.Map} <p>the <code>CLASS_TABLE_MAP</code> field.</p>
     * @see java.util.Map
     */
    private final Map<Class<?>, MybatisTable> CLASS_TABLE_MAP = new ConcurrentHashMap<>();

    @Override
    public boolean supports(@NonNull Class<?> entityType) {
        return true;
    }

    @Override
    public MybatisTable createTable(@NonNull Class<?> entityType, @Nullable Class<?> identityType, Chain chain) {
        if (CLASS_TABLE_MAP.get(entityType) == null) {
            synchronized (entityType) {
                if (CLASS_TABLE_MAP.get(entityType) == null) {
                    MybatisTable mybatisTable = chain.createTable(entityType, identityType);
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
