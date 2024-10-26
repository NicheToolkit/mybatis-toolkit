package io.github.nichetoolkit.mybatis.simple;


import io.github.nichetoolkit.mybatis.enums.SimpleStatus;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.RestFilter;
import io.github.nichetoolkit.rice.filter.StatusFilter;
import org.springframework.lang.NonNull;

import java.util.*;

public class SimpleFilter extends RestFilter implements StatusFilter<SimpleStatus> {

    private SimpleStatus status;

    private Set<SimpleStatus> statuses;

    @Override
    public SimpleStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(SimpleStatus status) {
        this.status = status;
    }

    @Override
    public List<SimpleStatus> getStatuses() {
        if (GeneralUtils.isNotEmpty(statuses)) {
            return new ArrayList<>(statuses);
        }
        return Collections.emptyList();
    }

    @Override
    public void setStatuses(@NonNull Collection<SimpleStatus> statuses) {
        this.statuses = new HashSet<>(statuses);
    }

    @Override
    public void setStatuses(@NonNull SimpleStatus... statuses) {
        this.statuses = new HashSet<>(Arrays.asList(statuses));
    }

    public void addStatuses(@NonNull SimpleStatus... statuses) {
        if (GeneralUtils.isEmpty(this.statuses)) {
            this.statuses = new HashSet<>(Arrays.asList(statuses));
        } else {
            this.statuses.addAll(Arrays.asList(statuses));
        }
    }

    public void addStatuses(@NonNull Collection<SimpleStatus> statuses) {
        if (GeneralUtils.isEmpty(this.statuses)) {
            this.statuses = new HashSet<>(statuses);
        } else {
            this.statuses.addAll(statuses);
        }
    }

    @Override
    public StatusFilter<SimpleStatus> toStatusSql() {
        return toStatusSql("status");
    }

    @Override
    public StatusFilter<SimpleStatus> toStatusSql(@NonNull String alias) {
        return toStatusSql(SQL_BUILDER,alias);
    }
}
