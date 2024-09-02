package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.simple.TemplateEntity;
import io.github.nichetoolkit.mybatis.simple.TemplateFilter;
import io.github.nichetoolkit.mybatis.simple.TemplateKey;
import io.github.nichetoolkit.mybatis.simple.TemplateModel;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.RestInfoService;
import io.github.nichetoolkit.rice.RiceInfoService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * <code>TemplateServiceImpl</code>
 * <p>The type template service class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestInfoService
 * @see org.springframework.stereotype.Service
 * @since Jdk1.8
 */
@Service
public class TemplateServiceImpl extends RestInfoService<String, TemplateKey,TemplateModel, TemplateEntity, TemplateFilter> implements TemplateService {

    @Override
    protected void optionalInit(@NonNull TemplateModel model) throws RestException {
        model.setTime(Optional.ofNullable(model.getTime()).orElse(new Date()));
    }

    @Override
    protected String dynamicTablename(@NonNull String tablekey) throws RestException {
        return "ntr_simple".concat(tablekey);
    }

    @Override
    public String queryWhereSql(TemplateFilter filter) throws RestException {
        return filter.toTimeSql("time").toIdSql("id").addSorts("id").toSql();
    }
}
