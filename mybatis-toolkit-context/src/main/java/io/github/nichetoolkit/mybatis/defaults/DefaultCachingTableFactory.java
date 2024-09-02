package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.MybatisTableFactory;

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
    private final Map<Class<?>, MybatisTable> CLASS_TABLE_MAP = new ConcurrentHashMap<>();

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public MybatisTable createTable(Class<?> clazz, Chain chain) {
        if (CLASS_TABLE_MAP.get(clazz) == null) {
            synchronized (clazz) {
                if (CLASS_TABLE_MAP.get(clazz) == null) {
                    MybatisTable mybatisTable = chain.createTable(clazz);
                    if (mybatisTable != null) {
                        CLASS_TABLE_MAP.put(clazz, mybatisTable);
                    } else {
                        return null;
                    }
                }
            }
        }
        return CLASS_TABLE_MAP.get(clazz);
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
