package io.github.nichetoolkit.mybatis;

import io.github.nichetoolkit.mybatis.consts.EntityConstants;
import io.github.nichetoolkit.mybatis.consts.SQLConstants;
import io.github.nichetoolkit.mybatis.consts.ScriptConstants;
import io.github.nichetoolkit.mybatis.enums.SortType;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;

import static io.github.nichetoolkit.mybatis.MybatisTable.DELIMITER;

@Setter
@Getter
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

    public boolean isSpecialIdentity() {
        return this.field.isSpecialIdentity();
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
        return property(SQLConstants.EMPTY);
    }


    public String aliasColumn(String alias) {
        if (GeneralUtils.isNotEmpty(alias)) {
            return alias + SQLConstants.PERIOD + this.column;
        } else {
            return this.column;
        }
    }

    public String property(String prefix) {
        return prefix + this.field.fieldName();
    }

    public String variable() {
        return variable(SQLConstants.EMPTY);
    }

    public String variable(String prefix) {
        if (this.forceInsert) {
            return this.forceInsertValue;
        } else {
            Class<?> fieldType = this.field.fieldType();
            if (String.class == fieldType || Date.class == fieldType) {
                return signerVariable(prefix);
            } else {
                return dollarVariable(prefix);
            }
        }
    }

    public String signerVariable(String prefix) {
        return SQLConstants.SIGNER + SQLConstants.CURLY_LT
                + property(prefix)
                + jdbcTypeVariable().orElse(SQLConstants.EMPTY)
                + typeHandlerVariable().orElse(SQLConstants.EMPTY)
                + numericScaleVariable().orElse(SQLConstants.EMPTY)
                + SQLConstants.CURLY_GT;
    }

    public String dollarVariable(String prefix) {
        return SQLConstants.DOLLAR + SQLConstants.CURLY_LT
                + property(prefix)
                + jdbcTypeVariable().orElse(SQLConstants.EMPTY)
                + typeHandlerVariable().orElse(SQLConstants.EMPTY)
                + numericScaleVariable().orElse(SQLConstants.EMPTY)
                + SQLConstants.CURLY_GT;
    }


    public static String property(Class<?> identityType, String property) {
        if (String.class == identityType || Date.class == identityType) {
            return signerProperty(property);
        } else {
            return dollarProperty(property);
        }
    }

    public static String signerProperty(String property) {
        return SQLConstants.SIGNER + SQLConstants.CURLY_LT
                + property + SQLConstants.CURLY_GT;
    }

    public static String dollarProperty(String property) {
        return SQLConstants.DOLLAR + SQLConstants.CURLY_LT
                + property + SQLConstants.CURLY_GT;
    }

    public String excluded(@NonNull String tablename) {
        if (this.forceUpdate) {
            return this.columnName() + SQLConstants.BLANK + SQLConstants.CONTRAST_EQ
                    + SQLConstants.BLANK + this.forceUpdateValue;
        } else {
            return this.columnName() + SQLConstants.BLANK + SQLConstants.CONTRAST_EQ + SQLConstants.BLANK + SQLConstants.CASE
                    + SQLConstants.BLANK + SQLConstants.WHEN + SQLConstants.BLANK + SQLConstants.EXCLUDED + SQLConstants.PERIOD
                    + this.columnName() + SQLConstants.BLANK + SQLConstants.IS_NOT_NULL + SQLConstants.BLANK + SQLConstants.THEN
                    + SQLConstants.BLANK + SQLConstants.EXCLUDED + SQLConstants.PERIOD + this.columnName() + SQLConstants.BLANK
                    + SQLConstants.ELSE + SQLConstants.BLANK + tablename + SQLConstants.PERIOD + this.columnName() + SQLConstants.BLANK + SQLConstants.END;
        }
    }

    public String excluded() {
        return excluded(this.table.tablename());
    }

    public String keyword(String keyword) {
        return SQLConstants.COMMA + SQLConstants.BLANK + keyword + SQLConstants.CONTRAST_EQ;
    }

    public Optional<String> jdbcTypeVariable() {
        if (this.jdbcType != null && this.jdbcType != JdbcType.UNDEFINED) {
            return Optional.of(keyword(SQLConstants.JDBC_TYPE) + this.jdbcType);
        }
        return Optional.empty();
    }

    public Optional<String> typeHandlerVariable() {
        if (this.typeHandler != null && this.typeHandler != UnknownTypeHandler.class) {
            return Optional.of(keyword(SQLConstants.TYPE_HANDLER) + this.typeHandler.getName());
        }
        return Optional.empty();
    }

    public Optional<String> numericScaleVariable() {
        if (GeneralUtils.isNotEmpty(this.numericScale)) {
            return Optional.of(keyword(SQLConstants.NUMERIC_SCALE) + this.numericScale);
        }
        return Optional.empty();
    }

    public String columnAsProperty() {
        return columnAsProperty(SQLConstants.EMPTY);
    }

    public String aliasColumnAsProperty(String alias) {
        return aliasColumnAsProperty(alias, SQLConstants.EMPTY);
    }

    public String columnAsProperty(String prefix) {
        return aliasColumnAsProperty(SQLConstants.EMPTY, prefix);
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
            return aliasColumn(alias) + SQLConstants.BLANK + SQLConstants.AS
                    + SQLConstants.BLANK + property(prefix);
        }
        return aliasColumn(alias);
    }


    public String columnEqualsKey() {
        return aliasColumnEqualsKey(SQLConstants.EMPTY);
    }

    public String aliasColumnEqualsKey(String alias) {
        return aliasColumn(alias) + SQLConstants.BLANK + SQLConstants.CONTRAST_EQ
                + SQLConstants.BLANK + dollarProperty(EntityConstants.KEY);
    }

    public String columnEqualsLogic() {
        return aliasColumnEqualsLogic(SQLConstants.EMPTY);
    }

    public String aliasColumnEqualsLogic(String alias) {
        return aliasColumn(alias) + SQLConstants.BLANK + SQLConstants.CONTRAST_EQ
                + SQLConstants.BLANK + dollarProperty(EntityConstants.LOGIC);
    }

    public String columnEqualsOperate() {
        return aliasColumnEqualsOperate(SQLConstants.EMPTY);
    }

    public String aliasColumnEqualsOperate(String alias) {
        return aliasColumn(alias) + SQLConstants.BLANK + SQLConstants.CONTRAST_EQ
                + SQLConstants.BLANK + dollarProperty(EntityConstants.OPERATE);
    }

    public String columnEqualsLink() {
        return aliasColumnEqualsLink(SQLConstants.EMPTY);
    }

    public String aliasColumnEqualsLink(String alias) {
        return aliasColumn(alias) + SQLConstants.BLANK + SQLConstants.CONTRAST_EQ
                + SQLConstants.BLANK + signerProperty(EntityConstants.LINK_ID);
    }

    public String columnEqualsProperty() {
        return aliasColumnEqualsProperty(SQLConstants.EMPTY);
    }

    public String aliasColumnEqualsProperty(String alias) {
        return aliasColumnEqualsProperty(alias, SQLConstants.EMPTY);
    }

    public String columnNotEqualsProperty() {
        return aliasColumnNotEqualsProperty(SQLConstants.EMPTY);
    }


    public String aliasColumnNotEqualsProperty(String alias) {
        return aliasColumnNotEqualsProperty(alias, SQLConstants.EMPTY);
    }

    public String columnEqualsProperty(String prefix) {
        return aliasColumnEqualsProperty(SQLConstants.EMPTY, prefix);
    }

    public String aliasColumnEqualsProperty(String alias, String prefix) {
        return aliasColumn(alias) + SQLConstants.BLANK + SQLConstants.CONTRAST_EQ
                + SQLConstants.BLANK + variable(prefix);
    }

    public String columnNotEqualsProperty(String prefix) {
        return aliasColumnNotEqualsProperty(SQLConstants.EMPTY, prefix);
    }


    public String aliasColumnNotEqualsProperty(String alias, String prefix) {
        return aliasColumn(alias) + SQLConstants.BLANK + SQLConstants.CONTRAST_NEQ
                + SQLConstants.BLANK + variable(prefix);
    }

    public String notNullTest() {
        return notNullTest(SQLConstants.EMPTY);
    }

    public String notNullTest(String prefix) {
        return property(prefix) + SQLConstants.BLANK + SQLConstants.CONTRAST_NEQ
                + SQLConstants.BLANK + ScriptConstants.NULL;
    }

    public String notEmptyTest() {
        return notEmptyTest(SQLConstants.EMPTY);
    }

    public String notEmptyTest(String prefix) {
        if (this.field.fieldType() == String.class || this.field.fieldType() == Date.class) {
            return notNullTest(prefix) + SQLConstants.BLANK + ScriptConstants.AND
                    + SQLConstants.BLANK + property(prefix) + SQLConstants.BLANK + SQLConstants.CONTRAST_NEQ
                    + SQLConstants.BLANK + SQLConstants.EMPTY;
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
