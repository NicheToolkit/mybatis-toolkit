package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.mybatis.enums.SortType;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;

import static io.github.nichetoolkit.mybatis.MybatisTable.DELIMITER;

@Data
public class MybatisColumn extends MybatisProperty<MybatisColumn> {
    protected final MybatisField field;
    protected MybatisTable table;
    protected String column;
    protected String comment;
    protected Integer order;
    protected SortType sortType;
    protected Integer priority;

    protected boolean forceInsert;
    protected String forceInsertValue;
    protected boolean forceUpdate;
    protected String forceUpdateValue;

    protected JdbcType jdbcType;
    protected Class<? extends TypeHandler<?>> typeHandler;
    protected String numericScale;

    protected boolean select = true;
    protected boolean insert = true;
    protected boolean update = true;

    protected boolean exclude;

    protected boolean identityKey;
    protected boolean primaryKey;
    protected boolean linkKey;

    protected boolean alertKey;
    protected boolean operateKey;
    protected boolean uniqueKey;
    protected boolean unionKey;
    protected boolean logicKey;

    protected boolean ignored;

    protected MybatisColumn(MybatisField field) {
        this.field = field;
        this.ignored = field.ignored();
    }

    protected MybatisColumn(Map<String, String> properties, MybatisField field) {
        super(properties);
        this.field = field;
        this.ignored = field.ignored();
    }

    public static MybatisColumn of(MybatisField field) {
        return new MybatisColumn(field);
    }

    public static MybatisColumn of(MybatisField field, Map<String, String> properties) {
        if (GeneralUtils.isNotEmpty(properties)) {
            return new MybatisColumn(properties, field);
        } else {
            return new MybatisColumn(field);
        }
    }


    public boolean isAnyKey() {
        return this.identityKey | this.primaryKey | this.linkKey | this.unionKey
                | this.alertKey | this.logicKey | this.operateKey;
    }

    public boolean isIdentity() {
        return this.field.isIdentity();
    }

    public String columnName() {
        return this.column;
    }

    public String fieldName() {
        return this.field.fieldName();
    }

    public Class<?> javaType() {
        return this.field.fieldType();
    }

    public String property() {
        return property("");
    }


    public String aliasColumn(String alias) {
        if (GeneralUtils.isNotEmpty(alias)) {
            return alias + "." + this.column;
        } else {
            return this.column;
        }
    }

    public String property(String prefix) {
        return prefix + this.field.fieldName();
    }

    public String variable() {
        return variable("");
    }

    public String variable(String prefix) {
        if (this.forceInsert) {
            return this.forceInsertValue;
        } else {
            return "#{" + property(prefix)
                    + jdbcTypeVariable().orElse("")
                    + typeHandlerVariable().orElse("")
                    + numericScaleVariable().orElse("") + "}";
        }
    }

    public String excluded(@NonNull String tablename) {
        if (this.forceUpdate) {
            return this.columnName() + " = " + this.forceUpdateValue;
        } else {
            return this.columnName() + " = CASE WHEN EXCLUDED." + this.columnName() + " IS NOT NULL THEN EXCLUDED." +
                    this.columnName() + " ELSE " + tablename + "." + this.columnName() + " END";
        }
    }

    public String excluded() {
        return excluded(this.table.tablename());
    }

    public Optional<String> jdbcTypeVariable() {
        if (this.jdbcType != null && this.jdbcType != JdbcType.UNDEFINED) {
            return Optional.of(", jdbcType=" + this.jdbcType);
        }
        return Optional.empty();
    }

    public Optional<String> typeHandlerVariable() {
        if (this.typeHandler != null && this.typeHandler != UnknownTypeHandler.class) {
            return Optional.of(", typeHandler=" + this.typeHandler.getName());
        }
        return Optional.empty();
    }

    public Optional<String> numericScaleVariable() {
        if (GeneralUtils.isNotEmpty(this.numericScale)) {
            return Optional.of(", numericScale=" + this.numericScale);
        }
        return Optional.empty();
    }

    public String columnAsProperty() {
        return columnAsProperty("");
    }

    public String aliasColumnAsProperty(String alias) {
        return aliasColumnAsProperty(alias, "");
    }

    public String columnAsProperty(String prefix) {
        return aliasColumnAsProperty("", prefix);
    }

    public String aliasColumnAsProperty(String alias, String prefix) {
        /* 这里的column 和 property 的比较 应该是需要忽略界定符之后再比较 */
        /* mysql 中 【`order`】 应该认为是 和 field 的 【order】 相同 */
        String column = this.columnName();
        Matcher matcher = DELIMITER.matcher(this.columnName());
        if (matcher.find()) {
            column = matcher.group(1);
        }
        if (!Objects.equals(column, property(prefix))) {
            return aliasColumn(alias) + " AS " + property(prefix);
        }
        return aliasColumn(alias);
    }


    public String columnEqualsKey() {
        return aliasColumnEqualsKey("");
    }

    public String aliasColumnEqualsKey(String alias) {
        return aliasColumn(alias) + " = " + "${key}";
    }

    public String columnEqualsLogic() {
        return aliasColumnEqualsLogic("");
    }

    public String aliasColumnEqualsLogic(String alias) {
        return aliasColumn(alias) + " = " + "${logic}";
    }

    public String columnEqualsOperate() {
        return aliasColumnEqualsOperate("");
    }

    public String aliasColumnEqualsOperate(String alias) {
        return aliasColumn(alias) + " = " + "${operate}";
    }

    public String columnEqualsLink() {
        return aliasColumnEqualsLink("");
    }

    public String aliasColumnEqualsLink(String alias) {
        return aliasColumn(alias) + " = " + "#{linkId}";
    }

    public String columnEqualsProperty() {
        return aliasColumnEqualsProperty("");
    }

    public String aliasColumnEqualsProperty(String alias) {
        return aliasColumnEqualsProperty(alias, "");
    }

    public String columnNotEqualsProperty() {
        return aliasColumnNotEqualsProperty("");
    }


    public String aliasColumnNotEqualsProperty(String alias) {
        return aliasColumnNotEqualsProperty(alias, "");
    }

    public String columnEqualsProperty(String prefix) {
        return aliasColumnEqualsProperty("", prefix);
    }


    public String aliasColumnEqualsProperty(String alias, String prefix) {
        return aliasColumn(alias) + " = " + variable(prefix);
    }

    public String columnNotEqualsProperty(String prefix) {
        return aliasColumnNotEqualsProperty("", prefix);
    }


    public String aliasColumnNotEqualsProperty(String alias, String prefix) {
        return aliasColumn(alias) + " != " + variable(prefix);
    }

    public String notNullTest() {
        return notNullTest("");
    }

    public String notNullTest(String prefix) {
        return property(prefix) + " != null";
    }

    public String notEmptyTest() {
        return notEmptyTest("");
    }

    public String notEmptyTest(String prefix) {
        if (this.field.fieldType() == String.class) {
            return notNullTest(prefix) + " and " + property(prefix) + " != '' ";
        }
        return notNullTest();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MybatisColumn)) return false;
        MybatisColumn that = (MybatisColumn) o;
        return this.columnName().equals(that.columnName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.columnName());
    }

    @Override
    public String toString() {
        return columnEqualsProperty();
    }
}
