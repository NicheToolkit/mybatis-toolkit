package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.reflect.RestGenericTypes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <code>MybatisMapper</code>
 * <p>The mybatis mapper interface.</p>
 * @param <E> {@link java.lang.Object} <p>The parameter can be of any type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface MybatisMapper<E> {
    /**
     * <code>mapperType</code>
     * <p>The mapper type method.</p>
     * @return {@link java.lang.Class} <p>The mapper type return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.SuppressWarnings
     */
    @SuppressWarnings(value = "unchecked")
    default Class<E> mapperType() {
        return (Class<E>) Instance.mapperType(getClass());
    }

    /**
     * <code>table</code>
     * <p>The table method.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The table return object is <code>MybatisTable</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    default MybatisTable table() {
        return MybatisFactory.createTable(mapperType(), null, null, null);
    }

    /**
     * <code>Instance</code>
     * <p>The instance class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk1.8
     */
    class Instance {

        /**
         * <code>MAPPER_TYPES</code>
         * {@link java.util.Map} <p>The <code>MAPPER_TYPES</code> field.</p>
         * @see java.util.Map
         */
        private static Map<Class<?>, Class<?>> MAPPER_TYPES;

        /**
         * <code>mapperType</code>
         * <p>The mapper type method.</p>
         * @param clazz {@link java.lang.Class} <p>The clazz parameter is <code>Class</code> type.</p>
         * @return {@link java.lang.Class} <p>The mapper type return object is <code>Class</code> type.</p>
         * @see java.lang.Class
         */
        private static Class<?> mapperType(Class<?> clazz) {
            if (MAPPER_TYPES == null) {
                MAPPER_TYPES = new ConcurrentHashMap<>();
            }
            if (!MAPPER_TYPES.containsKey(clazz)) {
                MAPPER_TYPES.put(clazz, RestGenericTypes.resolveClass(RestGenericTypes.resolveType(
                        MybatisMapper.class.getTypeParameters()[0], clazz, MybatisMapper.class)));
            }
            return MAPPER_TYPES.get(clazz);
        }
    }
}
