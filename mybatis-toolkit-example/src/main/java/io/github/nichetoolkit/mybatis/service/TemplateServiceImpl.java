package io.github.nichetoolkit.mybatis.service;

import io.github.nichetoolkit.mybatis.simple.TemplateEntity;
import io.github.nichetoolkit.mybatis.simple.TemplateFilter;
import io.github.nichetoolkit.mybatis.simple.TemplateIdentity;
import io.github.nichetoolkit.mybatis.simple.TemplateModel;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rice.DefaultInfoService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * <code>TemplateServiceImpl</code>
 * <p>The template service class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultInfoService
 * @see org.springframework.stereotype.Service
 * @since Jdk1.8
 */
@Service
public class TemplateServiceImpl extends DefaultInfoService<TemplateModel, TemplateEntity, TemplateFilter, TemplateIdentity, String> implements TemplateService {

    @Override
    protected void optionalInit(@NonNull TemplateModel model) throws RestException {
        model.setTime(Optional.ofNullable(model.getTime()).orElse(new Date()));
    }

    @Override
    protected String dynamicTablename(@NonNull String tablekey) throws RestException {
        return "ntr_template".concat(tablekey);
    }

    @Override
    public String queryWhereSql(TemplateFilter filter) throws RestException {
        return filter.toTimeSql("time").toIdSql("id").addSorts("template_pk1", "template_pk2").toSql();
    }

}
