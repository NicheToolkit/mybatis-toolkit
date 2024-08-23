package io.github.nichetoolkit.mybatis.resolver;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

/**
 * <p>MybatisWildcardType</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
public class MybatisWildcardType implements WildcardType {
    private final Type[] lowerBounds;

    private final Type[] upperBounds;

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
