package io.github.nichetoolkit.mybatis;


import io.github.nichetoolkit.rest.reflect.RestGenericTypes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface MybatisMapper<E> {
    @SuppressWarnings(value = "unchecked")
    default Class<E> mapperType() {
        return (Class<E>) Instance.mapperType(getClass());
    }

    default MybatisTable table() {
        return MybatisFactory.createTable(mapperType(), null, null, null);
    }

    class Instance {

        private static Map<Class<?>, Class<?>> MAPPER_TYPES;

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
