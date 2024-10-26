package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.DefaultFilter;
import io.github.nichetoolkit.rice.filter.StatusFilter;
import org.springframework.lang.NonNull;

import java.util.*;

public class TemplateFilter extends DefaultFilter<TemplateIdentity,String> implements StatusFilter<TemplateAlertness> {

    private TemplateAlertness status;

    private Set<TemplateAlertness> statuses;
    
    public TemplateFilter() {
    }

    @Override
    public TemplateAlertness getStatus() {
        return status;
    }

    @Override
    public void setStatus(TemplateAlertness status) {
        this.status = status;
    }

    @Override
    public List<TemplateAlertness> getStatuses() {
        if (GeneralUtils.isNotEmpty(statuses)) {
            return new ArrayList<>(statuses);
        }
        return Collections.emptyList();
    }

    @Override
    public void setStatuses(@NonNull Collection<TemplateAlertness> statuses) {
        this.statuses = new HashSet<>(statuses);
    }

    @Override
    public void setStatuses(@NonNull TemplateAlertness... statuses) {
        this.statuses = new HashSet<>(Arrays.asList(statuses));
    }

    public void addStatuses(@NonNull TemplateAlertness... statuses) {
        if (GeneralUtils.isEmpty(this.statuses)) {
            this.statuses = new HashSet<>(Arrays.asList(statuses));
        } else {
            this.statuses.addAll(Arrays.asList(statuses));
        }
    }

    public void addStatuses(@NonNull Collection<TemplateAlertness> statuses) {
        if (GeneralUtils.isEmpty(this.statuses)) {
            this.statuses = new HashSet<>(statuses);
        } else {
            this.statuses.addAll(statuses);
        }
    }

    @Override
    public StatusFilter<TemplateAlertness> toStatusSql() {
        return toStatusSql("status.");
    }

    @Override
    public StatusFilter<TemplateAlertness> toStatusSql(@NonNull String alias) {
        return toStatusSql(SQL_BUILDER,alias);
    }
}
