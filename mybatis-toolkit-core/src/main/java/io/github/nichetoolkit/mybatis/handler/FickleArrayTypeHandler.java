package io.github.nichetoolkit.mybatis.handler;

import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rest.util.JsonUtils;

/**
 * <code>FickleArrayTypeHandler</code>
 * <p>The fickle array type handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.handler.FickleResultTypeHandler
 * @since Jdk1.8
 */
public class FickleArrayTypeHandler extends FickleResultTypeHandler {

    /**
     * <code>ARRAY_TYPE</code>
     * {@link com.fasterxml.jackson.databind.type.ArrayType} <p>The constant <code>ARRAY_TYPE</code> field.</p>
     * @see com.fasterxml.jackson.databind.type.ArrayType
     */
    private static final ArrayType ARRAY_TYPE = TypeFactory.defaultInstance().constructArrayType(RestFickle.OfRestFickle.class);

    /**
     * <code>FickleArrayTypeHandler</code>
     * <p>Instantiates a new fickle array type handler.</p>
     */
    FickleArrayTypeHandler() {
    }

    @Override
    Object parseResultJson(String json) {
        return JsonUtils.parseArray(json, ARRAY_TYPE);
    }

}
