package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.mybatis.enums.TemplateStatus1;
import io.github.nichetoolkit.mybatis.enums.TemplateStatus2;
import io.github.nichetoolkit.rice.table.RestAlertness;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * <code>TemplateAlertness</code>
 * <p>The template alertness class.</p>
 * @see  java.io.Serializable
 * @see  io.github.nichetoolkit.rice.table.RestAlertness
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
@RestAlertness
public class TemplateAlertness implements Serializable {

    /**
     * <code>status1</code>
     * {@link java.lang.Integer} <p>The <code>status1</code> field.</p>
     * @see  java.lang.Integer
     */
    private Integer status1;

    /**
     * <code>status2</code>
     * {@link java.lang.Integer} <p>The <code>status2</code> field.</p>
     * @see  java.lang.Integer
     */
    private Integer status2;

    /**
     * <code>TemplateAlertness</code>
     * <p>Instantiates a new template alertness.</p>
     */
    public TemplateAlertness() {
    }

    /**
     * <code>TemplateAlertness</code>
     * <p>Instantiates a new template alertness.</p>
     * @param status1 {@link java.lang.Integer} <p>The status 1 parameter is <code>Integer</code> type.</p>
     * @param status2 {@link java.lang.Integer} <p>The status 2 parameter is <code>Integer</code> type.</p>
     * @see  java.lang.Integer
     */
    public TemplateAlertness(Integer status1, Integer status2) {
        this.status1 = status1;
        this.status2 = status2;
    }

    /**
     * <code>TemplateAlertness</code>
     * <p>Instantiates a new template alertness.</p>
     * @param status1 {@link io.github.nichetoolkit.mybatis.enums.TemplateStatus1} <p>The status 1 parameter is <code>TemplateStatus1</code> type.</p>
     * @param status2 {@link io.github.nichetoolkit.mybatis.enums.TemplateStatus2} <p>The status 2 parameter is <code>TemplateStatus2</code> type.</p>
     * @see  io.github.nichetoolkit.mybatis.enums.TemplateStatus1
     * @see  io.github.nichetoolkit.mybatis.enums.TemplateStatus2
     */
    public TemplateAlertness(TemplateStatus1 status1, TemplateStatus2 status2) {
        this.status1 = Optional.ofNullable(status1).map(TemplateStatus1::getKey).orElse(null);
        this.status2 = Optional.ofNullable(status2).map(TemplateStatus2::getKey).orElse(null);
    }

    /**
     * <code>getStatus1</code>
     * <p>The get status 1 getter method.</p>
     * @return  {@link java.lang.Integer} <p>The get status 1 return object is <code>Integer</code> type.</p>
     * @see  java.lang.Integer
     */
    public Integer getStatus1() {
        return status1;
    }

    /**
     * <code>setStatus1</code>
     * <p>The set status 1 setter method.</p>
     * @param status1 {@link java.lang.Integer} <p>The status 1 parameter is <code>Integer</code> type.</p>
     * @see  java.lang.Integer
     */
    public void setStatus1(Integer status1) {
        this.status1 = status1;
    }

    /**
     * <code>getStatus2</code>
     * <p>The get status 2 getter method.</p>
     * @return  {@link java.lang.Integer} <p>The get status 2 return object is <code>Integer</code> type.</p>
     * @see  java.lang.Integer
     */
    public Integer getStatus2() {
        return status2;
    }

    /**
     * <code>setStatus2</code>
     * <p>The set status 2 setter method.</p>
     * @param status2 {@link java.lang.Integer} <p>The status 2 parameter is <code>Integer</code> type.</p>
     * @see  java.lang.Integer
     */
    public void setStatus2(Integer status2) {
        this.status2 = status2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateAlertness that = (TemplateAlertness) o;
        return Objects.equals(status1, that.status1) && Objects.equals(status2, that.status2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status1, status2);
    }
}
