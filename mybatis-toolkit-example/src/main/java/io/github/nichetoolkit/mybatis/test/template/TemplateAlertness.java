package io.github.nichetoolkit.mybatis.test.template;

import io.github.nichetoolkit.mybatis.table.RestAlertness;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * <code>TemplateAlertness</code>
 * <p>The template alertness class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.io.Serializable
 * @see lombok.Setter
 * @see lombok.Getter
 * @see io.github.nichetoolkit.mybatis.table.RestAlertness
 * @since Jdk1.8
 */
@Setter
@Getter
@SuperBuilder
@RestAlertness
public class TemplateAlertness implements Serializable {

    /**
     * <code>state1</code>
     * {@link java.lang.Integer} <p>The <code>state1</code> field.</p>
     * @see java.lang.Integer
     */
    private Integer state1;

    /**
     * <code>state2</code>
     * {@link java.lang.Integer} <p>The <code>state2</code> field.</p>
     * @see java.lang.Integer
     */
    private Integer state2;

    /**
     * <code>TemplateAlertness</code>
     * <p>Instantiates a new template alertness.</p>
     */
    public TemplateAlertness() {
    }

    /**
     * <code>TemplateAlertness</code>
     * <p>Instantiates a new template alertness.</p>
     * @param state1 {@link java.lang.Integer} <p>The state 1 parameter is <code>Integer</code> type.</p>
     * @param state2 {@link java.lang.Integer} <p>The state 2 parameter is <code>Integer</code> type.</p>
     * @see java.lang.Integer
     */
    public TemplateAlertness(Integer state1, Integer state2) {
        this.state1 = state1;
        this.state2 = state2;
    }

    /**
     * <code>TemplateAlertness</code>
     * <p>Instantiates a new template alertness.</p>
     * @param state1 {@link TemplateState1} <p>The state 1 parameter is <code>TemplateState1</code> type.</p>
     * @param state2 {@link TemplateState2} <p>The state 2 parameter is <code>TemplateState2</code> type.</p>
     * @see TemplateState1
     * @see TemplateState2
     */
    public TemplateAlertness(TemplateState1 state1, TemplateState2 state2) {
        this.state1 = Optional.ofNullable(state1).map(TemplateState1::getKey).orElse(null);
        this.state2 = Optional.ofNullable(state2).map(TemplateState2::getKey).orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateAlertness that = (TemplateAlertness) o;
        return Objects.equals(state1, that.state1) && Objects.equals(state2, that.state2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state1, state2);
    }
}
