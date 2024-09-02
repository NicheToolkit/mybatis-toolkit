package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.resolver.MybatisGenericTypeResolver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <code>MybatisEntityMapper</code>
 * <p>The type mybatis entity mapper interface.</p>
 * @param <E> {@link java.lang.Object} <p>the parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface MybatisEntityMapper<E> {
    /**
     * <code>clazz</code>
     * <p>the method.</p>
     * @return {@link java.lang.Class} <p>the return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.SuppressWarnings
     */
    @SuppressWarnings(value = "unchecked")
    default Class<E> clazz() {
        return (Class<E>) CachingMybatisClass.clazz(getClass());
    }

    /**
     * <code>table</code>
     * <p>the method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>the return object is <code>MybatisTable</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    default MybatisTable table() {
        return MybatisFactory.createTable(clazz());
    }

    /**
     * <code>CachingMybatisClass</code>
     * <p>The type caching mybatis class class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    class CachingMybatisClass {
        /**
         * <code>CLASS_MAP</code>
         * {@link java.util.Map} <p>the <code>CLASS_MAP</code> field.</p>
         * @see java.util.Map
         */
        static Map<Class<?>, Class<?>> CLASS_MAP = new ConcurrentHashMap<>();

        private static Class<?> clazz(Class<?> clazz) {
            if (!CLASS_MAP.containsKey(clazz)) {
                CLASS_MAP.put(clazz, MybatisGenericTypeResolver.resolveTypeToClass(MybatisGenericTypeResolver.resolveType(
                        MybatisEntityMapper.class.getTypeParameters()[0], clazz, MybatisEntityMapper.class)));
            }
            return CLASS_MAP.get(clazz);
        }
    }
}
