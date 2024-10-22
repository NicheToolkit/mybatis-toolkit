package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.mybatis.stereotype.column.RestUnionKey;
import io.github.nichetoolkit.mybatis.stereotype.table.RestIdentity;

import java.io.Serializable;
import java.util.Objects;

@RestIdentity
public class TemplateIdentity implements Serializable {
    @RestUnionKey
    private String templatePk1;
    @RestUnionKey
    private String templatePk2;

    public TemplateIdentity() {
    }

    public TemplateIdentity(String templatePk1, String templatePk2) {
        this.templatePk1 = templatePk1;
        this.templatePk2 = templatePk2;
    }

    public String getTemplatePk1() {
        return templatePk1;
    }

    public void setTemplatePk1(String templatePk1) {
        this.templatePk1 = templatePk1;
    }

    public String getTemplatePk2() {
        return templatePk2;
    }

    public void setTemplatePk2(String templatePk2) {
        this.templatePk2 = templatePk2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateIdentity that = (TemplateIdentity) o;
        return Objects.equals(templatePk1, that.templatePk1) && Objects.equals(templatePk2, that.templatePk2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(templatePk1, templatePk2);
    }
}
