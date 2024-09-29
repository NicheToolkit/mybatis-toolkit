package io.github.nichetoolkit.mybatis;


import io.github.nichetoolkit.rest.reflect.RestGenericTypes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface MybatisEntityMapper<E> {
    @SuppressWarnings(value = "unchecked")
    default Class<E> clazz() {
        return (Class<E>) CachingMybatisClass.clazz(getClass());
    }

    default MybatisTable table() {
        return MybatisFactory.createTable(clazz(), null, null, null);
    }

    class CachingMybatisClass {
        static Map<Class<?>, Class<?>> CLASS_MAP = new ConcurrentHashMap<>();

        private static Class<?> clazz(Class<?> clazz) {
            if (!CLASS_MAP.containsKey(clazz)) {
                CLASS_MAP.put(clazz, RestGenericTypes.resolveClass(RestGenericTypes.resolveType(
                        MybatisEntityMapper.class.getTypeParameters()[0], clazz, MybatisEntityMapper.class)));
            }
            return CLASS_MAP.get(clazz);
        }
    }
}
