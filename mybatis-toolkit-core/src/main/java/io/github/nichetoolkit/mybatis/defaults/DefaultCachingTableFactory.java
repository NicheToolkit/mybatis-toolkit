package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.MybatisTable;
import io.github.nichetoolkit.mybatis.MybatisTableFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>DefaultCachingTableFactory</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class DefaultCachingTableFactory implements MybatisTableFactory {
    /**
     * 缓存实体类信息
     */
    private final Map<Class<?>, MybatisTable> CLASS_TABLE_MAP = new ConcurrentHashMap<>();

    @Override
    public MybatisTable createTable(Class<?> clazz, Chain chain) {
        if (CLASS_TABLE_MAP.get(clazz) == null) {
            synchronized (clazz) {
                if (CLASS_TABLE_MAP.get(clazz) == null) {
                    MybatisTable entityTable = chain.createTable(clazz);
                    if (entityTable != null) {
                        CLASS_TABLE_MAP.put(clazz, entityTable);
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
