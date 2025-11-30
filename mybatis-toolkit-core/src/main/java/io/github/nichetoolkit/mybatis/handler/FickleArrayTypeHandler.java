package io.github.nichetoolkit.mybatis.handler;

import io.github.nichetoolkit.rest.holder.ObjectMapperHolder;
import tools.jackson.databind.type.ArrayType;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rest.util.JsonUtils;

/**
 * <code>FickleArrayTypeHandler</code>
 * <p>The fickle array type handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.handler.FickleResultTypeHandler
 * @since Jdk17
 */
public class FickleArrayTypeHandler extends FickleResultTypeHandler {

    /**
     * <code>ARRAY_TYPE</code>
     * {@link tools.jackson.databind.type.ArrayType} <p>The constant <code>ARRAY_TYPE</code> field.</p>
     * @see tools.jackson.databind.type.ArrayType
     */
    private static final ArrayType ARRAY_TYPE = ObjectMapperHolder.typeFactory().constructArrayType(RestFickle.OfRestFickle.class);

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
