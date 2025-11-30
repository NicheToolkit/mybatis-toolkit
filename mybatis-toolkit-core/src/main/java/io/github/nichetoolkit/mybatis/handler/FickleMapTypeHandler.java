package io.github.nichetoolkit.mybatis.handler;

import io.github.nichetoolkit.rest.holder.ObjectMapperHolder;
import tools.jackson.databind.type.CollectionType;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.util.JsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <code>FickleMapTypeHandler</code>
 * <p>The fickle map type handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.handler.FickleResultTypeHandler
 * @since Jdk17
 */
public class FickleMapTypeHandler extends FickleResultTypeHandler {

    /**
     * <code>COLLECTION_TYPE</code>
     * {@link tools.jackson.databind.type.CollectionType} <p>The constant <code>COLLECTION_TYPE</code> field.</p>
     * @see tools.jackson.databind.type.CollectionType
     */
    private static final CollectionType COLLECTION_TYPE = ObjectMapperHolder.typeFactory().constructCollectionType(List.class, RestFickle.OfRestFickle.class);

    /**
     * <code>FickleMapTypeHandler</code>
     * <p>Instantiates a new fickle map type handler.</p>
     */
    FickleMapTypeHandler() {
    }

    @Override
    Object parseResultJson(String json) {
        return parseFickleMap(json);
    }

    /**
     * <code>parseFickleMap</code>
     * <p>The parse fickle map method.</p>
     * @param json {@link java.lang.String} <p>The json parameter is <code>String</code> type.</p>
     * @return {@link java.util.Map} <p>The parse fickle map return object is <code>Map</code> type.</p>
     * @see java.lang.String
     * @see java.util.Map
     */
    protected Map<String, RestFickle.OfRestFickle<?>> parseFickleMap(String json) {
        List<RestFickle.OfRestFickle<?>> fickleList = JsonUtils.parseList(json, COLLECTION_TYPE);
        return RestOptional.ofEmptyable(fickleList).map(fickles ->
                        fickles.stream().collect(Collectors.toMap(RestFickle.OfRestFickle::getKey, Function.identity())))
                .orElse(new HashMap<>());
    }
}
