package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.simple.SimpleEntity;
import io.github.nichetoolkit.mybatis.simple.SimpleFilter;
import io.github.nichetoolkit.mybatis.simple.SimpleModel;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestKey;
import io.github.nichetoolkit.rice.RestInfoService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <code>SimpleServiceImpl</code>
 * <p>The simple service class.</p>
 * @see  io.github.nichetoolkit.rice.RestInfoService
 * @see  org.springframework.stereotype.Service
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Service
public class SimpleServiceImpl extends RestInfoService<SimpleModel, SimpleEntity, SimpleFilter> implements SimpleService {

    @Override
    protected void optionalInit(@NonNull SimpleModel model) throws RestException {
        model.setTime(Optional.ofNullable(model.getTime()).orElse(new Date()));
    }

    @Override
    protected String dynamicTablename(@NonNull String tablekey) throws RestException {
        return "ntr_simple".concat(tablekey);
    }

    @Override
    public String queryWhereSql(SimpleFilter filter) throws RestException {
        return filter.toTimeSql("time").toIdSql("id").addSorts("id").toSql();
    }
}
