package io.github.nichetoolkit.mybatis.simple;


import io.github.nichetoolkit.mybatis.enums.SimpleStatus;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.RestFilter;
import io.github.nichetoolkit.rice.filter.StatusFilter;
import org.springframework.lang.NonNull;

import java.util.*;

/**
 * <code>SimpleFilter</code>
 * <p>The simple filter class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestFilter
 * @see io.github.nichetoolkit.rice.filter.StatusFilter
 * @since Jdk1.8
 */
public class SimpleFilter extends RestFilter implements StatusFilter<SimpleStatus> {

    /**
     * <code>status</code>
     * {@link io.github.nichetoolkit.mybatis.enums.SimpleStatus} <p>The <code>status</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.enums.SimpleStatus
     */
    private SimpleStatus status;

    /**
     * <code>statuses</code>
     * {@link java.util.Set} <p>The <code>statuses</code> field.</p>
     * @see java.util.Set
     */
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

    /**
     * <code>addStatuses</code>
     * <p>The add statuses method.</p>
     * @param statuses {@link io.github.nichetoolkit.mybatis.enums.SimpleStatus} <p>The statuses parameter is <code>SimpleStatus</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.enums.SimpleStatus
     * @see org.springframework.lang.NonNull
     */
    public void addStatuses(@NonNull SimpleStatus... statuses) {
        if (GeneralUtils.isEmpty(this.statuses)) {
            this.statuses = new HashSet<>(Arrays.asList(statuses));
        } else {
            this.statuses.addAll(Arrays.asList(statuses));
        }
    }

    /**
     * <code>addStatuses</code>
     * <p>The add statuses method.</p>
     * @param statuses {@link java.util.Collection} <p>The statuses parameter is <code>Collection</code> type.</p>
     * @see java.util.Collection
     * @see org.springframework.lang.NonNull
     */
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
