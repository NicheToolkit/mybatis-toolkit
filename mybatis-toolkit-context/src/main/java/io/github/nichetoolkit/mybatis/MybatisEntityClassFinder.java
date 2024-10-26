package io.github.nichetoolkit.mybatis;


import io.github.nichetoolkit.rest.reflect.DefaultGenericArrayType;
import io.github.nichetoolkit.rest.reflect.DefaultParameterizedType;
import io.github.nichetoolkit.rest.reflect.DefaultWildcardType;
import io.github.nichetoolkit.rest.reflect.RestGenericTypes;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Predicate;

/**
 * <code>MybatisEntityClassFinder</code>
 * <p>The mybatis entity class finder class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisClassFinder
 * @since Jdk1.8
 */
public abstract class MybatisEntityClassFinder implements MybatisClassFinder {

    @Override
    public Optional<Class<?>> findEntity(@NonNull Class<?> mapperType, @Nullable Method mapperMethod) {
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
    public Optional<Class<?>> findIdentity(@NonNull Class<?> mapperType, @Nullable Method mapperMethod, @NonNull Class<?> entityType) {
        /* entityType 泛型寻找 */
        Optional<Class<?>> optionalClass;
        if (mapperMethod != null) {
            /* 判断参数 */
            optionalClass = identityTypeByParamTypes(mapperType, mapperMethod);
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
        }
        optionalClass = identityTypeByEntityType(entityType);
        if (optionalClass.isPresent()) {
            return optionalClass;
        }
        /* mapperType 泛型寻找 */
        return identityTypeByMapperType(mapperType);
    }

    public Optional<Class<?>> findLinkage(@NonNull Class<?> mapperType, @Nullable Method mapperMethod, @NonNull Class<?> entityType) {
        /* entityType 泛型寻找 */
        Optional<Class<?>> optionalClass;
        if (mapperMethod != null) {
            /* 判断参数 */
            optionalClass = linkageTypeByParamTypes(mapperType, mapperMethod);
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
        }
        optionalClass = linkageTypeByEntityType(entityType);
        if (optionalClass.isPresent()) {
            return optionalClass;
        }
        /* mapperType 泛型寻找 */
        return linkageTypeByMapperType(mapperType);
    }

    public Optional<Class<?>> findAlertness(@NonNull Class<?> mapperType, @Nullable Method mapperMethod, @NonNull Class<?> entityType) {
        /* entityType 泛型寻找 */
        Optional<Class<?>> optionalClass;
        if (mapperMethod != null) {
            /* 判断参数 */
            optionalClass = alertnessTypeByParamTypes(mapperType, mapperMethod);
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
        }
        optionalClass = alertnessTypeByEntityType(entityType);
        if (optionalClass.isPresent()) {
            return optionalClass;
        }
        /* mapperType 泛型寻找 */
        return alertnessTypeByMapperType(mapperType);
    }

    /**
     * <code>entityTypeByReturnType</code>
     * <p>The entity type by return type method.</p>
     * @param mapperType   {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>The mapper method parameter is <code>Method</code> type.</p>
     * @return {@link java.util.Optional} <p>The entity type by return type return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.reflect.Method
     * @see java.util.Optional
     */
    protected Optional<Class<?>> entityTypeByReturnType(Class<?> mapperType, Method mapperMethod) {
        Class<?> returnType = MybatisGenericTypes.resolveMapperReturnType(mapperMethod, mapperType);
        return isEntity(returnType) ? Optional.of(returnType) : Optional.empty();
    }

    /**
     * <code>entityTypeByParamTypes</code>
     * <p>The entity type by param types method.</p>
     * @param mapperType   {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>The mapper method parameter is <code>Method</code> type.</p>
     * @return {@link java.util.Optional} <p>The entity type by param types return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.reflect.Method
     * @see java.util.Optional
     */
    protected Optional<Class<?>> entityTypeByParamTypes(Class<?> mapperType, Method mapperMethod) {
        return classOfByTypes(RestGenericTypes.resolveParamTypes(mapperMethod, mapperType), this::isEntity);
    }

    /**
     * <code>entityTypeByMapperMethod</code>
     * <p>The entity type by mapper method method.</p>
     * @param mapperType   {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>The mapper method parameter is <code>Method</code> type.</p>
     * @return {@link java.util.Optional} <p>The entity type by mapper method return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.reflect.Method
     * @see java.util.Optional
     */
    protected Optional<Class<?>> entityTypeByMapperMethod(Class<?> mapperType, Method mapperMethod) {
        return classOfByTypes(RestGenericTypes.resolveMethodTypes(mapperMethod, mapperType), this::isEntity);
    }

    /**
     * <code>identityTypeByEntityType</code>
     * <p>The identity type by entity type method.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @return {@link java.util.Optional} <p>The identity type by entity type return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.util.Optional
     */
    protected Optional<Class<?>> identityTypeByEntityType(Class<?> entityType) {
        return classOfByTypes(RestGenericTypes.resolveSuperclassTypes(entityType), this::isIdentity);
    }

    /**
     * <code>identityTypeByParamTypes</code>
     * <p>The identity type by param types method.</p>
     * @param mapperType   {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>The mapper method parameter is <code>Method</code> type.</p>
     * @return {@link java.util.Optional} <p>The identity type by param types return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.reflect.Method
     * @see java.util.Optional
     */
    protected Optional<Class<?>> identityTypeByParamTypes(Class<?> mapperType, Method mapperMethod) {
        return classOfByTypes(RestGenericTypes.resolveParamTypes(mapperMethod, mapperType), this::isIdentity);
    }

    /**
     * <code>linkageTypeByEntityType</code>
     * <p>The linkage type by entity type method.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @return {@link java.util.Optional} <p>The linkage type by entity type return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.util.Optional
     */
    protected Optional<Class<?>> linkageTypeByEntityType(Class<?> entityType) {
        return classOfByTypes(RestGenericTypes.resolveSuperclassTypes(entityType), this::isLinkage);
    }

    /**
     * <code>linkageTypeByParamTypes</code>
     * <p>The linkage type by param types method.</p>
     * @param mapperType   {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>The mapper method parameter is <code>Method</code> type.</p>
     * @return {@link java.util.Optional} <p>The linkage type by param types return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.reflect.Method
     * @see java.util.Optional
     */
    protected Optional<Class<?>> linkageTypeByParamTypes(Class<?> mapperType, Method mapperMethod) {
        return classOfByTypes(RestGenericTypes.resolveParamTypes(mapperMethod, mapperType), this::isLinkage);
    }

    /**
     * <code>alertnessTypeByEntityType</code>
     * <p>The alertness type by entity type method.</p>
     * @param entityType {@link java.lang.Class} <p>The entity type parameter is <code>Class</code> type.</p>
     * @return {@link java.util.Optional} <p>The alertness type by entity type return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.util.Optional
     */
    protected Optional<Class<?>> alertnessTypeByEntityType(Class<?> entityType) {
        return classOfByTypes(RestGenericTypes.resolveSuperclassTypes(entityType), this::isAlertness);
    }

    /**
     * <code>alertnessTypeByParamTypes</code>
     * <p>The alertness type by param types method.</p>
     * @param mapperType   {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>The mapper method parameter is <code>Method</code> type.</p>
     * @return {@link java.util.Optional} <p>The alertness type by param types return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.reflect.Method
     * @see java.util.Optional
     */
    protected Optional<Class<?>> alertnessTypeByParamTypes(Class<?> mapperType, Method mapperMethod) {
        return classOfByTypes(RestGenericTypes.resolveParamTypes(mapperMethod, mapperType), this::isAlertness);
    }

    /**
     * <code>entityTypeByMapperType</code>
     * <p>The entity type by mapper type method.</p>
     * @param mapperType {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @return {@link java.util.Optional} <p>The entity type by mapper type return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.util.Optional
     */
    protected Optional<Class<?>> entityTypeByMapperType(Class<?> mapperType) {
        return classOfByTypes(RestGenericTypes.resolveInterfaceTypes(mapperType), this::isEntity);
    }

    /**
     * <code>identityTypeByMapperType</code>
     * <p>The identity type by mapper type method.</p>
     * @param mapperType {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @return {@link java.util.Optional} <p>The identity type by mapper type return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.util.Optional
     */
    protected Optional<Class<?>> identityTypeByMapperType(Class<?> mapperType) {
        return classOfByTypes(RestGenericTypes.resolveInterfaceTypes(mapperType), this::isIdentity);
    }

    /**
     * <code>linkageTypeByMapperType</code>
     * <p>The linkage type by mapper type method.</p>
     * @param mapperType {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @return {@link java.util.Optional} <p>The linkage type by mapper type return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.util.Optional
     */
    protected Optional<Class<?>> linkageTypeByMapperType(Class<?> mapperType) {
        return classOfByTypes(RestGenericTypes.resolveInterfaceTypes(mapperType), this::isLinkage);
    }

    /**
     * <code>alertnessTypeByMapperType</code>
     * <p>The alertness type by mapper type method.</p>
     * @param mapperType {@link java.lang.Class} <p>The mapper type parameter is <code>Class</code> type.</p>
     * @return {@link java.util.Optional} <p>The alertness type by mapper type return object is <code>Optional</code> type.</p>
     * @see java.lang.Class
     * @see java.util.Optional
     */
    protected Optional<Class<?>> alertnessTypeByMapperType(Class<?> mapperType) {
        return classOfByTypes(RestGenericTypes.resolveInterfaceTypes(mapperType), this::isAlertness);
    }


    /**
     * <code>classOfByType</code>
     * <p>The class of by type method.</p>
     * @param type      {@link java.lang.reflect.Type} <p>The type parameter is <code>Type</code> type.</p>
     * @param predicate {@link java.util.function.Predicate} <p>The predicate parameter is <code>Predicate</code> type.</p>
     * @return {@link java.util.Optional} <p>The class of by type return object is <code>Optional</code> type.</p>
     * @see java.lang.reflect.Type
     * @see java.util.function.Predicate
     * @see java.util.Optional
     */
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

    /**
     * <code>classOfByTypes</code>
     * <p>The class of by types method.</p>
     * @param types     {@link java.lang.reflect.Type} <p>The types parameter is <code>Type</code> type.</p>
     * @param predicate {@link java.util.function.Predicate} <p>The predicate parameter is <code>Predicate</code> type.</p>
     * @return {@link java.util.Optional} <p>The class of by types return object is <code>Optional</code> type.</p>
     * @see java.lang.reflect.Type
     * @see java.util.function.Predicate
     * @see java.util.Optional
     */
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
