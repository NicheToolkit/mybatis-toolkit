package io.github.nichetoolkit.mybatis.test.template;

import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.DefaultFilter;
import io.github.nichetoolkit.rice.filter.StatusFilter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;

import java.util.*;

/**
 * <code>TemplateFilter</code>
 * <p>The template filter class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultFilter
 * @see io.github.nichetoolkit.rice.filter.StatusFilter
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.experimental.SuperBuilder
 * @see lombok.NoArgsConstructor
 * @since Jdk1.8
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class TemplateFilter extends DefaultFilter<TemplateIdentity,String> implements StatusFilter<TemplateAlertness> {

    /**
     * <code>status</code>
     * {@link io.github.nichetoolkit.mybatis.test.template.TemplateAlertness} <p>The <code>status</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.test.template.TemplateAlertness
     */
    private TemplateAlertness status;

    /**
     * <code>statuses</code>
     * {@link java.util.Set} <p>The <code>statuses</code> field.</p>
     * @see java.util.Set
     */
    private Set<TemplateAlertness> statuses;

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

    /**
     * <code>setStatuses</code>
     * <p>The set statuses setter method.</p>
     * @param statuses {@link io.github.nichetoolkit.mybatis.test.template.TemplateAlertness} <p>The statuses parameter is <code>TemplateAlertness</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.test.template.TemplateAlertness
     * @see org.springframework.lang.NonNull
     */
    public void setStatuses(@NonNull TemplateAlertness... statuses) {
        this.statuses = new HashSet<>(Arrays.asList(statuses));
    }

    /**
     * <code>addStatuses</code>
     * <p>The add statuses method.</p>
     * @param statuses {@link io.github.nichetoolkit.mybatis.test.template.TemplateAlertness} <p>The statuses parameter is <code>TemplateAlertness</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.test.template.TemplateAlertness
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

    /**
     * <code>toStatusSql</code>
     * <p>The to status sql method.</p>
     * @return {@link io.github.nichetoolkit.rice.filter.StatusFilter} <p>The to status sql return object is <code>StatusFilter</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.rice.filter.StatusFilter
     * @see io.github.nichetoolkit.rest.RestException
     */
    public StatusFilter<TemplateAlertness> toStatusSql() throws RestException {
        return toStatusSql("status.");
    }

}
