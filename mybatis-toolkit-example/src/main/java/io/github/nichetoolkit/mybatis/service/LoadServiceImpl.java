package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.test.load.LoadEntity;
import io.github.nichetoolkit.mybatis.test.load.LoadFilter;
import io.github.nichetoolkit.mybatis.test.load.LoadIdentity;
import io.github.nichetoolkit.mybatis.test.load.LoadModel;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rice.DefaultInfoService;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * <code>LoadServiceImpl</code>
 * <p>The load service class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultInfoService
 * @see org.springframework.stereotype.Service
 * @since Jdk17
 */
@Service
public class LoadServiceImpl extends DefaultInfoService<LoadModel, LoadEntity, LoadFilter, LoadIdentity, String> implements LoadService {

    @Override
    protected void optionalInit(@NonNull LoadModel model) throws RestException {
        model.setTime(Optional.ofNullable(model.getTime()).orElse(new Date()));
    }

    @Override
    public String queryWhereSql(LoadFilter filter) throws RestException {
        return filter.toTimeSql("time").toIdSql("id").addSorts("load_pk1", "load_pk2").toSql();
    }

}
