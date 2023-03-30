package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.defaults.MybatisGenericTypeResolver;
import io.github.nichetoolkit.rest.RestException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>MybatisInfoMapper</p>
 * 实体类信息接口，继承当前接口即可在接口中方便的获取当前接口对应的实体类类型
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public interface MybatisInfoMapper<M> {
    /**
     * 获取当前接口对应的实体类类型
     *
     * @return 当前接口对应的实体类类型
     */
    @SuppressWarnings(value = "unchecked")
    default Class<M> clazz() {
        return (Class<M>) CachingMybatisClass.mybatisClass(getClass());
    }

    /**
     * 获取当前接口对应的实体表信息
     *
     * @return 当前接口对应的实体表信息
     */
    default MybatisTable table() throws RestException {
        return MybatisFactory.create(clazz());
    }

    /**
     * 缓存实体类类型
     */
    class CachingMybatisClass {
        static Map<Class<?>, Class<?>> MYBATIS_CLASS_MAP = new ConcurrentHashMap<>();

        /**
         * 获取接口对应的实体类类型
         *
         * @param clazz 继承的子接口
         * @return 实体类类型
         */
        private static Class<?> mybatisClass(Class<?> clazz) {
            if (!MYBATIS_CLASS_MAP.containsKey(clazz)) {
                MYBATIS_CLASS_MAP.put(clazz, MybatisGenericTypeResolver.resolveTypeToClass(MybatisGenericTypeResolver.resolveType(
                        MybatisInfoMapper.class.getTypeParameters()[0], clazz, MybatisInfoMapper.class)));
            }
            return MYBATIS_CLASS_MAP.get(clazz);
        }
    }
}
