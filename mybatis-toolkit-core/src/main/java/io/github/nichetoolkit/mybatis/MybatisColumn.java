package io.github.nichetoolkit.mybatis;

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

/**
 * <code>MybatisColumn</code>
 * <p>The mybatis column class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisProperty
 * @see lombok.Setter
 * @see lombok.Getter
 * @since Jdk1.8
 */
@Setter
@Getter
public class MybatisColumn extends MybatisProperty<MybatisColumn> {
    /**
     * <code>field</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The <code>field</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    protected final MybatisField field;
    /**
     * <code>table</code>
     * {@link io.github.nichetoolkit.mybatis.MybatisTable} <p>The <code>table</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisTable
     */
    protected MybatisTable table;
    /**
     * <code>column</code>
     * {@link java.lang.String} <p>The <code>column</code> field.</p>
     * @see java.lang.String
     */
    protected String column;
    /**
     * <code>comment</code>
     * {@link java.lang.String} <p>The <code>comment</code> field.</p>
     * @see java.lang.String
     */
    protected String comment;
    /**
     * <code>order</code>
     * {@link java.lang.Integer} <p>The <code>order</code> field.</p>
     * @see java.lang.Integer
     */
    protected Integer order;
    /**
     * <code>sortType</code>
     * {@link io.github.nichetoolkit.mybatis.enums.SortType} <p>The <code>sortType</code> field.</p>
     * @see io.github.nichetoolkit.mybatis.enums.SortType
     */
    protected SortType sortType;
    /**
     * <code>priority</code>
     * {@link java.lang.Integer} <p>The <code>priority</code> field.</p>
     * @see java.lang.Integer
     */
    protected Integer priority;

    /**
     * <code>forceInsert</code>
     * <p>The <code>forceInsert</code> field.</p>
     */
    protected boolean forceInsert;
    /**
     * <code>forceInsertValue</code>
     * {@link java.lang.String} <p>The <code>forceInsertValue</code> field.</p>
     * @see java.lang.String
     */
    protected String forceInsertValue;
    /**
     * <code>forceUpdate</code>
     * <p>The <code>forceUpdate</code> field.</p>
     */
    protected boolean forceUpdate;
    /**
     * <code>forceUpdateValue</code>
     * {@link java.lang.String} <p>The <code>forceUpdateValue</code> field.</p>
     * @see java.lang.String
     */
    protected String forceUpdateValue;

    /**
     * <code>jdbcType</code>
     * {@link org.apache.ibatis.type.JdbcType} <p>The <code>jdbcType</code> field.</p>
     * @see org.apache.ibatis.type.JdbcType
     */
    protected JdbcType jdbcType;
    /**
     * <code>typeHandler</code>
     * {@link java.lang.Class} <p>The <code>typeHandler</code> field.</p>
     * @see java.lang.Class
     */
    protected Class<? extends TypeHandler<?>> typeHandler;
    /**
     * <code>numericScale</code>
     * {@link java.lang.String} <p>The <code>numericScale</code> field.</p>
     * @see java.lang.String
     */
    protected String numericScale;

    /**
     * <code>select</code>
     * <p>The <code>select</code> field.</p>
     */
    protected boolean select = true;
    /**
     * <code>insert</code>
     * <p>The <code>insert</code> field.</p>
     */
    protected boolean insert = true;
    /**
     * <code>update</code>
     * <p>The <code>update</code> field.</p>
     */
    protected boolean update = true;

    /**
     * <code>exclude</code>
     * <p>The <code>exclude</code> field.</p>
     */
    protected boolean exclude;

    /**
     * <code>identityKey</code>
     * <p>The <code>identityKey</code> field.</p>
     */
    protected boolean identityKey;
    /**
     * <code>primaryKey</code>
     * <p>The <code>primaryKey</code> field.</p>
     */
    protected boolean primaryKey;
    /**
     * <code>linkKey</code>
     * <p>The <code>linkKey</code> field.</p>
     */
    protected boolean linkKey;

    /**
     * <code>alertKey</code>
     * <p>The <code>alertKey</code> field.</p>
     */
    protected boolean alertKey;
    /**
     * <code>operateKey</code>
     * <p>The <code>operateKey</code> field.</p>
     */
    protected boolean operateKey;
    /**
     * <code>uniqueKey</code>
     * <p>The <code>uniqueKey</code> field.</p>
     */
    protected boolean uniqueKey;
    /**
     * <code>unionKey</code>
     * <p>The <code>unionKey</code> field.</p>
     */
    protected boolean unionKey;
    /**
     * <code>logicKey</code>
     * <p>The <code>logicKey</code> field.</p>
     */
    protected boolean logicKey;

