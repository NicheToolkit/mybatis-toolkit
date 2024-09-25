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

/**
 * <code>MybatisEntityClassFinder</code>
 * <p>The type mybatis entity class finder class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisClassFinder
 * @since Jdk1.8
 */
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

    /**
     * <code>getEntityClassByMapperMethodReturnType</code>
     * <p>the entity class by mapper method return type getter method.</p>
     * @param mapperType   {@link java.lang.Class} <p>the mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>the mapper method parameter is <code>Method</code> type.</p>
     * @return {@link java.util.Optional} <p>the entity class by mapper method return type return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.reflect.Method
     * @see java.util.Optional
     */
    protected Optional<Class<?>> getEntityClassByMapperMethodReturnType(Class<?> mapperType, Method mapperMethod) {
        Class<?> returnType = MybatisGenericTypes.resolveMapperReturnType(mapperMethod, mapperType);
        return isEntity(returnType) ? Optional.of(returnType) : Optional.empty();
    }

    /**
     * <code>getEntityClassByMapperMethodParamTypes</code>
     * <p>the entity class by mapper method param types getter method.</p>
     * @param mapperType   {@link java.lang.Class} <p>the mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>the mapper method parameter is <code>Method</code> type.</p>
     * @return {@link java.util.Optional} <p>the entity class by mapper method param types return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.reflect.Method
     * @see java.util.Optional
     */
    protected Optional<Class<?>> getEntityClassByMapperMethodParamTypes(Class<?> mapperType, Method mapperMethod) {
        return getClassByTypes(RestGenericTypes.resolveParamTypes(mapperMethod, mapperType), this::isEntity);
    }

    /**
     * <code>getEntityClassByMapperMethodAndMapperType</code>
     * <p>the entity class by mapper method and mapper type getter method.</p>
     * @param mapperType   {@link java.lang.Class} <p>the mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>the mapper method parameter is <code>Method</code> type.</p>
     * @return {@link java.util.Optional} <p>the entity class by mapper method and mapper type return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.reflect.Method
     * @see java.util.Optional
     */
    protected Optional<Class<?>> getEntityClassByMapperMethodAndMapperType(Class<?> mapperType, Method mapperMethod) {
        return getClassByTypes(RestGenericTypes.resolveMethodTypes(mapperMethod, mapperType), this::isEntity);
    }

    /**
     * <code>getIdentityKeyClassByEntityType</code>
     * <p>the identity key class by entity type getter method.</p>
     * @param entityType {@link java.lang.Class} <p>the entity type parameter is <code>Class</code> type.</p>
     * @return {@link java.util.Optional} <p>the identity key class by entity type return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.util.Optional
     */
    protected Optional<Class<?>> getIdentityClassByEntityType(Class<?> entityType) {
        return getClassByTypes(RestGenericTypes.resolveSuperclassTypes(entityType), this::isIdentity);
    }


    /**
     * <code>getEntityClassByMapperType</code>
     * <p>the entity class by mapper type getter method.</p>
     * @param mapperType {@link java.lang.Class} <p>the mapper type parameter is <code>Class</code> type.</p>
     * @return {@link java.util.Optional} <p>the entity class by mapper type return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.util.Optional
     */
    protected Optional<Class<?>> getEntityClassByMapperType(Class<?> mapperType) {
        return getClassByTypes(RestGenericTypes.resolveInterfaceTypes(mapperType), this::isEntity);
    }

    /**
     * <code>getIdentityKeyClassByMapperType</code>
     * <p>the identity key class by mapper type getter method.</p>
     * @param mapperType {@link java.lang.Class} <p>the mapper type parameter is <code>Class</code> type.</p>
     * @return {@link java.util.Optional} <p>the identity key class by mapper type return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.util.Optional
     */
    protected Optional<Class<?>> getIdentityClassByMapperType(Class<?> mapperType) {
        return getClassByTypes(RestGenericTypes.resolveInterfaceTypes(mapperType), this::isIdentity);
    }


    /**
     * <code>getClassByType</code>
     * <p>the class by type getter method.</p>
     * @param type      {@link java.lang.reflect.Type} <p>the type parameter is <code>Type</code> type.</p>
     * @param predicate {@link java.util.function.Predicate} <p>the predicate parameter is <code>Predicate</code> type.</p>
     * @return {@link java.util.Optional} <p>the class by type return object is <code>Optional</code> type.</p>
     * @see java.lang.reflect.Type
     * @see java.util.function.Predicate
     * @see java.util.Optional
     */
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

    /**
     * <code>getClassByTypes</code>
     * <p>the class by types getter method.</p>
     * @param types     {@link java.lang.reflect.Type} <p>the types parameter is <code>Type</code> type.</p>
     * @param predicate {@link java.util.function.Predicate} <p>the predicate parameter is <code>Predicate</code> type.</p>
     * @return {@link java.util.Optional} <p>the class by types return object is <code>Optional</code> type.</p>
     * @see java.lang.reflect.Type
     * @see java.util.function.Predicate
     * @see java.util.Optional
     */
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
