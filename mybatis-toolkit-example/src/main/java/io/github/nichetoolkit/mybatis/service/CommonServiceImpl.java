package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.test.common.CommonEntity;
import io.github.nichetoolkit.mybatis.test.common.CommonFilter;
import io.github.nichetoolkit.mybatis.test.common.CommonModel;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rice.RestInfoService;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * <code>CommonServiceImpl</code>
 * <p>The common service class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestInfoService
 * @see org.springframework.stereotype.Service
 * @since Jdk17
 */
@Service
public class CommonServiceImpl extends RestInfoService<CommonModel, CommonEntity, CommonFilter> implements CommonService {

    @Override
    protected void optionalInit(@NonNull CommonModel model) throws RestException {
        model.setTime(Optional.ofNullable(model.getTime()).orElse(new Date()));
    }

    @Override
    public String queryWhereSql(CommonFilter filter) throws RestException {
        return filter.toTimeSql("time").toIdSql("id").addSorts("id").toSql();
    }
}
