package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.simple.SimpleEntity;
import io.github.nichetoolkit.mybatis.simple.SimpleFilter;
import io.github.nichetoolkit.mybatis.simple.SimpleModel;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.RiceInfoService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>SimpleServiceImpl</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Service
//@RestService(mapper = SimpleMapper.class)
public class SimpleServiceImpl extends RiceInfoService<SimpleModel, SimpleEntity, SimpleFilter> implements SimpleService {

    @Override
    protected void optionalInit(@NonNull SimpleModel model) throws RestException {
        if (GeneralUtils.isNotEmpty(model.getTime())) {
            model.setTime(new Date());
        }
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
