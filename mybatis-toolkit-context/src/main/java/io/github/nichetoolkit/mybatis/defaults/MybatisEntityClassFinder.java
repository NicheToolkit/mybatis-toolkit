package io.github.nichetoolkit.mybatis.defaults;


import io.github.nichetoolkit.mybatis.MybatisClassFinder;
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
            optionalClass = getEntityClassByMapperMethodReturnType(mapperType, mapperMethod);
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
            /* 再判断参数 */
            optionalClass = getEntityClassByMapperMethodParamTypes(mapperType, mapperMethod);
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
            /* 最后从接口泛型中获取 */
            optionalClass = getEntityClassByMapperMethodAndMapperType(mapperType, mapperMethod);
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
        }
        return getEntityClassByMapperType(mapperType);
    }

    @Override
    public Optional<Class<?>> findIdentity(@NonNull Class<?> mapperType, @NonNull Class<?> entityType) {
        /* entityType 泛型寻找 */
        Optional<Class<?>> optionalClass = getIdentityClassByEntityType(entityType);
        if (optionalClass.isPresent()) {
            return optionalClass;
        }
        /* mapperType 泛型寻找 */
        return getIdentityClassByMapperType(mapperType);
    }

    public Optional<Class<?>> findLinkage(@NonNull Class<?> mapperType, @NonNull Class<?> entityType) {
        /* entityType 泛型寻找 */
        Optional<Class<?>> optionalClass = getLinkageClassByEntityType(entityType);
        if (optionalClass.isPresent()) {
            return optionalClass;
        }
        /* mapperType 泛型寻找 */
        return getLinkageClassByMapperType(mapperType);
    }

    public Optional<Class<?>> findAlertness(@NonNull Class<?> mapperType, @NonNull Class<?> entityType) {
        /* entityType 泛型寻找 */
        Optional<Class<?>> optionalClass = getAlertnessClassByEntityType(entityType);
        if (optionalClass.isPresent()) {
            return optionalClass;
        }
        /* mapperType 泛型寻找 */
        return getAlertnessClassByMapperType(mapperType);
    }

    protected Optional<Class<?>> getEntityClassByMapperMethodReturnType(Class<?> mapperType, Method mapperMethod) {
        Class<?> returnType = MybatisGenericTypes.resolveMapperReturnType(mapperMethod, mapperType);
        return isEntity(returnType) ? Optional.of(returnType) : Optional.empty();
    }

    protected Optional<Class<?>> getEntityClassByMapperMethodParamTypes(Class<?> mapperType, Method mapperMethod) {
        return getClassByTypes(RestGenericTypes.resolveParamTypes(mapperMethod, mapperType), this::isEntity);
    }

    protected Optional<Class<?>> getEntityClassByMapperMethodAndMapperType(Class<?> mapperType, Method mapperMethod) {
        return getClassByTypes(RestGenericTypes.resolveMethodTypes(mapperMethod, mapperType), this::isEntity);
    }

    protected Optional<Class<?>> getIdentityClassByEntityType(Class<?> entityType) {
        return getClassByTypes(RestGenericTypes.resolveSuperclassTypes(entityType), this::isIdentity);
    }

    protected Optional<Class<?>> getLinkageClassByEntityType(Class<?> entityType) {
        return getClassByTypes(RestGenericTypes.resolveSuperclassTypes(entityType), this::isLinkage);
    }

    protected Optional<Class<?>> getAlertnessClassByEntityType(Class<?> entityType) {
        return getClassByTypes(RestGenericTypes.resolveSuperclassTypes(entityType), this::isAlertness);
    }

    protected Optional<Class<?>> getEntityClassByMapperType(Class<?> mapperType) {
        return getClassByTypes(RestGenericTypes.resolveInterfaceTypes(mapperType), this::isEntity);
    }

    protected Optional<Class<?>> getIdentityClassByMapperType(Class<?> mapperType) {
        return getClassByTypes(RestGenericTypes.resolveInterfaceTypes(mapperType), this::isIdentity);
    }

    protected Optional<Class<?>> getLinkageClassByMapperType(Class<?> mapperType) {
        return getClassByTypes(RestGenericTypes.resolveInterfaceTypes(mapperType), this::isLinkage);
    }

    protected Optional<Class<?>> getAlertnessClassByMapperType(Class<?> mapperType) {
        return getClassByTypes(RestGenericTypes.resolveInterfaceTypes(mapperType), this::isAlertness);
    }


    protected Optional<Class<?>> getClassByType(Type type, Predicate<Class<?>> predicate) {
        if (type instanceof Class) {
            Class<?> clazz = (Class<?>) type;
            if (predicate.test(clazz)) {
                return Optional.of((Class<?>) type);
            }
        } else if (type instanceof DefaultParameterizedType) {
            return getClassByTypes(((DefaultParameterizedType) type).getActualTypeArguments(), predicate);
        } else if (type instanceof DefaultWildcardType) {
            Optional<Class<?>> optionalClass = getClassByTypes(((DefaultWildcardType) type).getLowerBounds(), predicate);
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
            return getClassByTypes(((DefaultWildcardType) type).getUpperBounds(), predicate);
        } else if (type instanceof DefaultGenericArrayType) {
            return getClassByType(((DefaultGenericArrayType) type).getGenericComponentType(), predicate);
        }
        return Optional.empty();
    }

    protected Optional<Class<?>> getClassByTypes(Type[] types, Predicate<Class<?>> predicate) {
        for (Type type : types) {
            Optional<Class<?>> optionalClass = getClassByType(type, predicate);
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
        }
        return Optional.empty();
    }
}
