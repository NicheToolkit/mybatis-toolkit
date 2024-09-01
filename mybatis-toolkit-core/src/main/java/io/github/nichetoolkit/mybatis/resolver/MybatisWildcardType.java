package io.github.nichetoolkit.mybatis.resolver;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

/**
 * <code>MybatisWildcardType</code>
 * <p>The type mybatis wildcard type class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.lang.reflect.WildcardType
 * @since Jdk1.8
 */
public class MybatisWildcardType implements WildcardType {
    private final Type[] lowerBounds;

    private final Type[] upperBounds;

    /**
     * <code>MybatisWildcardType</code>
     * Instantiates a new mybatis wildcard type.
     * @param lowerBounds {@link java.lang.reflect.Type} <p>the lower bounds parameter is <code>Type</code> type.</p>
     * @param upperBounds {@link java.lang.reflect.Type} <p>the upper bounds parameter is <code>Type</code> type.</p>
     * @see java.lang.reflect.Type
     */
    MybatisWildcardType(Type[] lowerBounds, Type[] upperBounds) {
        super();
        this.lowerBounds = lowerBounds;
        this.upperBounds = upperBounds;
    }

    @Override
    public Type[] getLowerBounds() {
        return lowerBounds;
    }

    @Override
    public Type[] getUpperBounds() {
        return upperBounds;
    }
}
