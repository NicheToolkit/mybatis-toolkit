package io.github.nichetoolkit.mybatis.test.fickle;

import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rice.RestInfoService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * <code>FickleEntryServiceImpl</code>
 * <p>The fickle entry service class.</p>
 * @see  io.github.nichetoolkit.rice.RestInfoService
 * @see  org.springframework.stereotype.Service
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@Service
public class FickleEntryServiceImpl extends RestInfoService<FickleEntryModel, FickleEntryEntity, FickleEntryFilter> implements FickleEntryService {

    @Override
    protected void optionalInit(@NonNull FickleEntryModel model) throws RestException {
        model.setTime(Optional.ofNullable(model.getTime()).orElse(new Date()));
    }

    @Override
    public String queryWhereSql(FickleEntryFilter filter) throws RestException {
        return filter.toTimeSql("time").toIdSql("id").addSorts("id").toSql();
    }
}
