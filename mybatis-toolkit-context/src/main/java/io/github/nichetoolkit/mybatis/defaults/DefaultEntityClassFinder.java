package io.github.nichetoolkit.mybatis.defaults;


import io.github.nichetoolkit.mybatis.stereotype.table.RestEntity;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * <p>DefaultEntityClassFinder</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class DefaultEntityClassFinder extends MybatisEntityClassFinder {

    @Override
    public Optional<Class<?>> findEntity(Class<?> mapperType, Method mapperMethod) {
        if (mapperMethod != null) {
            /** 首先是接口方法 */
            if (mapperMethod.isAnnotationPresent(RestEntity.class)) {
                return Optional.of(mapperMethod.getDeclaringClass());
            }
        }
        /** 其次是接口上 */
        if (mapperType.isAnnotationPresent(RestEntity.class)) {
            return Optional.of(mapperType);
        }
        /** 没有明确指名的情况下，通过泛型获取 */
        return super.findEntity(mapperType, mapperMethod);
    }

    @Override
    public boolean isEntity(Class<?> clazz) {
        return clazz.isAnnotationPresent(RestEntity.class);
    }
}