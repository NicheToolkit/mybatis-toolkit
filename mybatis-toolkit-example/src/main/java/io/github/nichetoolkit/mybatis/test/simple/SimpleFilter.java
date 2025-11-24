package io.github.nichetoolkit.mybatis.test.simple;


import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.RestFilter;
import io.github.nichetoolkit.rice.filter.StatusFilter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;

import java.util.*;

/**
 * <code>SimpleFilter</code>
 * <p>The simple filter class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.RestFilter
 * @see io.github.nichetoolkit.rice.filter.StatusFilter
 * @see lombok.Setter
 * @see lombok.Getter
 * @see lombok.experimental.SuperBuilder
 * @see lombok.NoArgsConstructor
 * @since Jdk1.8
 */
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
public class SimpleFilter extends RestFilter implements StatusFilter<SimpleStatus> {

    /**
     * <code>status</code>
     * {@link io.github.nichetoolkit.mybatis.test.simple.SimpleStatus} <p>The <code>status</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.test.simple.SimpleStatus
     */
    private SimpleStatus status;

    /**
     * <code>statuses</code>
     * {@link java.util.Set} <p>The <code>statuses</code> field.</p>
     * @see java.util.Set
     */
    private Set<SimpleStatus> statuses;

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

    /**
     * <code>setStatuses</code>
     * <p>The set statuses setter method.</p>
     * @param statuses {@link io.github.nichetoolkit.mybatis.test.simple.SimpleStatus} <p>The statuses parameter is <code>SimpleStatus</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.test.simple.SimpleStatus
     * @see org.springframework.lang.NonNull
     */
    public void setStatuses(@NonNull SimpleStatus... statuses) {
        this.statuses = new HashSet<>(Arrays.asList(statuses));
    }

    /**
     * <code>toStatusSql</code>
     * <p>The to status sql method.</p>
     * @return {@link io.github.nichetoolkit.rice.filter.StatusFilter} <p>The to status sql return object is <code>StatusFilter</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.rice.filter.StatusFilter
     * @see io.github.nichetoolkit.rest.RestException
     */
    public StatusFilter<SimpleStatus> toStatusSql() throws RestException {
        return toStatusSql("status");
    }
}
