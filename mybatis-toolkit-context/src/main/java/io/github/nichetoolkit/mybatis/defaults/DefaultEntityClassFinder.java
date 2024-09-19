package io.github.nichetoolkit.mybatis.defaults;


import io.github.nichetoolkit.mybatis.stereotype.RestIdentityKey;
import io.github.nichetoolkit.mybatis.stereotype.RestMapper;
import io.github.nichetoolkit.mybatis.stereotype.table.RestEntity;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * <code>DefaultEntityClassFinder</code>
 * <p>The type default entity class finder class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.defaults.MybatisEntityClassFinder
 * @since Jdk1.8
 */
public class DefaultEntityClassFinder extends MybatisEntityClassFinder {

    @Override
    public Optional<Class<?>> findEntity(@NonNull Class<?> mapperType, Method mapperMethod) {
        if (mapperMethod != null) {
            /* 首先是接口方法 */
            if (mapperMethod.isAnnotationPresent(RestEntity.class)) {
                return Optional.of(mapperMethod.getDeclaringClass());
            }
        }
        /* 其次是接口上 */
        if (mapperType.isAnnotationPresent(RestEntity.class)) {
            return Optional.of(mapperType);
        }
        /* RestMapper 获取 */
        if (mapperType.isAnnotationPresent(RestMapper.class)) {
            RestMapper restMapper = mapperType.getAnnotation(RestMapper.class);
            Class<?> entityType = restMapper.entityType();
            if (GeneralUtils.isNotEmpty(entityType) && !entityType.equals(Object.class)) {
                return Optional.of(entityType);
            }
        }
        /* 没有明确指名的情况下，通过泛型获取 */
        return super.findEntity(mapperType, mapperMethod);
    }

    @Override
    public Optional<Class<?>> findIdentityKey(@NonNull Class<?> mapperType, @NonNull Class<?> entityType) {
        /* RestMapper 获取 */
        if (mapperType.isAnnotationPresent(RestMapper.class)) {
            RestMapper restMapper = mapperType.getAnnotation(RestMapper.class);
            Class<?> identityKeyType = restMapper.identityKeyType();
            if (GeneralUtils.isNotEmpty(identityKeyType) && !identityKeyType.equals(Object.class)) {
                return Optional.of(identityKeyType);
            }
        }
        return super.findIdentityKey(mapperType, entityType);
    }

    @Override
    public boolean isEntity(Class<?> clazz) {
        return clazz.isAnnotationPresent(RestEntity.class);
    }

    @Override
    public boolean isIdentityKey(Class<?> clazz) {
        return clazz.isAnnotationPresent(RestIdentityKey.class);
    }
}
