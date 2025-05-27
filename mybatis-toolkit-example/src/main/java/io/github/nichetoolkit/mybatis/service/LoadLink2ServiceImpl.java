package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.test.load.LoadLink2Entity;
import io.github.nichetoolkit.mybatis.test.load.LoadLink2Model;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rice.RestFilter;
import io.github.nichetoolkit.rice.RestInfoService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class LoadLink2ServiceImpl extends RestInfoService<LoadLink2Model, LoadLink2Entity, RestFilter> implements LoadLink2Service {

    @Override
    protected void optionalInit(@NonNull LoadLink2Model model) throws RestException {
        model.setTime(Optional.ofNullable(model.getTime()).orElse(new Date()));
    }

    @Override
    public String queryWhereSql(RestFilter filter) throws RestException {
        return filter.toTimeSql("time").toIdSql("id").addSorts("time").toSql();
    }

}
