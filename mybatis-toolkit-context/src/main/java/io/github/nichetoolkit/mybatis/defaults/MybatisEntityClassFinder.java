package io.github.nichetoolkit.mybatis.defaults;


import io.github.nichetoolkit.mybatis.MybatisClassFinder;
import io.github.nichetoolkit.mybatis.resolver.MybatisGenericTypeResolver;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * <code>MybatisEntityClassFinder</code>
 * <p>The type mybatis entity class finder class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisClassFinder
 * @since Jdk1.8
 */
public abstract class MybatisEntityClassFinder implements MybatisClassFinder {

    @Override
    public Optional<Class<?>> findEntity(Class<?> mapperType, Method mapperMethod) {
        /** 先判断返回值 */
        Optional<Class<?>> optionalClass;
        if (mapperMethod != null) {
            optionalClass = getEntityClassByMapperMethodReturnType(mapperType, mapperMethod);
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
            /** 再判断参数 */
            optionalClass = getEntityClassByMapperMethodParamTypes(mapperType, mapperMethod);
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
            /** 最后从接口泛型中获取 */
            optionalClass = getEntityClassByMapperMethodAndMapperType(mapperType, mapperMethod);
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
        }
        return getEntityClassByMapperType(mapperType);
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
        Class<?> returnType = MybatisGenericTypeResolver.resolveMapperReturnType(mapperMethod, mapperType);
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
        return getEntityClassByTypes(MybatisGenericTypeResolver.resolveParamTypes(mapperMethod, mapperType));
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
        return getEntityClassByTypes(MybatisGenericTypeResolver.resolveMapperTypes(mapperMethod, mapperType));
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
        return getEntityClassByTypes(MybatisGenericTypeResolver.resolveMapperTypes(mapperType));
    }

    /**
     * <code>getEntityClassByType</code>
     * <p>the entity class by type getter method.</p>
     * @param type {@link java.lang.reflect.Type} <p>the type parameter is <code>Type</code> type.</p>
     * @return {@link java.util.Optional} <p>the entity class by type return object is <code>Optional</code> type.</p>
     * @see java.lang.reflect.Type
     * @see java.util.Optional
     */
    protected Optional<Class<?>> getEntityClassByType(Type type) {
        if (type instanceof Class) {
            if (isEntity((Class<?>) type)) {
                return Optional.of((Class<?>) type);
            }
        } else if (type instanceof MybatisParameterizedType) {
            return getEntityClassByTypes(((MybatisParameterizedType) type).getActualTypeArguments());
        } else if (type instanceof MybatisWildcardType) {
            Optional<Class<?>> optionalClass = getEntityClassByTypes(((MybatisWildcardType) type).getLowerBounds());
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
            return getEntityClassByTypes(((MybatisWildcardType) type).getUpperBounds());
        } else if (type instanceof MybatisGenericArrayType) {
            return getEntityClassByType(((MybatisGenericArrayType) type).getGenericComponentType());
        }
        return Optional.empty();
    }

    /**
     * <code>getEntityClassByTypes</code>
     * <p>the entity class by types getter method.</p>
     * @param types {@link java.lang.reflect.Type} <p>the types parameter is <code>Type</code> type.</p>
     * @return {@link java.util.Optional} <p>the entity class by types return object is <code>Optional</code> type.</p>
     * @see java.lang.reflect.Type
     * @see java.util.Optional
     */
    protected Optional<Class<?>> getEntityClassByTypes(Type[] types) {
        for (Type type : types) {
            Optional<Class<?>> optionalClass = getEntityClassByType(type);
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
        }
        return Optional.empty();
    }
}
