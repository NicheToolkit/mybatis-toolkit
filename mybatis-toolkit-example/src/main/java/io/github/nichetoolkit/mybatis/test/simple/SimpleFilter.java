package io.github.nichetoolkit.mybatis.test.simple;


import io.github.nichetoolkit.rest.RestException;
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


    @Override
    public StatusFilter<SimpleStatus> toStatusSql() throws RestException {
        return toStatusSql("status");
    }

    @Override
    public StatusFilter<SimpleStatus> toStatusSql(@NonNull String alias) throws RestException {
         toStatusSql(SQL_BUILDER,alias);
         return this;
    }
}
