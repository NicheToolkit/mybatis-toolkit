package io.github.nichetoolkit.mybatis.defaults;


import io.github.nichetoolkit.mybatis.MybatisEntityClassFinder;
import io.github.nichetoolkit.mybatis.stereotype.table.RestAlertness;
import io.github.nichetoolkit.mybatis.stereotype.table.RestIdentity;
import io.github.nichetoolkit.mybatis.stereotype.RestMapper;
import io.github.nichetoolkit.mybatis.stereotype.table.RestEntity;
import io.github.nichetoolkit.mybatis.stereotype.table.RestLinkage;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * <code>DefaultEntityClassFinder</code>
 * <p>The default entity class finder class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisEntityClassFinder
 * @since Jdk1.8
 */
public class DefaultEntityClassFinder extends MybatisEntityClassFinder {

    @Override
    public Optional<Class<?>> findEntity(@NonNull Class<?> mapperType, @Nullable Method mapperMethod) {
        /* RestMapper 获取 */
        if (mapperType.isAnnotationPresent(RestMapper.class)) {
            RestMapper restMapper = mapperType.getAnnotation(RestMapper.class);
            Class<?> entityType = restMapper.entityType();
            if (GeneralUtils.isNotEmpty(entityType) && !entityType.equals(Object.class)) {
                return Optional.of(entityType);
            }
        }
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
        /* 没有明确指名的情况下，通过泛型获取 */
        return super.findEntity(mapperType, mapperMethod);
    }

    @Override
    public Optional<Class<?>> findIdentity(@NonNull Class<?> mapperType, @Nullable Method mapperMethod,@NonNull Class<?> entityType) {
        /* RestEntity 获取 */
        if (entityType.isAnnotationPresent(RestEntity.class)) {
            RestEntity restEntity = entityType.getAnnotation(RestEntity.class);
            Class<?> identityType = restEntity.identityType();
            if (GeneralUtils.isNotEmpty(identityType) && !identityType.equals(Object.class)) {
                return Optional.of(identityType);
            }
        }
        /* RestMapper 获取 */
        if (mapperType.isAnnotationPresent(RestMapper.class)) {
            RestMapper restMapper = mapperType.getAnnotation(RestMapper.class);
            Class<?> identityType = restMapper.identityType();
            if (GeneralUtils.isNotEmpty(identityType) && !identityType.equals(Object.class)) {
                return Optional.of(identityType);
            }
        }
        return super.findIdentity(mapperType, mapperMethod, entityType);
    }

    @Override
    public Optional<Class<?>> findLinkage(@NonNull Class<?> mapperType, @Nullable Method mapperMethod,@NonNull Class<?> entityType) {
        /* RestEntity 获取 */
        if (entityType.isAnnotationPresent(RestEntity.class)) {
            RestEntity restEntity = entityType.getAnnotation(RestEntity.class);
            Class<?> linkageType = restEntity.linkageType();
            if (GeneralUtils.isNotEmpty(linkageType) && !linkageType.equals(Object.class)) {
                return Optional.of(linkageType);
            }
        }
        /* RestMapper 获取 */
        if (mapperType.isAnnotationPresent(RestMapper.class)) {
            RestMapper restMapper = mapperType.getAnnotation(RestMapper.class);
            Class<?> linkageType = restMapper.linkageType();
            if (GeneralUtils.isNotEmpty(linkageType) && !linkageType.equals(Object.class)) {
                return Optional.of(linkageType);
            }
        }
        return super.findLinkage(mapperType, mapperMethod, entityType);
    }

    @Override
    public Optional<Class<?>> findAlertness(@NonNull Class<?> mapperType,@Nullable Method mapperMethod, @NonNull Class<?> entityType) {
        /* RestEntity 获取 */
        if (entityType.isAnnotationPresent(RestEntity.class)) {
            RestEntity restEntity = entityType.getAnnotation(RestEntity.class);
            Class<?> alertnessType = restEntity.alertnessType();
            if (GeneralUtils.isNotEmpty(alertnessType) && !alertnessType.equals(Object.class)) {
                return Optional.of(alertnessType);
            }
        }
        /* RestMapper 获取 */
        if (mapperType.isAnnotationPresent(RestMapper.class)) {
            RestMapper restMapper = mapperType.getAnnotation(RestMapper.class);
            Class<?> alertnessType = restMapper.alertnessType();
            if (GeneralUtils.isNotEmpty(alertnessType) && !alertnessType.equals(Object.class)) {
                return Optional.of(alertnessType);
            }
        }
        return super.findAlertness(mapperType, mapperMethod, entityType);
    }

    @Override
    public boolean isEntity(Class<?> clazz) {
        return clazz.isAnnotationPresent(RestEntity.class);
    }

    @Override
    public boolean isIdentity(Class<?> clazz) {
        return clazz.isAnnotationPresent(RestIdentity.class);
    }

    @Override
    public boolean isLinkage(Class<?> clazz) {
        return clazz.isAnnotationPresent(RestLinkage.class);
    }

    @Override
    public boolean isAlertness(Class<?> clazz) {
        return clazz.isAnnotationPresent(RestAlertness.class);
    }
}
