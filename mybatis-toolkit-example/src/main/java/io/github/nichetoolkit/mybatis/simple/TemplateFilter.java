package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.DefaultFilter;
import io.github.nichetoolkit.rice.filter.StatusFilter;
import org.springframework.lang.NonNull;

import java.util.*;

/**
 * <code>TemplateFilter</code>
 * <p>The template filter class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultFilter
 * @see io.github.nichetoolkit.rice.filter.StatusFilter
 * @since Jdk1.8
 */
public class TemplateFilter extends DefaultFilter<TemplateIdentity,String> implements StatusFilter<TemplateAlertness> {

    /**
     * <code>status</code>
     * {@link io.github.nichetoolkit.mybatis.simple.TemplateAlertness} <p>The <code>status</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.simple.TemplateAlertness
     */
    private TemplateAlertness status;

    /**
     * <code>statuses</code>
     * {@link java.util.Set} <p>The <code>statuses</code> field.</p>
     * @see java.util.Set
     */
    private Set<TemplateAlertness> statuses;

    /**
     * <code>TemplateFilter</code>
     * <p>Instantiates a new template filter.</p>
     */
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

    /**
     * <code>addStatuses</code>
     * <p>The add statuses method.</p>
     * @param statuses {@link io.github.nichetoolkit.mybatis.simple.TemplateAlertness} <p>The statuses parameter is <code>TemplateAlertness</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.simple.TemplateAlertness
     * @see org.springframework.lang.NonNull
     */
    public void addStatuses(@NonNull TemplateAlertness... statuses) {
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
