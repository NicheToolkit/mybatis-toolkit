package io.github.nichetoolkit.mybatis;


import io.github.nichetoolkit.mybatis.resolver.MybatisGenericTypes;
import io.github.nichetoolkit.rest.reflect.DefaultGenericArrayType;
import io.github.nichetoolkit.rest.reflect.DefaultParameterizedType;
import io.github.nichetoolkit.rest.reflect.DefaultWildcardType;
import io.github.nichetoolkit.rest.reflect.RestGenericTypes;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Predicate;

public abstract class MybatisEntityClassFinder implements MybatisClassFinder {

    @Override
    public Optional<Class<?>> findEntity(@NonNull Class<?> mapperType, Method mapperMethod) {
        /* 先判断返回值 */
        Optional<Class<?>> optionalClass;
        if (mapperMethod != null) {
            optionalClass = entityTypeByReturnType(mapperType, mapperMethod);
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
            /* 再判断参数 */
            optionalClass = entityTypeByParamTypes(mapperType, mapperMethod);
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
            /* 最后从接口泛型中获取 */
            optionalClass = entityTypeByMapperMethod(mapperType, mapperMethod);
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
        }
        return entityTypeByMapperType(mapperType);
    }

    @Override
    public Optional<Class<?>> findIdentity(@NonNull Class<?> mapperType, @NonNull Class<?> entityType) {
        /* entityType 泛型寻找 */
        Optional<Class<?>> optionalClass = identityTypeByEntityType(entityType);
        if (optionalClass.isPresent()) {
            return optionalClass;
        }
        /* mapperType 泛型寻找 */
        return identityTypeByMapperType(mapperType);
    }

    public Optional<Class<?>> findLinkage(@NonNull Class<?> mapperType, @NonNull Class<?> entityType) {
        /* entityType 泛型寻找 */
        Optional<Class<?>> optionalClass = linkageTypeByEntityType(entityType);
        if (optionalClass.isPresent()) {
            return optionalClass;
        }
        /* mapperType 泛型寻找 */
        return linkageTypeByMapperType(mapperType);
    }

    public Optional<Class<?>> findAlertness(@NonNull Class<?> mapperType, @NonNull Class<?> entityType) {
        /* entityType 泛型寻找 */
        Optional<Class<?>> optionalClass = alertnessTypeByEntityType(entityType);
        if (optionalClass.isPresent()) {
            return optionalClass;
        }
        /* mapperType 泛型寻找 */
        return alertnessTypeByMapperType(mapperType);
    }

    protected Optional<Class<?>> entityTypeByReturnType(Class<?> mapperType, Method mapperMethod) {
        Class<?> returnType = MybatisGenericTypes.resolveMapperReturnType(mapperMethod, mapperType);
        return isEntity(returnType) ? Optional.of(returnType) : Optional.empty();
    }

    protected Optional<Class<?>> entityTypeByParamTypes(Class<?> mapperType, Method mapperMethod) {
        return classOfByTypes(RestGenericTypes.resolveParamTypes(mapperMethod, mapperType), this::isEntity);
    }

    protected Optional<Class<?>> entityTypeByMapperMethod(Class<?> mapperType, Method mapperMethod) {
        return classOfByTypes(RestGenericTypes.resolveMethodTypes(mapperMethod, mapperType), this::isEntity);
    }

    protected Optional<Class<?>> identityTypeByEntityType(Class<?> entityType) {
        return classOfByTypes(RestGenericTypes.resolveSuperclassTypes(entityType), this::isIdentity);
    }

    protected Optional<Class<?>> linkageTypeByEntityType(Class<?> entityType) {
        return classOfByTypes(RestGenericTypes.resolveSuperclassTypes(entityType), this::isLinkage);
    }

    protected Optional<Class<?>> alertnessTypeByEntityType(Class<?> entityType) {
        return classOfByTypes(RestGenericTypes.resolveSuperclassTypes(entityType), this::isAlertness);
    }

    protected Optional<Class<?>> entityTypeByMapperType(Class<?> mapperType) {
        return classOfByTypes(RestGenericTypes.resolveInterfaceTypes(mapperType), this::isEntity);
    }

    protected Optional<Class<?>> identityTypeByMapperType(Class<?> mapperType) {
        return classOfByTypes(RestGenericTypes.resolveInterfaceTypes(mapperType), this::isIdentity);
    }

    protected Optional<Class<?>> linkageTypeByMapperType(Class<?> mapperType) {
        return classOfByTypes(RestGenericTypes.resolveInterfaceTypes(mapperType), this::isLinkage);
    }

    protected Optional<Class<?>> alertnessTypeByMapperType(Class<?> mapperType) {
        return classOfByTypes(RestGenericTypes.resolveInterfaceTypes(mapperType), this::isAlertness);
    }


    protected Optional<Class<?>> classOfByType(Type type, Predicate<Class<?>> predicate) {
        if (type instanceof Class) {
            Class<?> clazz = (Class<?>) type;
            if (predicate.test(clazz)) {
                return Optional.of((Class<?>) type);
            }
        } else if (type instanceof DefaultParameterizedType) {
            return classOfByTypes(((DefaultParameterizedType) type).getActualTypeArguments(), predicate);
        } else if (type instanceof DefaultWildcardType) {
            Optional<Class<?>> optionalClass = classOfByTypes(((DefaultWildcardType) type).getLowerBounds(), predicate);
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
            return classOfByTypes(((DefaultWildcardType) type).getUpperBounds(), predicate);
        } else if (type instanceof DefaultGenericArrayType) {
            return classOfByType(((DefaultGenericArrayType) type).getGenericComponentType(), predicate);
        }
        return Optional.empty();
    }

    protected Optional<Class<?>> classOfByTypes(Type[] types, Predicate<Class<?>> predicate) {
        for (Type type : types) {
            Optional<Class<?>> optionalClass = classOfByType(type, predicate);
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
        }
        return Optional.empty();
    }
}
