package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.resolver.MybatisGenericTypeResolver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>MybatisEntityMapper</p>
 * 实体类信息接口，继承当前接口即可在接口中方便的获取当前接口对应的实体类类型
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface MybatisEntityMapper<E> {
    /**
     * 获取当前接口对应的实体类类型
     * @return 当前接口对应的实体类类型
     */
    @SuppressWarnings(value = "unchecked")
    default Class<E> clazz() {
        return (Class<E>) CachingMybatisClass.clazz(getClass());
    }

    /**
     * 获取当前接口对应的实体表信息
     * @return 当前接口对应的实体表信息
     */
    default MybatisTable table() {
        return MybatisFactory.createTable(clazz());
    }

    /**
     * 缓存实体类类型
     */
    class CachingMybatisClass {
        static Map<Class<?>, Class<?>> CLASS_MAP = new ConcurrentHashMap<>();

        /**
         * 获取接口对应的实体类类型
         * @param clazz 继承的子接口
         * @return 实体类类型
         */
        private static Class<?> clazz(Class<?> clazz) {
            if (!CLASS_MAP.containsKey(clazz)) {
                CLASS_MAP.put(clazz, MybatisGenericTypeResolver.resolveTypeToClass(MybatisGenericTypeResolver.resolveType(
                        MybatisEntityMapper.class.getTypeParameters()[0], clazz, MybatisEntityMapper.class)));
            }
            return CLASS_MAP.get(clazz);
        }
    }
}
