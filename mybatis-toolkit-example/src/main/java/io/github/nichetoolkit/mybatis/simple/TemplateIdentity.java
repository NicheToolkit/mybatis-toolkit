package io.github.nichetoolkit.mybatis.simple;

import io.github.nichetoolkit.mybatis.stereotype.column.RestUnionKey;
import io.github.nichetoolkit.mybatis.stereotype.table.RestIdentity;

import java.util.Objects;

@RestIdentity
public class TemplateIdentity extends IdentityModel {

    @RestUnionKey
    private String templatePk2;

    public TemplateIdentity() {
    }

    public TemplateIdentity(String templatePk1, String templatePk2) {
        super(templatePk1);
        this.templatePk2 = templatePk2;
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
        if (!super.equals(o)) return false;
        TemplateIdentity that = (TemplateIdentity) o;
        return Objects.equals(templatePk2, that.templatePk2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), templatePk2);
    }
}
