package io.github.nichetoolkit.mybatis.defaults;

import io.github.nichetoolkit.mybatis.MybatisClassFinder;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * <p>DefaultEntityClassFinder</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
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
     * 根据方法返回值获取
     * @param mapperType   接口
     * @param mapperMethod 方法
     * @return 实体类类型
     */
    protected Optional<Class<?>> getEntityClassByMapperMethodReturnType(Class<?> mapperType, Method mapperMethod) {
        Class<?> returnType = MybatisGenericTypeResolver.resolveMapperReturnType(mapperMethod, mapperType);
        return isEntity(returnType) ? Optional.of(returnType) : Optional.empty();
    }

    /**
     * 根据方法参数获取
     * @param mapperType   接口
     * @param mapperMethod 方法
     * @return 实体类类型
     */
    protected Optional<Class<?>> getEntityClassByMapperMethodParamTypes(Class<?> mapperType, Method mapperMethod) {
        return getEntityClassByTypes(MybatisGenericTypeResolver.resolveParamTypes(mapperMethod, mapperType));
    }

    /**
     * 根据方法所在接口泛型获取，只有定义在泛型接口中的方法才能通过泛型获取，定义的最终使用类中的无法通过泛型获取
     * @param mapperType   接口
     * @param mapperMethod 方法
     * @return 实体类类型
     */
    protected Optional<Class<?>> getEntityClassByMapperMethodAndMapperType(Class<?> mapperType, Method mapperMethod) {
        return getEntityClassByTypes(MybatisGenericTypeResolver.resolveMapperTypes(mapperMethod, mapperType));
    }

    /**
     * 根据接口泛型获取，当前方法只根据接口泛型获取实体类，和当前执行的方法无关，是优先级最低的一种情况
     * @param mapperType 接口
     * @return 实体类类型
     */
    protected Optional<Class<?>> getEntityClassByMapperType(Class<?> mapperType) {
        return getEntityClassByTypes(MybatisGenericTypeResolver.resolveMapperTypes(mapperType));
    }

    /**
     * 根据 type 获取可能的实体类型
     * @param type 类型
     * @return 实体类类型
     */
    protected Optional<Class<?>> getEntityClassByType(Type type) {
        if (type instanceof Class) {
            if (isEntity((Class<?>) type)) {
                return Optional.of((Class<?>) type);
            }
        } else if (type instanceof MybatisGenericTypeResolver.ParameterizedTypeImpl) {
            return getEntityClassByTypes(((MybatisGenericTypeResolver.ParameterizedTypeImpl) type).getActualTypeArguments());
        } else if (type instanceof MybatisGenericTypeResolver.WildcardTypeImpl) {
            Optional<Class<?>> optionalClass = getEntityClassByTypes(((MybatisGenericTypeResolver.WildcardTypeImpl) type).getLowerBounds());
            if (optionalClass.isPresent()) {
                return optionalClass;
            }
            return getEntityClassByTypes(((MybatisGenericTypeResolver.WildcardTypeImpl) type).getUpperBounds());
        } else if (type instanceof MybatisGenericTypeResolver.GenericArrayTypeImpl) {
            return getEntityClassByType(((MybatisGenericTypeResolver.GenericArrayTypeImpl) type).getGenericComponentType());
        }
        return Optional.empty();
    }

    /**
     * 遍历 types 获取可能的实体类型
     * @param types 类型
     * @return 实体类类型
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
