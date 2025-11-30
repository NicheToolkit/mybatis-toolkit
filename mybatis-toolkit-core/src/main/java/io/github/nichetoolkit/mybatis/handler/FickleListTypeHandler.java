package io.github.nichetoolkit.mybatis.handler;

import io.github.nichetoolkit.rest.holder.ObjectMapperHolder;
import tools.jackson.databind.type.CollectionType;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rest.util.JsonUtils;

import java.util.List;

/**
 * <code>FickleListTypeHandler</code>
 * <p>The fickle list type handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.handler.FickleResultTypeHandler
 * @since Jdk17
 */
public class FickleListTypeHandler extends FickleResultTypeHandler {

    /**
     * <code>COLLECTION_TYPE</code>
     * {@link tools.jackson.databind.type.CollectionType} <p>The constant <code>COLLECTION_TYPE</code> field.</p>
     * @see tools.jackson.databind.type.CollectionType
     */
    private static final CollectionType COLLECTION_TYPE = ObjectMapperHolder.typeFactory().constructCollectionType(List.class, RestFickle.OfRestFickle.class);

    /**
     * <code>FickleListTypeHandler</code>
     * <p>Instantiates a new fickle list type handler.</p>
     */
    FickleListTypeHandler() {
    }

    @Override
    Object parseResultJson(String json) {
        return JsonUtils.parseList(json,COLLECTION_TYPE);
    }
}
