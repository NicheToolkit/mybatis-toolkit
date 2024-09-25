package io.github.nichetoolkit.mybatis.resolver;

import io.github.nichetoolkit.rest.reflect.RestGenericTypes;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.cursor.Cursor;

import java.lang.reflect.*;
import java.util.Collection;

import java.util.*;

/**
 * <code>MybatisGenericTypeResolver</code>
 * <p>The type mybatis generic type resolver class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class MybatisGenericTypes {

    /**
     * <code>resolveMapperReturnType</code>
     * <p>the mapper return type method.</p>
     * @param mapperMethod {@link java.lang.reflect.Method} <p>the mapper method parameter is <code>Method</code> type.</p>
     * @param mapperType   {@link java.lang.Class} <p>the mapper type parameter is <code>Class</code> type.</p>
     * @return {@link java.lang.Class} <p>the mapper return type return object is <code>Class</code> type.</p>
     * @see java.lang.reflect.Method
     * @see java.lang.Class
     */
    public static Class<?> resolveMapperReturnType(Method mapperMethod, Class<?> mapperType) {
        Class<?> returnType = mapperMethod.getReturnType();
        Type resolvedReturnType = RestGenericTypes.resolveReturnType(mapperMethod, mapperType);
        if (resolvedReturnType instanceof Class) {
            /* resolvedReturnType 为具体类型 */
            returnType = (Class<?>) resolvedReturnType;
            if (returnType.isArray()) {
                /* returnType 为数组类型 byte[]*/
                returnType = returnType.getComponentType();
            }
            if (void.class.equals(returnType)) {
                /* returnType 为void类型 时校验 ResultType注解类型 */
                ResultType resultTypeAnnotation = mapperMethod.getAnnotation(ResultType.class);
                if (resultTypeAnnotation != null) {
                    returnType = resultTypeAnnotation.value();
                }
            }
        } else if (resolvedReturnType instanceof ParameterizedType) {
            /* resolvedReturnType为 参数化带有泛型类型  List<Object> */
            ParameterizedType parameterizedType = (ParameterizedType) resolvedReturnType;
            Class<?> rawType = (Class<?>) parameterizedType.getRawType();
            if (Collection.class.isAssignableFrom(rawType) || Cursor.class.isAssignableFrom(rawType)) {
                /* 如果为Java的Collection类型{@link Collection} 或者 mybatis的游标类型 {@link Cursor}*/
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length == 1) {
                    Type returnTypeParameter = actualTypeArguments[0];
                    if (returnTypeParameter instanceof Class<?>) {
                        /* returnTypeParameter 为具体类型 Object */
                        returnType = (Class<?>) returnTypeParameter;
                    } else if (returnTypeParameter instanceof ParameterizedType) {
                        /* returnTypeParameter 为参数化带有泛型类型 List<Object> */
                        // (code issue #443) actual type can be and also a parameterized type
                        returnType = (Class<?>) ((ParameterizedType) returnTypeParameter).getRawType();
                    } else if (returnTypeParameter instanceof GenericArrayType) {
                        /* returnTypeParameter 支持参数化带有数组类型 List<byte[]>*/
                        Class<?> componentType = (Class<?>) ((GenericArrayType) returnTypeParameter).getGenericComponentType();
                        // (code issue #525) support List<byte[]>
                        returnType = Array.newInstance(componentType, 0).getClass();
                    }
                }
            } else if (mapperMethod.isAnnotationPresent(MapKey.class) && Map.class.isAssignableFrom(rawType)) {
                /* 如果为Java的Map类型{@link Map} Map<Key,Value></>*/
                // (code issue 504) Do not look into Maps if there is not MapKey annotation
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length == 2) {
                    Type returnTypeParameter = actualTypeArguments[1];
                    if (returnTypeParameter instanceof Class<?>) {
                        returnType = (Class<?>) returnTypeParameter;
                    } else if (returnTypeParameter instanceof ParameterizedType) {
                        // (code issue 443) actual type can be and also a parameterized type
                        returnType = (Class<?>) ((ParameterizedType) returnTypeParameter).getRawType();
                    }
                }
            } else if (Optional.class.equals(rawType)) {
                /* 如果为Java的Map类型{@link Optional} Optional<Object> */
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                Type returnTypeParameter = actualTypeArguments[0];
                if (returnTypeParameter instanceof Class<?>) {
                    returnType = (Class<?>) returnTypeParameter;
                }
            }
        }
        return returnType;
    }

}
