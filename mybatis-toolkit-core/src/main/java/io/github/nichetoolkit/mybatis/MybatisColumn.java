package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.util.GeneralUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;

import static io.github.nichetoolkit.mybatis.MybatisTable.DELIMITER;

/**
 * <p>MybatisColumn</p>
 * 实体中字段和列的对应关系接口
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Data
@Accessors(fluent = true)
public class MybatisColumn extends MybatisProperty<MybatisColumn> {
    /** 实体类字段 */
    protected final MybatisField field;
    /** 所在实体类 */
    protected MybatisTable table;
    /** 列名 */
    protected String column;
    /** 排序方式 */
    protected String orderBy;
    /** 排序的优先级，数值越小优先级越高 */
    protected int priority;
    /** 是否查询字段 */
    protected boolean selectable = true;
    /** 是否插入字段 */
    protected boolean insertable = true;
    /** 是否更新字段 */
    protected boolean updatable = true;
    /** jdbc类型 */
    protected JdbcType jdbcType;
    /** 类型处理器 */
    protected Class<? extends TypeHandler> typeHandler;
    /** 精度 */
    protected String numericScale;

    protected MybatisColumn(MybatisField mybatisField) {
        this.field = mybatisField;
    }

    public static MybatisColumn of(MybatisField field) {
        return new MybatisColumn(field);
    }

    /** Java 类型 */
    public Class<?> javaType() {
        return field().getType();
    }

    /**
     * 属性名
     */
    public String property() {
        return property("");
    }

    /**
     * 带指定前缀的属性名
     * @param prefix 指定前缀，需要自己提供"."
     */
    public String property(String prefix) {
        return prefix + field().getName();
    }

    /**
     * 返回 xml 变量形式 #{property}
     */
    public String variables() {
        return variables("");
    }

    /**
     * 返回带前缀的 xml 变量形式 #{prefix property}
     * @param prefix 指定前缀，需要自己提供"."
     */
    public String variables(String prefix) {
        return "#{" + property(prefix)
                + jdbcTypeVariables().orElse("")
                + typeHandlerVariables().orElse("")
                + numericScaleVariables().orElse("") + "}";
    }

    /**
     * 数据库类型 {, jdbcType=VARCHAR}
     */
    public Optional<String> jdbcTypeVariables() {
        if (this.jdbcType != null && this.jdbcType != JdbcType.UNDEFINED) {
            return Optional.of(", jdbcType=" + jdbcType);
        }
        return Optional.empty();
    }

    /**
     * 类型处理器 {, typeHandler=XXTypeHandler}
     */
    public Optional<String> typeHandlerVariables() {
        if (this.typeHandler != null && this.typeHandler != UnknownTypeHandler.class) {
            return Optional.of(", typeHandler=" + typeHandler.getName());
        }
        return Optional.empty();
    }

    /**
     * 小数位数 {, numericScale=2}
     */
    public Optional<String> numericScaleVariables() {
        if (GeneralUtils.isNotEmpty(this.numericScale)) {
            return Optional.of(", numericScale=" + numericScale);
        }
        return Optional.empty();
    }

    /**
     * 返回 column AS property 形式的字符串, 当 column 和 property 相同时没有别名
     */
    public String columnAsProperty() {
        return columnAsProperty("");
    }

    /**
     * 返回 column AS prefix property 形式的字符串
     * @param prefix 指定前缀，需要自己提供"."
     */
    public String columnAsProperty(String prefix) {
        /** 这里的column 和 property 的比较 应该是需要忽略界定符之后再比较 */
        /** mysql 中 【`order`】 应该认为是 和 field 的 【order】 相同 */
        String column = this.column;
        Matcher matcher = DELIMITER.matcher(this.column);
        if (matcher.find()) {
            column = matcher.group(1);
        }
        if (!Objects.equals(column, property(prefix))) {
            return this.column + " AS " + property(prefix);
        }
        return this.column;
    }

    /**
     * 返回 column = #{property} 形式的字符串
     */
    public String columnEqualsProperty() {
        return columnEqualsProperty("");
    }

    /**
     * 返回带前缀的 column = #{prefix property} 形式的字符串
     * @param prefix 指定前缀，需要自己提供"."
     */
    public String columnEqualsProperty(String prefix) {
        return this.column + " = " + variables(prefix);
    }

    /**
     * 返回 property != null 形式的字符串
     */
    public String notNullTest() {
        return notNullTest("");
    }

    /**
     * 返回带前缀的  prefix property != null 形式的字符串
     * @param prefix 指定前缀，需要自己提供"."
     */
    public String notNullTest(String prefix) {
        return property(prefix) + " != null";
    }

    /**
     * 当字段类型为 String 时，返回 property != null and property != '' 形式的字符串.
     * 其他类型时和 {@link #notNullTest()} 方法一样.
     */
    public String notEmptyTest() {
        return notEmptyTest("");
    }

    /**
     * 当字段类型为 String 时，返回 prefixproperty != null and prefixproperty != '' 形式的字符串.
     * 其他类型时和 {@link #notNullTest()} 方法一样.
     * @param prefix 指定前缀，需要自己提供"."
     */
    public String notEmptyTest(String prefix) {
        if (this.field.getType() == String.class) {
            return notNullTest(prefix) + " and " + property(prefix) + " != '' ";
        }
        return notNullTest();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MybatisColumn)) return false;
        MybatisColumn that = (MybatisColumn) o;
        return this.column.equals(that.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.column);
    }

    @Override
    public String toString() {
        return columnEqualsProperty();
    }
}