    /**
     * <code>ignored</code>
     * <p>The <code>ignored</code> field.</p>
     */
    protected boolean ignored;

    /**
     * <code>MybatisColumn</code>
     * <p>Instantiates a new mybatis column.</p>
     * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    protected MybatisColumn(MybatisField field) {
        this.field = field;
        this.ignored = field.ignored();
    }

    /**
     * <code>MybatisColumn</code>
     * <p>Instantiates a new mybatis column.</p>
     * @param properties {@link java.util.Map} <p>The properties parameter is <code>Map</code> type.</p>
     * @param field      {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @see java.util.Map
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    protected MybatisColumn(Map<String, String> properties, MybatisField field) {
        super(properties);
        this.field = field;
        this.ignored = field.ignored();
    }

    /**
     * <code>of</code>
     * <p>The of method.</p>
     * @param field {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>The of return object is <code>MybatisColumn</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisField
     */
    public static MybatisColumn of(MybatisField field) {
        return new MybatisColumn(field);
    }

    /**
     * <code>of</code>
     * <p>The of method.</p>
     * @param field      {@link io.github.nichetoolkit.mybatis.MybatisField} <p>The field parameter is <code>MybatisField</code> type.</p>
     * @param properties {@link java.util.Map} <p>The properties parameter is <code>Map</code> type.</p>
     * @return {@link io.github.nichetoolkit.mybatis.MybatisColumn} <p>The of return object is <code>MybatisColumn</code> type.</p>
     * @see io.github.nichetoolkit.mybatis.MybatisField
     * @see java.util.Map
     */
    public static MybatisColumn of(MybatisField field, Map<String, String> properties) {
        if (GeneralUtils.isNotEmpty(properties)) {
            return new MybatisColumn(properties, field);
        } else {
            return new MybatisColumn(field);
        }
    }

    /**
     * <code>isAnyKey</code>
     * <p>The is any key method.</p>
     * @return boolean <p>The is any key return object is <code>boolean</code> type.</p>
     */
    public boolean isAnyKey() {
        return this.identityKey | this.primaryKey | this.linkKey | this.unionKey
                | this.alertKey | this.logicKey | this.operateKey;
    }

    /**
     * <code>isSpecialIdentity</code>
     * <p>The is special identity method.</p>
     * @return boolean <p>The is special identity return object is <code>boolean</code> type.</p>
     */
    public boolean isSpecialIdentity() {
        return this.field.isIdentity();
    }

    /**
     * <code>isSpecialLinkage</code>
     * <p>The is special linkage method.</p>
     * @return boolean <p>The is special linkage return object is <code>boolean</code> type.</p>
     */
    public boolean isSpecialLinkage() {
        return this.field.isLinkage();
    }

    /**
     * <code>isSpecialAlertness</code>
     * <p>The is special alertness method.</p>
     * @return boolean <p>The is special alertness return object is <code>boolean</code> type.</p>
     */
    public boolean isSpecialAlertness() {
        return this.field.isAlertness();
    }

    /**
     * <code>isParentNotEmpty</code>
     * <p>The is parent not empty method.</p>
     * @return boolean <p>The is parent not empty return object is <code>boolean</code> type.</p>
     */
    public boolean isParentNotEmpty() {
        return this.field.isParentNotEmpty();
    }

    /**
     * <code>prefixOfParent</code>
     * <p>The prefix of parent method.</p>
     * @return {@link java.lang.String} <p>The prefix of parent return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String prefixOfParent() {
        return this.field.prefixOfParent();
    }

    /**
     * <code>columnName</code>
     * <p>The column name method.</p>
     * @return {@link java.lang.String} <p>The column name return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnName() {
        return this.column;
    }

    /**
     * <code>fieldName</code>
     * <p>The field name method.</p>
     * @return {@link java.lang.String} <p>The field name return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String fieldName() {
        return this.field.fieldName();
    }

    /**
     * <code>javaType</code>
     * <p>The java type method.</p>
     * @return {@link java.lang.Class} <p>The java type return object is <code>Class</code> type.</p>
     * @see java.lang.Class
     */
    public Class<?> javaType() {
        return this.field.fieldType();
    }

