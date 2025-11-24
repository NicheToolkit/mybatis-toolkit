package io.github.nichetoolkit.mybatis.test.template;

import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.DefaultFilter;
import io.github.nichetoolkit.rice.filter.StateFilter;
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
 * @see io.github.nichetoolkit.rice.filter.StateFilter
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
public class TemplateFilter extends DefaultFilter<TemplateIdentity,String> implements StateFilter<TemplateAlertness> {

    /**
     * <code>state</code>
     * {@link io.github.nichetoolkit.mybatis.test.template.TemplateAlertness} <p>The <code>state</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.test.template.TemplateAlertness
     */
    private TemplateAlertness state;

    /**
     * <code>states</code>
     * {@link java.util.Set} <p>The <code>states</code> field.</p>
     * @see java.util.Set
     */
    private Set<TemplateAlertness> states;

    @Override
    public List<TemplateAlertness> getStates() {
        if (GeneralUtils.isNotEmpty(states)) {
            return new ArrayList<>(states);
        }
        return Collections.emptyList();
    }

    @Override
    public void setStates(@NonNull Collection<TemplateAlertness> states) {
        this.states = new HashSet<>(states);
    }

    /**
     * <code>setStates</code>
     * <p>The set states setter method.</p>
     * @param states {@link io.github.nichetoolkit.mybatis.test.template.TemplateAlertness} <p>The states parameter is <code>TemplateAlertness</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.test.template.TemplateAlertness
     * @see org.springframework.lang.NonNull
     */
    public void setStates(@NonNull TemplateAlertness... states) {
        this.states = new HashSet<>(Arrays.asList(states));
    }

    /**
     * <code>addStates</code>
     * <p>The add states method.</p>
     * @param states {@link io.github.nichetoolkit.mybatis.test.template.TemplateAlertness} <p>The states parameter is <code>TemplateAlertness</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.test.template.TemplateAlertness
     * @see org.springframework.lang.NonNull
     */
    public void addStates(@NonNull TemplateAlertness... states) {
        if (GeneralUtils.isEmpty(this.states)) {
            this.states = new HashSet<>(Arrays.asList(states));
        } else {
            this.states.addAll(Arrays.asList(states));
        }
    }

    /**
     * <code>addStates</code>
     * <p>The add states method.</p>
     * @param states {@link java.util.Collection} <p>The states parameter is <code>Collection</code> type.</p>
     * @see java.util.Collection
     * @see org.springframework.lang.NonNull
     */
    public void addStates(@NonNull Collection<TemplateAlertness> states) {
        if (GeneralUtils.isEmpty(this.states)) {
            this.states = new HashSet<>(states);
        } else {
            this.states.addAll(states);
        }
    }

    /**
     * <code>toStateSql</code>
     * <p>The to state sql method.</p>
     * @return {@link io.github.nichetoolkit.rice.filter.StateFilter} <p>The to state sql return object is <code>StateFilter</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.rice.filter.StateFilter
     * @see io.github.nichetoolkit.rest.RestException
     */
    public StateFilter<TemplateAlertness> toStateSql() throws RestException {
        return toStateSql("state.");
    }

}
