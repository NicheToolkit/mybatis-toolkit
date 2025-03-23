package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.fickle.FickleField;
import io.github.nichetoolkit.mybatis.fickle.FickleType;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * <code>MybatisFickle</code>
 * <p>The mybatis fickle class.</p>
 * @see  io.github.nichetoolkit.mybatis.fickle.FickleField
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public class MybatisFickle implements FickleField<Object> {

    /**
     * <code>key</code>
     * {@link java.lang.String} <p>The <code>key</code> field.</p>
     * @see  java.lang.String
     */
    private String key;
    /**
     * <code>type</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisType} <p>The <code>type</code> field.</p>
     * @see  io.github.nichetoolkit.mybatis.MybatisType
     * @see  lombok.Setter
     */
    @Setter
    private FickleType type = MybatisType.OBJECT;

    /**
     * <code>MybatisFickle</code>
     * <p>Instantiates a new mybatis fickle.</p>
     */
    public MybatisFickle() {
    }

    /**
     * <code>MybatisFickle</code>
     * <p>Instantiates a new mybatis fickle.</p>
     * @param key {@link java.lang.String} <p>The key parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     */
    public MybatisFickle(String key) {
        this.key = key;
    }

    @NonNull
    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(@NonNull String key) {
        this.key = key;
    }

    @Override
    public String getAlias() {
        return "";
    }

    @Override
    public String getName() {
        return this.key;
    }

    @NonNull
    @Override
    public FickleType getType() {
        return this.type;
    }

    @Override
    public String getComment() {
        return "";
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MybatisFickle that = (MybatisFickle) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }
}