    /**
     * <code>property</code>
     * <p>The property method.</p>
     * @return {@link java.lang.String} <p>The property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String property() {
        return property(SQLConstants.EMPTY);
    }


    /**
     * <code>aliasColumn</code>
     * <p>The alias column method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumn(String alias) {
        if (GeneralUtils.isNotEmpty(alias)) {
            return alias + SQLConstants.PERIOD + this.column;
        } else {
            return this.column;
        }
    }

    /**
     * <code>property</code>
     * <p>The property method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String property(String prefix) {
        return prefix + this.field.fieldName();
    }

    /**
     * <code>variable</code>
     * <p>The variable method.</p>
     * @return {@link java.lang.String} <p>The variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String variable() {
        return variable(SQLConstants.EMPTY);
    }

    /**
     * <code>variable</code>
     * <p>The variable method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String variable(String prefix) {
        if (this.forceInsert) {
            return this.forceInsertValue;
        } else {
            if ((isSpecialIdentity() || isSpecialLinkage() || isSpecialAlertness()) && this.field.isParentNotEmpty()) {
                prefix = prefix + this.field.prefixOfParent() + SQLConstants.PERIOD;
            }
            return signerVariable(prefix);
        }
    }

    /**
     * <code>signerVariable</code>
     * <p>The signer variable method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The signer variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String signerVariable(String prefix) {
        return SQLConstants.SIGNER + SQLConstants.CURLY_LT
                + property(prefix)
                + jdbcTypeVariable().orElse(SQLConstants.EMPTY)
                + typeHandlerVariable().orElse(SQLConstants.EMPTY)
                + numericScaleVariable().orElse(SQLConstants.EMPTY)
                + SQLConstants.CURLY_GT;
    }

    /**
     * <code>dollarVariable</code>
     * <p>The dollar variable method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The dollar variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String dollarVariable(String prefix) {
        return SQLConstants.DOLLAR + SQLConstants.CURLY_LT
                + property(prefix)
                + jdbcTypeVariable().orElse(SQLConstants.EMPTY)
                + typeHandlerVariable().orElse(SQLConstants.EMPTY)
                + numericScaleVariable().orElse(SQLConstants.EMPTY)
                + SQLConstants.CURLY_GT;
    }


    /**
     * <code>property</code>
     * <p>The property method.</p>
     * @param identityType {@link java.lang.Class} <p>The identity type parameter is <code>Class</code> type.</p>
     * @param property     {@link java.lang.String} <p>The property parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The property return object is <code>String</code> type.</p>
     * @see java.lang.Class
     * @see java.lang.String
     */
    public static String property(Class<?> identityType, String property) {
        if (String.class == identityType || Date.class == identityType) {
            return signerProperty(property);
        } else {
            return dollarProperty(property);
        }
    }

    /**
     * <code>signerProperty</code>
     * <p>The signer property method.</p>
     * @param property {@link java.lang.String} <p>The property parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The signer property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static String signerProperty(String property) {
        return SQLConstants.SIGNER + SQLConstants.CURLY_LT
                + property + SQLConstants.CURLY_GT;
    }

    /**
     * <code>dollarProperty</code>
     * <p>The dollar property method.</p>
     * @param property {@link java.lang.String} <p>The property parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The dollar property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static String dollarProperty(String property) {
        return SQLConstants.DOLLAR + SQLConstants.CURLY_LT
                + property + SQLConstants.CURLY_GT;
    }

    /**
     * <code>excluded</code>
     * <p>The excluded method.</p>
     * @param tablename {@link java.lang.String} <p>The tablename parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The excluded return object is <code>String</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.lang.NonNull
     */
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

