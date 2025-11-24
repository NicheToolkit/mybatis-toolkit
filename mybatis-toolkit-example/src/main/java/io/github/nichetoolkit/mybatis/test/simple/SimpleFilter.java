package io.github.nichetoolkit.mybatis.test.simple;


import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.RestFilter;
import io.github.nichetoolkit.rice.filter.StateFilter;
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
 * @see io.github.nichetoolkit.rice.filter.StateFilter
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
public class SimpleFilter extends RestFilter implements StateFilter<SimpleState> {

    /**
     * <code>state</code>
     * {@link io.github.nichetoolkit.mybatis.test.simple.SimpleState} <p>The <code>state</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.test.simple.SimpleState
     */
    private SimpleState state;

    /**
     * <code>states</code>
     * {@link java.util.Set} <p>The <code>states</code> field.</p>
     * @see java.util.Set
     */
    private Set<SimpleState> states;

    @Override
    public List<SimpleState> getStates() {
        if (GeneralUtils.isNotEmpty(states)) {
            return new ArrayList<>(states);
        }
        return Collections.emptyList();
    }

    @Override
    public void setStates(@NonNull Collection<SimpleState> states) {
        this.states = new HashSet<>(states);
    }

    /**
     * <code>setStates</code>
     * <p>The set states setter method.</p>
     * @param states {@link io.github.nichetoolkit.mybatis.test.simple.SimpleState} <p>The states parameter is <code>SimpleState</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.test.simple.SimpleState
     * @see org.springframework.lang.NonNull
     */
    public void setStates(@NonNull SimpleState... states) {
        this.states = new HashSet<>(Arrays.asList(states));
    }

    /**
     * <code>toStateSql</code>
     * <p>The to state sql method.</p>
     * @return {@link io.github.nichetoolkit.rice.filter.StateFilter} <p>The to state sql return object is <code>StateFilter</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.rice.filter.StateFilter
     * @see io.github.nichetoolkit.rest.RestException
     */
    public StateFilter<SimpleState> toStateSql() throws RestException {
        return toStateSql("state");
    }
}
