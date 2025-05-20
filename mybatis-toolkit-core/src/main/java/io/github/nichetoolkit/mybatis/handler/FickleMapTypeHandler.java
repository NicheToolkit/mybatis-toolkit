package io.github.nichetoolkit.mybatis.handler;

import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.error.natives.UnsupportedErrorException;
import io.github.nichetoolkit.rest.stream.RestCollectors;
import io.github.nichetoolkit.rest.stream.RestStream;
import io.github.nichetoolkit.rest.util.JsonUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * @since Jdk1.8
 */
public class FickleMapTypeHandler extends FickleResultTypeHandler {

    /**
     * <code>COLLECTION_TYPE</code>
     * {@link com.fasterxml.jackson.databind.type.CollectionType} <p>The constant <code>COLLECTION_TYPE</code> field.</p>
     * @see com.fasterxml.jackson.databind.type.CollectionType
     */
    private static final CollectionType COLLECTION_TYPE = TypeFactory.defaultInstance().constructCollectionType(List.class, RestFickle.OfRestFickle.class);

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
