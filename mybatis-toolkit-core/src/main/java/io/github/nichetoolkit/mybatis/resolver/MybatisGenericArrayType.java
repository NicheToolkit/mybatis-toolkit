package io.github.nichetoolkit.mybatis.resolver;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

/**
 * <code>MybatisGenericArrayType</code>
 * <p>The type mybatis generic array type class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.lang.reflect.GenericArrayType
 * @since Jdk1.8
 */
public class MybatisGenericArrayType implements GenericArrayType {
    /**
     * <code>genericComponentType</code>
     * {@link java.lang.reflect.Type} <p>the <code>genericComponentType</code> field.</p>
     * @see java.lang.reflect.Type
     */
    private final Type genericComponentType;

    /**
     * <code>MybatisGenericArrayType</code>
     * Instantiates a new mybatis generic array type.
     * @param genericComponentType {@link java.lang.reflect.Type} <p>the generic component type parameter is <code>Type</code> type.</p>
     * @see java.lang.reflect.Type
     */
    MybatisGenericArrayType(Type genericComponentType) {
        super();
        this.genericComponentType = genericComponentType;
    }

    @Override
    public Type getGenericComponentType() {
        return genericComponentType;
    }
}