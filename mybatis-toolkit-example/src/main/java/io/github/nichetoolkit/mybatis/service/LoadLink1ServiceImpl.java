package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.test.load.*;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rice.RestFilter;
import io.github.nichetoolkit.rice.RestInfoService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * <code>LoadLink1ServiceImpl</code>
 * <p>The load link 1 service class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestInfoService
 * @see org.springframework.stereotype.Service
 * @since Jdk1.8
 */
@Service
public class LoadLink1ServiceImpl extends RestInfoService<LoadLink1Model, LoadLink1Entity, RestFilter> implements LoadLink1Service {

    @Override
    protected void optionalInit(@NonNull LoadLink1Model model) throws RestException {
        model.setTime(Optional.ofNullable(model.getTime()).orElse(new Date()));
    }

    @Override
    public String queryWhereSql(RestFilter filter) throws RestException {
        return filter.toTimeSql("time").toIdSql("id").addSorts("time").toSql();
    }

}
