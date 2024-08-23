package io.github.nichetoolkit.mybatis.resolver;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

/**
 * <p>MybatisGenericArrayType</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisGenericArrayType implements GenericArrayType {
    private final Type genericComponentType;

    MybatisGenericArrayType(Type genericComponentType) {
        super();
        this.genericComponentType = genericComponentType;
    }

    @Override
    public Type getGenericComponentType() {
        return genericComponentType;
    }
}