    /**
     * <code>excluded</code>
     * <p>The excluded method.</p>
     * @return {@link java.lang.String} <p>The excluded return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String excluded() {
        return excluded(this.table.tablename());
    }

    /**
     * <code>keyword</code>
     * <p>The keyword method.</p>
     * @param keyword {@link java.lang.String} <p>The keyword parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The keyword return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String keyword(String keyword) {
        return SQLConstants.COMMA + SQLConstants.BLANK + keyword + SQLConstants.CONTRAST_EQ;
    }

    /**
     * <code>jdbcTypeVariable</code>
     * <p>The jdbc type variable method.</p>
     * @return {@link java.util.Optional} <p>The jdbc type variable return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> jdbcTypeVariable() {
        if (this.jdbcType != null && this.jdbcType != JdbcType.UNDEFINED) {
            return Optional.of(keyword(SQLConstants.JDBC_TYPE) + this.jdbcType);
        }
        return Optional.empty();
    }

    /**
     * <code>typeHandlerVariable</code>
     * <p>The type handler variable method.</p>
     * @return {@link java.util.Optional} <p>The type handler variable return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> typeHandlerVariable() {
        if (this.typeHandler != null && this.typeHandler != UnknownTypeHandler.class) {
            return Optional.of(keyword(SQLConstants.TYPE_HANDLER) + this.typeHandler.getName());
        }
        return Optional.empty();
    }

    /**
     * <code>numericScaleVariable</code>
     * <p>The numeric scale variable method.</p>
     * @return {@link java.util.Optional} <p>The numeric scale variable return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public Optional<String> numericScaleVariable() {
        if (GeneralUtils.isNotEmpty(this.numericScale)) {
            return Optional.of(keyword(SQLConstants.NUMERIC_SCALE) + this.numericScale);
        }
        return Optional.empty();
    }

    /**
     * <code>columnAsProperty</code>
     * <p>The column as property method.</p>
     * @return {@link java.lang.String} <p>The column as property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnAsProperty() {
        return columnAsProperty(SQLConstants.EMPTY);
    }

    /**
     * <code>aliasColumnAsProperty</code>
     * <p>The alias column as property method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column as property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnAsProperty(String alias) {
        return aliasColumnAsProperty(alias, SQLConstants.EMPTY);
    }

    /**
     * <code>columnAsProperty</code>
     * <p>The column as property method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The column as property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnAsProperty(String prefix) {
        return aliasColumnAsProperty(SQLConstants.EMPTY, prefix);
    }

    /**
     * <code>aliasColumnAsProperty</code>
     * <p>The alias column as property method.</p>
     * @param alias  {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column as property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnAsProperty(String alias, String prefix) {
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

    /**
     * <code>columnEqualsDollar</code>
     * <p>The column equals dollar method.</p>
     * @param variable {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The column equals dollar return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnEqualsDollar(String variable) {
        return aliasColumnEqualsVariable(SQLConstants.EMPTY, variable, true);
    }

    /**
     * <code>aliasColumnEqualsDollar</code>
     * <p>The alias column equals dollar method.</p>
     * @param alias    {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @param variable {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column equals dollar return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnEqualsDollar(String alias, String variable) {
        return aliasColumnEqualsVariable(alias, variable, true);
    }

    /**
     * <code>columnEqualsSigner</code>
     * <p>The column equals signer method.</p>
     * @param variable {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The column equals signer return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnEqualsSigner(String variable) {
        return aliasColumnEqualsVariable(SQLConstants.EMPTY, variable, false);
    }

    /**
     * <code>aliasColumnEqualsSigner</code>
     * <p>The alias column equals signer method.</p>
     * @param alias    {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @param variable {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column equals signer return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnEqualsSigner(String alias, String variable) {
        return aliasColumnEqualsVariable(alias, variable, false);
    }

    /**
     * <code>columnEqualsVariable</code>
     * <p>The column equals variable method.</p>
     * @param variable         {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @param isDollarOrSigner boolean <p>The is dollar or signer parameter is <code>boolean</code> type.</p>
     * @return {@link java.lang.String} <p>The column equals variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnEqualsVariable(String variable, boolean isDollarOrSigner) {
        return aliasColumnEqualsVariable(SQLConstants.EMPTY, variable, isDollarOrSigner);
    }

    /**
     * <code>aliasColumnEqualsVariable</code>
     * <p>The alias column equals variable method.</p>
     * @param alias            {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @param variable         {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @param isDollarOrSigner boolean <p>The is dollar or signer parameter is <code>boolean</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column equals variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnEqualsVariable(String alias, String variable, boolean isDollarOrSigner) {
        return aliasColumn(alias) + SQLConstants.BLANK + SQLConstants.CONTRAST_EQ
                + SQLConstants.BLANK + (isDollarOrSigner ? dollarProperty(variable) : signerProperty(variable));
    }

    /**
     * <code>columnNotEqualsDollar</code>
     * <p>The column not equals dollar method.</p>
     * @param variable {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The column not equals dollar return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnNotEqualsDollar(String variable) {
        return aliasColumnEqualsVariable(SQLConstants.EMPTY, variable, true);
    }

    /**
     * <code>aliasNotColumnEqualsDollar</code>
     * <p>The alias not column equals dollar method.</p>
     * @param alias    {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @param variable {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias not column equals dollar return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasNotColumnEqualsDollar(String alias, String variable) {
        return aliasColumnEqualsVariable(alias, variable, true);
    }

    /**
     * <code>columnNotEqualsSigner</code>
     * <p>The column not equals signer method.</p>
     * @param variable {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The column not equals signer return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnNotEqualsSigner(String variable) {
        return aliasColumnEqualsVariable(SQLConstants.EMPTY, variable, false);
    }

    /**
     * <code>aliasColumnNotEqualsSigner</code>
     * <p>The alias column not equals signer method.</p>
     * @param alias    {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @param variable {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column not equals signer return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnNotEqualsSigner(String alias, String variable) {
        return aliasColumnEqualsVariable(alias, variable, false);
    }

    /**
     * <code>columnNotEqualsVariable</code>
     * <p>The column not equals variable method.</p>
     * @param variable         {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @param isDollarOrSigner boolean <p>The is dollar or signer parameter is <code>boolean</code> type.</p>
     * @return {@link java.lang.String} <p>The column not equals variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnNotEqualsVariable(String variable, boolean isDollarOrSigner) {
        return aliasColumnEqualsVariable(SQLConstants.EMPTY, variable, isDollarOrSigner);
    }

    /**
     * <code>aliasColumnNotEqualsVariable</code>
     * <p>The alias column not equals variable method.</p>
     * @param alias            {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @param variable         {@link java.lang.String} <p>The variable parameter is <code>String</code> type.</p>
     * @param isDollarOrSigner boolean <p>The is dollar or signer parameter is <code>boolean</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column not equals variable return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnNotEqualsVariable(String alias, String variable, boolean isDollarOrSigner) {
        return aliasColumn(alias) + SQLConstants.BLANK + SQLConstants.CONTRAST_NEQ
                + SQLConstants.BLANK + (isDollarOrSigner ? dollarProperty(variable) : signerProperty(variable));
    }

    /**
     * <code>columnEqualsProperty</code>
     * <p>The column equals property method.</p>
     * @return {@link java.lang.String} <p>The column equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnEqualsProperty() {
        return aliasColumnEqualsProperty(SQLConstants.EMPTY);
    }

    /**
     * <code>aliasColumnEqualsProperty</code>
     * <p>The alias column equals property method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnEqualsProperty(String alias) {
        return aliasColumnEqualsProperty(alias, SQLConstants.EMPTY);
    }

    /**
     * <code>columnNotEqualsProperty</code>
     * <p>The column not equals property method.</p>
     * @return {@link java.lang.String} <p>The column not equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnNotEqualsProperty() {
        return aliasColumnNotEqualsProperty(SQLConstants.EMPTY);
    }

    /**
     * <code>aliasColumnNotEqualsProperty</code>
     * <p>The alias column not equals property method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column not equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnNotEqualsProperty(String alias) {
        return aliasColumnNotEqualsProperty(alias, SQLConstants.EMPTY);
    }

    /**
     * <code>columnEqualsProperty</code>
     * <p>The column equals property method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The column equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnEqualsProperty(String prefix) {
        return aliasColumnEqualsProperty(SQLConstants.EMPTY, prefix);
    }

    /**
     * <code>aliasColumnEqualsProperty</code>
     * <p>The alias column equals property method.</p>
     * @param alias  {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnEqualsProperty(String alias, String prefix) {
        return aliasColumn(alias) + SQLConstants.BLANK + SQLConstants.CONTRAST_EQ
                + SQLConstants.BLANK + variable(prefix);
    }

    /**
     * <code>columnNotEqualsProperty</code>
     * <p>The column not equals property method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The column not equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String columnNotEqualsProperty(String prefix) {
        return aliasColumnNotEqualsProperty(SQLConstants.EMPTY, prefix);
    }

    /**
     * <code>aliasColumnNotEqualsProperty</code>
     * <p>The alias column not equals property method.</p>
     * @param alias  {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The alias column not equals property return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String aliasColumnNotEqualsProperty(String alias, String prefix) {
        return aliasColumn(alias) + SQLConstants.BLANK + SQLConstants.CONTRAST_NEQ
                + SQLConstants.BLANK + variable(prefix);
    }

    /**
     * <code>notNullTest</code>
     * <p>The not null test method.</p>
     * @return {@link java.lang.String} <p>The not null test return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String notNullTest() {
        return notNullTest(SQLConstants.EMPTY);
    }

    /**
     * <code>notNullTest</code>
     * <p>The not null test method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The not null test return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String notNullTest(String prefix) {
        return property(prefix) + SQLConstants.BLANK + SQLConstants.CONTRAST_NEQ
                + SQLConstants.BLANK + ScriptConstants.NULL;
    }

    /**
     * <code>notEmptyTest</code>
     * <p>The not empty test method.</p>
     * @return {@link java.lang.String} <p>The not empty test return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String notEmptyTest() {
        return notEmptyTest(SQLConstants.EMPTY);
    }

    /**
     * <code>notEmptyTest</code>
     * <p>The not empty test method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The not empty test return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
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
