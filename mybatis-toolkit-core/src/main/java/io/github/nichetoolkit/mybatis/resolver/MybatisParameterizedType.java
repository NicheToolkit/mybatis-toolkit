package io.github.nichetoolkit.mybatis.resolver;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * <p>ParameterizedTypeResolver</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisParameterizedType implements ParameterizedType {
    private final Class<?> rawType;

    private final Type ownerType;

    private final Type[] actualTypeArguments;

    public MybatisParameterizedType(Class<?> rawType, Type ownerType, Type[] actualTypeArguments) {
        super();
        this.rawType = rawType;
        this.ownerType = ownerType;
        this.actualTypeArguments = actualTypeArguments;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return actualTypeArguments;
    }

    @Override
    public Type getOwnerType() {
        return ownerType;
    }

    @Override
    public Type getRawType() {
        return rawType;
    }

    @Override
    public String toString() {
        return "ParameterizedTypeImpl [rawType=" + rawType + ", ownerType=" + ownerType + ", actualTypeArguments=" + Arrays.toString(actualTypeArguments) + "]";
    }
}