package io.github.nichetoolkit.mybatis.simple;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>TemplateKey</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Data
public class TemplateKey implements Serializable {
    private String templatePk1;

    private String templatePk2;

    public TemplateKey() {
    }

    public TemplateKey(String templatePk1, String templatePk2) {
        this.templatePk1 = templatePk1;
        this.templatePk2 = templatePk2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateKey that = (TemplateKey) o;
        return Objects.equals(templatePk1, that.templatePk1) &&
                Objects.equals(templatePk2, that.templatePk2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(templatePk1, templatePk2);
    }
}
