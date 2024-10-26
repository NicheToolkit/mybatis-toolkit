package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.mybatis.enums.TemplateStatus1;
import io.github.nichetoolkit.mybatis.enums.TemplateStatus2;
import io.github.nichetoolkit.mybatis.stereotype.table.RestAlertness;
import io.github.nichetoolkit.mybatis.stereotype.table.RestLinkage;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

@RestAlertness
public class TemplateAlertness implements Serializable {

    private Integer status1;

    private Integer status2;

    public TemplateAlertness() {
    }

    public TemplateAlertness(Integer status1, Integer status2) {
        this.status1 = status1;
        this.status2 = status2;
    }

    public TemplateAlertness(TemplateStatus1 status1, TemplateStatus2 status2) {
        this.status1 = Optional.ofNullable(status1).map(TemplateStatus1::getKey).orElse(null);
        this.status2 = Optional.ofNullable(status2).map(TemplateStatus2::getKey).orElse(null);
    }

    public Integer getStatus1() {
        return status1;
    }

    public void setStatus1(Integer status1) {
        this.status1 = status1;
    }

    public Integer getStatus2() {
        return status2;
    }

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
